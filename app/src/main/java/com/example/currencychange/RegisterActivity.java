package com.example.currencychange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth1;
    FirebaseUser user1;

    EditText edituser, editpass;
    Button btnkayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth1 = FirebaseAuth.getInstance();
        edituser = (EditText) findViewById(R.id.edit_reg_user);
        editpass = (EditText) findViewById(R.id.edit_reg_pass);
        btnkayit=(Button)findViewById(R.id.btn_reg_kayıt);
        btnkayit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reg_kayıt) {
            String us = edituser.getText().toString();
            String pas = editpass.getText().toString();

            if (!us.equals("") && !pas.equals("")) {
                register(us, pas);
            } else
                Toast.makeText(getApplicationContext(), "Eksik bilgi girdiniz", Toast.LENGTH_LONG).show();
        }


    }

    private void register(String us, String pas) {

        auth1.createUserWithEmailAndPassword(us, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "başarılı", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getApplicationContext(), "başarız", Toast.LENGTH_LONG).show();

            }
        });

    }

}


