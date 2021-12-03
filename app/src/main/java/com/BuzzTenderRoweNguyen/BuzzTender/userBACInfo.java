package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class userBACInfo extends AppCompatActivity {

    private static final String USERS = "users";
    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    public static final String TAG = "userBACInfo";
    public static final String CURRENT_USER= "currentUser";
    public static final String GENDER_KEY = "userGender";
    public static final Integer WEIGHT_KEY = 0;
    public static final Integer AGE_KEY = 0;
    public static String user_gender = "";
    public static String user_weight = "";

    //FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    //FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //FirebaseUser currentUser = mAuth.getCurrentUser();

    private List<User> userList = new ArrayList<>();
    private int mCurrentImage = 0;
    private String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    TextView nickname, gender, weight, age;
    User user;

    ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bacinfo);

        //get a reference to the toolbar - in order to setup logout functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //find views for to fill with firebase information
        constraintLayout = findViewById(R.id.profile_constraint_layout);
        nickname = findViewById(R.id.nicknameFireFilled);
        gender = findViewById(R.id.genderFireFilled);
        weight = findViewById(R.id.weightFireFilled);
        age = findViewById(R.id.ageFireFilled);


        getUserObject();

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
                        user = document.toObject(User.class);
                        fillViews();
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(userBACInfo.this, "Click the button to edit your profile.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void fillViews() {
        String weightFormatted = user.getWeight() + " lbs";
        nickname.setText(String.valueOf(user.getNickname()));
        weight.setText(weightFormatted);
        age.setText(String.valueOf(user.getAge()));
        gender.setText(String.valueOf(user.getGender()));
        constraintLayout.setVisibility(View.VISIBLE);
    }

    public void onUpdateUserProfileClick(View view){
        Intent intent = new Intent(userBACInfo.this, userProfile.class);
        startActivity(intent);
    }

    //signout functionality for the logout button on the toolbar
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //add the items to the actionbar
        getMenuInflater().inflate(R.menu.toolbar_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                //MainActivity signUserOut = new MainActivity();
                signOut();
                Intent logoutIntent = new Intent(userBACInfo.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}