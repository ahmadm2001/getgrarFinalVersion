package com.example.getgrarfinalversion;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
   // public static DatabaseReference refUsers=FBDB.getReference("users");

    public static DatabaseReference refcustomer=FBDB.getReference("Customers");
    public static DatabaseReference refdrivr=FBDB.getReference("Drivers");
    public static DatabaseReference refLocations=FBDB.getReference("location");
    public static DatabaseReference refoffergrar=FBDB.getReference("offergrar");
    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference refStor=FBST.getReference();
    public static StorageReference refImages=refStor.child("Images");

}

