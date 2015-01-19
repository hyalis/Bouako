package com.m2dl.helloandroid.apnview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camera1.R;
import com.m2dl.helloandroid.apnview.util.ImageSenderMail;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends Activity {
    private Bitmap originalImg, imgBlur, imgCircle, imgFocus;
    public static int cptTouch = 0;
    public static int x, y, radiusValue;
    private BitmapFunction bf = new BitmapFunction();
    private DecimalFormat df = new DecimalFormat("#.000");

    // Utilitaires pour le GPS
    private LocationListener mLocationListener;
    private LocationManager locationManager;

    // Elements graphiques
    private Switch typesSwitch;
    private ImageView img;
    private CustomSwitch valuesSwitch;
    private ImageButton submitBtn;
    private EditText commentaire;
    private SeekBar sk;

    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Récupération des elements graphiques
        sk = (SeekBar) findViewById(R.id.radius);
        img = (ImageView) findViewById(R.id.img);
        commentaire = (EditText)findViewById(R.id.commentaire);
        submitBtn = (ImageButton)findViewById(R.id.btnValidate);
        typesSwitch = (Switch) findViewById(R.id.type);
        valuesSwitch = (CustomSwitch) findViewById(R.id.valeur);

        // Ajout d'un Typeface à l'EditText de commentaire
        commentaire.setTypeface(StaticData.myTypeface);

        //
        radiusValue = 2;
        sk.setProgress(radiusValue);
        x = 450/2;
        y = 450/2;


        // Gestion de la position
        locationManager = (LocationManager) FormActivity.this.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                StaticData.lastLocation = df.format(location.getLatitude()) + " / " + df.format(location.getLongitude());
                Log.d("LOCATION", "LOCATION CHANGED :"+ StaticData.lastLocation);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

        // Gestion du clic sur le bouton submit
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline()){
                    Toast.makeText(FormActivity.this, "Message not sent : device is offline !", Toast.LENGTH_LONG).show();

                } else if (StaticData.lastLocation == null) {
                    Toast.makeText(FormActivity.this, "Message not sent : No location found. Please retry !", Toast.LENGTH_LONG).show();
                    Location l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(l != null)
                        StaticData.lastLocation = df.format(l.getLatitude()) + " / " + df.format(l.getLongitude());
                } else {

                    String date = new SimpleDateFormat("dd/MM/yy").format(new Date());
                    String type = getTypeChoosen();
                    new ImageSenderMail(FormActivity.this, imgFocus, type).sendImage(StaticData.login, date, StaticData.lastLocation, commentaire.getText().toString());

                }

            }
        });

        imgBlur = bf.fastblur(StaticData.imgBitmap, 25);
        redrawImg();

        // Gestion du point d'intéret de la photo: on floute tout sauf le point d'intéret à l'endroit ou l'user à cliqué
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                if (cptTouch > 4) {
                    System.gc();
                    x = (int)arg1.getX();
                    y = (int)arg1.getY();
                    redrawImg();
                    cptTouch = 0;
                }
                cptTouch++;
                return true;
            }
        });

        // Change la taille du cercle de non-flou
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.gc();
                FormActivity.radiusValue = progress;
                redrawImg();
            }
        });


        // Set Animal & Plante
        typesSwitch.setTextOff(StaticData.imageTypesAndSousTypes.get(0));
        typesSwitch.setTextOn(StaticData.imageTypesAndSousTypes.get(1));
        typesSwitch.setChecked(StaticData.switchTypeIsSelected);

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

    /**
     * Check si le device est connecté à internet
     * @return true si le device est connecté
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Retourne le type et sous-type de l'image prise par l'user
     * @return le type et sous-type de l'image prise par l'user
     */
    public String getTypeChoosen()
    {
        StringBuilder sb = new StringBuilder();
        if(typesSwitch.isChecked())
            sb.append(typesSwitch.getTextOn());
        else
            sb.append(typesSwitch.getTextOff());

        sb.append(" ");

        if(valuesSwitch.isChecked())
            sb.append(valuesSwitch.getTextOn());
        else
            sb.append(valuesSwitch.getTextOff());

        return sb.toString();
    }

    /**
     * Compute la nouvelle image à afficher (superposition d'une image flou, et d'une image en cercle non floue
     */
    public void redrawImg() {
        imgCircle = bf.getCroppedBitmap(StaticData.imgBitmap, x, y, radiusValue);
        imgFocus = bf.overlay(imgBlur, imgCircle);
        img.setImageBitmap(imgFocus);
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

    public void restart(View v){
        Intent intent = new Intent(FormActivity.this, ApnViewActivity.class);
        startActivity(intent);
        finish();
    }
}
