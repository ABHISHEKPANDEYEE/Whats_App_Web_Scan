package com.colliery.whatsweb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;



public class MainActivity extends AppCompatActivity  {

    private String toggleValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences getshrd = getSharedPreferences("SecureKey", MODE_PRIVATE);
        String storedPatternValue = getshrd.getString("str", "something went wrong");


        LottieAnimationView webWap = findViewById(R.id.webwapp);
        LottieAnimationView gallery = findViewById(R.id.Gallery);

        LottieAnimationView trash = findViewById(R.id.trash);

         webWap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, WebWhats.class);
                 startActivity(intent);

             }
         });




         gallery.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Intent intent = new Intent();
                 intent.setAction(android.content.Intent.ACTION_VIEW);
                 intent.setType("image/*");
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);

             }
         });






       trash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(MainActivity.this, "Hold until data is cleared", Toast.LENGTH_SHORT).show();
           }
       });
        trash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteAppData();
                Toast.makeText(MainActivity.this, "Data cleared sucessfully", Toast.LENGTH_SHORT).show();
                return true;
            }
        });




    }









    public void lockkafunction(View view)
    {
        final ViewGroup nullParent = null;
        final SharedPreferences getshrd = getSharedPreferences("SecureKey",MODE_PRIVATE);
        toggleValue = getshrd.getString("bool","Something went wrong");
        Boolean aBoolean = Boolean.parseBoolean(toggleValue);

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.make_pattern_dialog,nullParent);




        final SwitchCompat switchCompat = mview.findViewById(R.id.pattern_dialog);
        switchCompat.setChecked(aBoolean);



        alert.setView(mview);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);







        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {


                    Intent intent = new Intent(MainActivity.this,SetPassword.class);
                    startActivity(intent);
                    alertDialog.dismiss();
                    finish();

                }
                else {



                        toggleValue = "false";
                        SharedPreferences shrd = getSharedPreferences("SecureKey", MODE_PRIVATE);
                        SharedPreferences.Editor editor = shrd.edit();
                        editor.putString("bool", toggleValue);
                        editor.apply();



                          alertDialog.dismiss();




                }
            }
        });



        alertDialog.show();




    }


    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        } }
}


