package Model.ADTs;

import Model.Exceptions.DictionaryException;

import java.util.HashMap;

public class ADTDictionary<TKey, TValue> implements ADTDicionaryInterface<TKey, TValue> {
    private final HashMap<TKey, TValue> dictionary;

    public ADTDictionary(){
        /* Creates a new Dictionary
                Throws: None
                Return: None
        */
        this.dictionary = new HashMap<>();
    }

    @Override
    public void add(TKey key, TValue value) throws DictionaryException {
        /*  Adds a (key, value) pair to dictionary
                Throws: DictionaryException if the pair is already stored in dicionary
                Return: None
        */
        if(this.dictionary.containsKey(key))
            throw new DictionaryException("Key already stored!");

        this.dictionary.put(key, value);
    }

    @Override
    public void update(TKey key, TValue updateValue) throws  DictionaryException{
        /*  Updates a (key, value) pair from dictionary
                Throws: DictionaryException if pair is not stored in dicionary
                Return: None
        */
        if(!this.dictionary.containsKey(key))
            throw new DictionaryException("Record does not exist in dictionary!");

        this.dictionary.replace(key, updateValue);
    }

    @Override
    public TValue lookup(TKey key) throws DictionaryException{
        /*  Checks if a pair exists in dicionary
                Throws: DictionaryException if the pair is not stored in dictionary
                Return: The Value that is found at the given Key in dicionary
        */
        if(!this.dictionary.containsKey(key))
            throw new DictionaryException("Record does not exist in dictionary!");

        return this.dictionary.get(key);
    }

    @Override
    public String toString(){
        /*  Builds a String using the content of dicionary
                Throws: None
                Return: String containing information about the (key, value) stored in dicionary
                Format: TKey -> TValue;
        */
        StringBuilder result = new StringBuilder();

        for(TKey key : this.dictionary.keySet()){
            result.append(key);
            result.append(" -> ");
            result.append(this.dictionary.get(key).toString());
            result.append("; ");
        }
        return result.toString();
    }

    @Override
    public boolean isDefined(String key){
        /*  Checks if a pair with a given key is defined dictionary
                Throws: None
                Return: - True  - if the pair is found
                        - False - if the pair is not found
        */
        return this.dictionary.containsKey(key);
    }
}
