package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

public class AssignStatement implements StatementInterface {
    String Id;
    ExpressionInterface expression;

    public AssignStatement(String Id, ExpressionInterface expression ){
        //  Creates a new AssignStatement
        this.Id = Id;
        this.expression = expression;
    }

    public String getId(){
        //  Returns the Id of the statement;
        return this.Id;
    }

    public ExpressionInterface getExpression(){
        //  Returns the expression
        return this.expression;
    }

    public void setId(String Id){
        //  Sets the Id to a certain value
        this.Id = Id;
    }

    public void setExpression(ExpressionInterface expression){
        //  Sets the expression to a certain one
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        /*  Executes an assignment statement
                Steps:  -   Get the current execution Stack
                        -   Get the current symboltable
                        -   check if the variable is defined
                        -   check if the type of the expression matches the type of the argument
                        -   update the value of the variable or throw exception of anything is wrong
                Return: Program State after execution of the statement;
                Throws: -   DictionaryException -   if something goes wrong with the symboltable update
                        -   StatementException  -   if the type of the variable is not matched by the assigned operation
                                                -   if the variable is not yet declared

        */
        ADTStackInterface <StatementInterface> exeStack = state.getExecutionStack();
        ADTDicionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ADTDicionaryInterface<Integer, ValueInterface> heap = state.getHeap();

        if(symbolTable.isDefined(this.Id)){
            ValueInterface value = this.expression.evaluate(symbolTable, heap);
            TypeInterface typeId = (TypeInterface) (symbolTable.lookup(this.Id)).getType();

            if(value.getType().equals(typeId))
                symbolTable.update(this.Id, value);
            else throw new InvalidTypeException("Type of assigned operation does not match type of " + this.Id + "\n");
        }
        else throw new StatementException("Variable " + this.Id + " was not yet delcared\n");
        return null;
    }

    @Override
    public ADTDicionaryInterface<String, TypeInterface> TypeCheck(ADTDicionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        //  Checks if the variable type is equal to the expression type
        TypeInterface type_var = typeEnv.lookup(Id);
        TypeInterface type_expr = expression.TypeCheck(typeEnv);

        if (!type_expr.equals(type_var)) {
            throw new InvalidTypeException("Type of: " + this.Id + " does not match the type of: " + this.expression.toString());
        }

        return typeEnv;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Creates and retunrs a deepcopy of the statement
        return new AssignStatement(this.Id, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        //  Returns a string representation of the statement
        return this.Id + " = " + this.expression.toString();
    }
}
