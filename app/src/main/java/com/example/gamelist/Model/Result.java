package com.example.gamelist.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Result {
    private ArrayList<GamePlatform> platforms;

    private ArrayList<Genre> genres;

    private ArrayList<Game> games;

    public ArrayList<GamePlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<GamePlatform> platforms) {
        this.platforms = platforms;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
