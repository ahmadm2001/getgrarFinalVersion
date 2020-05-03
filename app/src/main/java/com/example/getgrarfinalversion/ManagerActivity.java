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
import android.view.Menu;
import android.view.MenuItem;
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
import static com.example.getgrarfinalversion.R.layout.activity_manager;
import static com.example.getgrarfinalversion.R.layout.customerdialog;

public class ManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
  //  Button btn, btnLocation;
    EditText eTprice, eTArrivalTime;
    graroffer graroffer;
    Boolean act=false;
    Double lat=null, long1=null;
    Manager manager = new Manager();
    TextView tvname, tvPhone, tvtypecar, tvnumbercar, tv5, tvCuslocation, tvtargetAddress,tv1,tv2;
    String customerdestination, customerlocation, Drivieraddress, price, ArrivalTime,lat1,long2,driveruid="es24";
    String name = "aaa", phone = "053302204", numbercar = "787878", typecar = "small", uid, name1, phone1;
    AlertDialog.Builder adb;
    Intent t;
    long count;
    LinearLayout layout;
    //double latitode, longitode;
   // LinearLayout customerdialog1;
   // Dialog customerdialog12;
    Customer customer;
    locationObject lo;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<String> offer = new ArrayList<>();
    ArrayList<locationObject> locationObjects2 = new ArrayList<>();
    locationObject lb = new locationObject();
    ArrayAdapter<String> adp;
    Intent intent;
    ProgressDialog pd;
    Button btLocation;
    /**
     * @author		Ahmad mashal
     * @version	    V1.0
     * @since		8/4/2020
     * This activity will show any active order based on location.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        FirebaseUser firebaseUser2 = refAuth.getCurrentUser();
        driveruid=firebaseUser2.getUid();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        btLocation=(Button)findViewById(R.id.btn) ;

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check premission
                if (ActivityCompat.checkSelfPermission(ManagerActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ManagerActivity.this, " Double click please ", Toast.LENGTH_LONG).show();

                    //when permission grantrd
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(ManagerActivity.this,
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
                            Geocoder geocoder = new Geocoder(ManagerActivity.this,
                                    Locale.getDefault());
                            // Intiialize address list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );
                                //set latitude on TextView
                                tv1.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b></b><br></fonnt>"
                                                + addresses.get(0).getLatitude()


                                ));
                                //set Longitude on TextView
                                tv2.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b></b><br></fonnt>"
                                                + addresses.get(0).getLongitude()

                                ));

                                long2=tv2.getText().toString();
                                long1=Double.parseDouble(long2);
                                lat1=tv1.getText().toString();
                                lat=Double.parseDouble(lat1);
                                Toast.makeText(ManagerActivity.this, " location"+long1+","+lat, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
            }
        });




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








    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Respond to the order that has been clicked.
         * <p>
         * @param parent,view,position,id
         */
        if(long1==null &&lat==null){
            Toast.makeText(ManagerActivity.this,"please find your location",Toast.LENGTH_SHORT).show();
        }
        lb = locationObjects2.get(position);
        uid = lb.getUid();
        name = lb.getName();
        phone = lb.getPhone();
        numbercar = lb.getNumbercar();
        count = lb.getCount();
        typecar = lb.getTypecar();
        customerlocation = lb.getMyLocation();
        customerdestination = lb.getAddrees();
        Toast.makeText(ManagerActivity.this, "" + name, Toast.LENGTH_SHORT).show();



        start();
    }

    ValueEventListener locationListener;{
        locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offer.clear();
                locationObjects2.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String firstAddress = (String) data.getKey();
                    locationObject LocationObject = data.getValue(locationObject.class);
                    if(LocationObject.isAct()) {
                        locationObjects2.add(LocationObject);
                        String Location = LocationObject.getMyLocation();
                        String finalAddrrss = LocationObject.getAddrees();
                        offer.add(Location);
                    }

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
        /**
         * Respond to Price offer acceptment or decline
         * <p>
         * @param dialog,which
         */
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                ArrivalTime = eTArrivalTime.getText().toString();
                ArrivalTime = ArrivalTime + "Minutes";
                price = eTprice.getText().toString();
                price = price + "₪";
                graroffer = new graroffer(name1, phone1, price, uid, ArrivalTime, lat, long1,driveruid,true, count);
                Toast.makeText(ManagerActivity.this, "" + graroffer.isAct(), Toast.LENGTH_SHORT).show();
                refoffergrar.child(""+ count).setValue(graroffer);
                 pd = ProgressDialog.show(ManagerActivity.this, "serch", "serching...", true);
                customerconfirm();
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
        /**
         * AlertDialog for the customer's Information.
         * <p>
         */

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
        adb.setTitle(name+"'s order" );
        //adb.setMessage("To continue click ok ");
        tvname.setText("Name: " + name);
        tvPhone.setText("Phone Number: " + phone);
        tvCuslocation.setText( customerlocation);
        tvtargetAddress.setText(name+"'s destination: " + customerdestination);
        tvnumbercar.setText("Car number: " + numbercar);
        tvtypecar.setText("car Type: " + typecar);


        adb.setNegativeButton("cancel", myclick);
        adb.setPositiveButton("OK", myclick);
        adb.show();

    }

    public void customerconfirm() {
        /**
         * Wait for the customer to confirm or decline the offer
         * <p>
         */

        pd = ProgressDialog.show(this, "Awaiting customer acceptment", "Waiting...", true);
        Query query = refLocations
                .orderByChild("count").equalTo(count);
        query.addValueEventListener(VEL);
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             *  Listener for if customer confirms the order or declining.
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(locationObject.class);
                }
                if (lo.getStatus() == 2) {
                    graroffer.setAct(false);
                    refoffergrar.child(""+graroffer.getCount()).setValue(graroffer);
                    lo.setDuid(driveruid);
                    refLocations.child(""+lo.getCount()).setValue(lo);
                    Toast.makeText(ManagerActivity.this, " Accepted!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    intent = new Intent(ManagerActivity.this, mapActivity.class);
                    startActivity(intent);
                } else {
                    if (lo.getStatus() == 0) {
                        refoffergrar.child("" + lo.getCount()).removeValue();
                        Toast.makeText(ManagerActivity.this, " Declined", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                        pd.cancel();

                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Show menu options
         * <p>
         * @param menu
         */
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**
         * Respond to the menu item selected
         * <p>
         * @param item
         */
        String s = item.getTitle().toString();
        t = new Intent(this, ManagerActivity.class);
        if (s.equals("Profile")) {
            t = new Intent(this, ProfileActivity.class);
            startActivity(t);
        }
        if (s.equals("Order History")) {
            t = new Intent(this, HistoryActivity.class);
            startActivity(t);
        }
        if (s.equals("Credits")) {
            t = new Intent(this, CreditsActivity.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
}