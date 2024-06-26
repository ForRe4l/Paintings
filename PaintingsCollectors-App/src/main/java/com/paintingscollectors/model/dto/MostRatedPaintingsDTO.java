package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MostRatedPaintingsDTO {
    private long id;
    private String name;
    private String author;
    private int votes;

    public MostRatedPaintingsDTO(long id, String name, String author,int votes) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.votes = votes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
