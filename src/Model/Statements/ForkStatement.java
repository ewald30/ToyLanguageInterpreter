package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTDictionary;
import Model.ADTs.ADTStack;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.ProgramState;
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
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        ADTStackInterface<StatementInterface> executionStack =  new ADTStack<StatementInterface>();
        executionStack.push(statement);

        //  Create a copy of the symbol table
        ADTDicionaryInterface<String, ValueInterface> symbolTable = new ADTDictionary<>();
        symbolTable.setContent(state.getSymbolTable().deepCopy());

        ProgramState childProgramState = new ProgramState(executionStack, symbolTable, statement,state.getOutput(), state.getFileTable(), state.getHeap());
        return childProgramState;

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
