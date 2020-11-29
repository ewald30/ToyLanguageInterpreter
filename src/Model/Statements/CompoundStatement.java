package Model.Statements;

import Model.ADTs.ADTStackInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.ProgramState;

public class CompoundStatement implements StatementInterface{
    StatementInterface statement1;
    StatementInterface statement2;

    public CompoundStatement(StatementInterface statement1, StatementInterface statement2){
        //  Creates a new comp statement
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    public StatementInterface getStatement1(){
        //  Returns the first statement
        return this.statement1;
    }

    public StatementInterface getStatement2(){
        //  Returns the second statement
        return this.statement2;
    }

    public void setStatement1(StatementInterface statement1){
        //  Sets the first statement
        this.statement1 = statement1;
    }

    public void setStatement2(StatementInterface statement2){
        //  Sets the second statement;
        this.statement2 = statement2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Returns the state of the program after the changes occur
                Throws: None
                Return: state of the program after the 2 statements have been pushed to the execution stack
        */
        ADTStackInterface <StatementInterface> exeStack = state.getExecutionStack();
        exeStack.push(this.statement2);
        exeStack.push(this.statement1);

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(this.statement1.deepCopy(), this.statement2.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.statement1.toString() + "; " + this.statement2.toString() + ")";
    }
}
