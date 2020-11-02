package Model.ADTs;

import Model.Exceptions.StackException;
public interface ADTStackInterface<TValue>{
    void push(TValue value);
    TValue pop() throws StackException;
    boolean isEmpty();
    String toString();
}
