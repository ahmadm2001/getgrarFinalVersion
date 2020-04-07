package com.example.getgrarfinalversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

public class mapforManager extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    Button btn;
    MarkerOptions place1,place2;
    Polyline currentPolyline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapfor_manager);
        btn= findViewById(R.id.btn);
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        fragment.getMapAsync(this);
        place1= new MarkerOptions().position(new LatLng(27.658143,85.3199503)).title("Location 1");
        place1= new MarkerOptions().position(new LatLng(27.667492,85.3208503)).title("Location 2");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

}
