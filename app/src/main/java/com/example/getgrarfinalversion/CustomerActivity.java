package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.FBref.refdrivr;

public class CustomerActivity extends AppCompatActivity {
//Initialize variable
    Button btLocation;
    TextView tv1,tv2,tv3,tv4,tv5;
    String uid="sfns",address1="dmfdf",location="ah,d", locationn = "locationssss";
    EditText targetAddress;
    String ahmad="haha";
    Customer customer;
    Manager yes;
    Boolean aBoolean=true;
    Intent intent;
    locationObject locationObject1;
    FusedLocationProviderClient fusedLocationProviderClient ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        //Assign variable
        targetAddress=(EditText)findViewById(R.id.targetAddress);
        btLocation=(Button)findViewById(R.id.ButtonGetCurrentLocation);
        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        tv3=(TextView) findViewById(R.id.tv3);
        tv4=(TextView) findViewById(R.id.tv4);
        tv5=(TextView) findViewById(R.id.tv5);

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check premission
                if (ActivityCompat.checkSelfPermission(CustomerActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    //when permission grantrd
                    getLocation();
                }else {
                    //when permission denied
                    ActivityCompat.requestPermissions(CustomerActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

                }
            }

            private void getLocation() {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // Intiialize location
                        Location location = task.getResult();
                        if (location!=null){
                            //Intiialize geoCoder
                            Geocoder geocoder = new Geocoder(CustomerActivity.this,
                                    Locale.getDefault());
                            // Intiialize address list
                            try {
                                List<Address> addresses= geocoder.getFromLocation(
                                        location.getLatitude(),location.getLongitude(),1
                                );
                                //set latitude on TextView
                                tv1.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b>Latitude:</b><br></fonnt>"
                                        + addresses.get(0).getLatitude()

                                ));
                                //set Longitude on TextView
                                tv2.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b>Longitude:</b><br></fonnt>"
                                                + addresses.get(0).getLongitude()

                                ));
                                //set country name
                                tv3.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b>country:</b><br></fonnt>"
                                                + addresses.get(0).getCountryName()));
                                //set Locality
                                tv4.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b>Locality:</b><br></fonnt>"
                                                + addresses.get(0).getLocality())
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
            }
        });
    }

   public void order(View view) {
       ahmad = tv5.getText().toString();
       address1 = targetAddress.getText().toString();

       if (address1.isEmpty()) targetAddress.setError("you must enter your destination");
       locationObject1 = new locationObject(ahmad, address1,uid);
       Toast.makeText(CustomerActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
       refLocations.child(address1).setValue(locationObject1);
       final ProgressDialog pd = ProgressDialog.show(this, "serch", "serching...", true);
       Query query = refdrivr.child("manager").orderByChild("phone").equalTo("true");
       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
               {//TODO get the data here
                  aBoolean=yes.getYes();
                   if(aBoolean){
                       intent = new Intent(CustomerActivity.this,Detailstow.class);
                       startActivity(intent);
                       Toast.makeText(CustomerActivity.this, "Successful match found", Toast.LENGTH_LONG).show();
                   }else {
                       intent = new Intent(CustomerActivity.this, ManagerActivity.class);
                       startActivity(intent);
                       Toast.makeText(CustomerActivity.this, "no one can take you", Toast.LENGTH_LONG).show();
                   }

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       };

       query.addValueEventListener(valueEventListener);

       {


           }


       }
   }

