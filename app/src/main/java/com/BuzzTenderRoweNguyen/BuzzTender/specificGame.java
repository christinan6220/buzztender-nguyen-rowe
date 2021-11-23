package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class specificGame extends AppCompatActivity {

    private CardView descriptionCard;
    private LinearLayout descriptionHidden, newGameCard;
    private ImageButton descriptionArrow, newGameArrow;
    private ConstraintLayout newGameHidden;

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


}