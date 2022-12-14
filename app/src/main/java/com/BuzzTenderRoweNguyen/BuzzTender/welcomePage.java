package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class welcomePage extends AppCompatActivity {

    private static final String TAG = "welcomePage";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //get a reference to the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_logout:
                //MainActivity signUserOut = new MainActivity();
                signOut();
                Intent logoutIntent = new Intent(welcomePage.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // opens activity_user_profile on button click
    public void onPlayerProfileClick(View view) {
        Intent intent = new Intent(welcomePage.this, userBACInfo.class);
        startActivity(intent);
    }
    //opens the games Activity when button is clicked
    public void onGamesAvailableClick(View view) {
        Intent intent = new Intent(welcomePage.this, gamesAvailable.class);
        startActivity(intent);
    }
    //opens the location services when clicked
    public void onDrinkingLocationsClick(View view) {
        Intent intent = new Intent(welcomePage.this, locationPage.class);
        startActivity(intent);
    }
    public void onBackPressed(){
        Toast.makeText(welcomePage.this,"There is no back action, try Logout instead", Toast.LENGTH_LONG).show();
    }
}