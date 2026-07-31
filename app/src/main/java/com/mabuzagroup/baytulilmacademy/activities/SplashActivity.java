package com.mabuzagroup.baytulilmacademy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.auth.RegisterActivity;

import com.mabuzagroup.baytulilmacademy.admin.AdminDashboardActivity;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;
import com.mabuzagroup.baytulilmacademy.constants.UserRoles;
import com.mabuzagroup.baytulilmacademy.models.User;
import com.mabuzagroup.baytulilmacademy.repositories.AuthRepository;
import com.mabuzagroup.baytulilmacademy.repositories.UserRepository;
import com.mabuzagroup.baytulilmacademy.student.StudentHomeActivity;

public class SplashActivity extends AppCompatActivity {

    private AuthRepository authRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        authRepository = new AuthRepository();
        userRepository = new UserRepository();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            String uid = authRepository.getCurrentUserId();

            if (uid == null) {
                Toast.makeText(this, "Opening Login", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(
                        SplashActivity.this,
                        LoginActivity.class));

                finish();
                return;
            }

            userRepository.getUserById(uid,
                    new UserRepository.UserDataCallback() {

                        @Override
                        public void onSuccess(User user) {

                            if (user == null) {
                                Toast.makeText(SplashActivity.this, "Opening Login", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(
                                        SplashActivity.this,
                                        LoginActivity.class));

                                finish();
                                return;
                            }

                            if (UserRoles.ADMIN.equals(user.getRole())) {
                                Toast.makeText(SplashActivity.this, "Opening Admin Dashboard", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(
                                        SplashActivity.this,
                                        AdminDashboardActivity.class));

                            } else {
                                Toast.makeText(SplashActivity.this, "Opening Student Home", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(
                                        SplashActivity.this,
                                        StudentHomeActivity.class));
                            }

                            finish();
                        }

                        @Override
                        public void onFailure(String message) {

                            startActivity(new Intent(
                                    SplashActivity.this,
                                    LoginActivity.class));

                            finish();
                        }
                    });

        }, 2000);
    }
}