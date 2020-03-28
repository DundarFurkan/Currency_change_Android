package com.example.currencychange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AmountActivity extends AppCompatActivity {
    TextView textView1,textView,textView2,textView3;
    String value,value1,useremail,uservalue,userıd,ind,dolar, key,tarih;
    TextView deneme1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref, push, ref;
    private FirebaseAuth mauth;
    private  StorageReference mStorageRef;
    FirebaseUser user;

    String yenitry=null;
    String yeniusd=null;
    String yeniaud=null;
    String yenieuro=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        textView=findViewById(R.id.textView);
        textView1=findViewById(R.id.textView2);//getvalue();
        textView2=findViewById(R.id.textView3);
        textView3=findViewById(R.id.textView4);


        firebaseDatabase=FirebaseDatabase.getInstance();
        myref=firebaseDatabase.getReference();
        mauth=FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();


        upload();

    }

   public void upload() {



       try{
           Bundle extra=getIntent().getExtras();
           value=extra.getString("bakiye");
           value1=extra.getString("tit");
           ind=extra.getString("index");
           dolar=extra.getString("dolar");
           tarih=extra.getString("tarih");

       }catch (Exception e)
       {

       }
       user=mauth.getCurrentUser();
       useremail=user.getEmail();
       uservalue=value;
       int a=useremail.indexOf("@");

       firebaseDatabase=FirebaseDatabase.getInstance();
       ref=firebaseDatabase.getReference();
       userıd=useremail.substring(0,a);
       push=ref.child("Money").push();
       key=push.getKey();
       myref.child("Money").child(userıd).child(userıd+ind).setValue(dolar+" dolar  "+ uservalue + " "+ value1 + " " +tarih);


        DatabaseReference newreference=firebaseDatabase.getReference("Money");

        DatabaseReference newreference1=firebaseDatabase.getReference(userıd);

        System.out.println("mail son deneme :"+ newreference1.getKey());
        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot  ds: dataSnapshot.getChildren())
                {
                    HashMap<String, String> hashMap=(HashMap<String, String>) ds.getValue();
                    System.out.println("buraya bak kardeş:" + hashMap);
                    textView3.setText("Amerikan Doları : "+yeniusd);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Yanlış yol" ,Toast.LENGTH_LONG).show();
            }
        });


    }


    public void  getvalue()
    {
        try{
            Bundle extra=getIntent().getExtras();
            value=extra.getString("bakiye");
            value1=extra.getString("tit");

        }catch (Exception e)
        {

        }

        if(value1.equals("try"))
        {

            textView.setText("Toplam bakiye : " + " 1961.0372 " + " TL " );
        }
        if(value1.equals("euro"))
        {

            textView1.setText("Toplam bakiye : " + " 307.407.66 " + " EURO " );
        }

        if(value1.equals("aud"))
        {

            textView2.setText("Toplam bakiye : " + " 496.2114 " + " AUD " );
        }
        if(value1.equals("usd"))
        {

            textView3.setText("Toplam bakiye : " +value + " USD " );
        }
    }
}
