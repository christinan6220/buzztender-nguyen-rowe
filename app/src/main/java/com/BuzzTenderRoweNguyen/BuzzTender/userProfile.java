package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class userProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //get reference to the User so that we can call it later to set it to the TextView
        final User user = (User)getIntent().getSerializableExtra("user");

        //get reference to the TextViews
        TextView gender = findViewById(R.id.genderFire);
        TextView weight = findViewById(R.id.weightFire);
        TextView age = findViewById(R.id.ageFire);

        //populate the textViews
        gender.setText(user.getGender());
        weight.setText(user.getWeight());
        age.setText(user.getAge());
    }
}