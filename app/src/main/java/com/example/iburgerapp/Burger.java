package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Burger extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    RadioButton r1, r2, r3, r4, r5, r6, r7, r8;
    ImageView b1, b2, b3, b4, b5, b6, b7, b8;
    int count1, count2, count3, count4 = 0;
    TextView t1, t2, t3, t4;
    ImageView folder, back;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    int val1, val2, val3, val4 = 1;
    int price1=5,price2=4,price3=6,price4=3;
    String ff, ff1, ff2, ff3;
    String userId;
    int rPrice1,rPrice2,rPrice3,rPrice4;
    private List<sOrder > order;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId= currentFirebaseUser.getUid();

        deleteBurger();

        rPrice1 = 0;
        rPrice2 = 0;
        rPrice3 = 0;
        rPrice4 = 0;

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_burger) ;

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Burger.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        folder = findViewById(R.id.folder);
        folder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!drawerLayout.isDrawerOpen(GravityCompat.END))
                    drawerLayout.openDrawer(GravityCompat.END);
                else drawerLayout.closeDrawer(GravityCompat.END);
            }

        });

        t1 = findViewById(R.id.textView12);
        t2 = findViewById(R.id.textView15);
        t3 = findViewById(R.id.textView20);
        t4 = findViewById(R.id.textView21);

        r1 = findViewById(R.id.radio_s);r1.setOnClickListener(this);
        r2 = findViewById(R.id.radio_m1);r2.setOnClickListener(this);
        r3 = findViewById(R.id.radio_h);r3.setOnClickListener(this);
        r4 = findViewById(R.id.radio_m2);r4.setOnClickListener(this);
        r5 = findViewById(R.id.radio_c);r5.setOnClickListener(this);
        r6 = findViewById(R.id.radio_m3);r6.setOnClickListener(this);
        r7 = findViewById(R.id.radio_f);r7.setOnClickListener(this);
        r8 = findViewById(R.id.radio_m4);r8.setOnClickListener(this);

        b1 = findViewById(R.id.imageView13);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count1++;
                t1.setText(String.valueOf(count1));
                val1 = 1;
                rPrice1 +=price1;
                if (val1==1 && count1 ==1){upload(price1 , count1, ff,rPrice1);}
                if (val1==1 && count1 >1 ){update(ff,count1,rPrice1);}

            }
        });

        b2 = findViewById(R.id.imageView14);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count1 <= 0) {
                    count1 = 0;
                    int a = 0;
                    val1 = 0;
                    t1.setText(String.valueOf(a));
                } else {
                    count1--;
                    t1.setText(String.valueOf(count1));
                    val1 = 1;
                    rPrice1 -=price1;
                    if (val1==1 && count1 >=1 ){update(ff,count1,rPrice1);}
                    if (val1==1 && count1 <1 ){deleteSingleSnake(ff);}
                }


            }

        });
        b3 = findViewById(R.id.imageView16);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2++;
                t2.setText(String.valueOf(count2));
                val2 = 1;
                rPrice2+=price2;
                if (val2==1 && count2 ==1){upload(price2 , count2, ff1,rPrice2);}
                if (val2==1 && count2 >1 ){update(ff1,count2,rPrice2);}

            }
        });
        b4 = findViewById(R.id.imageView17);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count2 <= 0) {
                    count2 = 0;
                    int a = 0;
                    val2 = 0;
                    t2.setText(String.valueOf(a));
                } else {
                    count2--;
                    t2.setText(String.valueOf(count2));
                    val2 = 1;
                    rPrice2 -=price2;
                    if (val2==1 && count2 >=1 ){update(ff1,count2,rPrice2);}
                    if (val2==1 && count2 <1 ){deleteSingleSnake(ff1);}
                }

            }
        });
        b5 = findViewById(R.id.imageView19);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3++;
                t3.setText(String.valueOf(count3));
                val3 = 1;
                rPrice3+=price3;
                if (val3==1 && count3 ==1){upload(price3 , count3, ff2,rPrice3);}
                if (val3==1 && count3 >1 ){update(ff2,count3,rPrice3);}

            }
        });
        b6 = findViewById(R.id.imageView20);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count3 <= 0) {
                    count3 = 0;
                    int a = 0;
                    val3 = 0;
                    t3.setText(String.valueOf(a));
                } else {
                    count3--;
                    t3.setText(String.valueOf(count3));
                    val3 = 1;
                    rPrice3 -=price3;
                    if (val3==1 && count3 >=1 ){update(ff2,count3,rPrice3);}
                    if (val3==1 && count3 <1 ){deleteSingleSnake(ff2);}
                }

            }
        });
        b7 = findViewById(R.id.imageView22);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count4++;
                t4.setText(String.valueOf(count4));
                val4 = 1;
                rPrice4 +=price4;
                if (val4 ==1 && count4 ==1){upload(price4, count4, ff3,rPrice4);}
                if (val4==1 && count4 >1 ){update(ff3,count4,rPrice4);}

            }
        });
        b8 = findViewById(R.id.imageView23);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count4 <= 0) {
                    count4 = 0;
                    int a = 0;
                    val4 = 0;
                    t4.setText(String.valueOf(a));
                } else {
                    count4--;
                    t4.setText(String.valueOf(count4));
                    val4 = 1;
                    rPrice4 -=price4;
                    if (val4==1 && count4 >=1 ){update(ff3,count4,rPrice4);}
                    if (val4==1 && count4 <1 ){deleteSingleSnake(ff3);}
                }

            }
        });

        r1.setChecked(true);
        ff= (String) r1.getText() ;
        r3.setChecked(true);
        ff1= (String) r3.getText() ;

        r5.setChecked(true);
        ff2= (String) r5.getText() ;

        r7.setChecked(true);
        ff3= (String) r7.getText() ;

    }

    private void deleteBurger() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delete = ref.child("Order").orderByChild("userId").equalTo(userId);

        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    sOrder s = deleteSnapshot.getValue(sOrder.class);
                    if(s.getType().equals("Burger")){
                    deleteSnapshot.getRef().removeValue();
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.radio_s:
                count1=0;
                rPrice1=0;
                t1.setText(String.valueOf(count1));
                r2.setChecked(false);
                ff = (String) r1.getText();
                price1 = 5;
                break;

            case R.id.radio_m1:
                //rPrice1= 0;
                count1=0;
                rPrice1=0;
                t1.setText(String.valueOf(count1));
                r1.setChecked(false);
                ff = (String) r1.getText()+" M E A L";
                price1=6;
                break;


            case R.id.radio_h:
                count2=0;
                rPrice2=0;
                t2.setText(String.valueOf(count2));
                r4.setChecked(false);
                ff1 = (String) r3.getText();
                price2=4;
                break;

            case R.id.radio_m2:
                //rPrice2 = 0;
                count2=0;
                rPrice2=0;
                t2.setText(String.valueOf(count2));
                r3.setChecked(false);
                ff1 = (String) r3.getText()+" M E A L";
                price2=6;
                break;

            case R.id.radio_c:
                count3=0;
                rPrice3=0;
                t3.setText(String.valueOf(count3));
                r6.setChecked(false);
                ff2 = (String) r5.getText();
                price3=6 ;
                break;

            case R.id.radio_m3:
                //rPrice3 = 0;
                count3=0;
                rPrice3=0;
                t3.setText(String.valueOf(count3));
                r5.setChecked(false);
                ff2 = (String) r5.getText()+" M E A L";
                price3=7;
                break;

            case R.id.radio_f:
                count4=0;
                rPrice4 = 0;
                t4.setText(String.valueOf(count4));
                r8.setChecked(false);
                ff3 = (String) r7.getText();
                price4 = 3;
                break;

            case R.id.radio_m4:
                //rPrice4= 0;
                count4=0;
                rPrice4 = 0;
                t4.setText(String.valueOf(count4));
                r7.setChecked(false);
                ff3 = (String) r7.getText()+" M E A L";
                price4 = 4;
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile.class));
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

        }//end switch
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    public void upload(int val , int count, String ff ,int p) {

         DatabaseReference postsRef = ref.child("Order");
         DatabaseReference newPostRef = postsRef.push();
         newPostRef.setValue(new sOrder(ff, val, count, userId ,p ,"Burger"));

    }


    public void update(String name, int num, int price) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delete = ref.child("Order").orderByChild("userId").equalTo(userId);

        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    sOrder s = deleteSnapshot.getValue(sOrder.class);
                    if (s.getType().equals("Burger")){
                        if (s.getoName().equals(name) ){

                            Map<String, Object> map = new HashMap<>();
                            map.put("/Order/" + deleteSnapshot.getKey() + "/rPrice/", price);//price
                            map.put("/Order/" + deleteSnapshot.getKey() + "/num/", num);//count1
                            ref.updateChildren(map);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void deleteSingleSnake(String ff) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delete = ref.child("Order").orderByChild("userId").equalTo(userId);

        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    sOrder s = deleteSnapshot.getValue(sOrder.class);
                    if(s.getType().equals("Burger") ){
                        if (s.getoName().equals(ff) ){
                            deleteSnapshot.getRef().setValue(null);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}