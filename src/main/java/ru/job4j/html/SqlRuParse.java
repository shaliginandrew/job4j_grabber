package ru.job4j.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class SqlRuParse {

    private String number = "";
    private String month = "";
    private String year = "";
    private String resultDate = "";
    private String resultTime = "";

    public void parsing(Element date) {

        String text = date.text();
        if (text.contains("янв")) {
            month = "01";
        }
        if (text.contains("фев")) {
            month = "02";
        }
        if (text.contains("мар")) {
            month = "03";
        }
        if (text.contains("апр")) {
            month = "04";
        }
        if (text.contains("май")) {
            month = "05";
        }
        if (text.contains("июн")) {
            month = "06";
        }
        if (text.contains("июл")) {
            month = "07";
        }
        if (text.contains("авг")) {
            month = "08";
        }
        if (text.contains("сен")) {
            month = "09";
        }
        if (text.contains("окт")) {
            month = "10";
        }
        if (text.contains("ноя")) {
            month = "11";
        }
        if (text.contains("дек")) {
            month = "12";
        }
        if (text.contains("сегодня")) {
            LocalDate localDate = LocalDate.now();
            resultDate = localDate.toString();
            resultTime = text.split(",")[1].trim();
        }
        if (text.contains("вчера")) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            resultDate = c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DAY_OF_MONTH);
            resultTime = text.split(",")[1].trim();
        }
        if (!text.contains("сегодня") && !text.contains("вчера")) {
            String[] d = text.split(" ");
            if (d[0].length() == 1) {
                number = "0" + d[0];
            }
            else {
                number = d[0];
            }
            year = "20" + StringUtils.chop(d[2]);
            resultDate = year + "-" + month + "-" + number;
            resultTime = d[3].trim();
        }
    }

    public void newFormat() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateNewFormat = LocalDate.parse(resultDate, dtf);

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        LocalTime time = LocalTime.parse(resultTime, dt);
        System.out.println(dateNewFormat + " " + resultTime);
        System.out.println("---------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        int count = 0;
        SqlRuParse sqlRuParse = new SqlRuParse();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            if (count > 2) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element date = td.parent().child(5);
                sqlRuParse.parsing(date);
                sqlRuParse.newFormat();
            }
            count ++;
        }
    }
}