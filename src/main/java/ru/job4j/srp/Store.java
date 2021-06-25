package ru.job4j.srp;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    List<Employee> findby(Predicate<Employee> filter);
}
