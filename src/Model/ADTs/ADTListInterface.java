package Model.ADTs;

import Model.Exceptions.ListException;
import Model.Exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public interface ADTListInterface<TValue> {
    void add(TValue value);
    TValue pop() throws MyException;
    String toString();
    ArrayList<TValue> getContent();
}
