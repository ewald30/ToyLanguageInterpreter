package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Expressions.ExpressionInterface;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.TypeInterface;

public class ForLoopStatement implements StatementInterface{
    String variable_id;
    ExpressionInterface assignment_expression;
    ExpressionInterface relational_expression;
    ExpressionInterface update_expression;
    StatementInterface statement;

    public ForLoopStatement(String variable_id, ExpressionInterface assignment_expression, ExpressionInterface relational_expression, ExpressionInterface update_relation,StatementInterface statement) {
        //  Constructor for the for loop statement
        this.variable_id = variable_id;
        this.assignment_expression = assignment_expression;
        this.relational_expression = relational_expression;
        this.update_expression = update_relation;
        this.statement = statement;
    }

    public String getVariable_id() {
        return variable_id;
    }

    public void setVariable_id(String variable_id) {
        this.variable_id = variable_id;
    }
    public ExpressionInterface getAssignment_expression() {
        return assignment_expression;
    }

    public void setAssignment_expression(ExpressionInterface assignment_expression) {
        this.assignment_expression = assignment_expression;
    }

    public ExpressionInterface getRelational_expression() {
        return relational_expression;
    }

    public void setRelational_expression(ExpressionInterface relational_expression) {
        this.relational_expression = relational_expression;
    }

    public ExpressionInterface getUpdate_expression() {
        return update_expression;
    }

    public void setUpdate_expression(ExpressionInterface update_expression) {
        this.update_expression = update_expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var for_loop = new WhileStatement(relational_expression,new CompoundStatement(statement, new AssignStatement(variable_id, update_expression)));
        var assignment_statement = new AssignStatement(variable_id,assignment_expression);

        state.getExecutionStack().push(for_loop);
        state.getExecutionStack().push(assignment_statement);

        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface assignment_type = assignment_expression.TypeCheck(typeEnv);
        TypeInterface update_type = update_expression.TypeCheck(typeEnv);
        TypeInterface relational_type = relational_expression.TypeCheck(typeEnv);
        TypeInterface variable_type = typeEnv.lookup(variable_id);

        if (!assignment_type.equals(new IntType()))
            throw new InvalidTypeException("Invalid assignment expression type! Expected: int, provided: " + assignment_type);

        if (!update_type.equals(new IntType()))
            throw new InvalidTypeException("Invalid arithmetic expression type! Expected: int, provided: " + update_type);

        if (!relational_type.equals(new BoolType()))
            throw new InvalidTypeException("Invalid relational expression type! Expected: int, provided: " + relational_type);

        if (!variable_type.equals(new IntType()))
            throw new InvalidTypeException("Invalid variable type! Expected: int, provided: " + variable_type);

        return typeEnv;
    }

    @Override
    public String toString(){
        //  Returns a string representation of the for loop statement
        return "for("+variable_id+" = "+assignment_expression.toString()+";"+relational_expression.toString()+";"+variable_id+" = "+update_expression.toString()+")";
    }


    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the for loop statement
        return new ForLoopStatement(variable_id,assignment_expression.deepCopy(),relational_expression.deepCopy(), update_expression.deepCopy(), statement.deepCopy());
    }
}
