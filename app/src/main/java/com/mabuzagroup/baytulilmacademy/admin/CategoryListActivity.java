package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.adapters.CategoryAdapter;
import com.mabuzagroup.baytulilmacademy.models.Category;
import com.mabuzagroup.baytulilmacademy.repositories.CategoryRepository;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView recyclerCategories;

    private CategoryAdapter adapter;

    private List<Category> categoryList;

    private CategoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        recyclerCategories = findViewById(R.id.recyclerCategories);

        FloatingActionButton fabAddCategory =
                findViewById(R.id.fabAddCategory);

        fabAddCategory.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                CategoryListActivity.this,
                                AddCategoryActivity.class
                        ))

        );

        categoryList = new ArrayList<>();

        adapter = new CategoryAdapter(

                categoryList,

                new CategoryAdapter.OnCategoryActionListener() {

                    @Override
                    public void onEdit(Category category) {

                        // We'll open EditCategoryActivity here
                        Intent intent = new Intent(
                                CategoryListActivity.this,
                                EditCategoryActivity.class
                        );

                        intent.putExtra("categoryId", category.getId());
                        intent.putExtra("categoryName", category.getName());
                        intent.putExtra("categoryDescription", category.getDescription());

                        startActivity(intent);

                    }

                    @Override
                    public void onDelete(Category category) {

                        // We'll delete here
                        new AlertDialog.Builder(CategoryListActivity.this)
                                .setTitle("Delete Category")
                                .setMessage("Are you sure you want to delete \"" +
                                        category.getName() + "\"?")
                                .setPositiveButton("Delete", (dialog, which) -> {

                                    repository.deleteCategory(
                                            category.getId(),

                                            new CategoryRepository.CategoryCallback() {

                                                @Override
                                                public void onSuccess() {

                                                    Toast.makeText(
                                                            CategoryListActivity.this,
                                                            "Category deleted successfully",
                                                            Toast.LENGTH_SHORT
                                                    ).show();

                                                    loadCategories();
                                                }

                                                @Override
                                                public void onFailure(String message) {

                                                    Toast.makeText(
                                                            CategoryListActivity.this,
                                                            message,
                                                            Toast.LENGTH_LONG
                                                    ).show();
                                                }
                                            });

                                })

                                .setNegativeButton("Cancel", null)
                                .show();

                    }
                }

        );

        recyclerCategories.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerCategories.setAdapter(adapter);

        repository = new CategoryRepository();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories();   // CategoryListActivity
    }

    private void loadCategories() {

        repository.getCategories(new CategoryRepository.CategoryListCallback() {

            @Override
            public void onSuccess(List<Category> categories) {

                categoryList.clear();

                categoryList.addAll(categories);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        CategoryListActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();

            }
        });

    }

}