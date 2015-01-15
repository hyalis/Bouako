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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.camera1.R;

public class ApnView extends Activity {

    private Camera cameraObject;
    private ShowCamera showCamera;
    private BitmapFunction bf = new BitmapFunction();
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
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
            ImgAPN.imgBitmap = bf.getResizedBitmap(bitmap, 450, 450);
            ImgAPN.imgBitmap = bf.rotateBitmap(ImgAPN.imgBitmap, 90);

            if(bitmap==null){
                Toast.makeText(getApplicationContext(), "not taken", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "taken", Toast.LENGTH_SHORT).show();
            }
            cameraObject.release();

            Intent intent = new Intent(ApnView.this, FormActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apn_view);
        cameraObject = isCameraAvailiable();
        showCamera = new ShowCamera(this, cameraObject);
        LinearLayout preview = (LinearLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);
    }
    public void snapIt(View view){
        cameraObject.takePicture(null, null, capturedIt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}