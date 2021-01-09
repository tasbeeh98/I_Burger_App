package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List<sOrder> listOrder;

    private RecyclerView rv;
    private MyAdapter adapter;
    String userId;
    ImageView folder,back,confirm;
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_orders);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentFirebaseUser.getUid();



        rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        listOrder = new ArrayList<>();



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query o = ref.child("Order").orderByChild("userId").equalTo(userId);

        o.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        sOrder l = npsnapshot.getValue(sOrder.class);
                        listOrder.add(l);

                    }

                    adapter = new MyAdapter(listOrder);
                    confirm.setVisibility(View.VISIBLE);
                    rv.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//end the first data



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
                Intent intent = new Intent(Orders.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        confirm = findViewById(R.id.con);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Orders.this, check.class);
                startActivity(intent);
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

            case R.id.nav_location:
                startActivity(new Intent(this, Location.class));
                break;


        }//end switch
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}