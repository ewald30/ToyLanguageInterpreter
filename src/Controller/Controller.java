package Controller;

import Model.ADTs.ADTList;
import Model.ADTs.ADTListInterface;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.*;
import Model.Statements.StatementInterface;
import Model.Values.ReferenceValue;
import Model.Values.ValueInterface;
import Repository.RepositoryInterface;
import Model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {
    RepositoryInterface repository;
    StringBuilder allSteptsStringRepresentation;
    ExecutorService executor;

    public void SetRepositoryFile(String filePath){
        //  Setter for the log file stored in repository
        repository.setLogFilePath(filePath);
    }

    public String GetRepositoryFile(){
        //  Getter for the log file stored in repository
        return repository.getLogFilePath();
    }

    public Controller(RepositoryInterface repository) {
        //  Constructor of the controller
        this.repository = repository;
        this.allSteptsStringRepresentation = new StringBuilder();
    }

    public void addProgram(ProgramState programState){
        //  Adds a new program state to the repository
        this.repository.addProgramState(programState);
        programState.getExecutionStack().push(programState.getOriginalProgram());
    }

    public String getOutput() throws ListException {
        //  Returns a string representation of the output
        String result = "         --------------------\n";
        //result = result+ "             Output: [" + this.repository.getCurrentProgramState().getOutput().toString() + "]\n";
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

//    public ProgramState singleStepExecution(ProgramState programState) throws StackException, StatementException, DictionaryException, EvaluationException {
//        /*  Performs the execution of a single statement
//                Throws: StackException if Execution state is empty
//                Return: execution of the current statement
//        */
//        ADTStackInterface<StatementInterface> executionStack = programState.getExecutionStack();
//
//        if (executionStack.isEmpty())
//            throw  new StackException("Execution Stack is empty!");
//
//        StatementInterface statement = executionStack.pop();
//        return statement.execute(programState);
//    }


    void singleStepForAllPrograms(List<ProgramState> programs) throws InterruptedException {
        /*  Executes a single step for all the
                Steps:  -   Log the programs to a log file
                        -   get the list of callables
                        -   execute one step of each program and update the program list
                        -   update the repo
                Throws: -   Interrupted Exception
                Return: None
        */


        //  Log the program states in the log file
        programs.forEach(p -> {
            try {
                repository.logProgramState(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });




        //  Construct the list of callables
        List<Callable<ProgramState>> callables = programs.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> { return p.oneStep();}))
                .collect(Collectors.toList());

        //  Execute the programs and update the list
        List<ProgramState> programsUpdated = executor.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());;
                    } catch (ExecutionException e) {
                        System.out.println(e.getMessage());;
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
        programs.addAll(programsUpdated);

        //  Log the new programs into a file
        programs.forEach(p-> {
            try {
                repository.logProgramState(p);
                p.getHeap().setContent(GarbageCollector(
                        getAddrFromSymTable(p.getSymbolTable().getContent().values()),
                        getAddrFromHeap(p.getHeap().getContent().values()),
                        p.getHeap().getContent()
                ));
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        //  Update the repository too
        repository.setProgramStates((ArrayList<ProgramState>)programs);

    }


    public void allStepsExecution() throws MyException, InterruptedException {

        executor = Executors.newFixedThreadPool(2);
        ArrayList<ProgramState> programs = (ArrayList<ProgramState>)removeCompletedPrograms(repository.getProgramStates());


        while (programs.size() > 0){
            //this.addStepToOutput(newState);


            //  Add here the garbage collector
            singleStepForAllPrograms(programs);
            programs = (ArrayList<ProgramState>)removeCompletedPrograms(programs);
        }
        executor.shutdownNow();
        repository.setProgramStates(programs);

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

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programList){
        /*  Returns the list of programs that are not completed yet
                Steps:  -   Filter the not completed programs
                        -   Collect them into a list
                Throws: None
                Return: The list of programs that are not completed
        */
        return programList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());


    }
}
