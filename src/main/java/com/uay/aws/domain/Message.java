package com.uay.aws.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Message {

    private long id;
    private String text;
    private String author;
    private List<String> tags;
    private Date date;

    @JsonCreator
    public Message(@JsonProperty("id") long id,
                   @JsonProperty("text") String text,
                   @JsonProperty("author") String author,
                   @JsonProperty("tags") List<String> tags) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.tags = tags;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", date=" + date +
                '}';
    }
}