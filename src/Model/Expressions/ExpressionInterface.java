package Model.Expressions;

import Model.ADTs.*;
import Model.Types.TypeInterface;
import Model.Values.*;
import Model.Exceptions.*;

import java.util.Dictionary;

public interface ExpressionInterface {
    ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws MyException;
    TypeInterface TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException;
    ExpressionInterface deepCopy();
    String toString();
}
