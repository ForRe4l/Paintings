package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import org.w3c.dom.Text;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class addPaintingDTO {

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

    public addPaintingDTO() {}

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
}
