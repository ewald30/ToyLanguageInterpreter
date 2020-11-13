package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTStackInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.FileException;
import Model.Exceptions.StatementException;
import Model.Expressions.ExpressionInterface;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Types.TypeInterface;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements StatementInterface {
    ExpressionInterface expression;

    public OpenRFileStatement(ExpressionInterface expression){
        //  Creates a new OpenRFileStatement
        this.expression = expression;
    }

    public ExpressionInterface getExpression() {
        //  Returns the expression
        return this.expression;
    }

    public void setExpression(ExpressionInterface expression) {
        //  Setter for expression
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, EvaluationException, DictionaryException {
        /*  Opens a file and adds it to the file table
                Steps:  -   Get the file table and symbol table
                        -   Check if the expression is of String Type
                        -   Check if a file with the given name is already defined in file table
                        -   Open the file and add it to the file table
                Throws: -   DictionaryException if the file is already defined
                        -   StatementException if the filename is not a string
                Return: The program state after the execution of the Open File Statement
        */
        ValueInterface fileName = expression.evaluate(state.getSymbolTable());
        ADTDicionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (! fileName.getType().equals(new StringType())){
            throw new StatementException("Invalid type in OpenRFIleStatement! Expected: String, got: "+fileName.getType());
        }

        StringValue fileNameString = (StringValue) fileName;

        if( fileTable.isDefined(fileNameString))
            throw new DictionaryException("Filename: " + fileNameString + " is already defined!");

        BufferedReader fileReader;

        try{
            fileReader = new BufferedReader(new FileReader(fileNameString.getValue()));
        } catch (IOException exception){
            throw new FileException("Opening a file returned an error: "+exception.getMessage());
        }

        fileTable.add(fileNameString, fileReader);
        return state;

    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a copy of the open file statement
        return new OpenRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        //  Returns a string representation of the statement
        return "OpenRFile(" + this.expression.toString() + ")";
    }
}
