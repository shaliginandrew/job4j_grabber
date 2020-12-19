package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlRuParse implements Parse {

    private ArrayList<Post> posts;
    private LocalDate dateStandart;
    private LocalTime timeStandart;

    public SqlRuParse() {
        this.posts = new ArrayList<>();
    }


    public void dateAndTimeStandart(ParsingDate.Datap p) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.ENGLISH);
        dateStandart = LocalDate.parse(p.resultDate, dtf);
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        timeStandart = LocalTime.parse(p.resultTime, dt);
    }


    private static String description(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        return doc
                .select(".msgTable").first()
                .select(".msgBody").get(1)
                .text();
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sql = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            sql.list(url + i);
        }
        int n = 1;
        for (Post t : sql.posts) {
            System.out.println(n);
            System.out.println(t.getLink());
            System.out.println(t.getText());
            System.out.println(t.getCreatedDate() + " " + t.getCreatedTime());
            System.out.println(t.getDescription());
            n++;
        }

    }

    @Override
    public List<Post> list(String link) throws IOException {

        Post postDetails = null;
        ParsingDate parsingDate = new ParsingDate();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            if (!href.text().contains("Сообщения от модераторов")
                    && !href.text().contains("Правила форума")
                    && !href.text().contains("Шпаргалки")) {
                Element date = td.parent().child(5);
                ParsingDate.Datap p = parsingDate.parsing(date);
                dateAndTimeStandart(p);
                postDetails = detail(href.attr("href"));
                posts.add(postDetails);
            }
        }
        return posts;
    }


    @Override
    public Post detail(String link) throws IOException {
        Post post = null;
        String description = description(link);
        Document doc = Jsoup.connect(link).get();
        post = new Post(link, doc.select(".messageHeader")
                .text(), description, dateStandart, timeStandart);
        return post;
    }
}