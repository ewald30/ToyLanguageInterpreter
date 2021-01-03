package Model.ADTs;

import Model.Exceptions.ListException;
import Model.Exceptions.MyException;

import java.awt.*;
import java.util.ArrayList;

public class ADTList<TValue> implements ADTListInterface<TValue> {
    private final ArrayList<TValue> list;

    public ArrayList<TValue> getContent() {
        return list;
    }

    public ADTList(){
        /*  Creates a new list
                Throws: None
                Return: None
        */
        this.list = new ArrayList<TValue>();
    }

    @Override
    public void add(TValue value){
        /*  Adds a new value to the list
                Throws: None
                Return: None
        */
        this.list.add(value);
    }

    @Override
    public TValue pop() throws MyException {
        /*  Executes a pop operation on the list
                Throws: ListException if list is empty
                Return: (TValue) value found at position 0 in list
        */
        if (this.list.isEmpty())
            throw new ListException("List is empty!");

        return this.list.get(0);
    }

    @Override
    public String toString(){
        /*  Creates and returns a string containing the elements from the list
                Throws: None
                Return: String containg all elements from the list
                Format: Tvalue, TValue, ...
        */
        StringBuilder result = new StringBuilder();
        for(TValue object : this.list){
            result.append(object);
            result .append(",");
        }

        return result.toString();
    }

}
