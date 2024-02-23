package com.colliery.whatsweb;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class SetPassword extends AppCompatActivity implements PatternLockViewListener {

    private PatternLockView patternLockView;
    private String patternValue;
    private TextView textView3;
    private Button button;
    static final String passKey = "com.colliery.webwhats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        textView3 = findViewById(R.id.textView3);
        button = findViewById(R.id.Contnue);


       patternLockView = findViewById(R.id.pattern_lock_view_set);
       patternLockView.addPatternLockListener(this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {
        textView3.setText("Release finger when done.");
        button.setTextColor(Color.parseColor("#8e8e8e"));
        button.setBackgroundColor(Color.parseColor("#ffffff"));
        patternLockView.setTactileFeedbackEnabled(true);
        patternValue = PatternLockUtils.patternToString(patternLockView,progressPattern);

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {

        if(patternValue.length() < 4)
        {   patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            textView3.setText("Connect at least 4 dots. Try again:");
            patternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.pomegranate));

        }
        else
            {
                patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                textView3.setText("Pattern recorded.");
                button.setTextColor(Color.parseColor("#ffffff"));
                button.setBackgroundColor(Color.parseColor("#5c6bc0"));
                patternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.colorPrimaryDark));

                     button.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             Intent intent = new Intent(SetPassword.this,ConfirmPassword.class);
                             intent.putExtra(passKey,patternValue);
                             startActivity(intent);
                             finish();
                         }
                     });
            }


    }

    @Override
    public void onCleared() {

    }




    public void textView4() {
        finish();
    }
}
