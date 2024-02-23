package com.colliery.whatsweb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class ConfirmPassword extends AppCompatActivity implements PatternLockViewListener {

    private PatternLockView patternLockView;
    private String patternValue;
    private Button button;
    private String toggleValue;
    private TextView textView32;
    private String upcomingPatternValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        button = findViewById(R.id.Confirm);
        textView32 = findViewById(R.id.textView32);

        patternLockView = findViewById(R.id.pattern_lock_view_confirm);
        patternLockView.addPatternLockListener(this);

        Intent intent = getIntent();
        upcomingPatternValue = intent.getStringExtra(SetPassword.passKey);
    }

    @Override
    public void onStarted() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

        textView32.setText("Release finger when done.");
        button.setTextColor(Color.parseColor("#8e8e8e"));
        button.setBackgroundColor(Color.parseColor("#ffffff"));
        patternLockView.setTactileFeedbackEnabled(true);

        patternValue = PatternLockUtils.patternToString(patternLockView,progressPattern);

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        if(patternValue.equals( upcomingPatternValue))
        {
            patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            textView32.setText("Your new unlock pattern:");
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackgroundColor(Color.parseColor("#5c6bc0"));
            patternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.colorPrimaryDark));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     toggleValue = "true";
                    Intent intent = new Intent(ConfirmPassword.this, MainActivity.class);
                    SharedPreferences shrd = getSharedPreferences("SecureKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.putString("str", patternValue);
                    editor.putString("bool",toggleValue);

                    editor.apply();
                    startActivity(intent);
                    finish();

                }
            });



        }
        else
        {
            textView32.setText("Wrong Pattern");
            patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            patternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.pomegranate));
            Intent intent = new Intent(this, SetPassword.class);
            Toast.makeText(this, "Wrong Pattern", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onCleared() {

    }

    public void textView42(View view) {

        finish();
    }
}
