package ru.job4j.srp;

import org.junit.Test;
import ru.job4j.srp.Employee;
import ru.job4j.srp.MemStore;
import ru.job4j.srp.ProgrammerReport;
import ru.job4j.srp.Report;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.Calendar;
import java.util.StringJoiner;

public class ProgrammerReportTest {
    @Test
    public void whenOldGenerated() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        Report engine = new ProgrammerReport(store);

        StringJoiner html = new StringJoiner(System.lineSeparator());

        html.add("<!DOCTYPE html>");
        html.add("<html>");
        html.add("<head>");
        html.add("<meta charset=\"UTF-8\">");
        html.add("<title>Employers</title>");
        html.add("</head>");
        html.add("<body>");

        html.add("<table>");
        html.add("<tr>");
        html.add("<th>Name</th>");
        html.add("<th>Hired</th>");
        html.add("<th>Fired</th>");
        html.add("<th>Salary</th>");
        html.add("</tr>");

        html.add("<tr>");
        html.add(String.format("<td>%s</td>", worker.getName()));
        html.add(String.format("<td>%s</td>", worker.getHired()));
        html.add(String.format("<td>%s</td>", worker.getFired()));
        html.add(String.format("<td>%s</td>", worker.getSalary()));
        html.add("</tr>");

        html.add("</table>");
        html.add("</body>");
        html.add("</html>");

        assertThat(engine.generate(em -> true), is(html.toString()));
    }
}
