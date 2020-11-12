package Model.Values;

import Model.Types.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();
    boolean equals(Object element);
    String toString();
    ValueInterface deepCopy();
}
