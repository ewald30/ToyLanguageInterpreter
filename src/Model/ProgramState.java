package Model;

import Model.ADTs.*;
import Model.Exceptions.*;
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
    ADTHeapInterface<Integer, ValueInterface> heap;
    int ID;

    public ProgramState(ADTStackInterface<StatementInterface> executionStack,
                        ADTDicionaryInterface<String, ValueInterface> symbolTable,
                        StatementInterface originalProgram, ADTListInterface<ValueInterface> output,
                        ADTDicionaryInterface <StringValue, BufferedReader> fileTable,
                        ADTHeapInterface<Integer, ValueInterface> heap) {
        //  Constructor for the program state
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.originalProgram = originalProgram;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        }

    public String toString(){
        //  Returns a string representation of the the program state
        String result = "   --------------------------------------\n        ID: " + this.ID + "\n    -------------------------------------\n\n";
        result = result + "   --------------------------------------\n        Execution Stack: " + this.executionStack.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        Symbol Table: " + this.symbolTable.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        File Table: " + this.fileTable.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        Heap: " + this.heap.toString() + "\n    -------------------------------------\n\n";
        result = result + "    -------------------------------------\n        Output: " + this.output.toString() + "\n    -------------------------------------\n\n\n\n";
        return result;
    }

    public int getId() {
        //  Returns the id of the program state
        return ID;
    }

    public void setId(int id) {
        //  Returns the di of the program state
        ID = id;
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
        //  Getter for the file table
        return fileTable;
    }

    public void setFileTable(ADTDicionaryInterface<StringValue, BufferedReader> fileTable) {
        //  Setter for the file table
        this.fileTable = fileTable;
    }

    public ADTHeapInterface<Integer, ValueInterface> getHeap() {
        //  Returns the heap
        return heap;
    }

    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty())
            throw new StackException("Execution stack is empty");

        StatementInterface currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted(){
        //  Checks if the program state is completed (if execution stack is empty or not)
        if (executionStack.isEmpty())
            return false;
        return true;
    }
}
