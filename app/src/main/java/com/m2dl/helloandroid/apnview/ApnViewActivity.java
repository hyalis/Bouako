package com.m2dl.helloandroid.apnview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.camera1.R;
import com.m2dl.helloandroid.apnview.utils.BitmapFunction;
import com.m2dl.helloandroid.apnview.utils.CustomSwitch;
import com.m2dl.helloandroid.apnview.utils.ShowCamera;
import com.m2dl.helloandroid.apnview.utils.StaticData;
import com.m2dl.helloandroid.apnview.utils.XmlParser;

public class ApnViewActivity extends Activity {

    private Camera cameraObject;
    private ShowCamera showCamera;
    private BitmapFunction bf = new BitmapFunction();
    private LinearLayout apnPreview;

    // Composants graphiques
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

        // Récupération des composants graphiques
        EditText commEditText = (EditText)findViewById(R.id.commentaire);
        ImageButton btnValidate = (ImageButton)findViewById(R.id.btnValidate);
        this.apnPreview = (LinearLayout) findViewById(R.id.apnPreview);
        LinearLayout preview = (LinearLayout) findViewById(R.id.camera_preview);
        this.typesSwitch = (Switch) findViewById(R.id.type);
        this.valuesSwitch = (CustomSwitch)findViewById(R.id.valeur);

        // On met en invisible les boutons inutiles pour cette vue
        commEditText.setVisibility(View.INVISIBLE);
        btnValidate.setVisibility(View.INVISIBLE);

        cameraObject = isCameraAvailiable();
        showCamera = new ShowCamera(this, cameraObject);
        preview.addView(showCamera);

        // Parse le fichier xml et stocke les types et sous types
        StaticData.imageTypesAndSousTypes = new XmlParser(ApnViewActivity.this).getTypesAndSousTypes();


        // Modification du texte des sliders en fonction du fichier xml lu, ici les types
        typesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(0));
        typesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(1));
        typesSwitch.setChecked(StaticData.switchTypeIsSelected);

        // Modification du texte des sliders en fonction du fichier xml lu, ici les sous-types
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
        ImageButton btnAPN = (ImageButton) findViewById(R.id.button_capture); // TODO peut mieux faire ?
        btnAPN.setVisibility(View.INVISIBLE);
        cameraObject.takePicture(null, null, capturedIt);
    }

    public void updateSwitch(boolean isChecked){
        if(!isChecked)
        {
            valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(2));
            valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(3));
        } else
        {
            valuesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(4));
            valuesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(5));
        }
        valuesSwitch.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}