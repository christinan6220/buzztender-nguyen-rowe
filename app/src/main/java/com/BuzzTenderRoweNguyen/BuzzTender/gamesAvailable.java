package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class gamesAvailable extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "gamesAvailable";
    private static final String GAMES = "games";
    private ArrayAdapter<Games> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_available);

        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView gamesListView = findViewById(R.id.gamesListView);
//        ArrayAdapter<Games> adapter = new ArrayAdapter<Games>(
//                this,
//                android.R.layout.simple_list_item_1,
//                new ArrayList<Games>()
//        );
        testytest();
        adapter = new GameAdapter(this, new ArrayList<Games>());
        gamesListView.setAdapter(adapter);
    }


//    TODO: create gameadapter
    class GameAdapter extends ArrayAdapter<Games> {
        ArrayList<Games> games;

        GameAdapter (Context context, ArrayList<Games> games) {
            super(context, 0 , games);
            this.games = games;
        }
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.games_list_item, parent, false);      // reads the xml and converts into java widgets
            }

            // grab Textviews here
            TextView gameName = convertView.findViewById(R.id.game_name);
            TextView gameDescription = convertView.findViewById(R.id.game_description);

            Games g = games.get(position);
            Log.d(TAG, "getView: " + g.getName());

            gameName.setText(g.getName());
            gameDescription.setText(g.getDescription());

            return convertView;
        }

    }

    //    load games grabs all the game documents from firestore, convert it back to Games obj and puts it in an array
    public void onLoadGamesClick(View view) {
        mDb.collection(GAMES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Games> games = new ArrayList<>();
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                            Games g = document.toObject(Games.class);
                            games.add(g);
                            Log.d(TAG, "loadGames: " + g.getName() + " " + g.getDescription());
                        }
                        adapter.clear();
                        adapter.addAll(games);
                    }
                });
    }

    public void testytest() {
        mDb.collection(GAMES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Games> games = new ArrayList<>();
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                            Games g = document.toObject(Games.class);
                            games.add(g);
                            Log.d(TAG, "testytest: " + g.getName() + " " + g.getDescription());
                        }
                        adapter.clear();
                        adapter.addAll(games);
                    }
                });
    }
}