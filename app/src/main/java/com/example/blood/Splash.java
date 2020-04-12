package com.example.blood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//
//        image = findViewById(R.id.imageView);
//        logo = findViewById(R.id.textView);
//
//        image.setAnimation(topAnim);
//        logo.setAnimation(bottomAnim);
//
//        new Handler().postDelayed(new Runnable(){
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void run(){
//                Intent intent = new Intent(Splash.this, Login.class);
//                Pair[] pairs = new Pair[2];
//                pairs[0] = new Pair<View,String>(image, "logo_image");
//                pairs[1] = new Pair<View,String>(logo, "logo_text");
//
//                if (android.os.Build.VERSION.SDK_INT >= 16) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this,pairs);
//                    startActivity(intent, options.toBundle());
//                }
//
//            }
//        },SPLASH_SCREEN);
    }
}
