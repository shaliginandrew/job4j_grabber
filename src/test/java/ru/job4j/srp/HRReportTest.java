package ru.job4j.srp;

import org.junit.Test;
import ru.job4j.srp.Employee;
import ru.job4j.srp.HRReport;
import ru.job4j.srp.MemStore;
import ru.job4j.srp.Report;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.Calendar;

public class HRReportTest {
    @Test
    public void whenOldGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker1 = new Employee("Ivan", now, now, 100);
        Employee worker2 = new Employee("Petr", now, now, 120);
        store.add(worker1);
        store.add(worker2);
        Report engine = new HRReport(store);
        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(System.lineSeparator())
                .append(worker2.getName()).append(";")
                .append(worker2.getSalary()).append(";")
                .append(System.lineSeparator())
                .append(worker1.getName()).append(";")
                .append(worker1.getSalary()).append(";")
                .append(System.lineSeparator());
        assertThat(engine.generate(m -> true), is(expect.toString()));
    }
}
