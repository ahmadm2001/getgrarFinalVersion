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

    LatLng CFL ;
    LatLng CLL;
    LatLng DFL;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    EditText eTprice,time;
    locationObject locationObject1;
    graroffer graroffer1;
    String UID,name,phone,Stowinglicense,numbercar,name1,phone1,cartype,email2,price,arrivalTime,Caddress,customerdestination;
    Boolean type=true;

    GoogleMap gMap;
    AlertDialog.Builder adb;
    private Marker c;
    private Marker d;
    private Marker cl;
    AlertDialog.Builder adb2;
    long count;
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


        Intent t = getIntent();
        count = t.getLongExtra("count", -9);
        Toast.makeText(this, ""+ count, Toast.LENGTH_SHORT).show();
               Query query = refLocations
                .orderByChild("duid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);

                Query query2 = refoffergrar
                .orderByChild("customeruid")
                .equalTo(UID);
        query2.addListenerForSingleValueEvent(VEL2);

        Query query3 = refLocations
                .orderByChild("uid")
                .equalTo(UID);
        query3.addListenerForSingleValueEvent(VEL3);

        Query query4 = refoffergrar
                .orderByChild("driveruid")
                .equalTo(UID);
        query4.addListenerForSingleValueEvent(VEL4);




    }




    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {

            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    locationObject1 = data.getValue(locationObject.class);
                    // locationObject1.setFirstlatlong1(longc);
                    if (locationObject1.getCount() == count) {
                    //    locationObject1.setFirstlat(lat);
                        name = locationObject1.getName();
                        phone = locationObject1.getPhone();
                        numbercar = locationObject1.getNumbercar();
                        cartype = locationObject1.getTypecar();
                        Caddress = locationObject1.getMyLocation();
                        customerdestination = locationObject1.getAddrees();
                        customerfirstlat = locationObject1.getFirstlat();
                        customerfirstlong = locationObject1.getFirstlatlong1();
                        customerlastlat = locationObject1.getLastlat();
                        customerlastlong = locationObject1.getLastlong();
                        type = true;
                    }
                }

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
                    if (graroffer1.getCount() == count) {
                        name1 = graroffer1.getName();
                        phone1 = graroffer1.getPhone();
                        price = graroffer1.getPrice();
                        arrivalTime = graroffer1.getArrivalTime();
                        driverlat = graroffer1.getLat();
                        driverlong = graroffer1.getLong1();


                        type = false;
                    }

                }

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }

    };

    com.google.firebase.database.ValueEventListener VEL3 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {

            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    locationObject1 = data.getValue(locationObject.class);
                    // locationObject1.setFirstlatlong1(longc);
                    if (locationObject1.getCount() == count) {
                        //    locationObject1.setFirstlat(lat);
                        name = locationObject1.getName();
                        phone = locationObject1.getPhone();
                        numbercar = locationObject1.getNumbercar();
                        cartype = locationObject1.getTypecar();
                        Caddress = locationObject1.getMyLocation();
                        customerdestination = locationObject1.getAddrees();
                        customerfirstlat = locationObject1.getFirstlat();
                        customerfirstlong = locationObject1.getFirstlatlong1();
                        customerlastlat = locationObject1.getLastlat();
                        customerlastlong = locationObject1.getLastlong();

                    }
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    com.google.firebase.database.ValueEventListener VEL4 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {

                    graroffer1 = data.getValue(graroffer.class);
                    if (graroffer1.getCount() == count) {
                        name1 = graroffer1.getName();
                        phone1 = graroffer1.getPhone();
                        price = graroffer1.getPrice();
                        arrivalTime = graroffer1.getArrivalTime();
                        driverlat = graroffer1.getLat();
                        driverlong = graroffer1.getLong1();



                    }

                }

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
        Toast.makeText(this, "open curler", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        Toast.makeText(this, "to open google map click on the left circle button", Toast.LENGTH_SHORT).show();
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
        //GetLastLocation();
        onMapReady(mMap);

    }

    public void lala(View view) {
            shos();
           // GetLastLocation();
    }
    public void shos() {
        CFL = new LatLng(customerfirstlat, customerfirstlong);
        CLL = new LatLng(customerlastlat, customerlastlong);
        DFL = new LatLng(driverlat, driverlong);
        if(type){
//            MarkerOptions mo1 = new MarkerOptions().position(CFL);
//            MarkerOptions mo2 = new MarkerOptions().position(CLL);
//            MarkerOptions mo3 = new MarkerOptions().position(DFL);
            c= mMap.addMarker(new MarkerOptions()
                    .position(CFL)
                    .title("your castomer is here"));
            c.setTag(0);
            d= mMap.addMarker(new MarkerOptions()
                    .position(DFL)
                    .title("you are here"));
            d.setTag(0);

            cl= mMap.addMarker(new MarkerOptions()
                    .position(CLL)
                    .title("customer destinatione"));
            cl.setTag(0);


            mMap.moveCamera(CameraUpdateFactory.newLatLng(CFL));
            drue();

        }else {

            c= mMap.addMarker(new MarkerOptions()
                    .position(CFL)
                    .title("you are here"));
            c.setTag(0);
            d= mMap.addMarker(new MarkerOptions()
                    .position(DFL)
                    .title("your driver is here"));
            d.setTag(0);

            cl= mMap.addMarker(new MarkerOptions()
                    .position(CLL)
                    .title("your  destinatione"));


            cl.setTag(0);

            drue();


        }
    }

    public void finish(View view) {
        if(type){
             Intent t = new Intent(mapActivity.this, ManagerActivity.class);
            startActivity(t);
            Toast.makeText(this, "Thanks for using Get Grar", Toast.LENGTH_SHORT).show();

        }else {
            Intent t = new Intent(mapActivity.this, CustomerActivity.class);
            startActivity(t);
            Toast.makeText(this, "Thanks for using Get Grar", Toast.LENGTH_SHORT).show();


        }
    }
}
