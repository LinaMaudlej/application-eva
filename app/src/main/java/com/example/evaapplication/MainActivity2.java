package com.example.evaapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         navView = findViewById(R.id.nav_view);
         navView.setOnNavigationItemReselectedListener(navigationItemReselectedListener);

    }
private  BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener=new BottomNavigationView.OnNavigationItemReselectedListener() {
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    Intent mainIntent = new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(mainIntent);
                    break;
                case R.id.navigation_settings:
                    Intent settingsIntent = new Intent(MainActivity2.this,SettingsActivity.class);
                    startActivity(settingsIntent);
                    break;
                case R.id.navigation_notifications:
                    Intent notificationIntent = new Intent(MainActivity2.this,NotificationsActivity.class);
                    startActivity(notificationIntent);
                    break;
                case R.id.navigation_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent logoutIntent = new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(logoutIntent);
                    finish();
                    break;
            }
        }
    };
}