package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class Next extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        constraintLayout=findViewById(R.id.dp);
        Snackbar.make(constraintLayout,"Login Succesfull",Snackbar.LENGTH_LONG).show();
    }
}
