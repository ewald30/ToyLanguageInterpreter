package Model.Types;

import Model.Values.*;
public class IntType implements TypeInterface {

    @Override
    public ValueInterface defaultValue() {
        /*  Returns a new Int value
                Throws: None
                Return: New int value (default = 0)
        */
        return new IntValue(0);
    }

    @Override
    public TypeInterface deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object element){
        /*  Checks if the type of another element is the same
                Throws: None
                Return: True  - if they are the same
                        False - if not
        */
        if(element instanceof IntType)
            return true;
        return false;
    }
}
