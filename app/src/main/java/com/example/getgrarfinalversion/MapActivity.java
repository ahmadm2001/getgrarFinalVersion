package com.example.getgrarfinalversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions place1,place2;
    Button getDirection;
    Double a=31.25181,b=34.7913;
    private Polyline currentPolyline;

    List<MarkerOptions> MarkerOptionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        getDirection=(Button) findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(MapActivity.this)
                        .execute(getUr1(place1.getPosition(),place2.getPosition(),"driving"),"driving");
            }
        });
        place1 = new MarkerOptions().position(new LatLng(31.25181,34.7913)).title("Location");
        place2 =new MarkerOptions().position(new LatLng(32.109333,34.855499)).title("Location");

        MarkerOptionsList.add(place1);
        MarkerOptionsList.add(place2);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(place1);
        mMap.addMarker(place2);

        showAllMarkers();





    }

    private void showAllMarkers() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (MarkerOptions m : MarkerOptionsList){
            builder.include(m.getPosition());
        }

        LatLngBounds bounds = builder.build();

         int width = getResources().getDisplayMetrics().widthPixels;
         int height = getResources().getDisplayMetrics().heightPixels;
         int padding = (int) (width * 0.30);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
        mMap.animateCamera(cu);



    }

    private String getUr1(LatLng origin,LatLng destination , String directionMode){
        String str_origin = "origin" + origin.latitude +","+ origin.longitude;

        String str_drst="destination"  + destination.latitude +","+ destination.longitude;

        String Mode="Mode"+directionMode;

        String parameter = str_origin+"&"+str_drst+"&"+Mode;

        String formet =" json";

        String ur1 = "https://maps.googleleapis.com/maps/api/directions" +formet + "2"
                +parameter + "&key*AIzaSyCI-4RaDocwneRsw2ryTRPMf7NzGV-F1CE";


            return  ur1;



    }

    @Override
    public void onTaskDone(Object... values) {

        if (currentPolyline!=null)
            currentPolyline.remove();

            currentPolyline=mMap.addPolyline((PolylineOptions) values[0]);

    }


}
