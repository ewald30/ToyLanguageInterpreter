package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.MyException;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

import java.util.Dictionary;

public class VariableExpression implements ExpressionInterface {
    String id;

    public VariableExpression(String id){
        //  Creates a new variable expression
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws  MyException {
        /*
            Returns the result given by the symbol table
                Throws: Dictionary exception if something is wrong in lookup method of SymbolTable
                Return: Result given by SymbolTable
        */
        return symbolTable.lookup(id);
    }

    @Override
    public TypeInterface TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Returns the type found at the given id
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        //  Returns the id
        return this.id;
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new VariableExpression(this.id);
    }
}
