package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton snack,burger;
    ImageView folder;
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawerLayout = findViewById(R.id .drawer_layout );
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open ,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState() ;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile) ;


        snack = findViewById(R.id .snackBtn);
        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,Snack.class);
                startActivity(intent);
            }
        }) ;

        burger= findViewById(R.id .burger);
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,Burger.class);
                startActivity(intent);
            }
        }) ;


        folder = findViewById(R.id.folder);
        folder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(GravityCompat.END)) drawerLayout.openDrawer(GravityCompat.END);
                else drawerLayout.closeDrawer(GravityCompat.END);
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.nav_burger:
                startActivity(new Intent(this, Burger.class));
                break;
            case R.id.nav_snacks:
                startActivity(new Intent(this, Snack.class));
                break;

            case R.id.nav_orders:
                startActivity(new Intent(this, Orders.class));
                break;

            case R.id.nav_location:
                startActivity(new Intent(this, Location.class));
                break;

            case R.id.nav_logout :
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;

        }//end switch
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}

