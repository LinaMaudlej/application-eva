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

public class EvaControl extends AppCompatActivity {
    //for bluetooth
    Button led_on_off;
    TextView bluetooth_info;
    String bluetooth_address = null , bluetooth_name=null;
    BluetoothAdapter my_bluetooth = null;
    BluetoothSocket bluetooth_socket = null;
    Set<BluetoothDevice> paired_devices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //uniq id for hc05

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eva_control);

        Button forward= (Button) findViewById(R.id.forward);
        Button backward= (Button) findViewById(R.id.backward);
        Button right= (Button) findViewById(R.id.right);
        Button left= (Button) findViewById(R.id.left);
        try {
            connect_bt_and_test();
        } catch (Exception e) { }

        forward.setOnClickListener(v-> {
            send_byte_bluetooth("f"); //forward
        });

        backward.setOnClickListener(v-> {
            send_byte_bluetooth("b");
        });

        right.setOnClickListener(v-> {
            send_byte_bluetooth("r");
        });
        left.setOnClickListener(v-> {
            send_byte_bluetooth("l");
        });
    }

   //check led on/off.
    @SuppressLint("ClickableViewAccessibility")
    private void connect_bt_and_test() throws IOException {
        bluetooth_info=(TextView)findViewById(R.id.bluetooth_info);
        bluetooth_connect_device();
        led_on_off=(Button)findViewById(R.id.led_on_off);
        led_on_off.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) {send_byte_bluetooth("1");} //1 for on
                if(event.getAction() == MotionEvent.ACTION_UP){send_byte_bluetooth("0");} // 0 for off
                return true;
            }
        });

    }

    //bluetooth connection
    private void bluetooth_connect_device() throws IOException {
        try{
            my_bluetooth = BluetoothAdapter.getDefaultAdapter();
            bluetooth_address = my_bluetooth.getAddress();
            paired_devices = my_bluetooth.getBondedDevices();
            if (paired_devices.size()>0){
                for(BluetoothDevice bt : paired_devices){
                    bluetooth_address=bt.getAddress().toString();bluetooth_name = bt.getName().toString();
                    Toast.makeText(getApplicationContext(),"Connected", Toast.LENGTH_SHORT).show();
                }
            }

        }catch(Exception we){}
        my_bluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice dispositivo = my_bluetooth.getRemoteDevice(bluetooth_address);//connects to the device's address and checks if it's available
        bluetooth_socket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
        bluetooth_socket.connect();
        try { bluetooth_info.setText("BT Name: "+bluetooth_name+"\nBT Address: "+bluetooth_address); }
        catch(Exception e){}
    }


    private void send_byte_bluetooth(String s) {
        try {
            if (bluetooth_socket!=null){
                bluetooth_socket.getOutputStream().write(s.toString().getBytes()); //write to bluetooth
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}