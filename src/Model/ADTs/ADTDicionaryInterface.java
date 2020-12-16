package Model.ADTs;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;

public interface ADTDicionaryInterface<TKey, TValue> {
    void add(TKey key, TValue value) throws MyException;
    void remove(TKey key) throws MyException;
    void update(TKey key, TValue newValue) throws MyException;
    TValue lookup(TKey key) throws MyException;
    String toString();
    boolean isDefined(TKey key);
    Map<TKey, TValue> getContent();
    Map<TKey, TValue> deepCopy();
    void setContent(Map<TKey, TValue> content);


}
