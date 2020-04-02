package com.example.blood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class VerifyPhone extends AppCompatActivity {

    ImageView image;
    TextView logoText;
    TextView sloganText;
    TextInputLayout otp;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_phone);

        image = findViewById(R.id.verifyimage);
        logoText = findViewById(R.id.cmdverify);
        sloganText = findViewById(R.id.otpsent);
        otp = findViewById(R.id.otp);
        verify = findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyPhone.this, Success.class);
                Pair pairs[] = new Pair[5];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_text");
                pairs[3] = new Pair<View, String>(otp, "username_tran");
                pairs[4] = new Pair<View, String>(verify, "login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VerifyPhone.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}
