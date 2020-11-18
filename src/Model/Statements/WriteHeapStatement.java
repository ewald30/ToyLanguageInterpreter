package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Values.ReferenceValue;

public class WriteHeapStatement implements StatementInterface{
    private final String variableName;
    private final ExpressionInterface expression;

    public WriteHeapStatement(String variableName, ExpressionInterface expression) {
        //  Constructor for the write to heap statement
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Writes an expression to the heap
                Steps:  -   Get the symbol table and the heap
                        -   check if the variable is defined in the symbol table
                        -   check if the variable is a reference
                        -   check if the type of the expression matches the type of the referenced variable
                        -   update the allocated space at the given address with the new value
                Throws: -   VariableNotDefined  -   if the variable name is not contained in the symbol table
                                                -   if the address is not a key in the heap
                        -       InvalidType     -   if the variable is not a reference
                                                -   if the expression type and the referenced type do not match
                Return: -   The state of the program after the execution of this statement
        */
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();

        if (!symbolTable.isDefined(variableName))
            throw new VariableNotDefinedException("Variable " + variableName + " is not defined yet!");

        var value = symbolTable.lookup(variableName);

        if (!(value.getType() instanceof ReferenceType))
            throw new InvalidTypeException("Type of " + variableName + " should be reference type");

        var valueReference = (ReferenceValue) value;

        if (!heap.isDefined(valueReference.getAddress()))
            throw new VariableNotDefinedException("The address of the variable " + variableName + " is not stored in the heap!");

        var expressionEvaluated = expression.evaluate(symbolTable, heap);

        if(!expressionEvaluated.getType().equals(valueReference.getLocationType()))
            throw new InvalidTypeException("The type of expression and referenced type do not match");

        heap.update(valueReference.getAddress(), expressionEvaluated);
        return state;
    }

    @Override
    public String toString(){
        //  Returns a string representation of the statement
        return "wh(" + this.variableName + ", " + this.expression.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the expression
        return new WriteHeapStatement(variableName, expression.deepCopy());
    }
}
