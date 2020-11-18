package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface{
    ExpressionInterface expression1;
    ExpressionInterface expression2;

    char operation;

    public ArithmeticExpression(ExpressionInterface expression1, ExpressionInterface expression2, char operation){
        /*  Creates a new Arithmetic Expression
                Throws: None
                Return: None
        */
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public void setExpression1(ExpressionInterface expression1){
        //  Setter for the first expression
        this.expression1 = expression1;
    }

    public void setExpression2(ExpressionInterface expression2){
        //  Setter for the second expression
        this.expression2 = expression2;
    }

    public void setOperation(char operation){
        //  Setter for the operation
        this.operation = operation;
    }

    ExpressionInterface getExpression1(){
        //  Getter for the first expression
        return this.expression1;
    }

    ExpressionInterface getExpression2(){
        //  Getter for the second expression
        return this.expression2;
    }

    char getOperation(){
        //  Getter for the oepration
        return this.operation;
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws EvaluationException, DictionaryException {
        /*  Evaluates an arithmetic expression
                Steps:  -   Get the type of the operands from the SymbolTable
                        -   Check if both of them are integers
                        -   Perform the opreation based on the operator
                        -   Throw Exception of something is wrong along the way

                Throws:  EvaluationException if:    -   one of the operands is not an integer
                                                    -   division by zero
                Return: A new IntValue consisting of:   -   the addition of the 2 operands
                                                        -   the difference of the 2
                                                        -   the multiplication of the 2
                                                        -   the division of the 2
        */
        ValueInterface valueInterface1, valueInterface2;
        valueInterface1 = this.expression1.evaluate(symbolTable, heap);

        if (valueInterface1.getType().equals(new IntType())){
            valueInterface2 = this.expression2.evaluate(symbolTable, heap);

            if (valueInterface2.getType().equals(new IntType())) {
                IntValue intValue1 = (IntValue) valueInterface1;
                IntValue intValue2 = (IntValue) valueInterface2;

                int realValue1, realValue2;
                realValue1 = intValue1.getValue();
                realValue2 = intValue2.getValue();

                if (this.operation == '+')
                    return new IntValue(realValue1 + realValue2);

                if (this.operation == '-')
                    return new IntValue(realValue1 - realValue2);

                if (this.operation == '*')
                    return new IntValue(realValue1 * realValue2);

                if (this.operation == '/')
                    if (realValue2 == 0)
                        throw new EvaluationException("Division by zero is not possible!");
                    return new IntValue(realValue1 / realValue2);
            }
            else throw new EvaluationException("Second operand should be integer!");
        }
        else throw new EvaluationException("First operand should be Integer!");

    }


    @Override
    public String toString() {
        //  Returns a string representation of the operation
        return this.expression1.toString() + " " + this.operation + " " + this.expression2.toString();
    }

    @Override
    public ExpressionInterface deepCopy() {
        //  Creates and returns a deepCopy of the Arithmetic Expression
        ExpressionInterface expressionClone1 = this.expression1.deepCopy();
        ExpressionInterface expressionClone2 = this.expression2.deepCopy();
        return new ArithmeticExpression(expressionClone1, expressionClone2, this.operation);
    }
}

