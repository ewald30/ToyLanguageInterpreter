package Model.ADTs;
import Model.Exceptions.MyException;
import Model.Exceptions.StackException;

import java.util.Stack;

public class ADTStack<TValue> implements ADTStackInterface<TValue> {
    private Stack<TValue> ADTStack;

    public ADTStack(){
        /*  Creates a new stack
                Throws: None
                Return: None
        */
        this.ADTStack = new Stack<TValue>();
    }

    @Override
    public void push(TValue value){
        /*  Adds a new element to the stack
                Throws: None
                Return: None
        */
        this.ADTStack.add(value);
    }

    @Override
    public TValue pop() throws MyException {
        /*  Pops a value from the Stack
                Throws: StackException if the stack is empty
                Return: (TValue) Value found at the top of the stack
        */
        if (ADTStack.isEmpty())
            throw new StackException("Stack is empty!");

        return ADTStack.pop();
    }

    @Override
    public boolean isEmpty(){
        /*  Checks if the stack is empty
                Throws: None
                Return: True  - if the stack is empty
                        False - if the stack is not empty
        */
        return ADTStack.isEmpty();
    }

    @Override
    public String toString(){
        /*  Builds a string containig all the elements from the stack
                Throws: None
                Return: String containing all the elements from the stack
        */
        StringBuilder result = new StringBuilder();
        Object[] elements = this.ADTStack.toArray();

        for(int i=elements.length-1; i>=0; --i){
            result.append(elements[i]);
            result.append(", ");
        }
        return result.toString();
    }

    @Override
    public Stack<TValue> getContent() {
        //  Returns the content of the stack
        return ADTStack;
    }

    @Override
    public void setContent(Stack<TValue> content) {
        //  Sets the content of the stack
        ADTStack = content;
    }

    @Override
    public Stack<TValue> deepCopy() {
        return (Stack<TValue>) ADTStack.clone();
    }
}
