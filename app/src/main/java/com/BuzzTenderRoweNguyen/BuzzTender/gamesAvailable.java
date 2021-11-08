package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class gamesAvailable extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "gamesAvailable";
    private static final String GAMES = "game";
    private ArrayAdapter<Games> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_available);

        ListView gamesListView = findViewById(R.id.gamesListView);
        ArrayAdapter<Games> adapter = new ArrayAdapter<Games>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Games>()
        );
        gamesListView.setAdapter(adapter);
    }
    public void onLoadGamesClick(View view){
        mDb.collection(GAMES)
                .get()
                .addOnSuccessListener(
                        ArrayList<Games> game = new ArrayList<>();
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                            Games g = document.toObject(Games.class);
                            game.add(g);
                            Logd(TAG, g.getName() + " " + g.getDescription());
                        }
                        adapter.addAll(game);
                )
    }
}