package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.MyException;
import Model.Exceptions.StatementException;
import Model.ProgramState;
import Model.Types.TypeInterface;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws MyException;
    StatementInterface deepCopy();
    ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String,TypeInterface> typeEnv) throws MyException;
    String toString();
}
