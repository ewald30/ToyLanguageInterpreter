package Model.Types;

import Model.Values.*;

public class StringType implements TypeInterface {

    @Override
    public boolean equals(Object element){
        /*  Checks if an element is of type string
                Throws: None
                Return: True  - if the object is of type string
                        False - otherwise
        */
        if(element instanceof StringType)
            return true;
        return false;
    }

    @Override
    public ValueInterface defaultValue() {
        /*  Returns the default value for te string type
                Throws: None
                Return: "" (default value)
        */
        return new StringValue("");
    }

    @Override
    public TypeInterface deepCopy() {
        return new StringType();
    }

    @Override
    public String toString(){
        return "string";
    }
}
