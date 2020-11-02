package Model.Types;

import Model.Values.BoolValue;
import Model.Values.ValueInterface;

public interface TypeInterface {
    boolean equals(Object element);
    ValueInterface defaultValue();
    TypeInterface deepCopy();
    String toString();
}
