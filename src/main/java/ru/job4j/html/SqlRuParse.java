package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class SqlRuParse {

    private ArrayList<Post> postData;
    private LocalDate dateStandart;
    private LocalTime timeStandart;

    public SqlRuParse() {
        this.postData = new ArrayList<>();
    }

    public void loadPostDetails(Post postDetails) {
        postData.add(postDetails);
    }

    public void dateAndTimeStandart(ParsingDate.Datap p) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.ENGLISH);
        dateStandart = LocalDate.parse(p.resultDate, dtf);
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        timeStandart = LocalTime.parse(p.resultTime, dt);
    }

    public void parsingPage() throws IOException {
        ParsingDate parsingDate = new ParsingDate();
        String link = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {


            Document doc = Jsoup.connect(link + String.valueOf(i)).get();



            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                if (!href.text().contains("Сообщения от модераторов")
                        && !href.text().contains("Правила форума")
                        && !href.text().contains("Шпаргалки")) {
                    Element date = td.parent().child(5);
                    ParsingDate.Datap p = parsingDate.parsing(date);
                    dateAndTimeStandart(p);
                    String description = description(href.attr("href"));
                    Post postDetails = new Post(href.attr("href"), href.text(), description,  dateStandart, timeStandart);
                    loadPostDetails(postDetails);
                }
            }
        }
    }

    private static String description(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        return doc
                .select(".msgTable").first()
                .select(".msgBody").get(1)
                .text();
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();
        sqlRuParse.parsingPage();
        for (Post t : sqlRuParse.postData) {
            System.out.println(t.getLink());
            System.out.println(t.getText());
            System.out.println(t.getCreatedDate() + " " + t.getCreatedTime());
            System.out.println(t.getDescription());
        }
    }
}
