package com.example.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Updateprofile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ProgressDialog load;
    DrawerLayout drawerLayout;
    private ConstraintLayout constraintLayout;
    NavigationView navigationView;
    private TextView clickhere;
    TextInputLayout name, age, email, phone, adress;
    private Spinner blood, gender;
    private Button donate;
    private ImageView dp;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private static int PICK_IMAGE = 123;// for image loading purpose
    Uri imagepath;
    int flag=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_updateprofile);
        id();
        navbar_function();
        alertDialog.show();
        //checkin already user gave information
            display();

        //load the image
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                Intent intent = new Intent();
                intent.setType("image/*");//doc->application/pdf or application/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "SELECT IAMGE"), PICK_IMAGE);
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) Toast.makeText(Updateprofile.this,"Select Profile photo",Toast.LENGTH_LONG).show();
                else if (validate()) {
                    //add user data to firebase
                    add_data();
                }
            }
        });
    }

    private void display(){
        try {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            firebaseStorage.getReference(FirebaseAuth.getInstance().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(dp);

                }
            });
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                    user_information user = dataSnapshot.getValue(user_information.class);
                    name.getEditText().setText(user.getName());

                    for(int i=0;i<blood.getCount();i++){
                        if(blood.getItemAtPosition(i).toString().equalsIgnoreCase(user.getBlood())){
                            blood.setSelection(i);
                            break;
                        }

                    }
                    age.getEditText().setText(user.getAge());
                    email.getEditText().setText(user.getEmail());
                    phone.getEditText().setText(user.getPhone());
                    adress.getEditText().setText(user.getAdress());
                    if(user.getGender().equalsIgnoreCase("Female"))
                        gender.setSelection(1);
                    else if(user.getGender().equalsIgnoreCase("Others"))
                            gender.setSelection(2);
                    alertDialog.dismiss();
                    }
                    catch(Exception e){alertDialog.dismiss();}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    alertDialog.dismiss();
                    Toast.makeText(Updateprofile.this, "Reload this page ", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(Exception e){alertDialog.dismiss();}
    }

    private boolean validate() {
        if (name.getEditText().getText().toString().isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        }
        else name.setErrorEnabled(false);
         if (phone.getEditText().getText().toString().isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        }
        else phone.setErrorEnabled(false);
        if (age.getEditText().getText().toString().isEmpty()) {
            age.setError("Field cannot be empty");
            return false;
        }
        else age.setErrorEnabled(false);
        if (true) {
            String val = email.getEditText().getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (val.isEmpty()) {
                email.setError("Field cannot be empty");
                return false;
            } else if (!val.matches(emailPattern)) {
                email.setError("Invalid email address");
                return false;
            } else {
                email.setError(null);
                email.setErrorEnabled(false);
            }
        }
        if (adress.getEditText().getText().toString().isEmpty()) {
            adress.setError("Field can not be empty");
            return false;
        }
        else adress.setErrorEnabled(false);


        //function to validate this
        return true;
    }


    private void add_data(){
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
        final StorageReference storageReference= FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());
        UploadTask uploadTask=storageReference.putFile(imagepath);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                alertDialog.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=String.valueOf(uri);
                        //name,url,age,blood,phone,email,userid,gender,adress
                        user_information user_information=new user_information(name.getEditText().getText().toString(),url,age.getEditText().getText().toString(),blood.getSelectedItem().toString(),phone.getEditText().getText().toString(),email.getEditText().getText().toString(),FirebaseAuth.getInstance().getUid().toString(),gender.getSelectedItem().toString(),adress.getEditText().getText().toString());
                        databaseReference.setValue(user_information).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alertDialog.dismiss();
                                Snackbar.make(constraintLayout,"Succesfully Registered",Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }


    private void id(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        dp=findViewById(R.id.profile_image);
        name=findViewById(R.id.upname);
        email=findViewById(R.id.upemail);
        age=findViewById(R.id.upage);
        phone=findViewById(R.id.upphone);
        gender=findViewById(R.id.upgender);
        blood=findViewById(R.id.upblood);
        donate=findViewById(R.id.donate);
        adress=findViewById(R.id.upaddress);
        clickhere=findViewById(R.id.click);
        constraintLayout=findViewById(R.id.dattha);// for snackbar
        //adding progress bar
        builder=new AlertDialog.Builder(Updateprofile.this);
        LayoutInflater inflater=Updateprofile.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_bar,null));
        builder.setCancelable(false);

        alertDialog=builder.create();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null){
            imagepath=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                dp.setImageBitmap(bitmap);
            }catch(Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void navbar_function(){

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent (Updateprofile.this,Next1.class);
                startActivity(intent);
                /*navigationView.setCheckedItem(R.id.nav_home);*/
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent (Updateprofile.this,Profile.class);
                startActivity(intent1);
                /*navigationView.setCheckedItem(R.id.nav_profile);*/
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_guidlines:
                startActivity(new Intent(this,Updateprofile.class));
                break;
            case R.id.nav_search:
                //Search popup
                Intent intent2=new Intent(getApplicationContext(),Search_popup.class);

                startActivity(intent2);


        }
        return true;
    }
}
