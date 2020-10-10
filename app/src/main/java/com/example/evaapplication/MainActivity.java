package com.example.evaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //todo modify tool bar with icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button call = (Button) findViewById(R.id.call);
        Button control = (Button) findViewById(R.id.control);

        control.setOnClickListener(v -> {
            Intent eva_control_intent = new Intent(this, EvaControl.class);
            startActivity(eva_control_intent);

        });

        call.setOnClickListener(v -> {
            Intent eva_call_intent = new Intent(this, EvaCall.class);
            startActivity(eva_call_intent);

        });
    }

}