package com.mabuzagroup.baytulilmacademy.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.repositories.AuthRepository;
import com.mabuzagroup.baytulilmacademy.utils.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new AuthRepository();

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (!ValidationUtils.isValidFullName(fullName)) {
            etFullName.setError("Enter your full name");
            return;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Invalid email");
            return;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (!ValidationUtils.passwordsMatch(password, confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        authRepository.registerUser(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {

                Toast.makeText(RegisterActivity.this,
                        "Registration Successful",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(RegisterActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}