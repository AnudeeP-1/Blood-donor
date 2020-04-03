package com.example.blood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Phonesignup extends AppCompatActivity {

    Button sendotp;
    TextInputLayout regPhoneNo;
    //Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phonesignup);

        sendotp = findViewById(R.id.phonesignupsendotp);
        regPhoneNo = findViewById(R.id.phonesignupnum);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                String phoneno=regPhoneNo.getEditText().getText().toString();

                Intent intent = new Intent(Phonesignup.this, VerifyPhone.class);
                intent.putExtra("phone",phoneno);
                startActivity(intent);
            }


        });

        //regBtn = findViewById(R.id.phonesignupsendotp);

    }

    /*public void registerUser(View view) {
        String phoneno=regPhoneNo.getText().toString();
        Intent intent=new Intent(getApplicationContext(),VerifyPhone.class);
        intent.putExtra("phone",phoneno);
        startActivity(intent);

    }*/
}
