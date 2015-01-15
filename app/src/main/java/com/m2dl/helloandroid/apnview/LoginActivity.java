package com.m2dl.helloandroid.apnview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.camera1.R;
import com.m2dl.helloandroid.apnview.util.XmlParser;

public class LoginActivity extends Activity {
    private ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        confirm = (ImageButton) findViewById(R.id.imageButton);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(LoginActivity.this, "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                new XmlParser(LoginActivity.this).getTypesAndSousTypes();
            }

        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
}
