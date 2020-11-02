package Model.Values;

import Model.Types.*;
public class IntValue implements ValueInterface{
    int value;

    public IntValue(int value){
        /*  Creates a new object of type IntValue
                Throws: None
                Return: None
        */
        this.value = value;
    }

    public int getValue(){
        //  Returns the value of the object
        return this.value;
    }

    @Override
    public TypeInterface getType() {
        //  Returns the type of the object
        return new IntType();
    }

    @Override
    public String toString() {
        /*  Returns a string containing the value of the object
                Throws: None
                Return: None
        */
        return String.valueOf(this.value);
    }

    @Override
    public ValueInterface deepCopy() {
        /*  Return a deep copy of the eleement
                Throws: None
                Return: deep copu of the element
        */
        IntValue clone = new IntValue(this.value);
        return clone;
    }
}
