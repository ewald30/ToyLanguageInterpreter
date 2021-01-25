package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public class NotExpression implements ExpressionInterface{
    ExpressionInterface expression;

    public NotExpression(ExpressionInterface expression) {
        //  Constructor of the not expression
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
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws MyException {
        /*  Evaluates a gotten expression as its negation
                Steps:  -   Evaluate the expression
                        -   Check the expression returned value
                        -   Return a new value containing the negation of expression evaluation value
                Throws: -   EvaluationException if the type of evaluated expression is not Bool type
                Return: -   A new bool value equal with !expression
        */
        var expression_value = this.expression.evaluate(symbolTable, heap);

        if (!expression_value.getType().equals(new BoolType()))
            throw new EvaluationException("Invalid type for expression. Expected: BoolType, got: " + expression_value.getType().toString());

        var returned_value = (BoolValue) expression_value;
        return new BoolValue(!returned_value.getValue());
    }

    @Override
    public TypeInterface TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        /*  Checks the type of the expression
                Throws: InvalidTypeException if the expression is not of bool type
                Return: BoolType of no exception is deployed
        */
        TypeInterface type = expression.TypeCheck(typeEnv);

        if (!type.equals(new BoolType()))
            throw new InvalidTypeException("The type of: " + expression.toString() + " should be BoolType!");

        return new BoolType();

    }

    @Override
    public ExpressionInterface deepCopy() {
        //  Returns a deep copy of the negation expression
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "!(" + this.expression.toString() + ")";
    }
}
