package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class userBACInfo extends AppCompatActivity {

    public static final String TAG = "userBACInfo";
    public static final String CURRENT_USER= "currentUser";
    public static final String GENDER_KEY = "userGender";
    public static final Integer WEIGHT_KEY = 0;
    public static final Integer AGE_KEY = 0;

    //FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private List<User> userList = new ArrayList<>();
    private int mCurrentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bacinfo);
    }

    public void saveItem(final View view){
        EditText genderUpload = (EditText) findViewById(R.id.genderInput);
        EditText weightUpload = (EditText) findViewById(R.id.weightInfo);
        EditText ageUpload = (EditText) findViewById(R.id.ageInfo);
    }
}