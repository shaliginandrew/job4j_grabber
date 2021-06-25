package ru.job4j.srp;

import java.util.StringJoiner;
import java.util.function.Predicate;

public class ProgrammerReport implements Report {
    private Store store;

    public ProgrammerReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
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

        for (Employee employer : store.findby(filter)) {
            html.add("<tr>");
            html.add(String.format("<td>%s</td>", employer.getName()));
            html.add(String.format("<td>%s</td>", employer.getHired()));
            html.add(String.format("<td>%s</td>", employer.getFired()));
            html.add(String.format("<td>%s</td>", employer.getSalary()));
            html.add("</tr>");
        }

        html.add("</table>");
        html.add("</body>");
        html.add("</html>");

        return html.toString();
    }
}

