package com.m2dl.helloandroid.apnview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.camera1.R;

public class ApnViewActivity extends Activity {

    private Camera cameraObject;
    private ShowCamera showCamera;
    private BitmapFunction bf = new BitmapFunction();
    private LinearLayout apnPreview;
    private TextView message;

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

            Thread thread = new Thread() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
                    StaticData.imgBitmap = bf.getResizedBitmap(bitmap, 450, 450);
                    StaticData.imgBitmap = bf.rotateBitmap(StaticData.imgBitmap, 90);

                    cameraObject.release();

                    Intent intent = new Intent(ApnViewActivity.this, FormActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            thread.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apn_view);
        this.apnPreview = (LinearLayout) findViewById(R.id.apnPreview);
        this.message = (TextView) findViewById(R.id.message);

        cameraObject = isCameraAvailiable();
        showCamera = new ShowCamera(this, cameraObject);
        LinearLayout preview = (LinearLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);
    }
    public void snapIt(View view){
        this.apnPreview.setVisibility(View.GONE);
        this.message.setText("Patientez...");
        cameraObject.takePicture(null, null, capturedIt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}