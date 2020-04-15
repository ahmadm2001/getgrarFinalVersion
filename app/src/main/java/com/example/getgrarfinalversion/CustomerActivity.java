package com.example.getgrarfinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import static com.example.getgrarfinalversion.FBref.refoffergrar;

public class CustomerActivity extends AppCompatActivity {
    //Initialize variable
    Button btLocation;
    TextView tv1, tv2, tv3, tv4, tv5,tvprice,tvPhonee1,tvname1,tvArrivalTime;
    String uid = "sfns", address1 = "dmfdf", location = "ah,d", locationn = "locationssss",name,phone,numbercar,typecar;
    EditText targetAddress;
    graroffer lo=new graroffer();
    int status=1;
    ProgressDialog pd;
    LinearLayout offerdialog;
    locationObject locationObject2;
    String ahmad = "haha",Name1,Phone1,ArrivalTime,price;
   // Customer customer=new Customer();
    Manager yes;
    Boolean aBoolean = true,inorder;
    Intent intent;
    Intent t;
    String UID;
    AlertDialog.Builder adb;
    locationObject locationObject1;
    FusedLocationProviderClient fusedLocationProviderClient;
    locationObject locationObject3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        //Assign variable
        targetAddress = (EditText) findViewById(R.id.targetAddress);
        btLocation = (Button) findViewById(R.id.ButtonGetCurrentLocation);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        //get uid from firebase
       // FirebaseUser firebaseUser = refAuth.getCurrentUser();
        //UID=firebaseUser.getUid();

        /*FirebaseUser firebaseUser = refcustomer.();
        refcustomer.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer =dataSnapshot.getValue(Customer.class);
                name = customer.getName();
                phone = customer.getPhone();
                typecar=customer.getTypeCar();
                numbercar=customer.getNumbercar();
                UID = customer.getUid();
                // קורא מידע מהפיירבייס
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/






        ValueEventListener uploadlitner = new ValueEventListener (){
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                for(DataSnapshot data : ds.getChildren()){
                    UID =  (String) data.getKey();
                    Customer customer = data.getValue(Customer.class);
                    name = customer.getName();
                    phone = customer.getPhone();
                    typecar=customer.getTypeCar();
                    numbercar=customer.getNumbercar();
                    inorder=customer.getinorder();
               // UID = customer.getUid();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("mehmezh_dog", "Failed to read value", databaseError.toException());
            }
        };
        refcustomer.addValueEventListener(uploadlitner);








        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check premission
                if (ActivityCompat.checkSelfPermission(CustomerActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission grantrd
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(CustomerActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }

            private void getLocation() {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // Intiialize location
                        Location location = task.getResult();
                        if (location != null) {
                            //Intiialize geoCoder
                            Geocoder geocoder = new Geocoder(CustomerActivity.this,
                                    Locale.getDefault());
                            // Intiialize address list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
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


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
            }
        });
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(graroffer.class);

                }
                Name1 = lo.getName();
                Phone1 = lo.getPhone();
                price = lo.getPrice();
                ArrivalTime = lo.getArrivalTime();
                pd.dismiss();
                confirmation();

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    public void confirmation(){
        offerdialog = (LinearLayout) getLayoutInflater().inflate(R.layout.offerdialog, null);

        tvname1 = (TextView) offerdialog.findViewById(R.id.tvname1);
        tvPhonee1 = (TextView) offerdialog.findViewById(R.id.tvPhonee1);
        tvprice = (TextView) offerdialog.findViewById(R.id.tvprice);
        tvArrivalTime = (TextView) offerdialog.findViewById(R.id.tvArrivalTime);

        tvname1.setText("Name: " + Name1);
        tvPhonee1.setText("Phone: " + Phone1);
        tvprice.setText("The price  is: " + price);
        tvArrivalTime.setText("ArrivalTime in: " + Name1);



        adb = new AlertDialog.Builder(this);
        adb.setView(offerdialog);
        adb.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                status = 2;
                locationObject2 = new locationObject(ahmad, address1, UID, name, numbercar, typecar, phone,status);
                refLocations.child(address1).setValue(locationObject2);
                Toast.makeText(CustomerActivity.this, "Lesson Accepted", Toast.LENGTH_LONG).show();
                intent = new Intent(CustomerActivity.this,mapforManager.class);
                startActivity(intent);
            }
        });
        adb.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                status = 0;
                locationObject3 = new locationObject(ahmad, address1, UID, name, numbercar, typecar, phone,status);
                refLocations.child(address1).setValue(locationObject3);
                Toast.makeText(CustomerActivity.this, "Lesson Declined", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    public void order(View view) {
        ahmad = tv5.getText().toString();
        address1 = targetAddress.getText().toString();
        if (address1.isEmpty()) targetAddress.setError("you must enter your destination");
        locationObject1 = new locationObject(ahmad, address1, UID, name, numbercar, typecar, phone,status);
        Toast.makeText(CustomerActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
        refLocations.child(address1).setValue(locationObject1);
         pd = ProgressDialog.show(this, "serch", "serching...", true);
        Query query = refoffergrar
               .orderByChild("uid")
                    .equalTo(UID);
        query.addValueEventListener(VEL);
      //  Query query1 = refdrivr.child("manager").orderByChild("phone").equalTo("true");
       // t = new Intent(this, Detailstow.class);
        //startActivity(t);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String s = item.getTitle().toString();
        t = new Intent(this, CustomerActivity.class);
        if (s.equals("Profile")) {
            t = new Intent(this, ProfileActivity.class);
            startActivity(t);
        }
        if (s.equals("History")) {
            t = new Intent(this, CreditsActivity.class);
            startActivity(t);
        }
        if (s.equals("cridets")) {
            t = new Intent(this, CreditsActivity.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
}

