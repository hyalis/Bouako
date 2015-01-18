package com.m2dl.helloandroid.apnview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.camera1.R;
import com.m2dl.helloandroid.apnview.util.XmlParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ApnViewActivity extends Activity {

    private Camera cameraObject;
    private ShowCamera showCamera;
    private BitmapFunction bf = new BitmapFunction();
    private LinearLayout apnPreview;
    private TextView message;

    private Switch typesSwitch;
    private CustomSwitch valuesSwitch;


    public static Camera isCameraAvailiable(){
        Camera object = null;
        try {
            object = Camera.open();
        }
        catch (Exception e){
        }
        return object;
    }

    private PictureCallback capturedIt = new PictureCallback() {

        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data.length);
            StaticData.imgBitmap = bf.getResizedBitmap(bitmap, 450, 450);
            StaticData.imgBitmap = bf.rotateBitmap(StaticData.imgBitmap, 90);

            cameraObject.release();

            Intent intent = new Intent(ApnViewActivity.this, FormActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apn_view);

        EditText commEditText = (EditText)findViewById(R.id.commentaire);
        commEditText.setVisibility(View.INVISIBLE);
        ImageButton btnValidate = (ImageButton)findViewById(R.id.btnValidate);
        btnValidate.setVisibility(View.INVISIBLE);

        this.apnPreview = (LinearLayout) findViewById(R.id.apnPreview);
        cameraObject = isCameraAvailiable();
        showCamera = new ShowCamera(this, cameraObject);
        LinearLayout preview = (LinearLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);

        StaticData.imageTypesAndSousTypes = new XmlParser(ApnViewActivity.this).getTypesAndSousTypes();

        typesSwitch = (Switch) findViewById(R.id.type);
        this.valuesSwitch = (CustomSwitch)findViewById(R.id.valeur);


        // Set Animal & Plante
        typesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(0));
        typesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(1));
        typesSwitch.setChecked(StaticData.switchTypeIsSelected);

        // Set -50/+50
        valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(2));
        valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(3));
        updateSwitch(StaticData.switchTypeIsSelected);
        valuesSwitch.setChecked(StaticData.switchSousTypeIsSelected);

        typesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StaticData.switchTypeIsSelected = isChecked;
                updateSwitch(isChecked);
            }
        });

        valuesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StaticData.switchSousTypeIsSelected = isChecked;
            }
        });
    }

    public void snapIt(View view){
        ImageButton btnAPN = (ImageButton) findViewById(R.id.button_capture);
        btnAPN.setVisibility(View.INVISIBLE);
        cameraObject.takePicture(null, null, capturedIt);
    }

    public void updateSwitch(boolean isChecked){
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
        valuesSwitch.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}