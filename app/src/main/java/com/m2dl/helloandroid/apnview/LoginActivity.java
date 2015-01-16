package com.m2dl.helloandroid.apnview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camera1.R;

public class LoginActivity extends Activity {
    private ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        confirm = (ImageButton) findViewById(R.id.imageButton);

        StaticData.myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        EditText loginEditText = (EditText)findViewById(R.id.login);
        loginEditText.setTypeface(StaticData.myTypeface);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {



                //Toast.makeText(LoginActivity.this, "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                //new XmlParser(LoginActivity.this).getTypesAndSousTypes();
                StaticData.login = ((EditText)findViewById(R.id.login)).getText().toString();
                Log.e("Login", "Login user = " + StaticData.login);
                Intent intent = new Intent(LoginActivity.this, ApnViewActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
}
