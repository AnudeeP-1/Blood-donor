package com.example.blood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run(){
                Intent intent = new Intent(MainActivity.this, Splash.class);
                //Pair[] pairs = new Pair[2];
                //pairs[0] = new Pair<View,String>(image, "logo_image");
                //pairs[1] = new Pair<View,String>(logo, "logo_text");
                startActivity(intent);

                /*if (android.os.Build.VERSION.SDK_INT >= 16) {
                    //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                   *//* options.toBundle());*//*
                }*/
                            //wertyuiop   PUSH TRY
            }
        },5000);
    }
}
