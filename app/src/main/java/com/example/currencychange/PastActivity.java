package com.example.currencychange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PastActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref,push;
    String key,key1,useremail, userıd;
    ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    FirebaseAuth mauth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference();
        push=ref.child("Money").push();
        listView=findViewById(R.id.liste);

       listItems=new ArrayList<String>();

        adapter=new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listItems);

        mauth= FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        useremail=user.getEmail();
        int a=useremail.indexOf("@");
        userıd=useremail.substring(0,a);


        load();

    }

    public void load() {
        DatabaseReference newreference=firebaseDatabase.getReference("Money/"+userıd);

        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                for (DataSnapshot  ds: dataSnapshot.getChildren())
//                {
                    HashMap<String, String> hashMap=(HashMap<String, String>) dataSnapshot.getValue();
                    System.out.println("gelen son size:" + hashMap.size());

                    try {
                        if(hashMap.size() <= 1) {
                            listItems.add(hashMap.get(userıd + 1));
                        }
                        else if(hashMap.size() <= 2){
                            listItems.add(hashMap.get(userıd + 1));
                            listItems.add(hashMap.get(userıd + 2));
                        } else if(hashMap.size() <= 3) {
                            listItems.add(hashMap.get(userıd + 1));
                            listItems.add(hashMap.get(userıd + 2));
                            listItems.add(hashMap.get(userıd + 3));
                        }
                        else if(hashMap.size() <= 4) {
                            listItems.add(hashMap.get(userıd + 1));
                            listItems.add(hashMap.get(userıd + 2));
                            listItems.add(hashMap.get(userıd + 3));
                            listItems.add(hashMap.get(userıd + 4));
                        }
                        else if(hashMap.size() <= 5) {
                            listItems.add(hashMap.get(userıd + 1));
                            listItems.add(hashMap.get(userıd + 2));
                            listItems.add(hashMap.get(userıd + 3));
                            listItems.add(hashMap.get(userıd + 4));
                            listItems.add(hashMap.get(userıd + 5));

                        }
                        else{
                            for(int i= hashMap.size(); i > hashMap.size()-5; i--) {
                                listItems.add(hashMap.get(userıd + i));
                            }
                        }
                    }catch (Exception e) {

//                    }



                }
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Yanlış yol" ,Toast.LENGTH_LONG).show();
            }
        });




    }


}
