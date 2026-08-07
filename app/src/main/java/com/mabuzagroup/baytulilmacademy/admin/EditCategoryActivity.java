package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Category;
import com.mabuzagroup.baytulilmacademy.repositories.CategoryRepository;

public class EditCategoryActivity extends AppCompatActivity {

    EditText etCategoryName;
    EditText etCategoryDescription;
    Button btnUpdateCategory;
    private CategoryRepository repository;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        etCategoryName = findViewById(R.id.etCategoryName);
        etCategoryDescription = findViewById(R.id.etDescription);
        btnUpdateCategory = findViewById(R.id.btnSaveCategory);

        String name = getIntent().getStringExtra("categoryName");
        String description = getIntent().getStringExtra("categoryDescription");
        categoryId = getIntent().getStringExtra("categoryId");

        etCategoryName.setText(name);
        etCategoryDescription.setText(description);

        repository = new CategoryRepository();

        btnUpdateCategory.setOnClickListener(v -> updateCategory());
    }

    private void updateCategory() {

        String name =
                etCategoryName.getText().toString().trim();

        String description =
                etCategoryDescription.getText().toString().trim();

        if (name.isEmpty()) {
            etCategoryName.setError("Required");
            return;
        }

        if (description.isEmpty()) {
            etCategoryDescription.setError("Required");
            return;
        }

        Category category = new Category(
                categoryId,
                name,
                description,
                "",
                true
        );

        repository.updateCategory(
                category,
                new CategoryRepository.CategoryCallback() {

                    @Override
                    public void onSuccess() {

                        Toast.makeText(
                                EditCategoryActivity.this,
                                "Category updated successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(
                                EditCategoryActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}