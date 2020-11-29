package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Values.ReferenceValue;

public class NewStatement implements StatementInterface{
    private final String variableName;
    private final ExpressionInterface expression;

    public NewStatement(String var_name, ExpressionInterface expression) {
        //  Constructor fot eh new Statement
        this.variableName = var_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*`Executes a new statement
                Steps   -   get the heap and the symbol table
                        -   check the validity of the variable name
                        -   evaluate the expression and check the validity too
                        -   get a new address and allocate a new place in the heap for the new reference value
                Throws: -   VariableNotDefinedException if the variable name can't be found in symbol table
                        -   InvalidTypeException if the variable is not of reference type
                                                 if the expression type and reference type do not match
                Return: the program state after the execution of the new statement
        */
        var heap = state.getHeap();
        var symbolTable = state.getSymbolTable();
        var expressionInfo = expression.evaluate(symbolTable, heap);

        if (!symbolTable.isDefined(variableName))
            throw new VariableNotDefinedException("Variable: " + variableName + " is not defined");

        var variableInfo = symbolTable.lookup(variableName);

        if (!(variableInfo.getType() instanceof ReferenceType))
            throw new InvalidTypeException("Invalid type for variable: " + variableName + "Expected: ReferenceType, got: " + variableInfo.getType());

        var variableValue = (ReferenceValue) variableInfo;

        if (!expressionInfo.getType().equals(variableValue.getLocationType()))
            throw new InvalidTypeException("Expression type and reference type do not match!");

        var address = heap.getNewAddress();
        heap.add(address, expressionInfo);
        symbolTable.update(variableName, new ReferenceValue(address, variableValue.getLocationType()));
        return null;

    }

    @Override
    public String toString() {
        //  Returns a string representation of the new statement
        return "New(" + this.variableName + ", " + this.expression.toString() +")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the statement
        return new NewStatement(variableName, expression.deepCopy());
    }
}
