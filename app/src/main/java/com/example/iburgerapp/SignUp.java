package com.example.iburgerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private EditText editTextFullName , editTextPhone , editTextEmail , editTextLocation ,editTextPassword, editTextRePassword;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       mAuth = FirebaseAuth.getInstance();

        editTextFullName = findViewById(R.id. fullName);
        editTextPhone  = findViewById(R.id.phoneNumber);
        editTextEmail = findViewById(R.id. email);
        editTextLocation = findViewById(R.id.location);
        editTextPassword = findViewById(R.id.password);
        editTextRePassword = findViewById(R.id.rePassword);

        signUp = findViewById(R.id.signUp ) ;
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

    }

    private void RegisterUser(){
        final String fullName = editTextFullName.getText().toString().trim();
        final String Phone = editTextPhone.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        final String Location = editTextLocation.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if (fullName.isEmpty() ){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus() ;
            return;
        }
        if (Phone.isEmpty() ){
            editTextPhone.setError("Phone is required");
            editTextPhone.requestFocus() ;
            return;
        }
        if (Email.isEmpty() ){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus() ;
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please provide valid Email!");
            editTextEmail.requestFocus() ;
            return;
        }
        if (Location.isEmpty() ){
            editTextLocation.setError("Location is required");
            editTextLocation.requestFocus() ;
            return;
        }
        if (Password.isEmpty() ){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus() ;
            return;
        }
        if (Password.length() < 6){
            editTextPassword.setError("Min password length should be 6 character");
            editTextPassword.requestFocus() ;
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email ,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName ,Phone ,Email , Location) ;

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUp .this, "User has been register successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this,HomeScreen.class));
                                    }
                                    else {
                                        Toast.makeText(SignUp .this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUp .this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}