package com.mabuzagroup.baytulilmacademy.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.auth.LoginActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);

        MaterialCardView cardLogout = findViewById(R.id.cardLogout);

        MaterialCardView cardViewCategories =
                findViewById(R.id.cardCategories);

        cardViewCategories.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                AdminDashboardActivity.this,
                                CategoryListActivity.class
                        ))

        );

        MaterialCardView cardCourses =
                findViewById(R.id.cardCourses);

        cardCourses.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                AdminDashboardActivity.this,
                                AddCourseActivity.class
                        ))

        );

        cardLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });
    }
}