package Model.ADTs;

import Model.Exceptions.DictionaryException;

import java.util.HashMap;
import java.util.Map;

public interface ADTDicionaryInterface<TKey, TValue> {
    void add(TKey key, TValue value) throws DictionaryException;
    void remove(TKey key) throws DictionaryException;
    void update(TKey key, TValue newValue) throws DictionaryException;
    TValue lookup(TKey key) throws DictionaryException;
    String toString();
    boolean isDefined(TKey key);
    Map<TKey, TValue> getContent();
    void setContent(Map<TKey, TValue> content);


}
