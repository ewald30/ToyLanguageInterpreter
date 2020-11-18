package Model.ADTs;

import java.util.Set;

public interface ADTHeapInterface <TKey, TValue> extends ADTDicionaryInterface <TKey, TValue>{
    int getNewAddress();
}

