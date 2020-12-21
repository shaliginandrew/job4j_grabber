package ru.job4j.grabber;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class Post {
    private String id;
    private String text;
    private String description;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String link;

    public Post(String link, String text, String description, LocalDate createdDate, LocalTime createdTime) {
        this.text = text;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.description = description;
        this.link = link;
        //this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }
}

