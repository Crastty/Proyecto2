package com.example.proyecto2.Firebase;


import com.example.proyecto2.Model.PlaceModel;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyFirebase {

    public void addFirebase (PlaceModel mDatos, DatabaseReference myRef) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss ddMMyyyy");
        String currentDateandTime = sdf.format(new Date());
        myRef.child("message").child(currentDateandTime).setValue(mDatos);

    }

}
