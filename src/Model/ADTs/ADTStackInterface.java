package Model.ADTs;

import Model.Exceptions.MyException;
import Model.Exceptions.StackException;

import java.util.Stack;

public interface ADTStackInterface<TValue>{
    void push(TValue value);
    TValue pop() throws MyException;
    boolean isEmpty();
    String toString();
    Stack<TValue> getContent();
    void setContent(Stack<TValue> content);
    Stack<TValue> deepCopy();
}
