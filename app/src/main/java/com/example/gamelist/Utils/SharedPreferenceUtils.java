package com.example.gamelist.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gamelist.Model.Game;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceUtils {

    private SharedPreferences mPrefs;

    public SharedPreferenceUtils (SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
    }

    public void saveGame(Game selectedGame){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString( selectedGame.getId() , new Gson().toJson( selectedGame) );
        editor.apply();
    }

    public ArrayList<Game> loadGames(){
        ArrayList<Game> data = new ArrayList<Game>();
        Gson gson = new Gson();
        HashMap<String, String> result = (HashMap<String, String>) mPrefs.getAll();
        for (String values : result.values()) {
            data.add(gson.fromJson(values,  Game.class));
        }
        return data;
    }

    public void removeGame(Game selectedGame){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove( selectedGame.getId() );
        editor.apply();
    }

    public boolean isPresent ( Game game) {
        return mPrefs.contains(game.getId());
    }
}
