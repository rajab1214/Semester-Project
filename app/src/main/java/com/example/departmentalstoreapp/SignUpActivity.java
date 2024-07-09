package com.example.departmentalstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText emailEditText, phoneNumberEditText, countryEditText, passwordEditText, confirmPasswordEditText;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myDb = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.editTextEmail);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        countryEditText = findViewById(R.id.editTextCountry);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        signUpButton = findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String country = countryEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (email.isEmpty() || phoneNumber.isEmpty() || country.isEmpty() ||
                        password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Not a valid email", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (myDb.checkUser(email)) {
                    Toast.makeText(SignUpActivity.this, "User already registered. Please sign in.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = myDb.insertData(email, phoneNumber, country);
                    if (isInserted) {
                        Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
}
