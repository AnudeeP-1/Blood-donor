package com.example.blood;

import android.app.Application;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class SimpleBlog extends Application {
    Button hello,close;
    TextView cut;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Picasso.Builder builder=new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built=builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
        //blood group popup
        blood_group_popup();

        //find and confirm popup
        find_and_confirm_popup();

    }
    public void blood_group_popup(){
        final Dialog MyDialog = new Dialog(SimpleBlog.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_blood_group_popup);
        hello = (Button)MyDialog.findViewById(R.id.hello);
        close = (Button)MyDialog.findViewById(R.id.close);
        cut=(TextView) MyDialog.findViewById(R.id.cut);
        hello.setEnabled(true);
        close.setEnabled(true);
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
                //  Intent intent=new Intent(getApplicationContext(),search.class);
                //  startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
              //  Toast.makeText(getApplicationContext(), "HELLO I AM CLICKING CLOSE BUTTON", Toast.LENGTH_LONG).show();
                 MyDialog.cancel();
            }
        });


        MyDialog.show();
    }
    public void find_and_confirm_popup(){
        final Dialog MyDialog = new Dialog(SimpleBlog.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_find_and_confirm_popup);
        hello = (Button)MyDialog.findViewById(R.id.hello);
        close = (Button)MyDialog.findViewById(R.id.close);
        cut=(TextView) MyDialog.findViewById(R.id.cut);
        hello.setEnabled(true);
        close.setEnabled(true);
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
                //  Intent intent=new Intent(getApplicationContext(),search.class);
                //  startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(getApplicationContext(), "HELLO I AM CLICKING CLOSE BUTTON", Toast.LENGTH_LONG).show();
                // MyDialog.cancel();
            }
        });
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyDialog.cancel();
            }
        });
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }
}
