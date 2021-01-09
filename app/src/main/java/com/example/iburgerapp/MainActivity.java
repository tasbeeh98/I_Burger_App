package com.example.iburgerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView forgetText,signUpText,loginText;
    private FirebaseAuth mAuth;
    private EditText editTextEmail ,editTextPassword;
    String ch;

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
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmailValid(editTextEmail.getText().toString())){
                    editTextEmail.setError("Not Valid");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        editTextPassword=findViewById(R.id.editTextPassword) ;

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("Login" , MODE_PRIVATE);
        String ch = preferences.getString("remember","");
        if (ch .equals("true")){
            Intent activity = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(activity);
        }
        else if (ch .equals("false")){
            Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();

        }
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
                    SharedPreferences preferences1 = getSharedPreferences("Login" , MODE_PRIVATE);
                    SharedPreferences .Editor editor = preferences1.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Intent activity = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(activity);

                }else
                {Toast.makeText(MainActivity.this, "Failed to login! please check your credentials!", Toast.LENGTH_SHORT).show();}

            }
        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}