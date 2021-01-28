package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTDictionary;
import Model.ADTs.ADTStack;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.ProgramState;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

public class ForkStatement implements StatementInterface{
    StatementInterface statement;

    public ForkStatement(StatementInterface statement) {
        //  Constructor for the fork statement
        this.statement = statement;
    }

    public StatementInterface getStatement() {
        //  Getter for the statement
        return statement;
    }

    public void setStatement(StatementInterface statement) {
        //  Setter for the statement
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Executes an fork statement
                Steps:  -   Creates a new execution stack with the given statement in it
                        -   Creates a copy of the symbol table
                        -   Creates a new program state that will be executed in parallel
                Throws: None
                Return: A new program state
        */
        ADTStackInterface<StatementInterface> executionStack =  new ADTStack<StatementInterface>();
        executionStack.push(statement);

        //  Create a copy of the symbol table
        ADTDicionaryInterface<String, ValueInterface> symbolTable = new ADTDictionary<>();
        symbolTable.setContent(state.getSymbolTable().deepCopy());

        ProgramState childProgramState = new ProgramState(executionStack, symbolTable, statement,state.getOutput(), state.getFileTable(), state.getHeap(), state.getSemaphoreTable());
        state.setId(childProgramState.getId() + 1);
        return childProgramState;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks the type of the statement
        statement.TypeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        //  Returns a string representation of the fork statement
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the fork statement
        return new ForkStatement(statement.deepCopy());
    }
}
