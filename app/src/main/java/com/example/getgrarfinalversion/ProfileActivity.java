package com.example.getgrarfinalversion;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import static com.example.getgrarfinalversion.FBref.refAuth;
import static com.example.getgrarfinalversion.FBref.refImages;
import static com.example.getgrarfinalversion.FBref.refcustomer;
import static com.example.getgrarfinalversion.FBref.refdrivr;
import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    TextView towinglicense,nameview,phoneview,eTnumbercar,eTemail,typecar;
    ImageView iV;
    String UID,name,phone,Stowinglicense,numbercar,email,name1,phone1,email1,cartype,email2,numbercar1;
    Intent intent;
    int Gallery=1;
    Customer customer;
    Manager driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameview = (TextView) findViewById(R.id.nameview);
        phoneview = (TextView) findViewById(R.id.phoneview);
        towinglicense = (TextView) findViewById(R.id.towinglicense);
        eTnumbercar = (TextView) findViewById(R.id.eTnumbercar);
        eTemail = (TextView) findViewById(R.id.eTemail);
        typecar = (TextView) findViewById(R.id.typecar);

        iV=(ImageView)findViewById(R.id.iV);

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();

        Query query = refcustomer
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(VEL);

        Query query2 = refdrivr
                .orderByChild("uid")
                .equalTo(UID);
        query2.addListenerForSingleValueEvent(VEL2);
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
                for(DataSnapshot data : dS.getChildren()) {
                    customer = data.getValue(Customer.class);
                    name = customer.getName();
                    phone = customer.getPhone();
                    cartype=customer.getTypeCar();
                    email=customer.getEmail();
                    numbercar=customer.getNumbercar();
                }
               customerprof();

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
                    driver = data.getValue(Manager.class);
                    name1 = driver.getName();
                    phone1 = driver.getPhone();
                    email2=driver.getEmail();
                    Stowinglicense=driver.getTowinglicense();
                    numbercar1=driver.getNumbercar();
                    drevierprof();
                }

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    /**
     * Selecting image file to upload to Firebase Storage
     * <p>
     *
     * @param view
     */
    public void upload(View view) {
        Intent si = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si, Gallery);
    }

    /**
     * Uploading selected image file to Firebase Storage
     * <p>
     *
     * @param requestCode   The call sign of the intent that requested the result
     * @param resultCode    A code that symbols the status of the result of the activity
     * @param data          The data returned
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Gallery) {
                Uri file = data.getData();
                if (file != null) {
                    final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
                    StorageReference refImg = refImages.child("aaa.jpg");
                    refImg.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * Downloading selected image file from Firebase Storage
     * <p>
     *
     * @param view
     */
    public void download(View view) throws IOException {
        final ProgressDialog pd=ProgressDialog.show(this,"Image download","downloading...",true);

        StorageReference refImg = refImages.child("aaa.jpg");

        final File localFile = File.createTempFile("aaa","jpg");
        refImg.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(ProfileActivity.this, "Image download success", Toast.LENGTH_LONG).show();
                String filePath = localFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                iV.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(ProfileActivity.this, "Image download failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void customerprof() {
        /**
         * Shows the appropriate items for the customer's profile.
         * <p>
         */


        nameview.setText("Name :"+name);
        phoneview.setText("Phone :"+phone);
        eTnumbercar.setText("Car Number: "+numbercar);
        eTemail.setText("Email: "+email);
        typecar.setText("Car Type: "+cartype);
    }

    private void drevierprof() {
        /**
         * Shows the appropriate items for the driver's profile.
         * <p>
         */

        typecar.setVisibility(View.INVISIBLE);
        towinglicense.setVisibility(View.VISIBLE);

        nameview.setText("Name :"+name1);
        phoneview.setText("Phone :"+phone1);
        eTnumbercar.setText("Car Number: "+numbercar1);
        eTemail.setText("Email: "+email2);
        towinglicense.setText("Towing License Number: "+Stowinglicense);
    }
}