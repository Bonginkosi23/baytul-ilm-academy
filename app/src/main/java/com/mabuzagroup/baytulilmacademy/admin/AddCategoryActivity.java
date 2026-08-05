package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Category;
import com.mabuzagroup.baytulilmacademy.repositories.CategoryRepository;

public class AddCategoryActivity extends AppCompatActivity {

    private TextInputEditText etCategoryName;
    private TextInputEditText etDescription;
    private MaterialButton btnSaveCategory;
    private ProgressBar progressBar;

    private CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);

        categoryRepository = new CategoryRepository();

        etCategoryName = findViewById(R.id.etCategoryName);
        etDescription = findViewById(R.id.etDescription);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        progressBar = findViewById(R.id.progressBar);

        btnSaveCategory.setOnClickListener(v -> saveCategory());
    }

    private void saveCategory() {

        String name = etCategoryName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (name.isEmpty()) {
            etCategoryName.setError("Enter category name");
            etCategoryName.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter description");
            etDescription.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnSaveCategory.setEnabled(false);

        String id = categoryRepository.generateCategoryId();

        Category category = new Category(
                id,
                name,
                description,
                "",
                true
        );

        categoryRepository.saveCategory(category,
                new CategoryRepository.CategoryCallback() {

                    @Override
                    public void onSuccess() {

                        progressBar.setVisibility(View.GONE);
                        btnSaveCategory.setEnabled(true);

                        Toast.makeText(AddCategoryActivity.this,
                                "Category saved successfully!",
                                Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        progressBar.setVisibility(View.GONE);
                        btnSaveCategory.setEnabled(true);

                        Toast.makeText(AddCategoryActivity.this,
                                message,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}