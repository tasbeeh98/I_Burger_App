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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView forgetText,signUpText,loginText;
    private FirebaseAuth mAuth;
    private EditText editTextEmail ,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forgetText = findViewById(R.id.forgetText);
        forgetText.setOnClickListener(this);

        signUpText= findViewById(R.id.signUp );
        signUpText.setOnClickListener(this);

        loginText = findViewById(R.id.login );
        loginText.setOnClickListener(this);

        editTextEmail=findViewById(R.id.editTextEmailAddress) ;
        editTextPassword=findViewById(R.id.editTextPassword) ;

        mAuth = FirebaseAuth.getInstance();

         }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.forgetText :
                startActivity(new Intent(this,ForgetPassword.class));
                break;
            case R.id.signUp :
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.login :
                //startActivity(new Intent(this,HomeScreen.class));
                userLogin();
                break;
        }}

    private void userLogin() {
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(Email ,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,HomeScreen.class));
                }else
                {Toast.makeText(MainActivity.this, "Failed to login! please check your credentials!", Toast.LENGTH_SHORT).show();}

            }
        });
    }
}