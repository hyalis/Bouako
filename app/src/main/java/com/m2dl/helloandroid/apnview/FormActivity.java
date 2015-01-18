package com.m2dl.helloandroid.apnview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.camera1.R;

public class FormActivity extends Activity {
    private Bitmap originalImg, imgBlur, imgCircle, imgFocus;
    private ImageView img;
    public static int cptTouch = 0;
    public static int x, y, radiusValue;
    private BitmapFunction bf = new BitmapFunction();
    private Switch typesSwitch;
    private CustomSwitch valuesSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        SeekBar sk = (SeekBar) findViewById(R.id.radius);

        EditText commEditText = (EditText)findViewById(R.id.commentaire);
        commEditText.setTypeface(StaticData.myTypeface);

        radiusValue = 2;
        sk.setProgress(radiusValue);

        x = 450/2;
        y = 450/2;

        img = (ImageView) findViewById(R.id.img);

        imgBlur = bf.fastblur(StaticData.imgBitmap, 25);
        imgCircle = bf.getCroppedBitmap(StaticData.imgBitmap, x, y, radiusValue);
        imgFocus = bf.overlay(imgBlur, imgCircle);
        img.setImageBitmap(imgFocus);


        img.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                if (cptTouch > 4) {
                    System.gc();
                    x = (int)arg1.getX();
                    y = (int)arg1.getY();
                    Log.e("Touch img", "X = " + x + " Y = " + y);
                    redrawImg();
                    cptTouch = 0;
                }
                cptTouch++;
                return true;
            }
        });

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                Log.e("prog", "val = " + progress);
                System.gc();
                FormActivity.radiusValue = progress;
                redrawImg();
            }
        });


        typesSwitch = (Switch) findViewById(R.id.type);
        valuesSwitch = (CustomSwitch) findViewById(R.id.valeur);

        // Set Animal & Plante
        typesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(0));
        typesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(1));

        // Set -50/+50
        valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(2));
        valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(3));

        typesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(2));
                    valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(3));
                    Log.e("","ON= "+valuesSwitch.getTextOn() + " OFF = " + valuesSwitch.getTextOff());
                } else
                {
                    valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(4));
                    valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(5));
                    Log.e("","ON= "+valuesSwitch.getTextOn() + " OFF = " + valuesSwitch.getTextOff());
                }
                valuesSwitch.setActivated(false);valuesSwitch.setActivated(true);
                valuesSwitch.requestLayout();
            }
        });
    }


    public void redrawImg() {
        imgCircle = bf.getCroppedBitmap(StaticData.imgBitmap, x, y, radiusValue);
        imgFocus = bf.overlay(imgBlur, imgCircle);
        img.setImageBitmap(imgFocus);
    }

    public void restart(View v){
        Intent intent = new Intent(FormActivity.this, ApnViewActivity.class);
        startActivity(intent);
        finish();
    }
}
