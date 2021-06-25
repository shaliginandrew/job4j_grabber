package ru.job4j.srp;

import org.junit.Test;
import ru.job4j.srp.AccountantReport;
import ru.job4j.srp.Employee;
import ru.job4j.srp.MemStore;
import ru.job4j.srp.Report;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.Calendar;

public class AccountantReportTest {
    @Test
    public void whenOldGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 150);
        store.add(worker);
        Report engine = new AccountantReport(store);
        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(worker.getName()).append(";")
                .append(worker.getHired()).append(";")
                .append(worker.getFired()).append(";")
                .append("2,50").append(";")
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true), is(expect.toString()));
    }
}
