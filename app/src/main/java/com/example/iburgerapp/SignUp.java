package com.example.iburgerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        editTextPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String ed_text = editTextPhone.getText().toString().trim();
                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
                {editTextPhone.setError("empty");}
                if (ed_text.length() > 2 )
                {String test =  editTextPhone.getText().toString().trim();

                    String bar = test.substring(0, 3);
                    if (bar.equals("079") || bar.equals("077") || bar.equals("078")){}
                    else {editTextPhone.setError("Must Start with 079/078/077");}
                }
                if (ed_text.length() > 10 ){editTextPhone.setError("10 digit only");}

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        editTextEmail = findViewById(R.id. email);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmailValid(editTextEmail.getText().toString())){
                    editTextEmail.setError("Not Valid");
                }
                if(isEmailValid(editTextEmail.getText().toString())){
                    editTextEmail.setError("Valid");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        editTextLocation = findViewById(R.id.location);

        editTextPassword = findViewById(R.id.password);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ed_text = editTextPassword.getText().toString().trim();
                if (ed_text.length() <= 6 ){
                    editTextPassword.setError("Min password length should be 6 character");
                }else if (ed_text.length() > 6 ) {
                    if(!isValidPassword(editTextPassword.getText().toString())){
                        editTextPassword.setError("Not Valid");
                    }
                    if(isValidPassword(editTextPassword.getText().toString())){ editTextPassword.setError("Valid");}
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

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
        String rePass = editTextRePassword.getText().toString().trim();

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
        if (Password.equals(rePass)) {
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(fullName, Phone, Email, Location);

                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "User has been register successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this, HomeScreen.class));
                                        } else {
                                            Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUp.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            editTextRePassword .setError("Not Match!");
            editTextRePassword.requestFocus() ;
        }
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}