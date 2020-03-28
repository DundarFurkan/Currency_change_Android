package com.example.currencychange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainPage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    FirebaseAuth auth;
    FirebaseUser user;

    TextView turk,euro,aud;
    EditText money,money2;

    String jsontl,jsoneuro,jsonaud,last,title;
    int i=1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.exit)
        {
            auth.signOut();
            Toast.makeText(getApplicationContext(),"Çıkış yapıldı",Toast.LENGTH_LONG).show();
            Intent i=new Intent(MainPage.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        if(item.getItemId()==R.id.amount){
            Intent amo=new Intent(this,AmountActivity.class);
            startActivity(amo);

        }
        if(item.getItemId()==R.id.past)
        {
            Intent x=new Intent(this,PastActivity.class);
            startActivity(x);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();


        turk=findViewById(R.id.text_turk);
        euro=findViewById(R.id.text_euro);
        aud=findViewById(R.id.text_aud);
        money=findViewById(R.id.editText3);
        money2=findViewById(R.id.editText4);

        DownloadData usd=new DownloadData();

        try{

            String url="http://www.apilayer.net/api/live?access_key=5c1ce55fd13ae370aabcce8c1f276dc9";
            usd.execute(url);

        }catch (Exception e)
        {

        }


    }

    public   class  DownloadData extends AsyncTask<String,Void,String>
    {



        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();

                while(data>0)
                {
                    char character=(char)data;
                    result+=character;

                    data=inputStreamReader.read();

                }
                return result;

            }catch (Exception e)
            {
                return null;
            }



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                String quotes=jsonObject.getString("quotes");
                JSONObject jsonObject1=new JSONObject(quotes);
                jsontl=jsonObject1.getString("USDTRY");
                jsoneuro=jsonObject1.getString("USDEUR");
                jsonaud=jsonObject1.getString("USDAUD");

                turk.setText("Türk Lirası :" + jsontl);
                euro.setText("Euro : " + jsoneuro);
                aud.setText("Avustralya Doları :"+ jsonaud);

            }
            catch (Exception e)
            {

            }
        }

    }


    public void conv2(View v)
    {

        PopupMenu popupMenu=new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup);
        popupMenu.show();


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {



        switch (menuItem.getItemId())
        {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Amerikan Doları",Toast.LENGTH_LONG).show();
                 title="usd";
                convert(title,"1");
                break;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Türk Lirası",Toast.LENGTH_LONG).show();
                 title="try";
                convert(title,jsontl);
                break;
            case R.id.item3:
                Toast.makeText(getApplicationContext(),"EURO",Toast.LENGTH_LONG).show();
                 title="euro";
                convert(title,jsoneuro);
                break;
            case R.id.item4:
                Toast.makeText(getApplicationContext(),"Avustralya Doları",Toast.LENGTH_LONG).show();
                 title="aud";
                convert(title,jsonaud);
                break;

        }


        return false;


    }
    public  void convert (String title, String mny)
    {
        last=money.getText().toString();
        float a=Float.parseFloat(mny);
        float b=Float.parseFloat(last);

        float c=a*b;
        last=String.valueOf(c);
        money2.setText(last);
    }

    public void send(View v)

    {
        if(v.getId()==R.id.button3)
        {

            Calendar calendar=Calendar.getInstance();

            SimpleDateFormat tarih = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String date=tarih.format(calendar.getTime());
        Intent send=new Intent(MainPage.this,PastActivity.class);
        send.putExtra("bakiye",last);
        send.putExtra("tit",title);
        send.putExtra("index",String.valueOf(i));
        send.putExtra("dolar",money.getText().toString());
        send.putExtra("tarih",date);
        startActivity(send);
        i++;
        }
    }


}
