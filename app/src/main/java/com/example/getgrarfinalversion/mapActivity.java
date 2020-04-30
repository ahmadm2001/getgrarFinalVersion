package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.FBref.refdrivr;
import static com.example.getgrarfinalversion.FBref.refoffergrar;
import static com.example.getgrarfinalversion.R.layout.customerdialog;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback{
    EditText eTprice,time;
    Polyline polyline;
    locationObject locationObject1;
    graroffer graroffer1;
    String UID,name,phone,Stowinglicense,numbercar,name1,phone1,cartype,email2,price,arrivalTime,Caddress,customerdestination;
    Boolean type=true;
    GoogleMap gMap;
    AlertDialog.Builder adb;
    AlertDialog.Builder adb2;
    TextView tvname, tvPhone, tvtypecar, tvnumbercar, tv5, tvCuslocation, tvtargetAddress;
    Button dialogbtn;
    LinearLayout offerdialog,layout;
    TextView tvprice,tvPhonee1,tvname1,tvArrivalTime;
    Double customerfirstlat=32.223,customerfirstlong=33.44,customerlastlat=23.3,customerlastlong=50.66,driverlat=90.9,driverlong=80.00;


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
                shoshe();
                opendialog();
            }
        });


        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();

        Query query = refcustomer
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);




    }

    public void shoshe(){
        if(type){
            Query query2 = refoffergrar
                    .orderByChild("customeruid")
                    .equalTo(UID);
            query2.addListenerForSingleValueEvent(VEL3);


        }
        else{
            Query query2 = refLocations
                    .orderByChild("duid")
                    .equalTo(UID);
            query2.addListenerForSingleValueEvent(VEL2);

        }
    }

    com.google.firebase.database.ValueEventListener VEL3 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * Listener for if customer is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    locationObject1 = data.getValue(locationObject.class);
                    name = locationObject1.getName();
                    phone = locationObject1.getPhone();
                    numbercar=locationObject1.getNumbercar();
                    cartype=locationObject1.getTypecar();
                    Caddress=locationObject1.getMyLocation();
                    customerdestination=locationObject1.getAddrees();
                    customerfirstlat=locationObject1.getFirstlat();
                    customerfirstlong=locationObject1.getFirstlatlong1();
                    customerlastlat=locationObject1.getLastlat();
                    customerlastlong=locationObject1.getLastlong();

                     //true = is customer

                }
                type = true;

            }

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };


    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * Listener for if customer is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
               /* for(DataSnapshot data : dS.getChildren()) {
                    locationObject1 = data.getValue(locationObject.class);
                    name = locationObject1.getName();
                    phone = locationObject1.getPhone();
                    numbercar=locationObject1.getNumbercar();
                    cartype=locationObject1.getTypecar();
                    Caddress=locationObject1.getMyLocation();
                    customerdestination=locationObject1.getAddrees();
                    customerfirstlat=locationObject1.getFirstlat();
                    customerfirstlong=locationObject1.getFirstlatlong1();
                    customerlastlat=locationObject1.getLastlat();
                    customerlastlong=locationObject1.getLastlong();

                     //true = is customer

                }*/
                type = true;

            }
            else {
                type=false;

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };




    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * Listener for if driver is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    graroffer1 = data.getValue(graroffer.class);
                    name1 = graroffer1.getName();
                    phone1 = graroffer1.getPhone();
                    price=graroffer1.getPrice();
                    arrivalTime=graroffer1.getArrivalTime();
                    driverlat=graroffer1.getLat();
                    driverlong=graroffer1.getLong1();
                    //false = is drivier

                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public void opendialog() {
        shoshe();
        if(type){

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
       /*     offerdialog = (LinearLayout) getLayoutInflater().inflate(R.layout.offerdialog, null);
            tvname1 = (TextView) offerdialog.findViewById(R.id.tvname1);
            tvPhonee1 = (TextView) offerdialog.findViewById(R.id.tvPhonee1);
            tvprice = (TextView) offerdialog.findViewById(R.id.tvprice);
            tvArrivalTime = (TextView) offerdialog.findViewById(R.id.tvArrivalTime);
            adb.setView(offerdialog);
            adb.setTitle("Your driver");
            tvname1.setText("Name: " + name1);
            tvPhonee1.setText("Phone: " + phone1);
            tvprice.setText("The price  is: " + price);
            tvArrivalTime.setText("ArrivalTime in: " + arrivalTime);
            adb.setPositiveButton("back to map", myclick);
            adb.show();*/

        }
        else {
            layout = (LinearLayout) getLayoutInflater().inflate(customerdialog, null);
            tvname = (TextView) layout.findViewById(R.id.tvname);
            tvPhone = (TextView) layout.findViewById(R.id.tvPhone);
            tvtypecar = (TextView) layout.findViewById(R.id.tvtypecar);
            tvnumbercar = (TextView) layout.findViewById(R.id.tvnumbercar);
            tvCuslocation = (TextView) layout.findViewById(R.id.tvCuslocation);
            tvtargetAddress = (TextView) layout.findViewById(R.id.tvtargetAddress);
            time=(EditText) layout.findViewById(R.id.eTeTArrivalTime);
            eTprice=(EditText) layout.findViewById(R.id.eTprice);

            adb2 = new AlertDialog.Builder(this);
            adb2.setView(layout);
            adb2.setTitle("your customer");
            tvname.setText("Name: " + name);
            tvPhone.setText("Phone Number: " + phone);
            tvCuslocation.setText(name +"location: " + Caddress);
            tvtargetAddress.setText("customer destination: " + customerdestination);
            tvnumbercar.setText("Car number: " + numbercar);
            tvtypecar.setText("car Type: " + cartype);
            time.setVisibility(View.INVISIBLE);
            eTprice.setVisibility(View.INVISIBLE);
            adb2.setPositiveButton("back to map", myclick);
            adb2.show();

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
       //if (firstLG == null || secLG == null || lastLG == null) {
         //   Toast.makeText(mapActivity.this, " map cant be open  ", Toast.LENGTH_LONG).show();

        //}
        gMap = googleMap;
          Toast.makeText(mapActivity.this,  driverlat+" , "+driverlong, Toast.LENGTH_LONG).show();
       LatLng firstLG = new LatLng(driverlat, driverlong);
        LatLng secLG= new LatLng(customerfirstlat,customerfirstlong);
       LatLng lastLG= new LatLng(customerlastlat,customerlastlong);
        if(type){
            MarkerOptions markerOptions1 = new MarkerOptions().position(secLG).title("You Are Here");
            MarkerOptions markerOptions=new MarkerOptions().position(firstLG).title("your driver is here");
            MarkerOptions markerOptions2=new MarkerOptions().position(lastLG).title("Your destination");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(secLG));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(secLG, 6));
            googleMap.addMarker(markerOptions1);
            googleMap.addMarker(markerOptions);
            googleMap.addMarker(markerOptions2);
            Draw();

        }
        else {
            MarkerOptions mdriver = new MarkerOptions().position(firstLG).title("You Are Here");
            MarkerOptions mcustomer = new MarkerOptions().position(secLG).title("Your customer is here");
            MarkerOptions mdestination = new MarkerOptions().position(lastLG).title("customer destination");

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(firstLG));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(firstLG, 6));
            Marker driverL = googleMap.addMarker(mdriver);
            Marker customerL = googleMap.addMarker(mcustomer);
            Marker destination = googleMap.addMarker(mdestination);


            if (firstLG == secLG) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(secLG));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(secLG, 6));
                Draw();
                if (secLG == lastLG) {
                    Toast.makeText(mapActivity.this, " The process was completed successfully   ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mapActivity.this, HistoryActivity.class);
                    startActivity(intent);

                }

            }

        }
}
    private void Draw() {
        Polyline line = gMap.addPolyline(new PolylineOptions()
                .add(new LatLng(driverlat, driverlong), new LatLng(customerfirstlat,customerfirstlong),new LatLng(customerlastlat,customerlastlong))
                .width(5)
                .color(Color.RED));

    }



}
