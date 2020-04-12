package com.example.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Next1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RelativeLayout donate,request;
    Button hello,close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_next1);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        donate=findViewById(R.id.donate);
        request=findViewById(R.id.request);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recomonded popup
               // MyCustomAlertDialog();
                recomended();

            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Next1.this,List_Of_Users.class));


            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                /*navigationView.setCheckedItem(R.id.nav_home);*/
                break;
            case R.id.nav_profile:
                Intent intent = new Intent (Next1.this,Profile.class);
                startActivity(intent);
                /*navigationView.setCheckedItem(R.id.nav_profile);*/
                break;
            case R.id.nav_guidlines:
                Intent intent1 = new Intent (Next1.this,Updateprofile.class);
                startActivity(intent1);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_search:
                //Search popup
                    search();

        }
        return true;
    }


    public void recomended(){
        final Dialog MyDialog = new Dialog(Next1.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_recomended);

        hello = (Button)MyDialog.findViewById(R.id.hello);
        close = (Button)MyDialog.findViewById(R.id.close);

        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
                //  Intent intent=new Intent(getApplicationContext(),search.class);

                //  startActivity(intent);

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
    public void search(){
        final Dialog MyDialog = new Dialog(Next1.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.activity_search_popup);

        hello = (Button)MyDialog.findViewById(R.id.hello);
        close = (Button)MyDialog.findViewById(R.id.close);

        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 Intent intent=new Intent(getApplicationContext(),Profile.class);
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
}
