package Model.Values;

import Model.Types.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();
    String toString();
    ValueInterface deepCopy();
}
