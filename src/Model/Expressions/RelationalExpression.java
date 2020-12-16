package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.ValueInterface;

public class RelationalExpression implements ExpressionInterface{
    ExpressionInterface expression1;
    ExpressionInterface expression2;

    String relation;

    public RelationalExpression(ExpressionInterface expression1, ExpressionInterface expression2, String relation) {
        //  Constructor for RelationalExpression
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.relation = relation;
    }

    public ExpressionInterface getExpression1() {
        //  Getter for expression1
        return expression1;
    }

    public void setExpression1(ExpressionInterface expression1) {
        //  setter for expression1
        this.expression1 = expression1;
    }

    public ExpressionInterface getExpression2() {
        //  Getter for expression2
        return expression2;
    }

    public void setExpression2(ExpressionInterface expression2) {
        //  Setter for expression2
        this.expression2 = expression2;
    }

    public String getRelation() {
        //  Getter for relation
        return relation;
    }

    public void setRelation(String relation) {
        //  Setter for relation
        this.relation = relation;
    }


    @Override
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable, ADTDicionaryInterface<Integer, ValueInterface> heap) throws MyException {
        var valueExpression1 = this.expression1.evaluate(symbolTable, heap);
        var valueExpression2 = this.expression2.evaluate(symbolTable, heap);

        if (!valueExpression1.getType().equals(new IntType()))
            throw new EvaluationException("Invalid type for expression1. Expected: Int got: " + valueExpression1.getType().toString());

        if (!valueExpression2.getType().equals(new IntType()))
            throw new EvaluationException("Invalid type for expression1. Expected: Int got: " + valueExpression2.getType().toString());

        var realValue1 = (IntValue) valueExpression1;
        var realValue2 = (IntValue) valueExpression2;

        if(this.relation.equals("=="))
            return new BoolValue(realValue1.getValue() == realValue2.getValue());

        if(this.relation.equals("!="))
            return new BoolValue(realValue1.getValue() != realValue2.getValue());

        if (this.relation.equals("<"))
            return new BoolValue(realValue1.getValue() < realValue2.getValue());

        if (this.relation.equals("<="))
            return new BoolValue(realValue1.getValue() <= realValue2.getValue());

        if (this.relation.equals(">"))
            return new BoolValue(realValue1.getValue() > realValue2.getValue());

        if (this.relation.equals(">="))
            return new BoolValue(realValue1.getValue() >= realValue2.getValue());

        return null;
    }

    @Override
    public TypeInterface TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        /*  Checks the type of the two expressions
                Throws: InvalidTypeException if one of the expression is not of int type
                Return: IntType of no exception is deployed
        */
        TypeInterface type1, type2;
        type1 = expression1.TypeCheck(typeEnv);
        type2 = expression2.TypeCheck(typeEnv);

        if (!type1.equals(new IntType()))
            throw new InvalidTypeException("The type of: " + expression1.toString() + " should be IntType!");

        if (!type2.equals(new IntType()))
            throw new InvalidTypeException("The type of: " + expression2.toString() + " should be IntType!");

        return new BoolType();
    }

    @Override
    public ExpressionInterface deepCopy() {
        return null;
    }

    @Override
    public String toString(){
        //  Returns a string representation of the relational expression
        return this.expression1.toString() + " " + this.relation + " " + this.expression2.toString();
    }
}
