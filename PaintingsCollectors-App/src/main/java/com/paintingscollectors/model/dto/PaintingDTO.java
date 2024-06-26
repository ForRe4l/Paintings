package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import org.w3c.dom.Text;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaintingDTO {
    private long id;

    private User owner;

    @NotBlank
    @Size(min = 5, max = 40)
    private String name;

    @NotBlank
    @Size(min = 5, max = 40)
    private String author;

    @NotBlank
    private String imageUrl;

    @NotNull
    private StyleName styleName;

    public PaintingDTO(long id, User owner, String name, String author, String imageUrl, StyleName styleName) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.styleName = styleName;
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

    public  String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public  StyleName getStyleName() {
        return styleName;
    }

    public void setStyleName(StyleName style) {
        this.styleName = style;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }


}
