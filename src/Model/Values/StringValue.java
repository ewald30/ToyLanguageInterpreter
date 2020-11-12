package Model.Values;

import Model.Types.StringType;
import Model.Types.TypeInterface;

public class StringValue implements ValueInterface{
    String value;

    public StringValue(String value){
        //  Creates a new String with a given value
        this.value = value;
    }

    public String getValue() {
        //  Getter for the value
        return this.value;
    }

    @Override
    public ValueInterface deepCopy() {
        //  Returns a deep copy of the StringValue
        StringValue clone = new StringValue(this.value);
        return clone;
    }

    @Override
    public TypeInterface getType() {
        //  Returns the type of the StringValue
        return new StringType();
    }

    @Override
    public String toString(){
        //  Returns a string representation of the StringValue
        return this.value;
    }
}
