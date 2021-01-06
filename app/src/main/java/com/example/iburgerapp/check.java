package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class check extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView folder,back,orderNow;
    TextView price;
    String userId;
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId= currentFirebaseUser.getUid();

        getPrice();

        price= findViewById(R.id .price);

        drawerLayout = findViewById(R.id .drawer_layout );
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open ,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState() ;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile) ;

        folder = findViewById(R.id.folder);
        folder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(GravityCompat.END)) drawerLayout.openDrawer(GravityCompat.END);
                else drawerLayout.closeDrawer(GravityCompat.END);
            }

        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(check.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        orderNow = findViewById(R.id.orderNow);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(check.this, Confirm.class);
                startActivity(intent);
            }
        });
    }

    private void getPrice() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Order").orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            int sum=0;
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot data: dataSnapshot.getChildren()){

                sum += data.child("rPrice").getValue(Integer.class);

            }
            price.setText(""+ sum);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }//end getPrice

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

            case R.id.nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;

        }//end switch
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }


}