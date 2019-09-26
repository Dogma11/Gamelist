package com.example.gamelist.Model;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.internal.platform.Platform;

public final class Game implements Serializable {

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

    public static ArrayList<String> getArrayPlatformName (ArrayList<GamePlatform> dataPlatform) {
        ArrayList<String> platformNames = new ArrayList<>();
        for (GamePlatform platform : dataPlatform) {
            platformNames.add(platform.getName());
        }
        return platformNames;
    }

    public static ArrayList<String> getArrayGenresName (ArrayList<Genre> dataGenre) {
        ArrayList<String> genreNames = new ArrayList<>();
        for (Genre genre : dataGenre) {
            genreNames.add(genre.getName());
        }
        return genreNames;
    }

    public static String getGenreByName (String name, ArrayList<Genre> dataGenre) {
        String genreId = "";
        for(Genre genre : dataGenre){
            if(genre.getName() != null && name != "" && genre.getName().contains(name)) {
                return genre.getId();
            }
        }
        return genreId;
    }

    public static String getPlatformByName (String name, ArrayList<GamePlatform> dataPlatform) {
        String platformId = "";
        for(GamePlatform platform : dataPlatform){
            if(platform.getName() != null && name != "" && platform.getName().contains(name)) {
                return platform.getId();
            }
        }
        return platformId;
    }

    public static String getPlatformsName (ArrayList<GamePlatform> iterator) {
        String string;
        Integer i = 0;
        StringBuilder builder = new StringBuilder();
        if (iterator.size() <= 0) {
            return "";
        }
        if (iterator.size() > 1){
            for (GamePlatform platform : iterator)
            {
                i ++;
                if (platform != null) {
                    builder.append(platform.getSanitazedName());
                    if (i != iterator.size()){
                        builder.append(", ");
                    }
                }
            }
            string = builder.toString();
        } else {
            string = iterator.get(0).getSanitazedName();
        }
        return string;
    }

    public static String getGenresName (ArrayList<Genre> iterator) {
        String string;
        StringBuilder builder = new StringBuilder();
        Integer i = 0;
        if (iterator.size() <= 0) {
            return "";
        }
        if (iterator.size() > 1){
            for (Genre genre : iterator)
            {
                i ++;
                if (genre != null) {
                    builder.append(genre.getSanitazedName());
                    if (i != iterator.size()){
                        builder.append(", ");
                    }
                }
            }
            string = builder.toString();
        } else {
            string = iterator.get(0).getSanitazedName();
        }
        return string;
    }

}
