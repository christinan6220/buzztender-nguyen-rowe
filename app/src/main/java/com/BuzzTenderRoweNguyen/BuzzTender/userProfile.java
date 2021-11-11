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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class userProfile extends AppCompatActivity {

    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "userProfile";
    private static final String USERS = "users";
    private FirebaseAuth mAuth;

    TextView weight, age;
    Spinner gender;

    String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //get a reference to the toolbar - in order to setup logout functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        //get reference to the User so that we can call it later to set it to the TextView
        final User user = (User)getIntent().getSerializableExtra("user");

        //get reference to the TextViews
        gender = findViewById(R.id.genderFire);
        weight = findViewById(R.id.weightFire);
        age = findViewById(R.id.ageFire);

        //populate the textViews
        // will keep commented atm bc they crash the app - null vals
//        TODO: populate fields if they already exist in firebase
//        gender.setText(user.getGender());
//        weight.setText(user.getWeight());
//        age.setText(user.getAge());

    }

    public void onClickUpdateProfile (View view) {
        // check that all field are filled out - still need to set spinner for gender
        if (weight.getText().toString().isEmpty() | age.getText().toString().isEmpty() | gender.getSelectedItemPosition() == 0){
            Toast.makeText(getApplicationContext(), "Cannot update profile, make sure all fields are filled in.", Toast.LENGTH_SHORT).show();
        } else {
            addUIDDocument();
            Toast.makeText(userProfile.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(userProfile.this, userBACInfo.class);
            startActivity(intent);
        }
    }

//    creates a new user object and adds it to the collection "users"
//    document id will be the same as the UID
    public void addUIDDocument() {
        User user = new User(
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

    public void reloadProfile() {
//        String getName = mDb.collection(USERS).document(currentUID).get("name");
        Log.d(TAG, "reloadProfile: getname " + getName);
    }
}