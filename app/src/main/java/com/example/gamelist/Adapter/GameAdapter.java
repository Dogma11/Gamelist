package com.example.gamelist.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gamelist.Model.Game;
import com.example.gamelist.Model.GamePlatform;
import com.example.gamelist.Model.Genre;
import com.example.gamelist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private ArrayList<Game> data;

    public GameAdapter(ArrayList<Game> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_row, parent, false);
        return new GameAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game datum = data.get(position);
        Log.w("cover", String.valueOf(datum.getCovers()) );
        if (datum.getCovers() != null) {
            Picasso.get().load("http:" + datum.getCovers().getUrl()).into(holder.imageCover);
        } else {
            holder.imageCover.setImageResource(R.mipmap.ic_launcher);
        }
        if (datum.getGenres() != null && !datum.getGenres().isEmpty()){
            holder.tvGenres.setText(getGenresName(datum.getGenres()));
        } else {
            holder.tvGenres.setText("?");
        }
        if (datum.getPlatforms() != null) {
            holder.tvPlatforms.setText(getPlatformName(datum.getPlatforms()));
        }else {
            holder.tvPlatforms.setText("?");
        }
        if (datum.getRating() != null) {
            holder.tvRating.setText(String.valueOf(datum.getRating().intValue()));
        } else {
            holder.tvRating.setText("?");
        }

        holder.tvName.setText(datum.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NewApi")
    public String getGenresName (ArrayList<Genre> iterator) {
        String string;
        StringBuilder builder = new StringBuilder();
        if (iterator.size() <= 0) {
            return "";
        }
        if (iterator.size() > 1){
            for (Genre genre : iterator)
            {
                if (genre != null) {
                    builder.append(genre.getName());
                    builder.append(",");
                }
            }
            string = builder.toString();
        } else {
            string = iterator.get(0).getName();
        }
        return string;
    }

    @SuppressLint("NewApi")
    public String getPlatformName (ArrayList<GamePlatform> iterator) {
        String string;
        StringBuilder builder = new StringBuilder();
        if (iterator.size() <= 0) {
            return "";
        }
        if (iterator.size() > 1){
            for (GamePlatform platform : iterator)
            {
                if (platform != null) {
                    builder.append(platform.getName());
                    builder.append(",");
                }
            }
            string = builder.toString();
        } else {
            string = iterator.get(0).getName();
        }
        return string;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {//2

        TextView tvName, tvGenres, tvRating, tvPlatforms;
        ImageView imageCover;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGenres = itemView.findViewById(R.id.textGenre);
            tvName = itemView.findViewById(R.id.textName);
            tvRating = itemView.findViewById(R.id.textRating);
            tvPlatforms = itemView.findViewById(R.id.textPlatform);
            imageCover = itemView.findViewById(R.id.imageCover);
        }
    }


}
