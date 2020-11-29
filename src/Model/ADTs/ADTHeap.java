package Model.ADTs;

import java.util.HashMap;

public class ADTHeap<TKey, TValue> extends ADTDictionary<TKey, TValue> implements ADTHeapInterface<TKey, TValue>{
    int lastUsedAddress = 1;        // 0 is considered an invalid address


    public int getLastUsedAddress() {
        //  Returns the last used address
        return lastUsedAddress;
    }

    @Override
    public int getNewAddress(){
        //  Returns a new address available to be used
        return lastUsedAddress++;
    }


}