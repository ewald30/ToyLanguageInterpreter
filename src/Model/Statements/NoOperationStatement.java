package Model.Statements;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.ProgramState;

public class NoOperationStatement implements StatementInterface {

    public NoOperationStatement() {};

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "";
    }
}
