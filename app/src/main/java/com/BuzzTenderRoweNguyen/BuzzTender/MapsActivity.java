package com.BuzzTenderRoweNguyen.BuzzTender;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import com.BuzzTenderRoweNguyen.BuzzTender.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings setting = mMap.getUiSettings();
        setting.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        Map<String, String> sydneyData = ImmutableMap.of(
                "pickup", "8:00pm",
                "destination", "Melbourne"
        );
        Marker sydneyMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        sydneyMarker.setTag(sydneyData);

        LatLng anotherPlace = new LatLng(-33, 149.8);
        Marker anotherMarker = mMap.addMarker(
                new MarkerOptions().position(anotherPlace)
                        .title("Another place")
        );
        Map<String, String> anotherMarkersData = ImmutableMap.of(
                "pickup", "5:00pm",
                "destination", "Sydney"
        );
        anotherMarker.setTag(anotherMarkersData);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Map<String, String> data = (Map<String, String>) marker.getTag();
        Toast.makeText(this, "Pickup time:" + data.get("pickup") + ", Dest:" + data.get("destination"),
                Toast.LENGTH_SHORT)
                .show();
        return false;
    }
}