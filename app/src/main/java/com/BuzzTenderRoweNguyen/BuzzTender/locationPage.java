package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class locationPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);
        //get a reference to the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                Intent logoutIntent = new Intent(locationPage.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void clickOnDemand(View view) {
        startActivity(new Intent(this, OnDemandActivity.class));
    }

    public void clickMaps(View view){
        startActivity(new Intent(this, MapsActivity.class));
    }
    }