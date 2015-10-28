package net.ucopy.drugcheck.tools;

import java.io.Serializable;
import java.util.Map.Entry;

public final class KeyValueEntry<K, V> implements Entry<K, V>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final K key;
    private V value;

    public KeyValueEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }


}