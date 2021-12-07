package com.BuzzTenderRoweNguyen.BuzzTender;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class OnDemandActivity extends AppCompatActivity {

    private static final String TAG = "OnDemandActivity";
    private FirebaseAuth mAuth;

    // Dictionary keys so we can store the last seen lat, lon, and acc in onSaveInstanceState()
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String ACCURACY_KEY = "accuracy";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOCATIONRECORD_COLLECTION = "location-recordings";

    TextView latText;
    TextView lonText;
    TextView accuracyText;

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);

                        if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                            Toast.makeText(this, "I can see precise location!", Toast.LENGTH_SHORT).show();
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                            Toast.makeText(this, "I can see approximate location!", Toast.LENGTH_SHORT).show();
                        } else {
                            // No location access granted.
                            Toast.makeText(this, "I need permission to access location in order to record locations.", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    //handles all the logic for us (figuring out where I am)
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ondemand);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //get a reference to the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //create the up functionality in the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        latText = findViewById(R.id.latitude);
        lonText = findViewById(R.id.longitude);
        accuracyText = findViewById(R.id.acc);
        if (savedInstanceState != null) {
            latText.setText(savedInstanceState.getCharSequence(LATITUDE_KEY));
            lonText.setText(savedInstanceState.getCharSequence(LONGITUDE_KEY));
            accuracyText.setText(savedInstanceState.getCharSequence(ACCURACY_KEY));
        }


        // Setup the RecyclerView to display new and changed LocationRecords in the Firestore
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = db.collection(LOCATIONRECORD_COLLECTION)
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<LocationRecord> options = new FirestoreRecyclerOptions.Builder<LocationRecord>()
                .setQuery(query, LocationRecord.class)
                .setLifecycleOwner(this)
                .build();

        // The OnDataChangedListener makes the RecyclerView scroll to the topmost (newest) item
        // automatically when one is added.
        LocationRecordAdapter adapter = new LocationRecordAdapter(options, () -> recyclerView.scrollToPosition(0));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        Button recordLocationBtn = findViewById(R.id.record_button);
        recordLocationBtn.setOnClickListener(this::recordClick);


        // TODO: Initialize the object that we will use for retrieving location readings.

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
                Intent logoutIntent = new Intent(OnDemandActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(LATITUDE_KEY, latText.getText());
        outState.putCharSequence(LONGITUDE_KEY, lonText.getText());
        outState.putCharSequence(ACCURACY_KEY, accuracyText.getText());
        super.onSaveInstanceState(outState);
    }

    /**
     * This method is called when the user clicks the Record button. It goes through the process of
     * making sure the device can get its location, checking user permission, and finally grabbing
     * a location reading.
     *
     * @param view the Record button that was pressed
     */
    private void recordClick(View view) {

        boolean hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        boolean hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (!hasCoarseLocationPermission && !hasFineLocationPermission) {
            Toast.makeText(this, "I need permission to access location in order to record locations.", Toast.LENGTH_SHORT).show();
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        } else {
            // TODO: Get the last known location from the location object.
            // Whether it gets a GPS reading, a WiFi location, or a cell tower location is all
            // hidden in the logic of Google's FusedLocationClient. However, it will try to give
            // a precise location.

            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                    this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            double accuracy = location.getAccuracy();

                            latText.setText(String.format("%.7f", latitude));
                            lonText.setText(String.format("%.7f", longitude));
                            accuracyText.setText(String.format("%.7f", accuracy));

                            LocationRecord lr = new LocationRecord(
                                    new Date(location.getTime()),
                                    new GeoPoint(latitude, longitude),
                                    accuracy
                            );

                            db.collection(LOCATIONRECORD_COLLECTION)
                                    .add(lr)
                                    .addOnSuccessListener(documentReference -> {
                                        Log.d("OnDemandActivity", "Saved the document!");
                                    })
                                    .addOnFailureListener(e -> {Log.e("OnDemandActivity", "Failed to save.");
                                    });
                        }
                    }
            );

        }
    }
}
