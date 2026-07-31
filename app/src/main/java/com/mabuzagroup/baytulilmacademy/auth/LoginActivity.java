package com.mabuzagroup.baytulilmacademy.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.activities.MainActivity;

import com.mabuzagroup.baytulilmacademy.constants.UserRoles;
import com.mabuzagroup.baytulilmacademy.models.User;
import com.mabuzagroup.baytulilmacademy.repositories.AuthRepository;
import com.mabuzagroup.baytulilmacademy.repositories.UserRepository;
import com.mabuzagroup.baytulilmacademy.student.StudentHomeActivity;
import com.mabuzagroup.baytulilmacademy.admin.AdminDashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        authRepository = new AuthRepository();
        userRepository = new UserRepository();

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        authRepository.loginUser(email, password, new AuthRepository.AuthCallback() {

            @Override
            public void onSuccess() {

                String uid = authRepository.getCurrentUserId();

                if (uid == null) {

                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(LoginActivity.this,
                            "User not found.",
                            Toast.LENGTH_LONG).show();

                    return;
                }

                userRepository.getUserById(uid, new UserRepository.UserDataCallback() {

                    @Override
                    public void onSuccess(User user) {

                        progressBar.setVisibility(View.GONE);

                        if (user == null) {

                            Toast.makeText(LoginActivity.this, "Opening Login", Toast.LENGTH_SHORT).show();

                            Toast.makeText(LoginActivity.this,
                                    "User profile not found.",
                                    Toast.LENGTH_LONG).show();

                            return;
                        }

                        if (UserRoles.ADMIN.equals(user.getRole())) {
                            Toast.makeText(LoginActivity.this, "Opening Admin Dashboard", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this,
                                    AdminDashboardActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, "Opening Student Home", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this,
                                    StudentHomeActivity.class));
                        }

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(LoginActivity.this,
                                message,
                                Toast.LENGTH_LONG).show();
                    }

                });

            }

            @Override
            public void onFailure(String message) {

                progressBar.setVisibility(View.GONE);

                Toast.makeText(LoginActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();

            }

        });

    }
}