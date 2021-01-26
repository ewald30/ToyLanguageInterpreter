package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Expressions.ExpressionInterface;
import Model.Expressions.LogicExpression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState;
import Model.Types.TypeInterface;

import javax.swing.plaf.nimbus.State;

public class SwitchStatement implements StatementInterface{
    ExpressionInterface switch_expression;
    ExpressionInterface case1_expression;
    StatementInterface statement1;
    ExpressionInterface case2_expression;
    StatementInterface statement2;
    StatementInterface statement3;

    public SwitchStatement(ExpressionInterface switch_expression, ExpressionInterface case1_expression, StatementInterface statement1, ExpressionInterface case2_expression, StatementInterface statement2, StatementInterface statement3) {
        this.switch_expression = switch_expression;
        this.case1_expression = case1_expression;
        this.statement1 = statement1;
        this.case2_expression = case2_expression;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }


    public ExpressionInterface getSwitch_expression() {
        return switch_expression;
    }

    public void setSwitch_expression(ExpressionInterface switch_expression) {
        this.switch_expression = switch_expression;
    }

    public ExpressionInterface getCase1_expression() {
        return case1_expression;
    }

    public void setCase1_expression(ExpressionInterface case1_expression) {
        this.case1_expression = case1_expression;
    }

    public StatementInterface getStatement1() {
        return statement1;
    }

    public void setStatement1(StatementInterface statement1) {
        this.statement1 = statement1;
    }

    public ExpressionInterface getCase2_expression() {
        return case2_expression;
    }

    public void setCase2_expression(ExpressionInterface case2_expression) {
        this.case2_expression = case2_expression;
    }

    public StatementInterface getStatement2() {
        return statement2;
    }

    public void setStatement2(StatementInterface statement2) {
        this.statement2 = statement2;
    }

    public StatementInterface getStatement3() {
        return statement3;
    }

    public void setStatement3(StatementInterface statement3) {
        this.statement3 = statement3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StatementInterface if_statement =
                new IfStatement(new RelationalExpression(switch_expression, case1_expression, "=="),
                        statement1,
                        new IfStatement(new RelationalExpression(switch_expression, case2_expression, "=="),
                                statement2,
                                statement3));

        state.getExecutionStack().push(if_statement);
        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks the type of the statements and the expressions
        TypeInterface switch_type = switch_expression.TypeCheck(typeEnv);
        TypeInterface expression1_type = case1_expression.TypeCheck(typeEnv);
        TypeInterface expression2_type = case2_expression.TypeCheck(typeEnv);

        if (!switch_type.equals(expression1_type))
            throw new InvalidTypeException("The type of:" + switch_expression.toString() +" and the type of: " + case1_expression.toString() + " should be the same.");

        if (!switch_type.equals(expression2_type))
            throw new InvalidTypeException("The type of:" + switch_expression.toString() +" and the type of: " + case2_expression.toString() + " should be the same.");

        if (!expression1_type.equals(expression2_type))
            throw new InvalidTypeException("The type of:" + case1_expression.toString() +" and the type of: " + case2_expression.toString() + " should be the same.");

        statement1.TypeCheck(typeEnv);
        statement2.TypeCheck(typeEnv);
        statement3.TypeCheck(typeEnv);
        return typeEnv;

    }

    @Override
    public String toString(){
        //  Returns a string representation of the switch statement
        return "switch("+switch_expression.toString()+
                ") (case " + case1_expression.toString()+": " + statement1.toString()+
                ") (case " + case2_expression.toString()+": " + statement2.toString()+
                ") (default: " + statement3.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the switch statement
        return new SwitchStatement(switch_expression.deepCopy(),case1_expression.deepCopy(),statement1.deepCopy(),case2_expression.deepCopy(),statement2.deepCopy(),statement3.deepCopy());
    }
}
