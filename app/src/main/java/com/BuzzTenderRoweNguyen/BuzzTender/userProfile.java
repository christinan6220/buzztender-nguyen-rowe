package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Objects;


public class userProfile extends AppCompatActivity {

    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "userProfile";
    private static final String USERS = "users";
    private FirebaseAuth mAuth;

    TextView nickname, weight, age;
    Spinner gender;

    String currentUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //get a reference to the toolbar - in order to setup logout functionality
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        //get reference to the User so that we can call it later to set it to the TextView
        final User user = (User)getIntent().getSerializableExtra("user");

        //get reference to the TextViews
        nickname = findViewById(R.id.nicknameFire);
        gender = findViewById(R.id.genderFire);
        weight = findViewById(R.id.weightFire);
        age = findViewById(R.id.ageFire);

        updateProfile();



    }

    public void onClickUpdateProfile (View view) {
        // check that all field are filled out - still need to set spinner for gender
        if (nickname.getText().toString().isEmpty() | weight.getText().toString().isEmpty() | age.getText().toString().isEmpty() | gender.getSelectedItemPosition() == 0){
            Toast.makeText(getApplicationContext(), "Cannot update profile, make sure all fields are filled in.", Toast.LENGTH_SHORT).show();
        } else {
            addUIDDocument();
            Toast.makeText(userProfile.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(userProfile.this, userBACInfo.class);
            startActivity(intent);
        }
    }

//    creates a new user object and adds it to the collection "users", will overwrite data if there
//      is already a userobj associated with the current uid
//    document id will be the same as the UID
    public void addUIDDocument() {
        User user = new User(
                nickname.getText().toString(),
                gender.getSelectedItem().toString(),
                Integer.parseInt(weight.getText().toString()),
                Integer.parseInt(age.getText().toString()));
        mDb.collection("users").document(currentUID).set(user)
                // there was a success listener but it gave an error - not needed?
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Could not add user");
                        Toast.makeText(userProfile.this, "Failed to add user!", Toast.LENGTH_SHORT).show();
                    }
                });
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
                Intent logoutIntent = new Intent(userProfile.this, MainActivity.class);
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
                        // convert document to user obj
                        User userObj = document.toObject(User.class);
                        // populate textfields
                        assert userObj != null;
                        nickname.setText(String.valueOf(userObj.getNickname()));
                        weight.setText(String.valueOf(userObj.getWeight()));
                        age.setText(String.valueOf(userObj.getAge()));
                        if (userObj.getGender().equals("Male")) {
                            gender.setSelection(1);
                        } else {
                            gender.setSelection(2);
                        }
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