package com.example.gamelist.Async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gamelist.MainActivity;

import com.example.gamelist.Model.Genre;
import com.example.gamelist.Utils.WebService;

import java.util.ArrayList;

public class GenreAsync extends AsyncTask {

    private MainActivity mainActivity;
    private ArrayList<Genre> resultatGenres;

    private Exception exception;

    public GenreAsync(Spinner textGenre, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            resultatGenres = WebService.getGenres("limit 5;");
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
            Log.w("bug", exception.getMessage());
        } else {
            mainActivity.addallDataGenre(resultatGenres);
        }


    }
}
