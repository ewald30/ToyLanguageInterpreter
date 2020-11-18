package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.EvaluationException;
import Model.Values.ValueInterface;

public class ValueExpression implements ExpressionInterface{
    ValueInterface value;

    public ValueExpression(ValueInterface value){
        //  Creates a new ValueExpression
        this.value = value;
    }

    public ValueInterface getValue(){
        //  Returns the value of the expression
        return this.value;
    }

    public void setValue(ValueInterface value){
        //  Sets the value of the expression
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws EvaluationException {
        //  Returns the value of the expression
        return this.value;
    }

    @Override
    public String toString() {
        //  Returns the value of the expression as a string
        return this.value.toString();
    }

    @Override
    public ExpressionInterface deepCopy() {
        //  Returns a deepcopy of the expression4
        return new ValueExpression(this.value.deepCopy());
    }
}
