package com.example.iburgerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    private Button ResetPassword;
    private EditText emailEditText;
    //private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        emailEditText = findViewById(R.id .EmailReset);

        //mAuth = FirebaseAuth.getInstance();

        ResetPassword = findViewById(R.id.ResetBtn);
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }//end onCreate()

    private void resetPassword(){
        String Email = emailEditText.getText().toString().trim();


        if (Email.isEmpty() ){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus() ;
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            emailEditText.setError("Please provide valid Email!");
            emailEditText.requestFocus() ;
            return;
        }
        //mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
        //   @Override
        //  public void onComplete(@NonNull Task<Void> task) {
        //     if (task.isSuccessful()){
        ////        Toast.makeText(ForgetPassword.this, "Check your Email to Reset Password", Toast.LENGTH_SHORT).show();
        //  }else
        // {Toast.makeText(ForgetPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show();}

    }
    //    }) ;
}
//}