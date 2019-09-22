package com.example.gamelist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    
    private EditText searchName;
    
    private Spinner textGenre, textPlatform, textRating;

    private RecyclerView recyclerGame;

    private ArrayList<Game> data;
    private ArrayList<Genre> dataGenre;
    private ArrayList<GamePlatform> dataPlatform;

    private GameAdapter gameAdaper;

    private GenreAsync genreAsync;
    private PlatformAsync platformAsync;
    private GameAsync gameAsync;


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

        gameAdaper = new GameAdapter(data);

        recyclerGame.setAdapter(gameAdaper);
        recyclerGame.setLayoutManager(new LinearLayoutManager(this));
        genreAsync = new GenreAsync(textGenre, this);
        genreAsync.execute();

        platformAsync = new PlatformAsync(textPlatform, this);
        platformAsync.execute();
        ArrayList<String> ratings = new ArrayList<>();
        for (Integer i = 0; i <= 100; i++) {
            ratings.add( i.toString() );
        }

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textRating.setAdapter(ratingAdapter);

    }
    // DATA SETTER GETTER CLEANER
    // GAMEDATA
    public void addallData(ArrayList<Game> data) {
        this.data.addAll(data);
    }
    public void clearData() {
        this.data.clear();
    }
    // GENRE DATA
    public void addallDataGenre(ArrayList<Genre> dataGenre) {
        this.dataGenre.addAll(dataGenre);
        Log.w("tag", this.dataGenre.getClass().toString());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.addAll(getGenresName(this.dataGenre));
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textGenre.setAdapter(genreAdapter);
        genreAsync = null;
    }
    public ArrayList<String> getGenresName (ArrayList<Genre> dataGenre) {
        ArrayList<String> genreNames = new ArrayList<>();
        for (Genre genre : dataGenre) {
            genreNames.add(genre.getName());
        }
        return genreNames;
    }
    // PLATFORM DATA
    public void addallDataPlatform(ArrayList<GamePlatform> dataPlatform) {
        this.dataPlatform.addAll(dataPlatform);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.addAll(getPlatformName(this.dataPlatform));
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textPlatform.setAdapter(platformAdapter);
        platformAsync = null;
    }
    public ArrayList<String> getPlatformName (ArrayList<GamePlatform> dataPlatform) {
        ArrayList<String> platformNames = new ArrayList<>();
        for (GamePlatform platform : dataPlatform) {
            platformNames.add(platform.getName());
        }
        return platformNames;
    }

    public void onbtSend(View view) {
        String selectedPlatform = getPlatformByName(textPlatform.getSelectedItem().toString());
        String selectedGenre = getGenreByName(textGenre.getSelectedItem().toString());
        String ratingMin = String.valueOf(textRating.getSelectedItem());
        String searchQuery;
        if (searchName.getText().toString() != ""){
            searchQuery = searchName.getText().toString();
        } else {
            searchQuery = "";
        }



        Log.w("tag", selectedPlatform);
        if (gameAsync == null || gameAsync.getStatus() == AsyncTask.Status.FINISHED) {

            gameAsync = new GameAsync(recyclerGame, this, selectedGenre, selectedPlatform, ratingMin, searchQuery);
            gameAsync.execute();
        }
    }

    public String getGenreByName (String name) {
        String genreId = "";
        for(Genre genre : dataGenre){
            if(genre.getName() != null && name != "" && genre.getName().contains(name)) {
                return genre.getId();
            }
        }
        return genreId;
    }
    public String getPlatformByName (String name) {
        String platformId = "";
        for(GamePlatform platform : dataPlatform){
            if(platform.getName() != null && name != "" && platform.getName().contains(name)) {
                return platform.getId();
            }
        }
        return platformId;
    }


    //ADAPTER
    public GameAdapter getGameAdaper() {
        return gameAdaper;
    }

    public void setGameAdaper(GameAdapter gameAdaper) {
        this.gameAdaper = gameAdaper;
    }

}
