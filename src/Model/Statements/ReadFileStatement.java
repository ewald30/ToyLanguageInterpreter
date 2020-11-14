package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.ExpressionInterface;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.IOException;

public class ReadFileStatement implements StatementInterface{
    ExpressionInterface expression;
    String variableName;

    public ReadFileStatement(ExpressionInterface expression, String varName){
        //  Constructor fort the read file statement
        this.expression = expression;
        this.variableName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Reads a line from a file, converts it to an integer value, and adds it to the symbol table
                Steps:  -   Check if file is of string type and the variable to store the integer is of int type
                        -   Check if the file is defined in file table and the variable in the symbol table
                        -   Try to read a line from the file and try to convert it to an integer
                Throws: -   InvalidTypeException if the filename is not StringType or variable is not IntType
                        -   StatementException if the file is not opened yet or the variable was not declared yet
                        -   FileException if we reached the EOF or the file is not in the correct format
                Return: The state of the program after executing this statement
        */
        var symbolTable = state.getSymbolTable();
        var fileNameInfo = expression.evaluate(symbolTable);
        var fileNameString = (StringValue) fileNameInfo;
        var fileTable = state.getFileTable();

        //  Check if the filename is of string type
        if (!fileNameInfo.getType().equals(new StringType()))
            throw new InvalidTypeException("Invalid type of file name. Expected: String, got: " + fileNameInfo.getType());

        //  Check if the variable name is defined in the symbol table
        if (!symbolTable.isDefined(variableName))
            throw new StatementException(variableName + " was note declared yet!");

        var variableValue = symbolTable.lookup(variableName);

        //  Check if the variable value is of int type
        if (!variableValue.getType().equals(new IntType()))
            throw new InvalidTypeException("Invalid type of the used variable. Expected: Int, got: " + variableValue.getType().toString());

        //  Check if the filename is defined in the file table
        if (!fileTable.isDefined(fileNameString))
            throw new StatementException("The given file is not opened yet!");

        //  Get the buffered reader for the file
        var file = fileTable.lookup(fileNameString);
        int number;

        try {
            //  Try to read a line containing an integer
            number = Integer.parseInt(file.readLine());
        } catch (IOException exception) {
            //  Throw exception if we reached EOF
            throw new FileException("Reached the EOF!");
        } catch (NumberFormatException exception) {
            //  Throw exception if the line can't be converted to int
            throw new FileException("Incorrect file format!");
        }

        //  Add the new int value to the symbol table
        symbolTable.update(variableName, new IntValue(number));
        return state;
    }

    @Override
    public String toString() {
        //  Returns a string representation of the statement
        return "ReadFile("+this.expression.toString() + this.variableName + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the read file statement
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }
}
