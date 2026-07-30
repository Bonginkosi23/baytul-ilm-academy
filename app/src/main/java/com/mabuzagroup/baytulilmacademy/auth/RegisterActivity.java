package com.mabuzagroup.baytulilmacademy.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.constants.UserRoles;
import com.mabuzagroup.baytulilmacademy.models.User;
import com.mabuzagroup.baytulilmacademy.repositories.AuthRepository;
import com.mabuzagroup.baytulilmacademy.repositories.UserRepository;
import com.mabuzagroup.baytulilmacademy.utils.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new AuthRepository();
        userRepository = new UserRepository();

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

        // Validate Full Name
        if (!ValidationUtils.isValidFullName(fullName)) {
            etFullName.setError("Enter your full name");
            etFullName.requestFocus();
            return;
        }

        // Validate Email
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Invalid email address");
            etEmail.requestFocus();
            return;
        }

        // Validate Password
        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // Validate Confirm Password
        if (!ValidationUtils.passwordsMatch(password, confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        btnRegister.setEnabled(false);

        authRepository.registerUser(email, password, new AuthRepository.AuthCallback() {

            @Override
            public void onSuccess() {

                String uid = authRepository.getCurrentUserId();

                if (uid == null) {

                    btnRegister.setEnabled(true);

                    Toast.makeText(RegisterActivity.this,
                            "Unable to retrieve user ID.",
                            Toast.LENGTH_LONG).show();

                    return;
                }

                User user = new User(
                        uid,
                        fullName,
                        email,
                        UserRoles.STUDENT,
                        "",
                        System.currentTimeMillis()
                );

                userRepository.saveUser(user, new UserRepository.UserCallback() {

                    @Override
                    public void onSuccess() {

                        btnRegister.setEnabled(true);

                        Toast.makeText(RegisterActivity.this,
                                "Registration completed successfully!",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this,
                                LoginActivity.class));

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        btnRegister.setEnabled(true);

                        Toast.makeText(RegisterActivity.this,
                                "Failed to save profile: " + message,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(String message) {

                btnRegister.setEnabled(true);

                Toast.makeText(RegisterActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}