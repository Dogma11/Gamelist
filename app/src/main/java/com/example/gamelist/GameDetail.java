package com.example.gamelist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamelist.Adapter.ArtworkAdapter;
import com.example.gamelist.Async.ArtworksAsync;
import com.example.gamelist.Model.Artwork;
import com.example.gamelist.Model.Cover;
import com.example.gamelist.Model.Game;
import com.example.gamelist.Utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.io.Writer;
import java.util.ArrayList;

public class GameDetail extends AppCompatActivity {

    private ArtworkAdapter artworkAdapter;

    private ArrayList<Artwork> artworks = new ArrayList<>();

    private Game selectedGame;
    private Cover gameCover;

    private Context context;
    private SharedPreferences prefs;
    private SharedPreferenceUtils myPrefs;

    private TextView detailGenres, detailPlatforms, detailName, detailScore;
    private RecyclerView list;
    private Button btFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        detailGenres    = findViewById(R.id.detailGenres);
        detailPlatforms = findViewById(R.id.detailPlatforms);
        detailScore     = findViewById(R.id.detailScore);
        detailName      = findViewById(R.id.detailName);
        btFavorite      = findViewById(R.id.btFavorite);
        list = findViewById(R.id.imageSlider);
        context = getApplicationContext();

        prefs = context.getSharedPreferences("Favorite.xml", MODE_PRIVATE);

        myPrefs = new SharedPreferenceUtils(prefs);

        artworkAdapter = new ArtworkAdapter(artworks);
        list.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL,false));
        list.setAdapter(artworkAdapter);

        Bundle bundle = this.getIntent().getExtras() ;
        selectedGame = (Game) bundle.getSerializable("selectedGame");

        // TODO: REMOVE
        /*for (Game game : myPrefs.loadGames() ) {
            myPrefs.removeGame(game);
        }*/

        ArtworksAsync artworksAsync = new ArtworksAsync(selectedGame, this);
        try {
            artworksAsync.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // SETTING GAME DATA DETAIL
        if (selectedGame != null ) {
            if (selectedGame.getCover() != null && selectedGame.getCover().getUrl() != null && selectedGame.getCover().getUrl().isEmpty()) {
                gameCover = new Cover(selectedGame.getCover().getId(), selectedGame.getCover().getUrl());
                artworks.add(new Artwork(gameCover.getId(), gameCover.getUrl()));
            }
            // ~~~~~~GENRES~~~~~~
            if (selectedGame.getGenres() != null) {
                detailGenres.setText(Game.getGenresName(selectedGame.getGenres()));
            } else {
                detailGenres.setText(getString(R.string.unknown));
            }
            // ~~~~~~PLATFORMS~~~~~~
            if (selectedGame.getPlatforms() != null) {
                detailPlatforms.setText( Game.getPlatformsName(selectedGame.getPlatforms()));
            } else {
                detailPlatforms.setText(R.string.unknown);
            }
            // ~~~~~~NAME~~~~~~
            if (selectedGame.getName() != null) {
                detailName.setText(selectedGame.getName());
            } else {
                detailName.setText(R.string.unknown);
            }
            // ~~~~~~RATING~~~~~~
            if (selectedGame.getRating() > 0) {
                String score = String.valueOf(Math.round(selectedGame.getRating())) ;
                detailScore.setText(score);
            } else {
                detailScore.setText(R.string.unknown);
            }

            if (selectedGame.isFav() || myPrefs.isPresent(selectedGame)) {
                btFavorite.setText(R.string.removefavorite);
            } else {
                btFavorite.setText(R.string.addfavorite);
            }
        }
    }

    public void addAllData(ArrayList<Artwork> data) {
        this.artworks.clear();
        if (gameCover != null) {
            this.artworks.add(new Artwork(gameCover.getId(), gameCover.getUrl()));
        }
        this.artworks.addAll(data);
        artworkAdapter.notifyDataSetChanged();

    }

    public void addFavorite(View view) {
        if (this.myPrefs != null && selectedGame != null ) {
            if (selectedGame.isFav()) {
                myPrefs.removeGame(selectedGame);
                btFavorite.setText(getString(R.string.addfavorite));
                selectedGame.setFav(false);
            } else {
                myPrefs.saveGame(selectedGame);
                btFavorite.setText(getString(R.string.removefavorite));
                selectedGame.setFav(true);
            }
        }
    }
}
