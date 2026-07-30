package com.mabuzagroup.baytulilmacademy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.auth.RegisterActivity;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            }

            finish();

        }, 2000);
    }
}