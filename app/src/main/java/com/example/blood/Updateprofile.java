package com.example.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class Updateprofile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BroadcastReceiver MyReceiver = null;
    Toolbar toolbar;
    private String url,temp;
    public String latti,longi,city,postalCode;
    ProgressDialog load;
    DrawerLayout drawerLayout;
    Button hello,close;
    private ConstraintLayout constraintLayout;
    NavigationView navigationView;
    private TextView clickhere,latti_xml,longi_xml,image_xml;
    TextInputLayout name, age, email, phone, adress;
    private Spinner blood, gender;
    private Button donate;
    private ImageView dp;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private static int PICK_IMAGE = 123;// for image loading purpose
    Uri imagepath;
    int flag=0;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private ResultReceiver resultReceiver;
    public double lattitude,longitude;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_updateprofile);
        id();
        final ConnectionDetector cd=new ConnectionDetector(this);
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
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Updateprofile.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);

        }else{
            statusCheck();
            getCurrentLocation();
        }

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) Toast.makeText(Updateprofile.this,"Select Profile photo",Toast.LENGTH_LONG).show();
                else if(!cd.isConnected())
                Toast.makeText(Updateprofile.this,"Check your network",Toast.LENGTH_LONG).show();

                else if (validate()) {
                    //add user data to firebase
                    add_data();
                }
            }
        });

    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void display(){
        try {

            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
            firebaseDatabase.keepSynced(true);
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                    final user_information user = dataSnapshot.getValue(user_information.class);
                    name.getEditText().setText(user.getName());

                    for(int i=0;i<blood.getCount();i++){
                        if(blood.getItemAtPosition(i).toString().equalsIgnoreCase(user.getBlood())){
                            blood.setSelection(i);
                            break;
                        }

                    }
                    url=user.getUrl();

                        Picasso.get().load(user.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(dp, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(user.getUrl()).fit().centerCrop().into(dp);
                                    }
                                });
                   // Toast.makeText(Updateprofile.this,"  uri is: "+imagepath,Toast.LENGTH_LONG).show();
                    age.getEditText().setText(user.getAge());
                    email.getEditText().setText(user.getEmail());
                    phone.getEditText().setText(user.getPhone());
                    adress.getEditText().setText(user.getAdress());
                    longi_xml.setText(user.getLongi());
                    latti_xml.setText(user.getLatti());
                    image_xml.setText(user.getImagepath());
                    if(user.getGender().equalsIgnoreCase("Female"))
                        gender.setSelection(1);
                    else if(user.getGender().equalsIgnoreCase("Others"))
                            gender.setSelection(2);
                    alertDialog.dismiss();
                        flag=1;
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
        if(imagepath!=null) {
            try {
                //for online mode
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(city);
                databaseReference.keepSynced(true);
                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());

                UploadTask uploadTask = storageReference.putFile(imagepath);

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
                                url = String.valueOf(uri);

                                //function call for getting lat and long
                               // permission();

                                // TO GET REQUIREDPERMISSION for the first time  OR  to check whether the permission is been given


                                //name,url,age,blood,phone,email,userid,gender,adress   here add long and lat at last
                                user_information user_information = new user_information(new String(String.valueOf(imagepath)),name.getEditText().getText().toString(), url, age.getEditText().getText().toString(), blood.getSelectedItem().toString(), phone.getEditText().getText().toString(), email.getEditText().getText().toString(), FirebaseAuth.getInstance().getUid().toString(), gender.getSelectedItem().toString(), adress.getEditText().getText().toString(),latti,longi);
                                databaseReference.child(postalCode).setValue(user_information).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        alertDialog.dismiss();
                                        Snackbar.make(constraintLayout, "Succesfully Registered", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }catch (Exception e){Toast.makeText(Updateprofile.this,e.getMessage()+"   DP",Toast.LENGTH_LONG).show();}
        }
        else{
            try {
                //if image is not selected for second time in offline mode

               // Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();

                  final DatabaseReference databaseReference11 = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
                  databaseReference11.keepSynced(true);
                  final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());

                  UploadTask uploadTask = storageReference.putFile(Uri.parse(image_xml.getText().toString()));
                  uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                          alertDialog.show();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          alertDialog.dismiss();
                          Toast.makeText(Updateprofile.this, "Check your internet", Toast.LENGTH_LONG).show();
                      }
                  }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          user_information user_information = new user_information(image_xml.getText().toString(), name.getEditText().getText().toString(), url, age.getEditText().getText().toString(), blood.getSelectedItem().toString(), phone.getEditText().getText().toString(), email.getEditText().getText().toString(), FirebaseAuth.getInstance().getUid().toString(), gender.getSelectedItem().toString(), adress.getEditText().getText().toString(), latti_xml.getText().toString(), longi_xml.getText().toString());
                          databaseReference11.setValue(user_information).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  alertDialog.dismiss();
                                  Snackbar.make(constraintLayout, "Succesfully Registered", Snackbar.LENGTH_LONG).show();
                              }
                          });
                      }
                  });


            }catch (Exception e){alertDialog.dismiss();Toast.makeText(Updateprofile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                statusCheck();
                getCurrentLocation();
            }else{
                new AlertDialog.Builder(Updateprofile.this){}
                        .setTitle("GIVE ACCESS!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(Updateprofile.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_CODE_LOCATION_PERMISSION);
                                }
                            }
                        })
                        .create()
                        .show();
            }
        }
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
        image_xml=findViewById(R.id.imagepath);
        latti_xml=findViewById(R.id.latti_xml);
        longi_xml=findViewById(R.id.long_xml);
        constraintLayout=findViewById(R.id.dattha);// for snackbar
        //adding progress bar
        builder=new AlertDialog.Builder(Updateprofile.this);
        LayoutInflater inflater=Updateprofile.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_bar,null));
        builder.setCancelable(false);
        alertDialog=builder.create();

    }

    //getting current location
    public void getCurrentLocation() {
        final LocationRequest locationrequest = new LocationRequest();
        locationrequest.setInterval(10000);
        locationrequest.setFastestInterval(3000);
        locationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(Updateprofile.this)
                .requestLocationUpdates(locationrequest,new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Updateprofile.this)
                                .removeLocationUpdates(this);
                        if(locationResult!= null && locationResult.getLocations().size() > 0){
                            int latestlocationindex = locationResult.getLocations().size() - 1;
                            lattitude = locationResult.getLocations().get(latestlocationindex).getLatitude();
                            longitude = locationResult.getLocations().get(latestlocationindex).getLongitude();

                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(Updateprofile.this, Locale.getDefault());


                            try {
                                addresses = geocoder.getFromLocation(lattitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String dattha = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
                            postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                            Toast.makeText(Updateprofile.this,city, Toast.LENGTH_LONG).show();
                            Toast.makeText(Updateprofile.this,postalCode, Toast.LENGTH_LONG).show();
                            adress.getEditText().setText(dattha);

                            latti=Double.toString(lattitude);
                            longi=Double.toString(longitude);



                        }
                        else{
                            Toast.makeText(Updateprofile.this,"loading!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, Looper.getMainLooper());

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
                final Dialog MyDialog = new Dialog(Updateprofile.this);
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


        }
        return true;
    }
}
