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

import com.example.camera1.R;
import com.m2dl.helloandroid.apnview.utils.StaticData;

public class LoginActivity extends Activity {
    private ImageButton confirm;
    private EditText login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mode fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        // Récupération des élements graphiques
        confirm = (ImageButton) findViewById(R.id.imageButton);
        login = (EditText)findViewById(R.id.login);

        // Edition du design de l'EditText
        StaticData.myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        login.setTypeface(StaticData.myTypeface);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StaticData.login = login.getText().toString();
                if(StaticData.login.isEmpty())
                    StaticData.login = "user";

                Log.d("Login", "Login user = " + StaticData.login);
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
