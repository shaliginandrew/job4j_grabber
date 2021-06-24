package ru.job4j.kiss;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MaxMin {
    public static <T> T max(List<T> value, Comparator<T> comparator) {
        return value.isEmpty() ? null : maxMin(value, comparator.reversed());
    }

    public static <T> T min(List<T> value, Comparator<T> comparator) {
        return value.isEmpty() ? null : maxMin(value, comparator);
    }

    public static <T> T maxMin(List<T> list, Comparator<T> comparator) {
        Iterator<T> i = list.iterator();
        T val = i.next();
        while (i.hasNext()) {
            T curr = i.next();
            if (comparator.compare(val, curr) > 0) {
                val = curr;
            }
        }
        return val;
    }
}