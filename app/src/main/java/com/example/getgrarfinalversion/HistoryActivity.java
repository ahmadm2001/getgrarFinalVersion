package com.example.getgrarfinalversion;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String UID,uidd, price, Name1, Phone1,name,phone,price2,firstAdress,lastAdress,time;
    TextView tvnamee1, tvPhonee1, tvname1,tvPhone1,tVprice1,tVprice;
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


        Query query = refoffergrar
                .orderByChild("uid")
                .equalTo(UID);
        query.addValueEventListener(VEL);

        Query query1 = refLocations
                .orderByChild("duid")
                .equalTo(uidd);
        query1.addValueEventListener(VEL2);


    }
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             * Listener for if student is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(graroffer.class);
                    if (!lo.isAct()) {
                        Detail.add(lo);
                        price=lo.getPrice();
                        time=lo.getArrivalTime();
                        details.add("price: " + price + " time: " + time);
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
             * Listener for if teacher is authenticated with the app
             * <p>
             * @param dS
             */
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    locationOb = data.getValue(locationObject.class);
                    if (!locationOb.isAct()) {
                        lob.add(locationOb);
                        firstAdress=locationOb.getMyLocation();
                        lastAdress=locationOb.getAddrees();
                        details.add("from: " + firstAdress + " to: " + lastAdress);
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
        if (st.equals("Orders")&&type) {
            intent = new Intent(HistoryActivity.this, ManagerActivity.class);
            startActivity(intent);
        }
        else if(st.equals("Orders")&&!type){
            intent = new Intent(HistoryActivity.this, CustomerActivity.class);
            startActivity(intent);
        }
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

                time=Detail.get(position).getArrivalTime();
                price = Detail.get(position).getPrice();
                Name1 = Detail.get(position).getName();
                Phone1 = Detail.get(position).getPhone();

                adblv();
            }
        }
        else{
            if (!lob.get(position).isAct()) {
                name = lob.get(position).getName();
                phone = lob.get(position).getPhone();

                adbltc();
            }

        }
    }

    private void adbltc() {
        /**
         * Open up AlertDialog for Teacher
         * <p>
         */
        odial = (LinearLayout) getLayoutInflater().inflate(R.layout.historydial, null);

        tvname1 = (TextView) odial.findViewById(R.id.tvname1);
        tvPhone1 = (TextView) odial.findViewById(R.id.tvPhone1);
          tVprice = (TextView) odial.findViewById(R.id.tVprice);

        tvname1.setText("Student: " + name);
        tvPhone1.setText("Phone: " + phone);
        tVprice.setText("Price: " + price2);



        add = new AlertDialog.Builder(this);
        add.setView(odial);
        AlertDialog ade = add.create();
        ade.show();
    }


    public void adblv(){
        /**
         * Open up AlertDialog for Student
         * <p>
         */
        OfferDial = (LinearLayout) getLayoutInflater().inflate(R.layout.priceofferdial, null);

        tvnamee1 = (TextView) OfferDial.findViewById(R.id.tvnamee1);
        tvPhonee1 = (TextView) OfferDial.findViewById(R.id.tvPhonee1);
         tVprice1 = (TextView) OfferDial.findViewById(R.id.tVprice1);

        tvnamee1.setText("Teacher: " + Name1);
        tvPhonee1.setText("Phone: " + Phone1);
        tVprice1.setText("The price for 1 hour is: " + price);



        adb = new AlertDialog.Builder(this);
        adb.setView(OfferDial);
        AlertDialog ad = adb.create();
        ad.show();
    }

}

