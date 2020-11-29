package Model.Types;

import Model.Values.*;


public class ReferenceType implements TypeInterface{
    private final TypeInterface inner;

    public ReferenceType(TypeInterface inner) {
        //  Constructor fo reference type
        this.inner = inner;
    }

    public TypeInterface getInner() {
        //  Getter for reference type
        return inner;
    }

    @Override
    public boolean equals(Object element){
        /*  Checks an element is an instance of ReferenceType
                Throws: None
                Return: True    -   if the element is an instance of ReferenceType
                        False   -   otherwise
        */
        if (element instanceof ReferenceType)
            return inner.equals(((ReferenceType)element).getInner());
        return false;
    }

    @Override
    public ValueInterface defaultValue() {
        //  Returns the default value of an ReferenceType instance
        return new ReferenceValue(0,inner);
    }

    @Override
    public TypeInterface deepCopy() {
        //  Returns a copy of the reference type
        return new ReferenceType(inner.deepCopy());
    }

    @Override
    public String toString(){
        //  Returns a string representation of the reference type
        return "Ref(" + inner.toString()+")";
    }
}
