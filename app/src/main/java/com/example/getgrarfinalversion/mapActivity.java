package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;


import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.FBref.refdrivr;
import static com.example.getgrarfinalversion.FBref.refoffergrar;
import static com.example.getgrarfinalversion.R.layout.customerdialog;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback{
    Location mlocationC,locationD;
    private GoogleMap mMap;


    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    EditText eTprice,time;
    Polyline polyline;
    locationObject locationObject1;
    graroffer graroffer1;
    String UID,name,phone,Stowinglicense,numbercar,name1,phone1,cartype,email2,price,arrivalTime,Caddress,customerdestination;
    Boolean type=true;
    Double lat,longc,latD,LonfD;
    GoogleMap gMap;
    AlertDialog.Builder adb;
    AlertDialog.Builder adb2;

    Button dialogbtn;
    LinearLayout offerdialog,layout;
    TextView tvname, tvPhone, tvtypecar, tvnumbercar, tv5, tvCuslocation, tvtargetAddress;
    TextView tvprice,tvPhonee1,tvname1,tvArrivalTime;
    Double customerfirstlat,customerfirstlong,customerlastlat,customerlastlong,driverlat,driverlong;


    /**
     * @author		Ahmad mashal
     * @version	    V1.0
     * @since		5/4/2020
     * map activity for both users for arriving driver to customer
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(mapActivity.this);
        layout = new LinearLayout(this);
        offerdialog=new LinearLayout(this);
        dialogbtn= (Button )findViewById(R.id.buttonDetails);
        dialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shoshe();
                opendialog();
            }
        });


        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();

        /*Query query = refcustomer
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);*/

               Query query = refLocations
                .orderByChild("duid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);

        Query query2 = refoffergrar
                .orderByChild("customeruid")
                .equalTo(UID);
        query2.addListenerForSingleValueEvent(VEL2);




    }
    private void GetLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mlocationC=location;
                    Toast.makeText(getApplicationContext(),mlocationC.getLatitude()+"."+mlocationC.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(mapActivity.this);
                    if (type){
                        driverlat=mlocationC.getAltitude();
                        driverlong=mlocationC.getLongitude();
                    }else{
                        customerfirstlat=mlocationC.getLatitude();
                        customerfirstlong=mlocationC.getLongitude();
                    }

                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_Code:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    GetLastLocation();
        }

    }



    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {

            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    locationObject1 = data.getValue(locationObject.class);
                    locationObject1.setFirstlatlong1(longc);
                    locationObject1.setFirstlat(lat);
                    name = locationObject1.getName();
                    phone = locationObject1.getPhone();
                    numbercar = locationObject1.getNumbercar();
                    cartype = locationObject1.getTypecar();
                    Caddress = locationObject1.getMyLocation();
                    customerdestination = locationObject1.getAddrees();
                    customerfirstlat = locationObject1.getFirstlat();
                   customerfirstlong = locationObject1.getFirstlatlong1();
                    locationObject1.setFirstlat(customerfirstlat);
                    customerlastlat = locationObject1.getLastlat();
                    customerlastlong = locationObject1.getLastlong();
                }
                type = true;
                GetLastLocation();

            }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
};

    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {

                    graroffer1 = data.getValue(graroffer.class);
                    name1 = graroffer1.getName();
                    phone1 = graroffer1.getPhone();
                    price=graroffer1.getPrice();
                    arrivalTime=graroffer1.getArrivalTime();
                    driverlat=graroffer1.getLat();
                    driverlong=graroffer1.getLong1();

                }
                type = false;
                GetLastLocation();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }

    };





    public void opendialog() {

        if(type){
            /**
             * dialog for if manager
             * <p>
             * @param dS
             */
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mapActivity.this);
            View mView = getLayoutInflater().inflate(customerdialog, null);

            //layout = (LinearLayout) getLayoutInflater().inflate(customerdialog, null);
            tvname = (TextView) mView.findViewById(R.id.tvname);
            tvPhone = (TextView) mView.findViewById(R.id.tvPhone);
            tvtypecar = (TextView) mView.findViewById(R.id.tvtypecar);
            tvnumbercar = (TextView) mView.findViewById(R.id.tvnumbercar);
            tvCuslocation = (TextView) mView.findViewById(R.id.tvCuslocation);
            tvtargetAddress = (TextView) mView.findViewById(R.id.tvtargetAddress);
            time=(EditText) mView.findViewById(R.id.eTeTArrivalTime);
            eTprice=(EditText) mView.findViewById(R.id.eTprice);


            //mBuilder.setTitle("your customer");
           tvname.setText("Name: " + name);
            tvPhone.setText("Phone Number: " + phone);
            tvCuslocation.setText(name +"location: " + Caddress);
            tvtargetAddress.setText("customer destination: " + customerdestination);
            tvnumbercar.setText("Car number: " + numbercar);
            tvtypecar.setText("car Type: " + cartype);
         /*    mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();*/
            time.setVisibility(View.INVISIBLE);
            eTprice.setVisibility(View.INVISIBLE);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
        }
        else{
            /**
             * dialog for if customer
             * <p>
             * @param dS
             */
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mapActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.offerdialog, null);
            tvname1 = (TextView) mView.findViewById(R.id.tvname1);
            tvPhonee1 = (TextView) mView.findViewById(R.id.tvPhonee1);
            tvprice = (TextView) mView.findViewById(R.id.tvprice);
            tvArrivalTime = (TextView) mView.findViewById(R.id.tvArrivalTime);
            tvname1.setText("Name: " + name1);
            tvPhonee1.setText("Phone: " + phone1);
            tvprice.setText("The price  is: " + price);
            tvArrivalTime.setText("ArrivalTime in: " + arrivalTime);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();


        }





    }
    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                dialog.cancel();
            }

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng CFL = new LatLng(customerfirstlat, customerfirstlong);
        LatLng CLL = new LatLng(customerlastlat, customerlastlong);
        LatLng DFL = new LatLng(driverlat, driverlong);
        if(type){
            MarkerOptions mo1 = new MarkerOptions().position(CFL);
            MarkerOptions mo2 = new MarkerOptions().position(CLL);
            MarkerOptions mo3 = new MarkerOptions().position(DFL);

            mMap.addMarker(new MarkerOptions().position(CFL).title("you are here "));
            mMap.addMarker(new MarkerOptions().position(CLL).title("your castomer is here "));
            mMap.addMarker(new MarkerOptions().position(DFL).title("customer destinatione "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(CFL));
            drue();

        }else {
            MarkerOptions mo1 = new MarkerOptions().position(CFL);
            MarkerOptions mo2 = new MarkerOptions().position(CLL);
            MarkerOptions mo3 = new MarkerOptions().position(DFL);

            mMap.addMarker(new MarkerOptions().position(CLL).title("you are here "));
            mMap.addMarker(new MarkerOptions().position(CFL).title("your driver is here "));
            mMap.addMarker(new MarkerOptions().position(DFL).title("your  destinatione "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(CFL));
            drue();
            
            
        }

    }

    private void drue() {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(driverlat, driverlong), new LatLng(customerfirstlat, customerfirstlong)
                        , new LatLng(customerlastlat, customerlastlong))
                .width(5)
                .color(Color.RED));
        updatingLcation();
    }
    private void  updatingLcation(){
        if(customerlastlat==driverlat&&customerlastlong==driverlong){
            if(type){
                 Intent t = new Intent(mapActivity.this, ManagerActivity.class);
                 startActivity(t);
                Toast.makeText(mapActivity.this, "The process is over", Toast.LENGTH_SHORT).show();
            }
            Intent t = new Intent(mapActivity.this, CustomerActivity.class);
            startActivity(t);
            Toast.makeText(mapActivity.this, "The process is over", Toast.LENGTH_SHORT).show();
        }
        GetLastLocation();
        onMapReady(mMap);

    }

}
