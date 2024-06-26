package com.paintingscollectors.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "styles")
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "style_name", unique = true, nullable = false)
    private StyleName styleName;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "style")
    private Set<Painting> paintings = new HashSet<>();

    public Style(StyleName styleName,String description) {
        this.styleName = styleName;
        this.description = description;
    }

    public Style() {
        paintings = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  StyleName getStyleName() {
        return styleName;
    }

    public void setStyleName( StyleName styleName) {
        this.styleName = styleName;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public Set<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(Set<Painting> paintings) {
        this.paintings = paintings;
    }
}