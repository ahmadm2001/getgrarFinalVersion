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
import android.os.Message;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
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

    Button btLocation,btn;
    TextView tv1, tv2, tv3, tv4, tv5,tvprice,tvPhonee1,tvname1,tvArrivalTime,latLongTV;
    String uid , address1 , location , locationn ,name,phone,numbercar,typecar,lat1,long2,lastlat,lastlong,Duid="";
    EditText targetAddress;
    graroffer lo=new graroffer();
    int status=1;
    Double firstlat,firstlong1;
    ProgressDialog pd;
    LinearLayout offerdialog;
    locationObject locationObject2;
    String ahmad = "haha",Name1,Phone1,ArrivalTime,price;
   // Customer customer=new Customer();
    Manager yes;
    Boolean act=false;
    Boolean aBoolean = true;
    Intent intent;
    Intent t;
    String UID;
    long count;
    AlertDialog.Builder adb;
    locationObject locationObject1;
    Double llat,long3;
    FusedLocationProviderClient fusedLocationProviderClient;
    locationObject locationObject3;
    /**
     * @author		Ahmad mashal
     * @version	    V1.0
     * @since		7/4/2020
     * customer's will order  in this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        btn=(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetAddress = (EditText) findViewById(R.id.targetAddress);
                GeocodingLocation locationAddress = new GeocodingLocation();
                String address = targetAddress.getText().toString();

                locationAddress.getAddressFromLocation(address,
                  getApplicationContext(), new GeocoderHandler());
            }
        });
        //Assign variable
        btLocation = (Button) findViewById(R.id.ButtonGetCurrentLocation);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        latLongTV = (TextView) findViewById(R.id.latLongTV1);



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
                /**
                 * Asking for getting location permission.
                 * <p>
                 *
                 * @param	view Button	on click operate the action.
                 */
                if (ActivityCompat.checkSelfPermission(CustomerActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission grantrd
                    Toast.makeText(CustomerActivity.this, " Double click please ", Toast.LENGTH_LONG).show();

                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(CustomerActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }

            private void getLocation() {
                /**
                 * When premission is granted, the action will fetch the users location.
                 * <p>
                 */
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
                                        "<font color =' #6200EE'><b></b><br></fonnt>"
                                                + addresses.get(0).getLatitude()


                                ));
                                //set Longitude on TextView
                                tv2.setText(Html.fromHtml(
                                        "<font color =' #6200EE'><b></b><br></fonnt>"
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
                            long2=tv2.getText().toString();
                            firstlong1=Double.parseDouble(long2);
                            lat1=tv1.getText().toString();
                            firstlat=Double.parseDouble(lat1);
                        }

                    }
                });
            }
        });
    }

    private class GeocoderHandler extends Handler {
        /**
         * casting string address to latlong
         * <p>
         /* @param  on click operate the action
         */

        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

           // latlong3 = Double.parseDouble(locationAddress);
            lastlat = locationAddress.substring(0,7);
            lastlong =locationAddress.substring(19,26);

            llat = Double.parseDouble(lastlat);
            long3 = Double.parseDouble(lastlong);
            Toast.makeText(CustomerActivity.this, llat+","+long3, Toast.LENGTH_LONG).show();
            latLongTV.setText(locationAddress);

        }
    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * After driver offering price it will come here.
             * <p>
             *
             * @param DataSnapshot dS
             */
            if (dS.exists()) {
                Toast.makeText(CustomerActivity.this, " Accepted", Toast.LENGTH_LONG).show();
                for(DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(graroffer.class);

                }
                if(lo.isAct()) {
                    Name1 = lo.getName();
                    Phone1 = lo.getPhone();
                    price = lo.getPrice();
                    ArrivalTime = lo.getArrivalTime();
                    pd.dismiss();

                    confirmation();
                }

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    public void confirmation(){
        /**
         * Opens up AlertDialog for the confirmation of the Lesson.
         * <p>
         *
         */
        offerdialog = (LinearLayout) getLayoutInflater().inflate(R.layout.offerdialog, null);
        tvname1 = (TextView) offerdialog.findViewById(R.id.tvname1);
        tvPhonee1 = (TextView) offerdialog.findViewById(R.id.tvPhonee1);
        tvprice = (TextView) offerdialog.findViewById(R.id.tvprice);
        tvArrivalTime = (TextView) offerdialog.findViewById(R.id.tvArrivalTime);

        tvname1.setText("Name: " + Name1);
        tvPhonee1.setText("Phone: " + Phone1);
        tvprice.setText("The price  is: " + price);
        tvArrivalTime.setText("ArrivalTime in: " + ArrivalTime);



        adb = new AlertDialog.Builder(this);
        adb.setView(offerdialog);
        adb.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * When accepted it will update the driver's uid, order status, if the order is still active and the price,
                 * it will sent the user to map activity.
                 * <p>
                 *
                 * @param	DialogInterface dialog, int which.
                 */
                lo.setAct(false);
                status = 2;

                locationObject2 = new locationObject(ahmad, address1, UID, name
                        , numbercar, typecar, phone,status,firstlong1,firstlat,llat,long3,Duid,false,count);
                refLocations.child("" + count).setValue(locationObject2);
                Toast.makeText(CustomerActivity.this, " Accepted", Toast.LENGTH_LONG).show();
                intent = new Intent(CustomerActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        adb.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * When cancelled it will update the order status and will keep searching for another driver.
                 * <p>
                 *
                 * @param	DialogInterface dialog, int which.
                 */
                status = 0;
                locationObject3 = new locationObject(ahmad, address1, UID, name
                        , numbercar, typecar, phone,status,firstlong1,firstlat,llat,long3,Duid,true, count);


                refLocations.child("" + count).setValue(locationObject3);
                Toast.makeText(CustomerActivity.this, " Declined", Toast.LENGTH_LONG).show();
                dialog.cancel();
                pd.show();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }
    public void order(View view) {
        /**
         * The action will upload the order to firebase and it will search for an offer.
         * <p>
         *
         * @param view Button on click operate the action.
         */
        ahmad = tv5.getText().toString();
       address1 = targetAddress.getText().toString();
       if (!address1.equals("") && !ahmad.equals("")) {
           DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
           mDatabaseRef.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()) {
                       count = dataSnapshot.getChildrenCount();
                       count = count + 1;
                       locationObject1 = new locationObject(ahmad, address1, UID, name, numbercar, typecar
                               , phone,status,firstlong1,firstlat,llat,long3,Duid,true, count);
                       refLocations.child("" + count).setValue(locationObject1);
                       Toast.makeText(CustomerActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
                       pd = ProgressDialog.show(CustomerActivity.this, "Search", "Searching...", true);
                       Query query = refoffergrar
                               .orderByChild("count")
                               .equalTo(count);
                       query.addValueEventListener(VEL);
                   } else {
                       count = 1;
                       locationObject1 = new locationObject(ahmad, address1, UID, name, numbercar, typecar
                               , phone,status,firstlong1,firstlat,llat,long3,Duid,true, count);
                       refLocations.child("" + count).setValue(locationObject1);
                       Toast.makeText(CustomerActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
                       pd = ProgressDialog.show(CustomerActivity.this, "Search", "Searching...", true);
                       Query query = refoffergrar
                               .orderByChild("count")
                               .equalTo(count);
                       query.addValueEventListener(VEL);
                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
               }
           });

           //targetAddress.setError("you must enter your destination");
       }
       else{
           Toast.makeText(this, "you must enter your destination and click the location button ", Toast.LENGTH_SHORT).show();
       }



        /*locationObject1 = new locationObject(ahmad, address1, UID, name, numbercar, typecar
                , phone,status,firstlong1,firstlat,llat,long3,Duid,true);
        Toast.makeText(CustomerActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();*/

        /*refLocations.child(address1)
                .setValue(locationObject1);

         pd = ProgressDialog.show(this, "serch", "serching...", true);
        Query query = refoffergrar
               .orderByChild("customeruid")
                    .equalTo(UID);
        query.addValueEventListener(VEL);*/
    }




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
        t = new Intent(this, CustomerActivity.class);
        if (s.equals("Profile")) {
            t = new Intent(this, ProfileActivity.class);
            startActivity(t);
        }
        if (s.equals("Order History")) {
            t = new Intent(this, HistoryActivity.class);
            startActivity(t);
        }
        if (s.equals("cridets")) {
            t = new Intent(this, CreditsActivity.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
}

