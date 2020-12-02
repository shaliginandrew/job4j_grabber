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
import java.util.HashMap;
import java.util.Locale;

public class SqlRuParse {


    public void newFormat(ParsingDate.Datap par) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.ENGLISH);
        LocalDate dateNewFormat = LocalDate.parse(par.resultDate, dtf);

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        LocalTime time = LocalTime.parse(par.resultTime, dt);
        System.out.println(dateNewFormat + " " + par.resultTime);
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
                ParsingDate parsingDate = new ParsingDate();
                parsingDate.parsing(date);
                sqlRuParse.newFormat(parsingDate.parsing(date));
            }
            count++;
        }
    }
}