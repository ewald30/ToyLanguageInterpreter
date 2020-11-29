package Model.ADTs;

import Model.Exceptions.ListException;

import java.util.List;

public interface ADTListInterface<TValue> {
    void add(TValue value);
    TValue pop() throws ListException;
    String toString();
}
