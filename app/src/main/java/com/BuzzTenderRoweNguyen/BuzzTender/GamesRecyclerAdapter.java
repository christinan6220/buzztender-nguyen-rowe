package com.BuzzTenderRoweNguyen.BuzzTender;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class GamesRecyclerAdapter extends FirestoreRecyclerAdapter<Games, GamesRecyclerAdapter.GamesViewHolder> {
    private static final String TAG = "GamesRecyclerAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final OnItemClickListener listener;

    GamesRecyclerAdapter(FirestoreRecyclerOptions<Games> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;

    }

    GamesRecyclerAdapter(FirestoreRecyclerOptions<Games> options) {
        super(options);
        this.listener = null;
    }

    class GamesViewHolder extends RecyclerView.ViewHolder{
        final CardView view;
        final TextView gameName;
//        final ImageView gameImage;

        GamesViewHolder(CardView v) {
            super(v);
            view = v;
            gameName = v.findViewById(R.id.game_name);
        }
    }



    @Override
    protected void onBindViewHolder(@NonNull GamesViewHolder holder, int position, @NonNull final Games games) {
        holder.gameName.setText(games.getName());
        Log.d(TAG, "onBindViewHolder: just set name");
//        TODO: ^ for game image
        if (listener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAbsoluteAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.games_available_item_card,parent,false);
        return new GamesViewHolder(v);
    }
}
