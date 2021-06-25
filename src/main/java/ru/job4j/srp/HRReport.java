package ru.job4j.srp;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HRReport implements Report{
    private Store store;

    public HRReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> employers = sortEmployers(filter);
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;");
        text.append(System.lineSeparator());
        for (Employee employee : employers) {
            text.append(employee.getName()).append(";")
                    .append(employee.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }

    private List<Employee> sortEmployers(Predicate<Employee> filter) {
        return store
                .findby(filter)
                .stream()
                .sorted((o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary()))
                .collect(Collectors.toList());

    }
}
