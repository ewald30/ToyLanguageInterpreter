package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Expressions.ExpressionInterface;
import Model.Expressions.NotExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public class RepeatUntilStatement implements StatementInterface{
    StatementInterface statement;
    ExpressionInterface expression;

    public RepeatUntilStatement(StatementInterface statement, ExpressionInterface expression) {
        //  Constructor for RepeatUntilStatement
        this.statement = statement;
        this.expression = expression;
    }

    public StatementInterface getStatement() {
        //  Getter for statement
        return statement;
    }

    public void setStatement(StatementInterface statement) {
        //  Setter for the statement
        this.statement = statement;
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
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks the type of the condition and the statement of the repeat until statement
        TypeInterface expression_type = expression.TypeCheck(typeEnv);

        if (!expression_type.equals(new BoolType()))
            throw new InvalidTypeException("The type of the repeat until condition: " + expression_type.toString() + "should be BoolType!");

        statement.TypeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Executes an "repeat until" statement
                Steps:  -   Get the heap, symbol table and execution stack
                        -   Evaluate the condition and check its type
                        -   Execute the statement
                        -   Check if condition evaluates to false
                        -   Push the statement into the stack again if so
                Throws: -   InvalidTypeException if the condition type is not BoolType
                Return: -   Updated program state
        */

        var execution_stack = state.getExecutionStack();

        //  Check why this creates a new program state
        var while_statement = new WhileStatement(new NotExpression(expression),statement);
        execution_stack.push(while_statement);
        return null;

    }

    @Override
    public String toString(){
        //  Returns a string representation of the repeat until statement
        return "repeat(" + this.statement.toString() + ") until(" + this.expression.toString() +")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the statement
        return new RepeatUntilStatement(statement.deepCopy(), expression.deepCopy());
    }
}
