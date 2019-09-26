package com.example.gamelist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamelist.Adapter.ArtworkAdapter;
import com.example.gamelist.Async.ArtworksAsync;
import com.example.gamelist.Model.Artwork;
import com.example.gamelist.Model.Cover;
import com.example.gamelist.Model.Game;
import java.util.ArrayList;

public class GameDetail extends AppCompatActivity {

    private ArtworkAdapter artworkAdapter;

    private ArrayList<Artwork> artworks = new ArrayList<>();

    private Game selectedGame = null;
    private Cover gameCover = null;

    private TextView detailGenres, detailPlatforms, detailName, detailScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        detailGenres    = findViewById(R.id.detailGenres);
        detailPlatforms = findViewById(R.id.detailPlatforms);
        detailScore     = findViewById(R.id.detailScore);
        detailName      = findViewById(R.id.detailName);

        artworkAdapter = new ArtworkAdapter(artworks);
        RecyclerView list = findViewById(R.id.imageSlider);
        list.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL,false));
        list.setAdapter(artworkAdapter);

        this.selectedGame = MainActivity.selectedGame;
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
            if (selectedGame.getCovers() != null && selectedGame.getCovers().getUrl() != null && selectedGame.getCovers().getUrl().isEmpty()) {
                gameCover = new Cover(selectedGame.getCovers().getId(), selectedGame.getCovers().getUrl());
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
            if (selectedGame.getRating() != null) {
                String score = String.valueOf(Math.round(selectedGame.getRating())) ;
                detailScore.setText(score);
            } else {
                detailScore.setText(R.string.unknown);
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

}
