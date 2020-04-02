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
import com.spark.submitbutton.SubmitButton;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword, regPassword2;
    Button already;
    SubmitButton go;
    ImageView image;
    TextView logo;
    TextView desc;
    TextInputLayout txtf;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        go = findViewById(R.id.crazy_go);
        already = findViewById(R.id.already);

        image = findViewById(R.id.signimage);
        logo = findViewById(R.id.signtext);
        desc = findViewById(R.id.signdesc);
        txtf = findViewById(R.id.email);



        /*regName = findViewById(R.id.name);*/
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password2);
        regPassword2 = findViewById(R.id.password3);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SignUp.this,UserProfile.class));
            }
        }, 3000);*/


        go.setOnClickListener(new View.OnClickListener() {

            //Intent intent = new Intent(SignUp.this, UserProfile.class);
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (/*!validateName() | !validateUsername() | */!validateEmail() | !validatePassword() | !validatePassword2()) {
                    return;
                }

                Runnable r = new Runnable() {
                    public void run() {
                        Intent intent = new Intent(SignUp.this,Success.class);
                        startActivity(new Intent(SignUp.this, Success.class));
                        /*Pair[] pairs = new Pair[4];
                        pairs[0] = new Pair<View,String>(image, "logo_image");
                        pairs[1] = new Pair<View,String>(logo, "logo_text");
                        pairs[2] = new Pair<View,String>(desc, "logo_text");
                        pairs[3] = new Pair<View,String>(txtf, "email_trans");

                        if (android.os.Build.VERSION.SDK_INT >= 16) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                            startActivity(intent, options.toBundle());
                        }*/
                    }
                };
                v.postDelayed(r, 3000);

            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }


        });



    }

    private Boolean validateName() {

        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError(" Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    //for validation of re-enter password, replace the code in below function

    private Boolean validatePassword2() {
        String val = regPassword2.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword2.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword2.setError("Password is too weak");
            return false;
        } else {
            regPassword2.setError(null);
            regPassword2.setErrorEnabled(false);
            return true;
        }
    }}

