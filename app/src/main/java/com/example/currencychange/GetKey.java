package com.example.currencychange;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;

public class GetKey {


    public static  String getkey(DatabaseReference ref)
    {

        DatabaseReference push=ref.child("Money").push();
        String key=push.getKey();
        return  key;
    }
}
