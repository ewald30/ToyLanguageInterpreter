package Controller;

import Model.ADTs.ADTStackInterface;
import Model.Exceptions.*;
import Model.Statements.StatementInterface;
import Model.Values.ReferenceValue;
import Model.Values.ValueInterface;
import Repository.RepositoryInterface;
import Model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Controller {
    RepositoryInterface repository;
    StringBuilder allSteptsStringRepresentation;

    public Controller(RepositoryInterface repository) {
        //  Constructor of the controller
        this.repository = repository;
        this.allSteptsStringRepresentation = new StringBuilder();
    }

    public void addProgram(ProgramState programState){
        //  Adds a new program state to the repository
        this.repository.addProgramState(programState);
    }

    public String getOutput() throws ListException {
        //  Returns a string representation of the output
        String result = "         --------------------\n";
        result = result+ "             Output: [" + this.repository.getCurrentProgramState().getOutput().toString() + "]\n";
        result = result+"         --------------------\n";
        return result;
    }

    public String getAllSteps(){
        //  Returns a string representation of the all executed steps
        return this.allSteptsStringRepresentation.toString();
    }

    public void addStepToOutput(ProgramState state){
        //  Adds a new step to the all stepts string representation
        this.allSteptsStringRepresentation.append("\n/************************ Next Step ***********************/\n");
        this.allSteptsStringRepresentation.append(state.toString());
        this.allSteptsStringRepresentation.append("**************************************************************\n\n\n\n\n\n");

    }

    public ProgramState singleStepExecution(ProgramState programState) throws StackException, StatementException, DictionaryException, EvaluationException {
        /*  Performs the execution of a single statement
                Throws: StackException if Execution state is empty
                Return: execution of the current statement
        */
        ADTStackInterface<StatementInterface> executionStack = programState.getExecutionStack();

        if (executionStack.isEmpty())
            throw  new StackException("Execution Stack is empty!");

        StatementInterface statement = executionStack.pop();
        return statement.execute(programState);
    }


    public void allStepsExecution() throws ListException, EvaluationException, StatementException, StackException, DictionaryException {
        /*  Executes all steps of the program
                Throws: None
                Return: None
        */
        ProgramState programState = this.repository.getCurrentProgramState();
        repository.logProgramState();

        
        programState.getExecutionStack().push(programState.getOriginalProgram());

        while(!programState.getExecutionStack().isEmpty()){
            ProgramState newState = this.singleStepExecution(programState);
            getAddrFromHeap(newState.getHeap().getContent().values());
            //this.addStepToOutput(newState);
            repository.logProgramState();
            System.out.println("\nSYm"+ newState.getSymbolTable().getContent().values());
            System.out.println("\nHeap"+newState.getHeap().getContent().values());
            newState.getHeap().setContent(GarbageCollector(
                    getAddrFromSymTable(newState.getSymbolTable().getContent().values()),   //  Get the addresses from the symbol table
                    getAddrFromHeap(newState.getHeap().getContent().values()),              // Get the addresses from the heap
                    newState.getHeap().getContent()));
            repository.logProgramState();

        }
    }


    Map<Integer,ValueInterface> GarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddresses, Map<Integer,ValueInterface> heap){
        /*  Returns a new map used for the heap
                Steps:  -   creates a new collection from the addresses that are used in the heap OR in the symbol table
                        -   converts the collection into a map that will be the new heap
                Throws: None
                Return: A new map that will be the new heap
        */
        Map<Integer, ValueInterface> collection = heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return collection;
    }

    List<Integer> getAddrFromSymTable(Collection<ValueInterface> symTableValues){
        /*  Returns the addresses from the symbol table
                Steps:  -   Filters values from the symbol table that are of type reference value
                        -   Adds the address of that value to a new collection
                Throws: None
                Return: a list of integers containing all the addresses from the symbol table
        */
        List<Integer> symbolTableAddresses = symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> { ReferenceValue v1 = (ReferenceValue) v;return v1.getAddress(); })
                .collect(Collectors.toList());
        return symbolTableAddresses;
    }

    List<Integer> getAddrFromHeap(Collection<ValueInterface> heap){
        /*  Returns the addresses from the heap
                Steps:  -   Filters values from the heap that are of type reference value
                        -   Adds the address of that value to a new collection
                Throws: None
                Return: a list of integers containing all the addresses from the heap
        */
        List<Integer> heapAddresses = heap.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> { ReferenceValue v1 = (ReferenceValue) v;return v1.getAddress(); })
                .collect(Collectors.toList());
        return heapAddresses;

    }
}
