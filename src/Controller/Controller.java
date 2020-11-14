package Controller;

import Model.ADTs.ADTStackInterface;
import Model.Exceptions.*;
import Model.Statements.StatementInterface;
import Repository.RepositoryInterface;
import Model.*;



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

        //  Maybe delete this
        programState.getExecutionStack().push(programState.getOriginalProgram());

        while(!programState.getExecutionStack().isEmpty()){
            ProgramState newState = this.singleStepExecution(programState);
            //this.addStepToOutput(newState);
            repository.logProgramState();
        }
    }
}
