package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class specificGame extends AppCompatActivity {

    private CardView descriptionCard;
    private LinearLayout descriptionHidden, newGameCard;
    private ImageButton descriptionArrow, newGameArrow;
    private ConstraintLayout newGameHidden;

    private EditText spiritInput, beerInput, wineInput, hoursInput;

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

        descriptionCard = findViewById(R.id.game_description_card);
        descriptionHidden = findViewById(R.id.description_hidden);
        descriptionArrow = findViewById(R.id.description_expandable_button);
        newGameCard = findViewById(R.id.new_game_visible);
        newGameHidden = findViewById(R.id.lets_play_hidden);
        newGameArrow = findViewById(R.id.new_game_arrow);

        setClickListeners();
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
    public void BACCalculator(){
        //empty variable for gender
        int r = 0;
        //empty variable to find grams drank
        int grams = 0;
        //hours elapsed
        double hours2 = 0;
        //variable with BAC
        int BAC2 = 0;

        //grab each of the users inputs to calculate BAC
        EditText spirit = findViewById(R.id.spiritInput);
        Integer Cspirit = Integer.parseInt(spirit.getText().toString());
        EditText beer = findViewById(R.id.beerInput);
        Integer Cbeer = Integer.parseInt(beer.getText().toString());
        EditText wine = findViewById(R.id.wineInput);
        Integer Cwine = Integer.parseInt(wine.getText().toString());
        EditText hours = findViewById(R.id.hoursInput);
        Integer Chours = Integer.parseInt(hours.getText().toString());
        TextView BAC = findViewById(R.id.currentBAC);
        String gender = userBACInfo.user_gender;
        double weight = Integer.parseInt(userBACInfo.user_weight);

        //find the constant depending on gender
        if (gender.equals("Female")){
            r = (int) 0.55;
        }
        else {
            r = (int) 0.68;
        }

        //find the calculation in grams of each alcohol
        Cspirit = Cspirit*14;
        Cbeer = Cbeer*14;
        Cwine = Cwine*14;
        grams = Cspirit + Cbeer + Cwine;

        //calculate the body weight in grams
        weight= weight/0.0022046;

        //calculate the hours
        hours2 = (int) (Chours*0.015);

        //calculate BAC
        BAC2 = (int) ((grams/(weight*r)) *100);

        //find the last BAC
        BAC2 = (int) (BAC2 - hours2);

        //show the BAC
        BAC.setText(BAC2);

    }

    public void calculateBACClick(View view) {
        //get reference to all EditTexts and change them to strings to ensure they are not empty for Toast
        String spirit = spiritInput.getText().toString();
        String beer = beerInput.getText().toString();
        String wine = wineInput.getText().toString();
        String hours = hoursInput.getText().toString();
        if (TextUtils.isEmpty(spirit) || TextUtils.isEmpty(beer) || TextUtils.isEmpty(wine) || TextUtils.isEmpty(hours)){
            //if something isn't filled out make a toast to show you need to input it
            // If registration fails, display a message to the user.
            System.out.println("got here!");
            Toast.makeText(this, "Please enter information in every field (if you haven't drank anything enter 0) ",
                    Toast.LENGTH_LONG).show();
        }
        else {
            BACCalculator();
        }
    }
}