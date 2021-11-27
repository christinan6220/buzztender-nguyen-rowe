package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class gamesAvailable extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "gamesAvailable";
    private static final String GAMES = "games";
    private ArrayAdapter<Games> adapter;

    private GamesRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_available);

        Intent intent = getIntent();

//        //create the up functionality in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        ListView gamesListView = findViewById(R.id.gamesListView);
//        testytest();
//        adapter = new GameAdapter(this, new ArrayList<Games>());
//        gamesListView.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Query query = mDb.collection(GAMES)
                .orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Games> options = new FirestoreRecyclerOptions.Builder<Games>()
                .setQuery(query, Games.class)
                .build();

        mAdapter = new GamesRecyclerAdapter(options);
        recyclerView.setAdapter(mAdapter);

    }

    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }




}