package com.paintingscollectors.model.entity;

import org.w3c.dom.Text;

import javax.persistence.*;


@Entity
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;


    @ManyToOne
    private Style style;

    @ManyToOne
    private User owner;


    @Column(name = "image_url", nullable = false)
    private String imageUrl;


    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite;


    @Column(name = "votes", nullable = false)
    private int votes;

    public Painting() {}

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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public StyleName getStyleName() {
        return style.getStyleName();
    }
}