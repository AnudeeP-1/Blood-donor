package com.example.blood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_success);

        new Handler().postDelayed(new Runnable(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run(){
                Intent intent = new Intent(Success.this,Next1.class);
                startActivity(intent);
            }
        },1200);
    }
}
