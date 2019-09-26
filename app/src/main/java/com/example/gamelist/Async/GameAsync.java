package com.example.gamelist.Async;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gamelist.MainActivity;
import com.example.gamelist.Model.Game;
import com.example.gamelist.Utils.WebService;

import java.util.ArrayList;

public class GameAsync extends AsyncTask {

    private String searchQuery;
    private String selectedGenre;
    private String selectedPlatform;
    private String ratingMin;
    private MainActivity mainActivity;
    private ArrayList<Game> resultatGame;

    private RecyclerView recyclerGame;

    private Exception exception;
    private String query = "";

    public GameAsync(RecyclerView recyclerGame, MainActivity mainActivity, String selectedGenre, String selectedPlatform, String ratingMin, String searchQuery) {
        this.recyclerGame = recyclerGame;
        this.mainActivity = mainActivity;
        this.selectedGenre = selectedGenre;
        this.selectedPlatform = selectedPlatform;
        this.ratingMin = ratingMin;
        this.searchQuery = searchQuery;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String where = "where ";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            query += "search \"" + searchQuery.toLowerCase() + "\";";
        }
        if (!selectedPlatform.equals("")){
            query += where + "platforms = (" + selectedPlatform + ") ";
            where = "& ";
        }
        if (!selectedGenre.equals("")){
            query += where + "genres = (" + selectedGenre + ") ";
            where = "& ";
        }
        if (!ratingMin.equals("0")){
            query += where + "rating >= " + ratingMin +" ";
            where = "& ";
        }
        if (!where.equals("where ")){
            query += ";";
        }
        try {
            resultatGame = WebService.getGame(query);
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (exception != null) {
            Toast.makeText(mainActivity, exception.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            mainActivity.clearData();
            mainActivity.addAllData(resultatGame);

            mainActivity.getGameAdapter().notifyDataSetChanged();
        }


    }
}
