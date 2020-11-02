package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public class LogicExpression implements ExpressionInterface {
    ExpressionInterface expression1;
    ExpressionInterface expression2;

    String operator;

    public LogicExpression(ExpressionInterface expression1, ExpressionInterface expression2, String operator){
        /*  Creates a new Logic Expression
                Throws: None
                Return: None
        */
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    public void setExpression1(ExpressionInterface expression1){
        //  Setter for the first expression
        this.expression1 = expression1;
    }

    public void setExpression2(ExpressionInterface expression2){
        //  Setter for the second expression
        this.expression2 = expression2;
    }

    public void setOperator(String operator){
        //  Setter for the operator
        this.operator = operator;
    }

    ExpressionInterface getExpression1(){
        //  Getter for the first expression
        return this.expression1;
    }

    ExpressionInterface getExpression2(){
        //  Getter for the second expression
        return this.expression2;
    }

    String getOperator(){
        //  Getter for the operator
        return this.operator;
    }

    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable) throws EvaluationException, DictionaryException {
        /*  Evaluates a logic expression
                Steps:  -   Get the type of the operands from the SymbolTable
                        -   Check if both of them are boolean
                        -   Perform the logic opreation based on the operator
                        -   Throw Exception of something is wrong along the way

                Throws:  EvaluationException if:    -   one of the operands is not a boolean
                                                    -   invalid operator
                Return: A new IntValue consisting of:   -   result of "AND" operation
                                                        -   result of "OR" operation

        */
        ValueInterface valueInterface1, valueInterface2;
        valueInterface1 = this.expression1.evaluate(symbolTable);

        if (valueInterface1.getType().equals(new BoolType())){
            valueInterface2 = this.expression2.evaluate(symbolTable);

            if(valueInterface2.getType().equals(new BoolType())){
                BoolValue boolValue1 = (BoolValue) valueInterface1;
                BoolValue boolValue2 = (BoolValue) valueInterface2;

                boolean realValue1, realValue2;
                realValue1 = boolValue1.getValue();
                realValue2 = boolValue2.getValue();

                if(this.operator.equalsIgnoreCase("and"))
                    return new BoolValue(realValue1 && realValue2);

                if(this.operator.equalsIgnoreCase("or"))
                    return new BoolValue(realValue1 || realValue2);

                else throw new EvaluationException("Invalid oeprator!");
            }
            else throw new EvaluationException("Operand 2 should be boolean!");
        }
        else throw new EvaluationException("Operand 1 should be boolean");
    }

    @Override
    public String toString() {
        //  Returns a string representation of the operation
        return this.expression1.toString() + " " + this.operator + " " +this.expression2.toString();
    }

    @Override
    public ExpressionInterface deepCopy() {
        //  Creates and returns a deepCopy of the Logic Expression
        ExpressionInterface expressionClone1 = this.expression1.deepCopy();
        ExpressionInterface expressionClone2 = this.expression2.deepCopy();
        return new LogicExpression(expressionClone1, expressionClone2, this.operator);
    }
}
