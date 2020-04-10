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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.blood.R.layout.activity_login;

public class Login extends AppCompatActivity {

    Button emailsignup, phonesignup, login_btn,forgot;
    ImageView image;
    TextView logoText;
    TextView sloganText;
    TextInputLayout email, password;
    private ScrollView scrollView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    //GOOGLE
    private SignInButton but;
    private static int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthlistner;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthlistner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        id();      //findeViewById method called
        FirebaseUser user=firebaseAuth.getCurrentUser();
       if(user!=null){
            finish();
           startActivity(new Intent(this,Next1.class));
       }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGIN();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //popup for email password
            }
        });

        /*emailsignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair pairs[] = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                pairs[6] = new Pair<View, String>(emailsignup, "login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }

        });*/

        emailsignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }


        });



        phonesignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Phonesignup.class);
                startActivity(intent);
            }


        });

        //write the code for google sign up(The button id is passed to the variable "googlesignup"
        mAuthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if((firebaseAuth.getCurrentUser())!= null){
                    startActivity(new Intent(Login.this,Next1.class));

                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    //for setting id to variable
    private void id(){
        but = findViewById(R.id.googlBtn);
        image = findViewById(R.id.Logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        emailsignup = findViewById(R.id.emailsignup);
        phonesignup = findViewById(R.id.phonesignup);
        forgot=findViewById(R.id.forgot);
        //googlesignup = findViewById(R.id.googlesignup);
        email=findViewById(R.id.loginemail);
        scrollView=findViewById(R.id.scroll);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }

    private void LOGIN(){
        firebaseAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(),password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fireuser=firebaseAuth.getCurrentUser();
                    Boolean emailflag=fireuser.isEmailVerified();
                    if(emailflag){
                        finish();
                        startActivity(new Intent(Login.this,Next.class));
                    }
                    else{
                        Snackbar.make(scrollView,"Verify email",Snackbar.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                    }
                }
                else{
                    Snackbar.make(scrollView,"Enter proper Email and Password",Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
    //GOOGLE'S CODE
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed,
                Toast.makeText(Login.this,"Authentication went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(Login.this,"SIGNED IN SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails
                            Toast.makeText(Login.this,"SIGN IN FAILED",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
