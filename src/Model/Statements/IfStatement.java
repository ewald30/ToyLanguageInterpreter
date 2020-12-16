package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public class IfStatement implements StatementInterface {
    ExpressionInterface expression;
    StatementInterface thenStatement;
    StatementInterface elseStatement;

    public IfStatement(ExpressionInterface expression, StatementInterface thenStatement, StatementInterface elseStatement){
        //  Creates a new IfStatement
        this.expression = expression;
        this.elseStatement = elseStatement;
        this.thenStatement = thenStatement;
    }

    public ExpressionInterface getExpression(){
        //  Returns the expression
        return this.expression;
    }

    public StatementInterface getThenStatement(){
        //  Getter for thenStatement
        return this.thenStatement;
    }

    public StatementInterface getElseStatement(){
        //  Getter for elseStatement
        return this.elseStatement;
    }

    public void setExpression(ExpressionInterface expression){
        //  Setter for expression
        this.expression = expression;
    }

    public void setThenStatement(StatementInterface thenStatement){
        //  Setter for thenStatement
        this.thenStatement = thenStatement;
    }

    public void setElseStatement(StatementInterface elseStatement){
        //  Setter for elseStatement
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Checks to see if an if statement can be executed and changes the state of the program if that's the case
                Stepts: -   Get the type of the expression and check if it is bool
                        -   If so, execute the if statement and push on the executionStack thenStatement or the elseStatement
                Throws: -   DictionaryExpression
                        -   StatementException if the expression is not boolean
                Return: The state of the program after the execution of the if statement
        */
        ADTDicionaryInterface<Integer, ValueInterface> heap = state.getHeap();
        ValueInterface valueExpr = this.expression.evaluate(state.getSymbolTable(), heap);
        TypeInterface type = valueExpr.getType();

        if(type.equals(new BoolType())){
            ADTStackInterface <StatementInterface> executionStack = state.getExecutionStack();
            BoolValue boolValue = (BoolValue) valueExpr;
            boolean condition = boolValue.getValue();

            if(condition)
                executionStack.push(this.thenStatement);
            else
                executionStack.push(this.elseStatement);
        }
        else
            throw new InvalidTypeException("Conditional expression should be boolean!");

        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Returns the new type table after the expression and the two statements are checked
        TypeInterface expr_tyoe = expression.TypeCheck(typeEnv);

        if (!expr_tyoe.equals(new BoolType()))
            throw new InvalidTypeException("Condidion: " + this.expression.toString() + " should be of BoolType!");

        thenStatement.TypeCheck(typeEnv);
        elseStatement.TypeCheck(typeEnv);
        return typeEnv;

    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deepcopy of the IfStatement
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        //  Returns a string representation of the IfStatement
        String result;
        result = "( if(" + this.expression.toString();
        result = result + ") then (" + this.thenStatement.toString();
        result = result + ") else (" + this.elseStatement.toString() +"))";
        return result;
    }
}
