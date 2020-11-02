package Model.ADTs;

import Model.Exceptions.DictionaryException;
public interface ADTDicionaryInterface<TKey, TValue> {
    void add(TKey key, TValue value) throws DictionaryException;
    void update(TKey key, TValue newValue) throws DictionaryException;
    TValue lookup(TKey key) throws DictionaryException;
    String toString();
    boolean isDefined(String key);

}
