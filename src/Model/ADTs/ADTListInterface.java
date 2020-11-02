package Model.ADTs;

import Model.Exceptions.ListException;

public interface ADTListInterface<TValue> {
    void add(TValue value);
    TValue pop() throws ListException;
    String toString();
}
