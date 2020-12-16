package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Types.ReferenceType;
import Model.Types.TypeInterface;
import Model.Values.ReferenceValue;
import Model.Values.ValueInterface;

public class ReadHeapExpression implements ExpressionInterface{
    ExpressionInterface expression;

    public ReadHeapExpression(ExpressionInterface expression) {
        //  Constructor for Read heap expression
        this.expression = expression;
    }

    public ExpressionInterface getExpression() {
        //  Getter for the expression
        return expression;
    }

    public void setExpression(ExpressionInterface expression) {
        //  Setter for the expression
        this.expression = expression;
    }

    @Override
    public ExpressionInterface deepCopy() {
        //  Returns a deep copy of the expression
        ExpressionInterface expressionClone = expression.deepCopy();
        return new ReadHeapExpression(expressionClone);
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws MyException {
        /*  Evaluates an read from heap expression
                Steps:  -   evaluates the expression as an reference value
                        -   if the address is defined in the heap return the value
                Throws: EvaluationExpression if the address is not defined in heap
                                             the expression value is not reference value
                Return: The value from the heap table that is found at the given address

        */
        ValueInterface expressionValue = expression.evaluate(symbolTable, heap);

        if (!(expressionValue instanceof ReferenceValue))
            throw new EvaluationException("Expression should be reference value ");

        if (!heap.isDefined(((ReferenceValue) expressionValue).getAddress()))
            throw new EvaluationException("The address is invalid!");

        return heap.lookup(((ReferenceValue) expressionValue).getAddress());

    }

    @Override
    public TypeInterface TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        /*  Checks if an expression is of type ReferenceType
                Throws: InvalidTypeExpression if the type of expression is not ReferenceType
                Return: The inner content of reference stype
        */
        TypeInterface type;
        type = expression.TypeCheck(typeEnv);
        if (!(type instanceof ReferenceType))
            throw new InvalidTypeException("The type of: " + expression.toString() + " should be ReferenceType");

        ReferenceType refType = (ReferenceType) type;
        return refType.getInner();


    }

    @Override
    public String toString(){
        //  Returns a string representation of the expression
        return "rh " + this.expression.toString();
    }
}
