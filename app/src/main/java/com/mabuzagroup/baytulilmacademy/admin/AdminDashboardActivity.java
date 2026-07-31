package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mabuzagroup.baytulilmacademy.R;

import android.content.Intent;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;

import android.content.Intent;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);

        MaterialCardView cardCategories = findViewById(R.id.cardCategories);
        MaterialCardView cardLogout = findViewById(R.id.cardLogout);

        cardCategories.setOnClickListener(v ->
                startActivity(new Intent(this, AddCategoryActivity.class)));

        cardLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });
    }
}