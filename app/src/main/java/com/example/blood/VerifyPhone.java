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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    ImageView image;
    TextView logoText;
    TextView sloganText;
    TextInputLayout otp;
    Button verify;
    ProgressBar progressBar;
    String verificationCodeBySystem;
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
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        String Phoneverify = getIntent().getStringExtra("phone");
        sendVerificationCodeToUser(Phoneverify);
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

                   // progressBar.setVisibility(View.VISIBLE);


            }
        });
    }
    private void verifyCode(String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCode);
        signInTheUserByCredentials(credential);
    }
    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyPhone.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();
                            //Perform Your required action here to either let the user sign In or do something required
                            // Intent intent = new Intent(getApplicationContext(), Login.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);
                            startActivity(new Intent(VerifyPhone.this,Next.class));

                        } else {
                            Toast.makeText(VerifyPhone.this,"something problem",Toast.LENGTH_LONG).show();
                            Toast.makeText(VerifyPhone.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCodeToUser(String Phoneverify) {
        Toast.makeText(VerifyPhone.this,"109", Toast.LENGTH_SHORT).show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+Phoneverify,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    //Get the code in global variable
                    Toast.makeText(VerifyPhone.this,"122", Toast.LENGTH_SHORT).show();

                    verificationCodeBySystem = s;
                }
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Toast.makeText(VerifyPhone.this,"completed", Toast.LENGTH_SHORT).show();

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                    else{
                        String manual_code = otp.getEditText().getText().toString().trim();

                        if (code.isEmpty() || code.length() < 6) {
                            otp.setError("Wrong OTP...");
                            otp.requestFocus();
                            //return;
                        }
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(manual_code);
                    }

                }
                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyPhone.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            };
}
