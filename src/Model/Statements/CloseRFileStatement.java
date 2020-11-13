package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.FileException;
import Model.Exceptions.StatementException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements StatementInterface{
    ExpressionInterface expression;

    public CloseRFileStatement(ExpressionInterface expression){
        //  Constructor for Close File Statement
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Closes a file and removes if from file table
                Steps:  -   Get the file table and evaluate the expression
                        -   Check if the filename is of String type
                        -   Check if the file is declared in the file table
                        -   Close the file
                Throws: -   FileException if the file isn't contained in the file table
                        -   Statement Exception if the filename is not a StringType
                Return: The state of the program after closing the file
        */

        ValueInterface fileName = this.expression.evaluate(state.getSymbolTable());
        ADTDicionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!fileName.getType().equals(new StringType()))
            throw new StatementException("Invalid type in CloseRFIleStatement! Expected: String, got: "+fileName.getType());

        StringValue fileNameString = (StringValue)fileName;

        if (!fileTable.isDefined(fileNameString))
            throw new FileException("The given filename isn't defined in the file table.");

        var fileReader = fileTable.lookup(fileNameString);

        try{
            fileReader.close();
        } catch (IOException exception){
            throw new FileException("Closing a file returned an error: " + exception.getMessage());
        }

        fileTable.remove(fileNameString);
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the close file statement
        return new CloseRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        //  Returns a string representation of the statement
        return "CloseRFile(" + this.expression.toString() + ")";
    }
}