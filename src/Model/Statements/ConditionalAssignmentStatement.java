package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.InvalidTypeException;
import Model.Exceptions.MyException;
import Model.Exceptions.VariableNotDefinedException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;

public class ConditionalAssignmentStatement implements StatementInterface{
    ExpressionInterface conditional_expression;
    ExpressionInterface if_true_expression;
    ExpressionInterface if_false_expression;
    String variable_id;

    public ConditionalAssignmentStatement(String variable_id,ExpressionInterface conditional_expression, ExpressionInterface if_true_expression, ExpressionInterface if_false_expression) {
        this.conditional_expression = conditional_expression;
        this.if_true_expression = if_true_expression;
        this.if_false_expression = if_false_expression;
        this.variable_id = variable_id;
    }

    public String getVariable_id() {
        //  Getter for the variable id
        return variable_id;
    }

    public void setVariable_id(String variable_id) {
        //  Setter for the variable id
        this.variable_id = variable_id;
    }

    public ExpressionInterface getConditional_expression() {
        //  Getter for the conditional expression
        return conditional_expression;
    }

    public void setConditional_expression(ExpressionInterface conditional_expression) {
        //  Setter for the conditional expression
        this.conditional_expression = conditional_expression;
    }

    public ExpressionInterface getIf_true_expression() {
        //  Getter for the if true expression
        return if_true_expression;
    }

    public void setIf_true_expression(ExpressionInterface if_true_expression) {
        //  Setter for the if true expression
        this.if_true_expression = if_true_expression;
    }

    public ExpressionInterface getIf_false_expression() {
        //  Getter for the if false expression
        return if_false_expression;
    }

    public void setIf_false_expression(ExpressionInterface if_false_expression) {
        //  Setter for the if false expression
        this.if_false_expression = if_false_expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbol_table = state.getSymbolTable();
        var heap = state.getHeap();

        if (!symbol_table.isDefined(variable_id))
            throw new VariableNotDefinedException("Variable " + variable_id + "is not defined!");


        var if_statement = new IfStatement(conditional_expression,
                new AssignStatement(variable_id, if_true_expression),
                new AssignStatement(variable_id, if_false_expression));
        state.getExecutionStack().push(if_statement);

        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks the types of the expressions and the variable
        TypeInterface conditional_expr_type = conditional_expression.TypeCheck(typeEnv);
        TypeInterface true_expr_type = if_true_expression.TypeCheck(typeEnv);
        TypeInterface false_expr_type = if_false_expression.TypeCheck(typeEnv);

        TypeInterface variable_type = typeEnv.lookup(variable_id);

        if (!conditional_expr_type.equals(new BoolType()))
            throw new InvalidTypeException("The type of the conditional expression does not match the Bool Type! Provided type:" + conditional_expr_type.toString());

        if (!true_expr_type.equals(variable_type))
            throw new InvalidTypeException("The type of the true branch expression does not match the type of the variable("+ variable_type.toString()+")! Provided type: "+ true_expr_type.toString());

        if (!false_expr_type.equals(variable_type))
            throw new InvalidTypeException("The type of the false branch expression does not match the type of the variable("+ variable_type.toString()+")! Provided type: "+ false_expr_type.toString());

        return typeEnv;
    }


    @Override
    public StatementInterface deepCopy() {
        //  Creates a deep copy of the conditional assignment statement
        return new ConditionalAssignmentStatement(variable_id, conditional_expression.deepCopy(), if_true_expression.deepCopy(), if_false_expression.deepCopy());
    }

    @Override
    public String toString(){
        //  Returns a string representation of the conditional assignment statement
        return variable_id + "=("
                + this.conditional_expression.toString() + ")?"
                + this.if_true_expression.toString()+":"
                +this.if_false_expression.toString();
    }

}
