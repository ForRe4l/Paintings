package com.paintingscollectors.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany
    private Set<Painting> paintings = new HashSet<>();

    @ManyToMany
    private List<Painting> favoritePaintings ;

    @ManyToMany
    private Set<Painting> ratedPaintings = new HashSet<>();

    public User() {
        favoritePaintings = new ArrayList<>();
        ratedPaintings = new HashSet<>();
        paintings = new HashSet<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(Set<Painting> paintings) {
        this.paintings = paintings;
    }

    public List<Painting> getFavoritePaintings() {
        return favoritePaintings;
    }

    public void setFavoritePaintings(List<Painting> favoritePaintings) {
        this.favoritePaintings = favoritePaintings;
    }

    public Set<Painting> getRatedPaintings() {
        return ratedPaintings;
    }

    public void setRatedPaintings(Set<Painting> ratedPaintings) {
        this.ratedPaintings = ratedPaintings;
    }

    public void addFavourite(Painting favoritePaintings) {this.favoritePaintings.add(favoritePaintings);}

    public void removeFavourite(Painting painting) {
        this.favoritePaintings.remove(painting);
    }

    public void removeVoted(Painting painting) {
        this.ratedPaintings.remove(painting);
    }
}


