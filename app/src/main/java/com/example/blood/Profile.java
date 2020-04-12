package com.example.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout draw;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView profile;
    Button hello,close;
    TextView name,phone,email,blood,Age,Gender,Adress,text;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View inflatedView;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        id();
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        alertDialog.show();
        check=getIntent().getStringExtra("userID");
        if(check==null) {
            navigationView.setCheckedItem(R.id.nav_profile);
            display();//for current user
        }
        else{
            if(check.isEmpty()) {
                alertDialog.dismiss();
                Toast.makeText(this, "Enter valid user ID u have recieved", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,Next1.class));
            }
            else
            display1(check);
        }

        navigationView.setNavigationItemSelectedListener(this);



    }

    private void display1(String id){

        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
try{
//            firebaseStorage.getReference(FirebaseAuth.getInstance().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(final Uri uri) {
//                    Picasso.get().load(uri).networkPolicy(NetworkPolicy.OFFLINE).into(profile, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            Picasso.get().load(uri).fit().centerCrop().into(profile);
//                        }
//                    });
//
//
//                }
//            });
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(id);
            firebaseDatabase.keepSynced(true);
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                       user_information user = dataSnapshot.getValue(user_information.class);
                    }catch(Exception e){
                        alertDialog.dismiss();
                        finish();
                        Toast.makeText(Profile.this,"User doesn't have profile",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Profile.this,Next1.class));
                        }
                    final user_information user=dataSnapshot.getValue(user_information.class);
                    try {
                        Picasso.get().load(user.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(profile, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(user.getUrl()).fit().centerCrop().into(profile);
                            }
                        });


                    name.setText(user.getName());
                    blood.setText(user.getBlood());
                    Age.setText(user.getAge());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    Adress.setText(user.getAdress());
                    Gender.setText(user.getGender());
                    alertDialog.dismiss();
                    }catch (Exception e){alertDialog.dismiss();
                        finish();
                        Toast.makeText(Profile.this,"User doesn't have profile",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Profile.this,Next1.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Profile.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }catch (Exception e){alertDialog.dismiss();
        finish();
        Toast.makeText(Profile.this,"User doesn't have profile",Toast.LENGTH_LONG).show();
        startActivity(new Intent(Profile.this,Next1.class));
    }


    }


    private void display(){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
       try {
            firebaseStorage.getReference((FirebaseAuth.getInstance().getUid())).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(profile);

                }
            });
       }catch(Exception e){Toast.makeText(this,"You don't have profile page",Toast.LENGTH_LONG).show();
           startActivity(new Intent(Profile.this,Next1.class));
       }
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
            firebaseDatabase.keepSynced(true);
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                    final user_information user = dataSnapshot.getValue(user_information.class);
                    Picasso.get().load(user.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(profile, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(user.getUrl()).into(profile);
                        }
                    });
                    name.setText(user.getName());
                    blood.setText(user.getBlood());
                    Age.setText(user.getAge());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    Adress.setText(user.getAdress());
                    Gender.setText(user.getGender());
                    alertDialog.dismiss();
                    }catch(Exception e){Toast.makeText(Profile.this,"You don't have profile page",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Profile.this,Next1.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Profile.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    }

    private void id(){
        draw = findViewById(R.id.draw1);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        profile=findViewById(R.id.profile_image);
        name=findViewById(R.id.full_name);
        phone=findViewById(R.id.booking_label);
        email=findViewById(R.id.email1);
        Gender=findViewById(R.id.gender);
        Age=findViewById(R.id.age);
        Adress=findViewById(R.id.address);
        blood=findViewById(R.id.payment_label);

        builder=new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater=Profile.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_bar,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
    }

    @Override
    public void onBackPressed() {

        if (draw.isDrawerOpen(GravityCompat.START)) {
            draw.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                break;
            case R.id.nav_home:
                Intent intent = new Intent (Profile.this,Next1.class);
                startActivity(intent);
                break;
            case R.id.nav_guidlines:
                startActivity(new Intent(this,Updateprofile.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_search:
                final Dialog MyDialog = new Dialog(Profile.this);
                MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                MyDialog.setContentView(R.layout.activity_search_popup);

                hello = (Button)MyDialog.findViewById(R.id.hello);
                close = (Button)MyDialog.findViewById(R.id.close);

                hello.setEnabled(true);
                close.setEnabled(true);

                hello.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         Intent intent=new Intent(getApplicationContext(),Search_popup.class);
                        EditText text=MyDialog.findViewById(R.id.edittext);

                        intent.putExtra("userID",text.getText().toString().trim());

                        startActivity(intent);

                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialog.cancel();
                    }
                });

                MyDialog.show();
               break;

        }

        return true;

    }
}
