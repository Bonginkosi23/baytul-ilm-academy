package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Category;
import com.mabuzagroup.baytulilmacademy.models.Course;
import com.mabuzagroup.baytulilmacademy.repositories.CategoryRepository;
import com.mabuzagroup.baytulilmacademy.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class EditCourseActivity extends AppCompatActivity {

    private Spinner spCategory;
    private EditText etCourseTitle;
    private EditText etCourseDescription;
    private Button btnSaveCourse;

    private CourseRepository courseRepository;
    private CategoryRepository categoryRepository;

    private List<Category> categoryList;

    private String courseId;
    private String categoryId;
    private long createdAt;
    private String thumbnailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        spCategory = findViewById(R.id.spCategory);
        etCourseTitle = findViewById(R.id.etCourseTitle);
        etCourseDescription = findViewById(R.id.etCourseDescription);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);

        courseRepository = new CourseRepository();
        categoryRepository = new CategoryRepository();

        categoryList = new ArrayList<>();

        courseId = getIntent().getStringExtra("courseId");
        categoryId = getIntent().getStringExtra("categoryId");
        createdAt = getIntent().getLongExtra("createdAt", System.currentTimeMillis());
        thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");

        etCourseTitle.setText(getIntent().getStringExtra("courseTitle"));
        etCourseDescription.setText(getIntent().getStringExtra("courseDescription"));

        loadCategories();

        btnSaveCourse.setOnClickListener(v -> updateCourse());
    }

    private void loadCategories() {

        categoryRepository.getCategories(new CategoryRepository.CategoryListCallback() {

            @Override
            public void onSuccess(List<Category> categories) {

                categoryList.clear();

                categoryList.addAll(categories);

                ArrayAdapter<Category> adapter =
                        new ArrayAdapter<>(
                                EditCourseActivity.this,
                                android.R.layout.simple_spinner_item,
                                categoryList
                        );

                adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);

                spCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        EditCourseActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    private void updateCourse() {

        Category selectedCategory = (Category) spCategory.getSelectedItem();

        if (selectedCategory == null) {
            Toast.makeText(this,
                    "Please select a category",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String title = etCourseTitle.getText().toString().trim();
        String description = etCourseDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etCourseTitle.setError("Course title is required");
            etCourseTitle.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etCourseDescription.setError("Course description is required");
            etCourseDescription.requestFocus();
            return;
        }


        Course course = new Course(
                courseId,
                selectedCategory.getId(),
                title,
                description,
                thumbnailUrl == null ? "" : thumbnailUrl,
                true,
                createdAt
        );


        courseRepository.updateCourse(course, new CourseRepository.CourseCallback() {

            @Override
            public void onSuccess() {

                Toast.makeText(
                        EditCourseActivity.this,
                        "Course updated successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        EditCourseActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }
}