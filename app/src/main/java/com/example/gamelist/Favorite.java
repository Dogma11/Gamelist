package com.example.gamelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gamelist.Adapter.GameAdapter;
import com.example.gamelist.Model.Game;
import com.example.gamelist.Utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Favorite extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private RecyclerView recyclerGame;

    private ArrayList<Game> data;
    private GameAdapter gameAdapter;

    private Context context;

    private SharedPreferences prefs;
    private SharedPreferenceUtils myPrefs;

    public static Game selectedGame = null;

    private static final Type collectionOfGame = new TypeToken<Collection<Game>>() {}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerGame = findViewById(R.id.recyclerFavorite);
        recyclerGame.setLayoutManager(new LinearLayoutManager( this,  LinearLayoutManager.VERTICAL,false));
        context = getApplicationContext();

        prefs = context.getSharedPreferences("Favorite.xml", MODE_PRIVATE);
        myPrefs = new SharedPreferenceUtils(prefs);
        data = myPrefs.loadGames();
        gameAdapter = new GameAdapter(data);
        gameAdapter.setOnGameClickListener(this);
        recyclerGame.setAdapter(gameAdapter);

    }


    @Override
    public void onGameClick(Game game) {
        selectedGame = game;
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedGame", selectedGame);
        startActivity(new Intent( this, GameDetail.class ).putExtras(bundle));
    }
}
