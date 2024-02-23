package com.colliery.whatsweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class splash extends AppCompatActivity {

    private String toggleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences getshrd = getSharedPreferences("SecureKey",MODE_PRIVATE);
        toggleValue = getshrd.getString("bool","something went wrong");

        TextView colliery = findViewById(R.id.textView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.collierylogo);
        colliery.startAnimation(animation);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2000);

                    if (toggleValue.equals("true")) {
                        Intent intent = new Intent(splash.this,FacePassword.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                    Intent intent = new Intent(splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();}


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
