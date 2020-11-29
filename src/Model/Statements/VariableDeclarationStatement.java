package Model.Statements;

import Model.ADTs.ADTDicionaryInterface;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StatementException;
import Model.ProgramState;
import Model.Types.TypeInterface;
import Model.Values.ValueInterface;

public class VariableDeclarationStatement implements StatementInterface {
    String Id;
    TypeInterface type;

    public VariableDeclarationStatement(String Id, TypeInterface type){
        //  Creates a new variable declaration statement
        this.Id = Id;
        this.type = type;
    }

    public String getId(){
        //  Getter for the Id
        return this.Id;
    }

    public TypeInterface getType(){
        //  Getter for the type
        return this.type;
    }

    public void setId(String Id){
        //  Setter for the Id
        this.Id = Id;
    }

    public void setType(TypeInterface type){
        //  Setter for the Type
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, DictionaryException {
        /*  Declares a variable in the symbol table and assigns it the default value
                Throws: StatementException - if the variable is already delcared
                Return: State of the program after the execution of variable declaration statement
        */
        ADTDicionaryInterface <String, ValueInterface> symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(this.Id))
            throw new StatementException("Variable already declared in the symbol table!");
        else
            symbolTable.add(this.Id, type.defaultValue());

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        //  Returns a deep copy of the variable declaration statement
        return new VariableDeclarationStatement(this.Id, this.type.deepCopy());
    }

    @Override
    public String toString() {
        //  Returns a string representation of the variable declaration statement
        return this.type.toString() + " " + this.Id;
    }
}
