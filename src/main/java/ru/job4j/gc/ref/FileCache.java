package ru.job4j.gc.ref;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class FileCache implements Cache<String, String>{
    private Map<String, SoftReference<String>> map = new HashMap<>();


    @Override
    public String get(String key) throws IOException {
        return null;
    }

    @Override
    public void put(String key, String value) {

    }
}
