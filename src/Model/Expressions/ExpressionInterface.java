package Model.Expressions;

import Model.ADTs.*;
import Model.Values.*;
import Model.Exceptions.*;

public interface ExpressionInterface {
    ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable) throws EvaluationException, DictionaryException;
    ExpressionInterface deepCopy();
    String toString();
}
