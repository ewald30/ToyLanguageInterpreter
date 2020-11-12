package Model.Types;


import Model.Values.*;
public class BoolType implements TypeInterface{

    @Override
    public boolean equals(Object element){
        /*  Checks if an object is of BoolType
                Throws: None
                Return: True  - if object is of type bool
                        False - if not

        */
        if(element instanceof BoolType)
            return true;
        return false;
    }

    @Override
    public ValueInterface defaultValue() {
        /*  Returns a default value
                Throws: None
                Return: False (default value for bool type)
        */
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        /*  Returns a string representing the bool type
                Throws: None
                Return: String
        */
        return "bool";
    }

    @Override
    public TypeInterface deepCopy() {
        return new BoolType();
    }


}
