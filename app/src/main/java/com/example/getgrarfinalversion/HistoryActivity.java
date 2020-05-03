package com.example.getgrarfinalversion;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refLocations;
import static com.example.getgrarfinalversion.FBref.refoffergrar;
import static com.example.getgrarfinalversion.R.layout.customerdialog;


public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

   // String UID,uidd, price, Name1, Phone1,name,phone,price2,firstAdress,lastAdress
           // ,time,arrivalTime,numbercar,cartype,Caddress,customerdestination;
    graroffer lo;
    locationObject locationOb;
    Boolean type;
    LinearLayout OfferDial,odial;
    Intent intent;
    AlertDialog.Builder adb,add;
    ArrayList<String> details = new ArrayList<>();
    ArrayList<graroffer> Detail = new ArrayList<>();
    ArrayList<locationObject> lob = new ArrayList<>();
    ArrayAdapter<String> adp;
    ListView LV1;
    String UID,name,phone,Stowinglicense,numbercar,name1,phone1,cartype,email2,price,arrivalTime,Caddress,customerdestination,uidd;

    EditText eTprice,time2;
    TextView tvname, tvPhone, tvtypecar, tvnumbercar, tv5, tvCuslocation, tvtargetAddress;
    TextView tvprice,tvPhonee1,tvname1,tvArrivalTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);

        LV1 = (ListView) findViewById(R.id.LV1);
        LV1.setOnItemClickListener(HistoryActivity.this);
        LV1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        uidd = firebaseUser.getUid();
/*

        Query query = refoffergrar
                .orderByChild("uid")
                .equalTo(UID);
        query.addValueEventListener(VEL);
        Query query1 = refLocations
               .orderByChild("Duid")
               .equalTo(uidd);
        query1.addValueEventListener(VEL2);*/

        Query query = refLocations
                .orderByChild("duid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL2);

        Query query2 = refoffergrar
                .orderByChild("customeruid")
                .equalTo(UID);
        query2.addListenerForSingleValueEvent(VEL);


    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * Listener for if customer is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(graroffer.class);
                    if (!lo.isAct()) {
                        Detail.add(lo);
                        /*name = lo.getName();
                        phone = lo.getPhone();*/
                        price=lo.getPrice();
                        arrivalTime=lo.getArrivalTime();
                        details.add("price: " + price + " time: " + arrivalTime);
                        type = false;



                    }


                }
                adp = new ArrayAdapter<String>(HistoryActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, details);
                LV1.setAdapter(adp);
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
                for (DataSnapshot data : dS.getChildren()) {
                    locationOb = data.getValue(locationObject.class);
                    if (!locationOb.isAct()) {
                        lob.add(locationOb);
                       // name = locationOb.getName();
                     //   phone = locationOb.getPhone();
                      //  numbercar = locationOb.getNumbercar();
                      //  cartype = locationOb.getTypecar();
                        Caddress = locationOb.getMyLocation();
                        customerdestination = locationOb.getAddrees();
                        details.add("from: " + Caddress + " to: " + customerdestination);
                        type = true;


                    }


                }
                adp = new ArrayAdapter<String>(HistoryActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, details);
                LV1.setAdapter(adp);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    public boolean onCreateOptionsMenu (Menu menu){
        /**
         * Show menu options
         * <p>
         * @param menu
         */
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        /**
         * Respond to the menu item selected
         * <p>
         * @param item
         */
        String st = item.getTitle().toString();
//        if (st.equals("Orders")&&type) {
//            intent = new Intent(HistoryActivity.this, ManagerActivity.class);
//            startActivity(intent);
//        }
//        else if(st.equals("Orders")&&!type){
//            intent = new Intent(HistoryActivity.this, CustomerActivity.class);
//            startActivity(intent);
//        }
        if (st.equals("Order History")) {
            intent = new Intent(HistoryActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        if (st.equals("Profile")) {
            intent = new Intent(HistoryActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if (st.equals("Credits")) {
            intent = new Intent(HistoryActivity.this, CreditsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Will fetch the data based on what the user is and will start the AD.
         * <p>
         * @param parent,view,position,id
         */
        if(!type) {
            if (!Detail.get(position).isAct()) {

                name1 = Detail.get(position).getName();
                phone1 = Detail.get(position).getPhone();
                price=Detail.get(position).getPrice();
                arrivalTime=Detail.get(position).getArrivalTime();


                /*time=Detail.get(position).getArrivalTime();
                price = Detail.get(position).getPrice();
                Name1 = Detail.get(position).getName();
                Phone1 = Detail.get(position).getPhone();*/

                adblv();
            }
        }
        else{
            if (!lob.get(position).isAct()) {
                // true
                name = lob.get(position).getName();
                phone = lob.get(position).getPhone();
                numbercar = lob.get(position).getNumbercar();
                  cartype = lob.get(position).getTypecar();
                Caddress = lob.get(position).getMyLocation();
                customerdestination = lob.get(position).getAddrees();

                adbltc();
            }

        }
    }

    private void adbltc() {
        /**
         * Open up AlertDialog for DRIVER
         * <p>
         */
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HistoryActivity.this);
        View mView = getLayoutInflater().inflate(customerdialog, null);

        tvname = (TextView) mView.findViewById(R.id.tvname);
        tvPhone = (TextView) mView.findViewById(R.id.tvPhone);
        tvtypecar = (TextView) mView.findViewById(R.id.tvtypecar);
        tvnumbercar = (TextView) mView.findViewById(R.id.tvnumbercar);
        tvCuslocation = (TextView) mView.findViewById(R.id.tvCuslocation);
        tvtargetAddress = (TextView) mView.findViewById(R.id.tvtargetAddress);
        time2=(EditText) mView.findViewById(R.id.eTeTArrivalTime);
        eTprice=(EditText) mView.findViewById(R.id.eTprice);


        tvname.setText("Name: " + name);
        tvPhone.setText("Phone Number: " + phone);
        tvCuslocation.setText(name +"location: " + Caddress);
        tvtargetAddress.setText("customer destination: " + customerdestination);
        tvnumbercar.setText("Car number: " + numbercar);
        tvtypecar.setText("car Type: " + cartype);
        mBuilder.setTitle("your customer history");
        time2.setVisibility(View.INVISIBLE);
        eTprice.setVisibility(View.INVISIBLE);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    public void adblv(){
        /**
         * Open up AlertDialog for Customer
         * <p>
         */

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HistoryActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.offerdialog, null);
        tvname1 = (TextView) mView.findViewById(R.id.tvname1);
        tvPhonee1 = (TextView) mView.findViewById(R.id.tvPhonee1);
        tvprice = (TextView) mView.findViewById(R.id.tvprice);
        tvArrivalTime = (TextView) mView.findViewById(R.id.tvArrivalTime);
        mBuilder.setTitle("your driver");
        tvname1.setText("Name: " + name1);
        tvPhonee1.setText("Phone: " + phone1);
        tvprice.setText("The price  is: " + price);
        tvArrivalTime.setText("ArrivalTime in: " + arrivalTime);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

}

