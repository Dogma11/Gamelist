package com.example.gamelist.Adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
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

    public OnGameClickListener onGameClickListener;

    public void setOnGameClickListener(OnGameClickListener onGameClickListener) {
        this.onGameClickListener = onGameClickListener;
    }

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
        final Game datum = data.get(position);
        Log.w("cover", String.valueOf(datum.getCover()) );
        if (datum.getCover() != null) {
            Picasso.get().load("http:" + datum.getCover().getUrl()).into(holder.imageCover);
        } else {
            holder.imageCover.setImageResource(R.mipmap.ic_launcher);
        }
        if (datum.getGenres() != null && !datum.getGenres().isEmpty()){
            holder.tvGenres.setText(Game.getGenresName(datum.getGenres()));
        } else {
            holder.tvGenres.setText("?");
        }
        if (datum.getPlatforms() != null) {
            holder.tvPlatforms.setText(Game.getPlatformsName(datum.getPlatforms()));
        }else {
            holder.tvPlatforms.setText("?");
        }
        if (datum.getRating() > 0) {
            holder.tvRating.setText(String.valueOf(Math.round(datum.getRating())));
        } else {
            holder.tvRating.setText("?");
        }
        holder.tvName.setText(datum.getName());
        Log.w("root", holder.root.toString() );
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameClickListener.onGameClick(datum);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvGenres, tvRating, tvPlatforms;
        ImageView imageCover;
        View root;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGenres = itemView.findViewById(R.id.textGenre);
            tvName = itemView.findViewById(R.id.textName);
            tvRating = itemView.findViewById(R.id.textRating);
            tvPlatforms = itemView.findViewById(R.id.textPlatform);
            imageCover = itemView.findViewById(R.id.imageCover);
            root = itemView.findViewById(R.id.root);
        }
    }

    public interface OnGameClickListener {
        void onGameClick(Game game);
    }
}
