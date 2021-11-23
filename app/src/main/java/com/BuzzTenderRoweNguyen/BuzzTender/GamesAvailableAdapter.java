package com.BuzzTenderRoweNguyen.BuzzTender;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GamesAvailableAdapter extends RecyclerView.Adapter<GamesAvailableAdapter.MyViewHolder> {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "gamesAvailable";
    private static final String GAMES = "games";
    private ArrayAdapter<Games> adapter;
    private ArrayList<Games> games;


    interface Listener { // interface == bunch of abstract elements, defines what someone else needs to define (user), kinda like a class
        void onClick (int position);
    }

    private Listener listener;

    void setListener(Listener listener) {
        this.listener = listener;
    }


//    TODO: finish this after creating card itme layout
    class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView layout;
        TextView gameName;
        ImageView gameImage;

        public MyViewHolder(@NonNull CardView itemView) {
            super(itemView);
            layout = itemView;
            gameName = layout.findViewById(R.id.game_name);
            gameImage = layout.findViewById(R.id.game_banner);

            mDb.collection(GAMES)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            games = new ArrayList<>();
                            for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                                Games g = document.toObject(Games.class);
                                games.add(g);
                                Log.d(TAG, "loadGames: " + g.getName() + " " + g.getDescription());
                            }
                        }
                    });
        }
    }

    public GamesAvailableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.games_available_item_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAvailableAdapter.MyViewHolder holder, int position) {
        holder.gameName.setText(games.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
