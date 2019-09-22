package com.example.gamelist.Utils;

import android.util.Log;

import com.example.gamelist.Model.Game;
import com.example.gamelist.Model.GamePlatform;
import com.example.gamelist.Model.Genre;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WebService {

    private static final Gson gson = new Gson();
    private static final String URL_GENRE = "https://api-v3.igdb.com/genres";
    private static final String FIELD_GENRE = "fields id,name;";
    private static final String URL_PLATFORM = "https://api-v3.igdb.com/platforms";
    private static final String FIELD_PLATFORM = "fields id,name;";
    private static final String URL_GAMES = "https://api-v3.igdb.com/games";
    private static final String FIELD_GAME = "fields id,name,genres.name,platforms.name,cover.url,rating;";
    private static final Type arrayOfGenre = new TypeToken<ArrayList<Genre>>() {}.getType();
    private static final Type arrayOfPlatform = new TypeToken<ArrayList<GamePlatform>>() {}.getType();
    private static final Type arrayOfGame = new TypeToken<ArrayList<Game>>() {}.getType();


    public static ArrayList<Genre> getGenres(String query) throws Exception {

        OkHttputils okHttputils = new OkHttputils();
        String response = "";
        try {
            response = OkHttputils.sendPostOkHttpRequest(URL_GENRE, FIELD_GENRE + query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("tag", response);

        ArrayList<Genre> result = gson.fromJson(response, arrayOfGenre);

        if (result == null) {
            throw new Exception("nothing return");
        }
        return result;

    }

    public static ArrayList<GamePlatform> getPlatform(String query) throws Exception {

        OkHttputils okHttputils = new OkHttputils();
        String response = "";
        try {
            response = OkHttputils.sendPostOkHttpRequest(URL_PLATFORM, FIELD_PLATFORM + query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("tag", response);

        ArrayList<GamePlatform> result = gson.fromJson(response, arrayOfPlatform);

        if (result == null) {
            throw new Exception("nothing return");
        }
        return result;

    }

    public static ArrayList<Game> getGame(String query) throws Exception {

        OkHttputils okHttputils = new OkHttputils();
        String response = "";
        Log.w("url", FIELD_GAME + query);
        try {
            response = OkHttputils.sendPostOkHttpRequest(URL_GAMES, FIELD_GAME + query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("tag", response);

        ArrayList<Game> result = gson.fromJson(response, arrayOfGame);

        if (result == null) {
            throw new Exception("nothing return");
        }
        return result;

    }
}
