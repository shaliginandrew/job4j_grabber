package ru.job4j.gc.ref;
import java.io.IOException;

public interface Cache<K, V> {

    V get(K key) throws IOException;
    void put(K key, V value);
}
