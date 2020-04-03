package com.example.blood;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

public class SignUp extends AppCompatActivity {

    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword, regPassword2;
    private Button already;
    private SubmitButton go;
    private ImageView image;
    private TextView logo, desc;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        id();





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

                if (validateEmail()&&validatePassword()&&validatePassword2()) { //validate fields
                    Toast.makeText(SignUp.this,"if came",Toast.LENGTH_LONG).show();
                    firebaseAuth.createUserWithEmailAndPassword(regEmail.getEditText().getText().toString().trim(),regPassword.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendMail(); //for send the mail
                                //Toast.makeText(SignUp.this,"email sent boss",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Runnable r = new Runnable() {
                        public void run() {
                            Intent intent = new Intent(SignUp.this,Login.class);
                            startActivity(new Intent(SignUp.this, Login.class));
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
        }
        else if(!regPassword.getEditText().getText().toString().trim().equals(regPassword2.getEditText().getText().toString().trim()))
        {
           regPassword2.setError("Confirm password is not matching");
           return false;
        }
        else
         {
            regPassword2.setError(null);
            regPassword2.setErrorEnabled(false);
            return true;
        }
    }
    private void id(){
        go = findViewById(R.id.crazy_go);
        already = findViewById(R.id.already);
        image = findViewById(R.id.signimage);
        logo = findViewById(R.id.signtext);
        desc = findViewById(R.id.signdesc);
        /*regName = findViewById(R.id.name);*/
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password2);
        regPassword2 = findViewById(R.id.password3);
        firebaseAuth=FirebaseAuth.getInstance();
    }


    private void sendMail(){
        FirebaseUser fireuser=firebaseAuth.getCurrentUser();
        if(fireuser!=null){
            fireuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this,"Successfully registered, chaeck your Email",Toast.LENGTH_SHORT).show();
                      //  senduserdata();
                        firebaseAuth.signOut();
                        finish();
                       // startActivity(new Intent(SignUp.this,Login.class));
                    }
                    else{
                        Toast.makeText(SignUp.this,"Verification Mail not sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //add name age mail t databse using java class
    private void senduserdata(){
        firebaseDatabase=FirebaseDatabase.getInstance();
       // userprofile up=new userprofile(regEmail.getEditText().getText().toString().trim(),name.getText().toString().trim(),url);
      //  myref.setValue(up);



    }


}

