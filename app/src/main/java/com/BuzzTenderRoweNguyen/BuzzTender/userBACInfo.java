package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class userBACInfo extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = "userBACInfo";
    private static final String USERS = "users";
    public static final String CURRENT_USER= "currentUser";
    public static final String GENDER_KEY = "userGender";
    public static final Integer WEIGHT_KEY = 0;
    public static final Integer AGE_KEY = 0;


    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private List<User> userList = new ArrayList<>();
    private int mCurrentImage = 0;

    TextView weightUpload, ageUpload, genderUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bacinfo);

        //get a reference to the toolbar - in order to setup logout functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        genderUpload = findViewById(R.id.genderFireFilled);
        weightUpload = findViewById(R.id.weightFireFilled);
        ageUpload = findViewById(R.id.ageFireFilled);

        updateProfile();


    }

//    public void saveItem(final View view){
//        genderUpload = findViewById(R.id.genderFireFilled);
//        weightUpload = findViewById(R.id.weightFireFilled);
//        ageUpload = findViewById(R.id.ageFireFilled);
//    }

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

    public void updateProfile() {
        DocumentReference docRef = mDb.collection(USERS).document(currentUID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        User userObj = document.toObject(User.class);
                        // populate textfields
                        assert userObj != null;
                        weightUpload.setText(String.valueOf(userObj.getWeight()));
                        ageUpload.setText(String.valueOf(userObj.getAge()));
                        genderUpload.setText(String.valueOf(userObj.getGender()));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}