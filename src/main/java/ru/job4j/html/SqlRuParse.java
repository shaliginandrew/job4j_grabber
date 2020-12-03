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


        public static void loadPostDetails(Post postDetails) {

            ArrayList<Post> postData = new ArrayList<>();
            postData.add(postDetails);

            System.out.println(postDetails.getLink());
            System.out.println(postDetails.getText());
             System.out.println(postDetails.getCreatedDate());
            System.out.println(postDetails.getCreatedTime());

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


                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.ENGLISH);
                        LocalDate dateNewFormat = LocalDate.parse(p.resultDate, dtf);

                        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
                        LocalTime time = LocalTime.parse(p.resultTime, dt);



                        Post postDetails = new Post(href.attr("href"), href.text(), dateNewFormat, time);

                        loadPostDetails(postDetails);



                    }
                }
            }


        }

        public static void main(String[] args) throws Exception {

            SqlRuParse sqlRuParse = new SqlRuParse();
            sqlRuParse.parsingPage();

        }
    }
