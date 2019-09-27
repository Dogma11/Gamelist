package com.example.gamelist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamelist.Adapter.GameAdapter;
import com.example.gamelist.Async.GameAsync;
import com.example.gamelist.Async.GenreAsync;
import com.example.gamelist.Async.PlatformAsync;
import com.example.gamelist.Model.Game;
import com.example.gamelist.Model.GamePlatform;
import com.example.gamelist.Model.Genre;

import java.util.ArrayList;

public class  MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {
    
    private EditText searchName;
    
    private Spinner textGenre, textPlatform, textRating;

    private RecyclerView recyclerGame;

    private ArrayList<Game> data;
    private ArrayList<Genre> dataGenre;
    private ArrayList<GamePlatform> dataPlatform;

    private GameAdapter gameAdapter;

    private GenreAsync genreAsync;
    private PlatformAsync platformAsync;
    private GameAsync gameAsync;

    public static Game selectedGame = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        searchName = findViewById(R.id.searchName);
        textGenre = findViewById(R.id.textGenre);
        textPlatform = findViewById(R.id.textPlatform);
        textRating = findViewById(R.id.textRating);
        recyclerGame = findViewById(R.id.recycleGame);


        data = new ArrayList<>();
        dataGenre = new ArrayList<>();
        dataPlatform = new ArrayList<>();

        gameAdapter = new GameAdapter(data);
        gameAdapter.setOnGameClickListener(this);

        recyclerGame.setAdapter(gameAdapter);
        recyclerGame.setLayoutManager(new LinearLayoutManager(this));
        genreAsync = new GenreAsync(textGenre, this);
        try {
            genreAsync.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        platformAsync = new PlatformAsync(textPlatform, this);
        try {
            platformAsync.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> ratings = new ArrayList<>();
        for (Integer i = 0; i <= 100; i++) {
            ratings.add( String.valueOf(i) );
        }

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textRating.setAdapter(ratingAdapter);

    }
    // DATA SETTER GETTER CLEANER
    // GAMEDATA
    public void addAllData(ArrayList<Game> data) {
        this.data.addAll(data);
    }
    public void clearData() {
        this.data.clear();
    }
    // GENRE DATA
    public void addAllDataGenre(ArrayList<Genre> dataGenre) {
        this.dataGenre.addAll(dataGenre);
        Log.w("tag", this.dataGenre.getClass().toString());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.addAll(Game.getArrayGenresName(this.dataGenre));
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textGenre.setAdapter(genreAdapter);
        genreAsync = null;
    }

    // PLATFORM DATA
    public void addAllDataPlatform(ArrayList<GamePlatform> dataPlatform) {
        this.dataPlatform.addAll(dataPlatform);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.addAll(Game.getArrayPlatformName(this.dataPlatform));
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textPlatform.setAdapter(platformAdapter);
        platformAsync = null;
    }

    public void onBtSend(View view) {
        String selectedPlatform = Game.getPlatformByName(textPlatform.getSelectedItem().toString(), dataPlatform);
        String selectedGenre = Game.getGenreByName(textGenre.getSelectedItem().toString(), dataGenre);
        String ratingMin = String.valueOf(textRating.getSelectedItem());
        String searchQuery;
        if (!searchName.getText().toString().isEmpty()){
            searchQuery = searchName.getText().toString();
        } else {
            searchQuery = "";
        }

        Log.e("tag", "here " + searchQuery);
        if (gameAsync == null || gameAsync.getStatus() == AsyncTask.Status.FINISHED) {

            gameAsync = new GameAsync(recyclerGame, this, selectedGenre, selectedPlatform, ratingMin, searchQuery);
            gameAsync.execute();
        }
    }

    //ADAPTER
    public GameAdapter getGameAdapter() {
        return gameAdapter;
    }

    public void setGameAdapter(GameAdapter gameAdapter) {
        this.gameAdapter = gameAdapter;
    }

    @Override
    public void onGameClick (Game game) {
        selectedGame = game;
        Bundle bundle = new Bundle();
        Log.e("click", "clicked");
        bundle.putSerializable("selectedGame", selectedGame);
        startActivity(new Intent(this, GameDetail.class).putExtras(bundle) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,25,0, "Favorite");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 25:
                startActivity( new Intent( this, Favorite.class ) );
        }
        return super.onOptionsItemSelected(item);
    }
}
