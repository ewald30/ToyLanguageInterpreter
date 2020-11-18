package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.TypeInterface;

public class ReferenceValue implements ValueInterface{
    private final int address;
    private final TypeInterface locationType;

    public ReferenceValue(int address, TypeInterface locationType) {
        //  Constructor for Reference Value
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        //  Returns the address
        return address;
    }

    public TypeInterface getLocationType() {
        //  Returns the locationType
        return locationType;
    }

    @Override
    public TypeInterface getType() {
        //  Returns the type of the reference
        return new ReferenceType(locationType);
    }

    @Override
    public ValueInterface deepCopy() {
        //  Returns a copy of the reference value
        return new ReferenceValue(address, locationType.deepCopy());
    }

    @Override
    public String toString(){
        //  Returns a string representation of the Reference Value
        return String.valueOf(address) + locationType.toString();
    }

}
