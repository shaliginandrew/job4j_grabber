package ru.job4j.html;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Post {

    private String text;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String link;

    public Post(String link, String text, LocalDate createdDate, LocalTime createdTime) {
        this.text = text;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.link = link;
    }
//

    public String getText() {
        return text;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public String getLink() {
        return link;
    }

}

