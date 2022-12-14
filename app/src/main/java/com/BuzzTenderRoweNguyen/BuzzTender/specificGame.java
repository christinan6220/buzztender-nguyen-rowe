package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.Objects;

public class specificGame extends AppCompatActivity {

    private CardView descriptionCard;
    private LinearLayout descriptionHidden, newGameCard;
    private ImageButton descriptionArrow, newGameArrow;
    private ConstraintLayout newGameHidden;

    private EditText spiritInput, beerInput, wineInput, hoursInput;

    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "specificGame.java";
    private static final String USERS = "users";
    private static final String GAMES = "games";
    private FirebaseAuth mAuth;

    String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private User userObj;
    private Games gameObj;

    private CompletedGameRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_game);

        //get a reference to the toolbar - in order to setup logout functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViews();
        setClickListeners();
        getUserObject();
        getGameObj();
        setGlobalGamesRecyclerView();



        Log.d(TAG, "onCreate: did the obj save?" + gameObj);
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

    private void findViews() {
        // finding views for the expandable cards
        descriptionCard = findViewById(R.id.game_description_card);
        descriptionHidden = findViewById(R.id.description_hidden);
        descriptionArrow = findViewById(R.id.description_expandable_button);
        newGameCard = findViewById(R.id.new_game_visible);
        newGameHidden = findViewById(R.id.lets_play_hidden);
        newGameArrow = findViewById(R.id.new_game_arrow);

        // finding views for BAC calculator
        spiritInput = findViewById(R.id.spiritInput);
        beerInput = findViewById(R.id.beerInput);
        wineInput = findViewById(R.id.wineInput);
        hoursInput = findViewById(R.id.hoursInput);
    }

    private void setGlobalGamesRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.globalGames_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Query query = mDb.collection("completedGames")
                .whereEqualTo("game", getIntent().getStringExtra("selectedGame"))
                .orderBy("createdTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CompletedGame> options = new FirestoreRecyclerOptions.Builder<CompletedGame>()
                .setQuery(query, CompletedGame.class)
                .build();

        mAdapter = new CompletedGameRecyclerAdapter(options);
        recyclerView.setAdapter(mAdapter);
    }

    private void setClickListeners() {
        //        SETTING UP CLICK LISTENERS
//      Click listener for expanding the description card
        descriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptionHidden.getVisibility() == View.VISIBLE){
                    descriptionHidden.setVisibility(View.GONE);
                    descriptionArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    descriptionHidden.setVisibility(View.VISIBLE);
                    descriptionArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        descriptionArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptionHidden.getVisibility() == View.VISIBLE){
                    descriptionHidden.setVisibility(View.GONE);
                    descriptionArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    descriptionHidden.setVisibility(View.VISIBLE);
                    descriptionArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

//      Click listener for expanding new game card
        newGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newGameHidden.getVisibility() == View.VISIBLE){
                    newGameHidden.setVisibility(View.GONE);
                    newGameArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    newGameHidden.setVisibility(View.VISIBLE);
                    newGameArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        newGameArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newGameHidden.getVisibility() == View.VISIBLE){
                    newGameHidden.setVisibility(View.GONE);
                    newGameArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    newGameHidden.setVisibility(View.VISIBLE);
                    newGameArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
    }

    private void getUserObject() {
//        grabs the current user object and save it to userObj
        DocumentReference docRef = mDb.collection(USERS).document(currentUID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // convert document to user obj
                        userObj = document.toObject(User.class);
                        Log.d(TAG, "onComplete: getuserobject " + userObj.getWeight());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void getGameObj() {
//        get current game data
        Task<QuerySnapshot> gameQuery = mDb.collection(GAMES)
                .whereEqualTo("name", getIntent().getStringExtra("selectedGame"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        gameObj = Objects.requireNonNull(task.getResult()).toObjects(Games.class).get(0);

                        // have to call fillTextViews() bc getGameObj is an async function
                        fillTextViews();
                    }
                });
    }

    private void fillTextViews() {
        TextView header, description, maintainBAC;

        header = findViewById(R.id.game_header);
        description = findViewById(R.id.description_text);
        maintainBAC = findViewById(R.id.maintainBAC);

        header.setText(gameObj.getName());
        description.setText(gameObj.getDescription());
        maintainBAC.setText(String.valueOf(gameObj.getBAC()));

    }

    public void BACCalculator(){
        //empty variable for gender
        double r = 0;
        //empty variable to find grams drank
        double grams = 0;
        //hours elapsed
        double hours2 = 0;
        //variable with BAC
        double BAC2 = 0;

        //grab each of the users inputs to calculate BAC
        int Cspirit = Integer.parseInt(spiritInput.getText().toString());
        int Cbeer = Integer.parseInt(beerInput.getText().toString());
        int Cwine = Integer.parseInt(wineInput.getText().toString());
        int Chours = Integer.parseInt(hoursInput.getText().toString());

        TextView currentBAC = findViewById(R.id.currentBAC);
        String gender = userObj.getGender();
        double weight = userObj.getWeight();

        //find the constant depending on gender
        if (gender.equals("female")){
            r = 0.55;
        }
        else {
            r = 0.68;
        }

        //find the calculation in grams of each alcohol
        Cspirit = Cspirit*14;
        Cbeer = Cbeer*14;
        Cwine = Cwine*14;
        grams = Cspirit + Cbeer + Cwine;

        //calculate the body weight in grams
        double newWeight = weight/0.0022046;

        //calculate the hours
        hours2 =  Chours * 0.015 ;

        //calculate BAC
        BAC2 = (grams/(newWeight*r)) * 100;

        //find the last BAC
        BAC2 = BAC2 - hours2;

        //format bac to 2 decimal places
        String calculatedBAC = String.format("%.2f", BAC2);

        //show the BAC
        currentBAC.setText(calculatedBAC);

        TextView yourBAC = findViewById(R.id.your_bac_label);
        yourBAC.setVisibility(View.VISIBLE);

    }

    public void calculateBACClick(View view) {
        //get reference to all EditTexts and change them to strings to ensure they are not empty for Toast
        String spirit = spiritInput.getText().toString();
        String beer = beerInput.getText().toString();
        String wine = wineInput.getText().toString();
        String hours = hoursInput.getText().toString();

        if (userObj == null){
            Toast.makeText(specificGame.this, "Could not calculate BAC - Make sure your profile is updated.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(spirit) || TextUtils.isEmpty(beer) || TextUtils.isEmpty(wine) || TextUtils.isEmpty(hours)){
            //if something isn't filled out make a toast to show you need to input it
            // If registration fails, display a message to the user.
            System.out.println("got here!");
            Toast.makeText(this, "Please enter information in every field (if you haven't drank anything enter 0) ",
                    Toast.LENGTH_LONG).show();
        } else {
            BACCalculator();
        }
    }

    public void saveNewGame(View view) {
//      private EditText spiritInput, beerInput, wineInput, hoursInput;
        Spinner result = findViewById(R.id.winLossInput);
        TextView bac = findViewById(R.id.currentBAC);

        // Check for empty fields

        if (userObj == null){
            Toast.makeText(specificGame.this, "Could not calculate BAC - Make sure your profile is updated.", Toast.LENGTH_SHORT).show();
        } else if (!checkForEmptyInput()) {
            Toast.makeText(specificGame.this, "Could not save game, make sure all fields are filled in.", Toast.LENGTH_SHORT).show();
        } else {
            // Create new completedGame obj and add to it firebase
            BACCalculator();
            //        CompletedGame(String game, String nickname, float bac, String result)
            CompletedGame newGame = new CompletedGame(
                    currentUID,
                    getIntent().getStringExtra("selectedGame"),
                    userObj.getNickname(),
                    Double.parseDouble(bac.getText().toString()),
                    result.getSelectedItem().toString(),
                    new Date());
            mDb.collection("completedGames").add(newGame)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(specificGame.this, "Saved game successfully!", Toast.LENGTH_SHORT).show();
//                            Todo: refresh adapter on new game
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Could not save game");
                            Toast.makeText(specificGame.this, "Failed to save game!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public boolean checkForEmptyInput() {
        // Return false if there are empty fields, true if everything is filled out
        String spirit = spiritInput.getText().toString();
        String beer = beerInput.getText().toString();
        String wine = wineInput.getText().toString();
        String hours = hoursInput.getText().toString();
        Spinner result = findViewById(R.id.winLossInput);

        return !TextUtils.isEmpty(spirit) && !TextUtils.isEmpty(beer) && !TextUtils.isEmpty(wine) && !TextUtils.isEmpty(hours) && result.getSelectedItemPosition() != 0;
    }

}   // end of main class