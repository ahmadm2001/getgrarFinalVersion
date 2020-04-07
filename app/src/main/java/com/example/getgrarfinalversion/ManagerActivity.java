package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.R.layout.customerdialog;

public class ManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    Button btn;
    TextView tvname  ,tvPhone,tvtypecar,tvnumbercar,tv5;
    String address;
    String name,phone,numbercar,typecar;
    AlertDialog.Builder adb;
    Intent t;
    LinearLayout customerdialog1;
    Dialog customerdialog12;
    Customer customer;
    FusedLocationProviderClient fusedLocationProviderClient ;
    ArrayList<String> offer=new ArrayList<>();
    ArrayList<locationObject> locationObjects2 = new ArrayList<>();
    ArrayAdapter<String> adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        lv=(ListView) findViewById(R.id.LV1);
        lv.setOnItemClickListener(ManagerActivity.this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(ManagerActivity.this, R.layout.support_simple_spinner_dropdown_item, offer);

        lv.setAdapter(adp);


        }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        start();
    };



        ValueEventListener locationListener;{
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
    DialogInterface.OnClickListener myclick= new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                getLocation();
                Intent t=new Intent(ManagerActivity.this,mapforManager.class);
                Toast.makeText(ManagerActivity.this,"Successful",Toast.LENGTH_SHORT).show();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel();

            }
        }

    };
    public void start() {
        ValueEventListener customerdetails;

        customerdialog1 = (LinearLayout) getLayoutInflater().inflate(R.layout.customerdialog, null);
        adb = new AlertDialog.Builder(this);
        adb.setView(customerdialog1);
        adb.setTitle("customer details  ");
        tvname = (TextView) findViewById(R.id.tvname);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvtypecar = (TextView) findViewById(R.id.tvtypecar);
        tvnumbercar = (TextView) findViewById(R.id.tvnumbercar);
        adb.setPositiveButton("enter", (DialogInterface.OnClickListener) myclick);
        adb.setNegativeButton("cancel", (DialogInterface.OnClickListener) myclick);
        adb.show();
        customerdetails = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String uid = (String) data.getKey();
                    Customer c = data.getValue(Customer.class);

                    name = c.getName();
                    phone = c.getPhone();
                    typecar = c.getTypeCar();
                    numbercar = c.getNumbercar();
                    tvname.setText(name);
                    tvPhone.setText(phone);
                    tvnumbercar.setText(numbercar);
                    tvtypecar.setText(typecar);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ManagerActivity", "Failed to read value", databaseError.toException());
            }


        };refcustomer.addValueEventListener(customerdetails);


    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // Intiialize location
                Location location = task.getResult();
                if (location!=null){
                    //Intiialize geoCoder
                    Geocoder geocoder = new Geocoder(ManagerActivity.this,
                            Locale.getDefault());
                    // Intiialize address list
                    try {
                        List<Address> addresses= geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        //set address
                        tv5.setText(Html.fromHtml(
                                "<font color =' #6200EE'><b>Address:</b><br></fonnt>"
                                        + addresses.get(0).getAddressLine(0))
                        );


                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }

            }
        });
        address = tv5.getText().toString();
        Toast.makeText(ManagerActivity.this, address, Toast.LENGTH_LONG).show();
    }

 }












