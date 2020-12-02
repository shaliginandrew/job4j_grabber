package ru.job4j.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

public class ParsingDate {
    private String number = "";
    private String year = "";


    public Datap parsing(Element date) {
        Datap dt = new Datap();

        String text = date.text();

        final HashMap<String, String> MONTHS = new HashMap<String, String>();
        MONTHS.put("янв", "01");
        MONTHS.put("фев", "02");
        MONTHS.put("мар", "03");
        MONTHS.put("апр", "04");
        MONTHS.put("май", "05");
        MONTHS.put("июн", "06");
        MONTHS.put("июл", "07");
        MONTHS.put("авг", "08");
        MONTHS.put("сен", "09");
        MONTHS.put("окт", "10");
        MONTHS.put("ноя", "11");
        MONTHS.put("дек", "12");



        if (text.contains("сегодня")) {
            LocalDate localDate = LocalDate.now();
            dt.resultDate = localDate.toString();
            dt.resultTime = text.split(",")[1].trim();
        }
        if (text.contains("вчера")) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            dt.resultDate = c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DAY_OF_MONTH);
            dt.resultTime = text.split(",")[1].trim();
        }
        if (!text.contains("сегодня") && !text.contains("вчера")) {
            String[] d = text.split(" ");
            if (d[0].length() == 1) {
                number = "0" + d[0];
            } else {
                number = d[0];
            }
            year = "20" + StringUtils.chop(d[2]);
            dt.resultDate = year + "-" + MONTHS.get(d[1]) + "-" + number;
            dt.resultTime = d[3].trim();
        }
        return dt;
    }


    public static class Datap {

        String resultDate = "";
        String resultTime = "";

        public Datap() {
            this.resultTime = resultTime;
            this.resultDate = resultDate;
        }
    }

}
