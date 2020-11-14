package Model.Expressions;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Types.IntType;
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
    public ValueInterface evaluate(ADTDicionaryInterface<String, ValueInterface> symbolTable) throws EvaluationException, DictionaryException {
        var valueExpression1 = this.expression1.evaluate(symbolTable);
        var valueExpression2 = this.expression2.evaluate(symbolTable);

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
    public ExpressionInterface deepCopy() {
        return null;
    }

    @Override
    public String toString(){
        //  Returns a string representation of the relational expression
        return this.expression1.toString() + " " + this.relation + " " + this.expression2.toString();
    }
}
