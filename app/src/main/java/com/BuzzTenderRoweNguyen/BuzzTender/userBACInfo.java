package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class userBACInfo extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    public static final String TAG = "userBACInfo";
    public static final String CURRENT_USER= "currentUser";
    public static final String GENDER_KEY = "userGender";
    public static final Integer WEIGHT_KEY = 0;
    public static final Integer AGE_KEY = 0;

    //FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    //FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //FirebaseUser currentUser = mAuth.getCurrentUser();

    private List<User> userList = new ArrayList<>();
    private int mCurrentImage = 0;
    private String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();



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

        TextView genderUpload = (TextView) findViewById(R.id.genderFireFilled);
        TextView weightUpload = (TextView) findViewById(R.id.weightFireFilled);
        TextView ageUpload = (TextView) findViewById(R.id.ageFireFilled);



        FirebaseFirestore.getInstance().collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int counter = 0;
                        if (task.isSuccessful()) {
                            HashMap<String, String> list = new HashMap();
                            System.out.println("Current UID = " + currentUID);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("getID = " + document.getId());
                                String test = document.getId();
                                if(test.equals(currentUID)) {
                                    System.out.println(document.getData());
                                    Collection data = document.getData().values();
                                    //System.out.println(data.iterator().next());
                                    for(Iterator i = data.iterator(); i.hasNext();){
                                        //System.out.println(i.next());
                                        System.out.println(counter);
                                        if (counter == 0){
                                            String weight = String.valueOf(i.next());
                                            System.out.println(weight);
                                            counter++;
                                            weightUpload.setText(weight);
                                        }
                                        else if (counter == 1){
                                            String gender = String.valueOf(i.next());
                                            System.out.println(gender);
                                            counter++;
                                            genderUpload.setText(gender);
                                        }
                                        else {
                                            String age = String.valueOf(i.next());
                                            System.out.println(age);
                                            ageUpload.setText(age);
                                        }

                                    }
                                }
                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });



    }

    public void saveItem(final View view){

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