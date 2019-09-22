package com.example.gamelist.Model;

import android.net.Uri;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.internal.platform.Platform;

public class Game {

    private String id;

    private String name;

    private ArrayList<Genre> genres;

    private ArrayList<GamePlatform> platforms;

    private Cover cover;

    private Double rating;


    public Cover getCovers() {
        return cover;
    }

    public void setCovers(Cover covers) {
        this.cover = covers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<GamePlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<GamePlatform> platforms) {
        this.platforms = platforms;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
