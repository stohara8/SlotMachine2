package com.example.slotmachine;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class HelpActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onPause(){ super.onPause(); }

    public void onResume() { super.onResume(); }


    public void onBackPressed(){
        finish();
    }


}