package ru.job4j.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        SqlRuParse sqlRuParse = new SqlRuParse();
        String link = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(link + String.valueOf(i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);

                if (!href.text().contains("Сообщения от модераторов")
                        && !href.text().contains("Правила форума")
                        && !href.text().contains("Шпаргалки")) {
                    System.out.println(href.attr("href"));
                    System.out.println(href.text());

                    Element date = td.parent().child(5);
                    ParsingDate parsingDate = new ParsingDate();
                    parsingDate.parsing(date);
                    sqlRuParse.newFormat(parsingDate.parsing(date));
                }
            }
        }
    }
}
