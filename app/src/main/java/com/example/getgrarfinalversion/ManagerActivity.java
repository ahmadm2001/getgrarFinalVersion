package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refdrivr;
import static com.example.getgrarfinalversion.FBref.refoffergrar;

import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.R.layout.customerdialog;

public class ManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    Button btn, btnLocation;
    EditText eTprice, eTArrivalTime;
    graroffer graroffer;
    Manager manager = new Manager();
    TextView tvname, tvPhone, tvtypecar, tvnumbercar, tv5, tvCuslocation, tvtargetAddress;
    String customerdestination, customerlocation, Drivieraddress, price, ArrivalTime;
    String name = "aaa", phone = "053302204", numbercar = "787878", typecar = "small", uid, name1, phone1;
    AlertDialog.Builder adb;
    Intent t;
    LinearLayout layout;
    double latitode, longitode;
    LinearLayout customerdialog1;
    Dialog customerdialog12;
    Customer customer;
    locationObject lo;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<String> offer = new ArrayList<>();
    ArrayList<locationObject> locationObjects2 = new ArrayList<>();
    locationObject lb = new locationObject();
    ArrayAdapter<String> adp;
    Intent intent;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

       /* Query query = refcustomer
                .orderByChild("uid")
                .equalTo(uid);
        query.addListenerForSingleValueEvent(VEL);*/


        layout = new LinearLayout(this);

        lv = (ListView) findViewById(R.id.LV1);
        lv.setOnItemClickListener(ManagerActivity.this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        adp = new ArrayAdapter<String>(ManagerActivity.this, R.layout.support_simple_spinner_dropdown_item, offer);

        lv.setAdapter(adp);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        FirebaseUser firebaseUser = refAuth.getCurrentUser();


        //  FirebaseUser firebaseUser = refAuth.getCurrentUser();
        refdrivr.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                //  UID =  (String) data.getKey();
                manager = ds.getValue(Manager.class);
                name1 = manager.getName();
                phone1 = manager.getPhone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed", "Failed to read value", databaseError.toException());
            }
        });
    }





    /*com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    Customer customer = data.getValue(Customer.class);
                    name = customer.getName();
                    phone = customer.getPhone();
                    typecar = customer.getTypeCar();
                    numbercar = customer.getNumbercar();

                    finish();
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };*/


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lb = locationObjects2.get(position);
        uid = lb.getUid();
        name = lb.getName();
        phone = lb.getPhone();
        numbercar = lb.getNumbercar();
        typecar = lb.getTypecar();
        customerlocation = lb.getMyLocation();
        customerdestination = lb.getAddrees();
        Toast.makeText(ManagerActivity.this, "" + name, Toast.LENGTH_SHORT).show();


//        tvname.setText("Name:" + ahmad);
        //      tvPhone.setText("Phone"+phone);
        //    tvnumbercar.setText("Number car"+numbercar);
        //  tvtypecar.setText("Type car"+typecar);
        start();
    }

    ;


    ValueEventListener locationListener;

    {
        locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offer.clear();
                locationObjects2.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String firstAddress = (String) data.getKey();
                    locationObject LocationObject = data.getValue(locationObject.class);
                    locationObjects2.add(LocationObject);
                    String Location = LocationObject.getMyLocation();
                    String finalAddrrss = LocationObject.getAddrees();
                    offer.add("My " + Location + " and my destination is :" + finalAddrrss);

                }

                adp = new ArrayAdapter<String>(ManagerActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, offer);
                lv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ManagerActivity", "Failed to read value", databaseError.toException());

            }

        };
        refLocations.addValueEventListener(locationListener);

    }

    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                ArrivalTime = eTArrivalTime.getText().toString();
                ArrivalTime = ArrivalTime + "Minutes";
                price = eTprice.getText().toString();
                price = price + "₪";
                graroffer = new graroffer(name1, phone1, price, uid, ArrivalTime);
                refoffergrar.child(uid).setValue(graroffer);
                 pd = ProgressDialog.show(ManagerActivity.this, "serch", "serching...", true);
                studentconfirm();
              //  Intent t = new Intent(ManagerActivity.this, mapforManager.class);
               // startActivity(t);
                Toast.makeText(ManagerActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel();

            }
        }

    };

    public void start() {
        layout = (LinearLayout) getLayoutInflater().inflate(customerdialog, null);
        tvname = (TextView) layout.findViewById(R.id.tvname);
        tvPhone = (TextView) layout.findViewById(R.id.tvPhone);
        tvtypecar = (TextView) layout.findViewById(R.id.tvtypecar);
        tvnumbercar = (TextView) layout.findViewById(R.id.tvnumbercar);
        tvCuslocation = (TextView) layout.findViewById(R.id.tvCuslocation);
        eTArrivalTime = (EditText) layout.findViewById(R.id.eTeTArrivalTime);
        eTprice = (EditText) layout.findViewById(R.id.eTprice);
        tvtargetAddress = (TextView) layout.findViewById(R.id.tvtargetAddress);
        adb = new AlertDialog.Builder(this);
        adb.setView(layout);
        adb.setTitle("your customer");
        adb.setMessage("If you want click OK");
        tvname.setText("Name: " + name);
        tvPhone.setText("Phone Number: " + phone);
        tvCuslocation.setText("C location: " + customerlocation);
        tvtargetAddress.setText("customer destination: " + customerdestination);
        tvnumbercar.setText("Car number: " + numbercar);
        tvtypecar.setText("car Type: " + typecar);


        adb.setNegativeButton("cancel", myclick);
        adb.setPositiveButton("OK", myclick);
        adb.show();

    }

    public void studentconfirm() {
        pd = ProgressDialog.show(this, "Awaiting student acceptment", "Waiting...", true);
        Query query = refLocations
                .orderByChild("uid").equalTo(uid);
        query.addValueEventListener(VEL);
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(locationObject.class);
                }
                if (lo.getStatus() == 2) {
                    Toast.makeText(ManagerActivity.this, " Accepted!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    intent = new Intent(ManagerActivity.this, MapActivity.class);
                    startActivity(intent);
                } else {
                    if (lo.getStatus() == 0) {
                        refoffergrar.child(uid).removeValue();
                        refLocations.child(uid).removeValue();
                        Toast.makeText(ManagerActivity.this, " Declined", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }


    };
}












