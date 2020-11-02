package Model.Statements;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.ProgramState;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException;
    StatementInterface deepCopy();
    String toString();
}
