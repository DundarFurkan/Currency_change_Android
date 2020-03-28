package com.example.currencychange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user;
    Button btnlogin,btnregister;
    EditText edituser,editpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        btnlogin=(Button)findViewById(R.id.button);
        btnregister=(Button)findViewById(R.id.button2);
        btnlogin.setOnClickListener(this);
        btnregister.setOnClickListener(this);

        if(user!=null)
        {
            Intent intent=new Intent(MainActivity.this,MainPage.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"mail="+""+user.getEmail(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.button)
        {
            Toast.makeText(getApplicationContext(),"Logine basıldı", Toast.LENGTH_LONG).show();
            edituser=(EditText)findViewById(R.id.editText);
            editpass=(EditText)findViewById(R.id.editText2);
            String user=edituser.getText().toString();
            String pass=editpass.getText().toString();
            if(!edituser.equals("") && !editpass.equals(""))
            {
                login(user,pass);
                //finish();
            }


        }
        if(view.getId()==R.id.button2)
        {
            Intent i=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Kayıt ol Basıldı", Toast.LENGTH_LONG).show();
        }

    }

    private void login(String user, String pass) {

        auth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    Intent intent=new Intent(MainActivity.this,MainPage.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"Giriş Yapıldı",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"giriş başarısız",Toast.LENGTH_LONG).show();

            }
        });


    }
}
