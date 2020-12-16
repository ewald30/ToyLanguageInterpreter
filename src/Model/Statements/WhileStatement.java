package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Values.BoolValue;

public class WhileStatement implements StatementInterface{
    private final ExpressionInterface expression;
    private final StatementInterface statement;

    public WhileStatement(ExpressionInterface expression, StatementInterface statement) {
        //  Constructor of the while statement
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Executes a while statement
                Steps:  -   Get the heap, symbol table, execution stack
                        -   Evaluate the condition and check whether the type of the condition is bool
                        -   Check if the condition evaluates to true
                        -   Push the content of the loop and the condition again on the stack
                Throws: -   InvalidType -   if the type of the condition is different than Bool Type
                Return: -   The state of the program after executing a

        */
        var symbolTable = state.getSymbolTable();
        var executionStack = state.getExecutionStack();
        var heap = state.getHeap();

        var expressionEvaluated = expression.evaluate(symbolTable, heap);

        if (!expressionEvaluated.getType().equals(new BoolType()))
            throw new InvalidTypeException("Condition should return a boolean value!");

        var expressionValue = (BoolValue) expressionEvaluated;
        if (expressionValue.getValue() == true){
            executionStack.push(this);
            executionStack.push(statement);
        }
        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks the condition and the statement of the while statement
        TypeInterface expr_type = expression.TypeCheck(typeEnv);

        if (!expr_type.equals(new BoolType()))
            throw new InvalidTypeException("The type of the while condition: " + this.expression.toString() + " should be BoolType!");

        statement.TypeCheck(typeEnv);
        return typeEnv;
    }

    public ExpressionInterface getExpression() {
        //  Returns the expression (condition)
        return expression;
    }

    public StatementInterface getStatement() {
        //  Returns the statement (loop content)
        return statement;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the while statement
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        //  Returns a string representation of the while statement
        return "while(" + this.expression.toString() + ") then (" + statement + ")";
    }
}
