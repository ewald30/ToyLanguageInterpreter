package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Values.ValueInterface;

public class VariableExpression implements ExpressionInterface {
    String id;

    public VariableExpression(String id){
        //  Creates a new variable expression
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws  DictionaryException {
        /*
            Returns the result given by the symbol table
                Throws: Dictionary exception if something is wrong in lookup method of SymbolTable
                Return: Result given by SymbolTable
        */
        return symbolTable.lookup(id);
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
