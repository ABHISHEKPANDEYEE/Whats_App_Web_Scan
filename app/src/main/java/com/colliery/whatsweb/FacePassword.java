package com.colliery.whatsweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class FacePassword extends AppCompatActivity implements PatternLockViewListener {

    private PatternLockView patternLockView;
    private String patternValue;
    private String storedPatternValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_password);

        SharedPreferences getshrd = getSharedPreferences("SecureKey", MODE_PRIVATE);
       storedPatternValue = getshrd.getString("str","something went wrong");

        patternLockView = findViewById(R.id.pattern_lock_view_dialog);
        patternLockView.addPatternLockListener(this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

        patternLockView.setTactileFeedbackEnabled(true);

        patternValue = PatternLockUtils.patternToString(patternLockView,progressPattern);

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {



        if (patternValue.equals(storedPatternValue)) {

            Intent intent = new Intent(FacePassword.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            patternLockView.clearPattern();
            Toast.makeText(this, "Incorrect pattern", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onCleared() {

    }
}
