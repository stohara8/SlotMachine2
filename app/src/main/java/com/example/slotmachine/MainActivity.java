package com.example.slotmachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable cherryImage;
    private Drawable grapeImage;
    private Drawable pearImage;
    private Drawable strawberryImage;
    private Drawable[] slotPics;

    private ImageView slot1;
    private ImageView slot2;
    private ImageView slot3;
    private ConstraintLayout layout;

    private EditText pointBox;
    private SeekBar speedBar;
    private Button start;
    private Button rules;

    public boolean on;
    public Handler handler;
    public UpdateSlot1 update1;
    public UpdateSlot2 update2;
    public UpdateSlot3 update3;
    public int slotint1;
    public int slotint2;
    public int slotint3;
    public Random rand;
    public int time;
    public double scoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.gridLayout);
        cherryImage = getDrawable(R.drawable.cherry);
        grapeImage = getDrawable(R.drawable.grape);
        pearImage = getDrawable(R.drawable.pear);
        strawberryImage = getDrawable(R.drawable.strawberry);

        pointBox = findViewById(R.id.pointBox);
        speedBar = findViewById(R.id.speedBar);
        start = findViewById(R.id.startButton);
        rules = findViewById(R.id.ruleButton);


        handler = new Handler();
        update1 = new UpdateSlot1();
        update2 = new UpdateSlot2();
        update3 = new UpdateSlot3();

        rand = new Random();
        time = 100 + rand.nextInt(900);
        scoreValue = 0;

        speedBar = findViewById(R.id.speedBar);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0){
                    time = 100 + rand.nextInt(900);
                } else {
                    time = ((100 + rand.nextInt(900))/progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        slotPics = new Drawable[]{cherryImage, grapeImage, pearImage, strawberryImage};

        slot1 = (ImageView) getLayoutInflater().inflate(R.layout.slot_view, null);
        slot1.setMinimumWidth(225);
        slot1.setMinimumHeight(225);
        slot1.setImageDrawable(cherryImage);
        grid.addView(slot1);

        slot2 = (ImageView) getLayoutInflater().inflate(R.layout.slot_view, null);
        slot2.setMinimumWidth(225);
        slot2.setMinimumHeight(225);
        slot2.setImageDrawable(grapeImage);
        grid.addView(slot2);

        slot3 = (ImageView) getLayoutInflater().inflate(R.layout.slot_view, null);
        slot3.setMinimumWidth(225);
        slot3.setMinimumHeight(225);
        slot3.setImageDrawable(pearImage);
        grid.addView(slot3);

        if (savedInstanceState == null) {
            slotint1 = 0;
            slotint2 = 1;
            slotint3 = 2;
            on = false;
        }
        else{
            slotint1 = savedInstanceState.getInt("SLOT1");
            slotint2 = savedInstanceState.getInt("SLOT2");
            slotint3 = savedInstanceState.getInt("SLOT3");

            if((slotint1-1) == -1){
                slot1.setImageDrawable(slotPics[3]);
            }
            else{
                slot1.setImageDrawable(slotPics[slotint1-1]);
            }

            if((slotint2-1) == -1){
                slot2.setImageDrawable(slotPics[3]);
            }
            else{
                slot2.setImageDrawable(slotPics[slotint2-1]);
            }

            if((slotint3-1) == -1){
                slot3.setImageDrawable(slotPics[3]);
            }
            else{
                slot3.setImageDrawable(slotPics[slotint3-1]);
            }

            on = savedInstanceState.getBoolean("ON");
            if (on) {
                start.setText("STOP");
            }

        }

    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(update1);
        handler.removeCallbacks(update2);
        handler.removeCallbacks(update3);
    }

    public void onResume(){
        super.onResume();
        if(on){
            handler.postDelayed(update1, time);
            handler.postDelayed(update2, time/2);
            handler.postDelayed(update3, time/3);
        }
    }

    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("SLOT1", slotint1);
        bundle.putInt("SLOT2", slotint2);
        bundle.putInt("SLOT3", slotint3);
        bundle.putBoolean("ON", on);
    }

    public void startPressed(View v){
        if(on) {
            on = false;
            handler.removeCallbacks(update1);
            handler.removeCallbacks(update2);
            handler.removeCallbacks(update3);
            pointValue();
        }
        else {
            on = true;
            start.setText("STOP");
            handler.postDelayed(update1, time);
            handler.postDelayed(update2, time/2);
            handler.postDelayed(update3, time/3);
        }
    }

    public void helpPressed(View v){
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
    }

    public void pointValue() {
        if(slot1.getDrawable() == cherryImage && slot2.getDrawable() == cherryImage && slot3.getDrawable() == cherryImage){
            scoreValue += 50;
            pointBox.setText(String.format("%.0f", scoreValue));
        }
        else if(slot1.getDrawable() == grapeImage && slot2.getDrawable() == grapeImage && slot3.getDrawable() == grapeImage){
            scoreValue += 25;
            pointBox.setText(String.format("%.0f", scoreValue));
        }
        else if(slot1.getDrawable() == pearImage && slot2.getDrawable() == pearImage && slot3.getDrawable() == pearImage){
            scoreValue += 20;
            pointBox.setText(String.format("%.0f", scoreValue));
        }
        else if(slot1.getDrawable() == strawberryImage && slot2.getDrawable() == strawberryImage && slot3.getDrawable() == strawberryImage){
            scoreValue += 10;
            pointBox.setText(String.format("%.0f", scoreValue));
        }
        else {
            scoreValue += 0;
            pointBox.setText(String.format("%.0f", scoreValue));
        }
    }

    private class UpdateSlot1 implements Runnable{

        public void run(){
            if(slotint1 == 0){
                slot1.setImageDrawable(slotPics[slotint1]);
            }
            else if(slotint1 == 1){
                slot1.setImageDrawable(slotPics[slotint1]);
            }
            else if(slotint1 == 2){
                slot1.setImageDrawable(slotPics[slotint1]);
            }
            else{
                slot1.setImageDrawable(slotPics[slotint1]);
            }
            if(slotint1 < 3){ slotint1++; }
            else{ slotint1 = 0; }
            handler.postDelayed(update1, time);
        }

    }

    private class UpdateSlot2 implements Runnable{

        public void run(){
            if(slotint2 == 0){
                slot2.setImageDrawable(slotPics[slotint2]);
            }
            else if(slotint1 == 1){
                slot2.setImageDrawable(slotPics[slotint2]);
            }
            else if(slotint1 == 2){
                slot2.setImageDrawable(slotPics[slotint2]);
            }
            else{
                slot2.setImageDrawable(slotPics[slotint2]);
            }
            if(slotint2 < 3){ slotint2++; }
            else{ slotint2 = 0; }
            handler.postDelayed(update2, time/2);
        }

    }

    private class UpdateSlot3 implements Runnable{

        public void run(){
            if(slotint3 == 0){
                slot3.setImageDrawable(slotPics[slotint3]);
            }
            else if(slotint3 == 1){
                slot3.setImageDrawable(slotPics[slotint3]);
            }
            else if(slotint3 == 2){
                slot3.setImageDrawable(slotPics[slotint3]);
            }
            else{
                slot3.setImageDrawable(slotPics[slotint3]);
            }
            if(slotint3 < 3){ slotint3++; }
            else{ slotint3 = 0; }
            handler.postDelayed(update3, time/3);
        }

    }

}
