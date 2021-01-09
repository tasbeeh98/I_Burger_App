package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView folder,back;
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;

    EditText LocationEdt,passwordEdt,fullNameEdt,phoneNumberEdt,rePassword;
    private FirebaseAuth mAuth;   
    TextView Login;
    String userId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId= currentFirebaseUser.getUid();
        
        mAuth = FirebaseAuth.getInstance();

        passwordEdt= findViewById(R.id.password);
        passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ed_text = passwordEdt.getText().toString().trim();
                if (ed_text.length() <= 6 ){
                    passwordEdt.setError("Min password length should be 6 character");
                }else if (ed_text.length() > 6 ) {
                    if(!isValidPassword(passwordEdt.getText().toString())){
                        passwordEdt.setError("Must include Upper/Lower,Number,Symbol");
                    }
                    if(isValidPassword(passwordEdt.getText().toString())){ passwordEdt.setError("Valid");}
                }
               }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        rePassword = findViewById(R.id .rePassword);

        LocationEdt = findViewById(R.id.location);
        fullNameEdt = findViewById(R.id.fullName);

        phoneNumberEdt= findViewById(R.id.phoneNumber);
        phoneNumberEdt.setInputType(InputType.TYPE_CLASS_NUMBER);

        phoneNumberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String ed_text = phoneNumberEdt.getText().toString().trim();
                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
                {phoneNumberEdt.setError("empty");}
                if (ed_text.length() > 2 )
                {String test =  phoneNumberEdt.getText().toString().trim();

                       String bar = test.substring(0, 3);
                       if (bar.equals("079") || bar.equals("077") || bar.equals("078")){}
                       else {phoneNumberEdt.setError("Must Start with 079/078/077");}
                }
                if (ed_text.length() > 10 ){phoneNumberEdt.setError("10 digit only");}

           }//end onTextChanged

            @Override
            public void afterTextChanged(Editable editable) {}

        });



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
                Intent intent = new Intent(Profile.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Login= findViewById(R.id .save); 
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               update();
            }
        }); 
            }

    private void update() {
        final String fullName = fullNameEdt.getText().toString().trim();
        final String Phone = phoneNumberEdt.getText().toString().trim();
        final String Location = LocationEdt.getText().toString().trim();
        final String pass = passwordEdt.getText().toString().trim();
        final String rePass = rePassword.getText().toString().trim();


        if (fullName.isEmpty() ){
            fullNameEdt.setError("Full name is required");
            fullNameEdt.requestFocus() ;
            return;
        }
        if (Phone.isEmpty() ){
            phoneNumberEdt.setError("Phone is required");
            phoneNumberEdt.requestFocus() ;
            return;
        }

        if (Location.isEmpty() ){
            LocationEdt.setError("Location is required");
            LocationEdt.requestFocus() ;
            return;
        }
        if (pass.isEmpty() ){
            passwordEdt.setError("Password is required");
            passwordEdt.requestFocus() ;
            return;
        }
        if (pass.length() < 6){
            passwordEdt.setError("Min password length should be 6 character");
            passwordEdt.requestFocus() ;
            return;
        }

        if (pass.equals(rePass)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myref = database.getReference();
            myref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    myref.child("User").child(userId).child("fullName").setValue(fullName);
                    myref.child("User").child(userId).child("location").setValue(Location);
                    myref.child("User").child(userId).child("phone").setValue(Phone);
                    updatePass();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("User", databaseError.getMessage());
                }
            });
        }
        else {
            rePassword.setError("Not Match!");
            rePassword.requestFocus() ;
        }

    }//end update()
    private void updatePass() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = passwordEdt.getText().toString().trim();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            passwordEdt.setText("");
                            fullNameEdt.setText("");
                            LocationEdt.setText("");
                            phoneNumberEdt.setText("");
                            rePassword.setText("");
                            Toast.makeText(Profile.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

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


        }//end switch
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}