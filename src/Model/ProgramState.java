package Model;

import Model.ADTs.ADTDicionaryInterface;
import Model.ADTs.ADTDictionary;
import Model.ADTs.ADTListInterface;
import Model.ADTs.ADTStackInterface;
import Model.Statements.StatementInterface;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;

public class ProgramState {
    ADTStackInterface <StatementInterface> executionStack;
    ADTDicionaryInterface <String, ValueInterface> symbolTable;
    StatementInterface originalProgram;
    ADTListInterface <ValueInterface> output;
    ADTDicionaryInterface <StringValue, BufferedReader> fileTable;

    public ProgramState(ADTStackInterface<StatementInterface> executionStack, ADTDicionaryInterface<String, ValueInterface> symbolTable, StatementInterface originalProgram, ADTListInterface<ValueInterface> output, ADTDicionaryInterface <StringValue, BufferedReader> fileTable) {
        //  Constructor for the program state
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.originalProgram = originalProgram;
        this.output = output;
        //  Change this part later
        this.fileTable = fileTable;
        }

    public String toString(){
        //  Returns a string representation of the the program state
        String result = "   --------------------------------------\n        Execution Stack: " + this.executionStack.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        Symbol Table: " + this.symbolTable.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        Output: " + this.output.toString() + "\n    -------------------------------------\n\n";
        return result;
    }

    public void setExecutionStack(ADTStackInterface<StatementInterface> executionStack) {
        //  Setter for execution stack
        this.executionStack = executionStack;
    }

    public void setSymbolTable(ADTDicionaryInterface<String, ValueInterface> symbolTable) {
        //  Setter for the symbol table
        this.symbolTable = symbolTable;
    }

    public void setOriginalProgram(StatementInterface originalProgram) {
        //  Setter for the original program
        this.originalProgram = originalProgram;
    }

    public void setOutput(ADTListInterface<ValueInterface> output) {
        //  Setter for the output
        this.output = output;
    }

    public ADTStackInterface <StatementInterface> getExecutionStack(){
        //  Returns the execution stack of the program
        return executionStack;
    }

    public ADTDicionaryInterface <String, ValueInterface> getSymbolTable(){
        //  Returns the symbol table of the program
        return symbolTable;
    }

    public ADTListInterface <ValueInterface> getOutput(){
        //  Returns the output of the program
        return output;
    }

    public StatementInterface getOriginalProgram(){
        //  Returns the original state of the program
        return originalProgram;
    }

    public ADTDicionaryInterface<StringValue, BufferedReader> getFileTable() {
        //  Getter for the filetable
        return fileTable;
    }

    public void setFileTable(ADTDicionaryInterface<StringValue, BufferedReader> fileTable) {
        //  Setter for the filetable
        this.fileTable = fileTable;
    }


}
