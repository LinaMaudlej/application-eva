package com.example.evaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class EvaCall extends AppCompatActivity {
    //todo add the skype interface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eva_call);
        //https://stackoverflow.com/questions/6414494/launch-skype-from-an-app-programmatically-pass-number-android/8844526#8844526

        Button video = (Button) findViewById(R.id.video);
        video.setOnClickListener(v -> {
            /*Intent sky = new Intent("android.intent.action.VIEW");
            sky.setData(Uri.parse("skype:" + "Hamza Maudlej"));
            startActivity(sky);*/
        });

    }
}