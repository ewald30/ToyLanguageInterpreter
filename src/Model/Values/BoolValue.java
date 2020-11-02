package Model.Values;

import Model.Types.*;
public class BoolValue implements ValueInterface{
    boolean value;

    public BoolValue(boolean val){
        /*  Creates a new bool value
                Throws: None
                Return: None
        */
        this.value = val;
    }

    public boolean getValue(){
        //  Returns the value of the object
        return this.value;
    }

    @Override
    public TypeInterface getType() {
        /*  Returns a new booltype instance
                Throws: None
                Return: BoolType
        */
        return new BoolType();
    }

    @Override
    public String toString() {
        /*  Returns the value as a string
                Throws: None
                Return: A string containing the value
        */
        return String.valueOf(this.value);
    }

    @Override
    public ValueInterface deepCopy() {
        /*  Returns a deepcopy with the stored value
                Throws: None
                Return: DeepCopy of this object
        */
        BoolValue clone = new BoolValue(this.value);
        return clone;
    }
}
