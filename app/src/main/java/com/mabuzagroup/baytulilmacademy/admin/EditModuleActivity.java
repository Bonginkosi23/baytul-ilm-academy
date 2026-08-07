package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Course;
import com.mabuzagroup.baytulilmacademy.models.Module;
import com.mabuzagroup.baytulilmacademy.repositories.CourseRepository;
import com.mabuzagroup.baytulilmacademy.repositories.ModuleRepository;

import java.util.ArrayList;
import java.util.List;

public class EditModuleActivity extends AppCompatActivity {

    private Spinner spCourse;
    private EditText etModuleTitle;
    private EditText etModuleDescription;
    private Button btnSaveModule;

    private CourseRepository courseRepository;
    private ModuleRepository moduleRepository;

    private String moduleId, courseId;
    private long createdAt;

    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);

        spCourse = findViewById(R.id.spCourse);
        etModuleTitle = findViewById(R.id.etModuleTitle);
        etModuleDescription = findViewById(R.id.etModuleDescription);
        btnSaveModule = findViewById(R.id.btnSaveModule);

        courseRepository = new CourseRepository();
        moduleRepository = new ModuleRepository();

        courseList = new ArrayList<>();

        moduleId = getIntent().getStringExtra("moduleId");
        courseId = getIntent().getStringExtra("courseId");
        createdAt = getIntent().getLongExtra(
                "createdAt",
                System.currentTimeMillis()
        );

        etModuleTitle.setText(
                getIntent().getStringExtra("moduleTitle")
        );

        etModuleDescription.setText(
                getIntent().getStringExtra("moduleDescription")
        );

        loadCourses();

        btnSaveModule.setOnClickListener(v -> updateModule());
    }

    private void loadCourses() {

        courseRepository.getCourses(new CourseRepository.CourseListCallback() {

            @Override
            public void onSuccess(List<Course> courses) {

                courseList.clear();
                courseList.addAll(courses);

                ArrayAdapter<Course> adapter =
                        new ArrayAdapter<>(
                                EditModuleActivity.this,
                                android.R.layout.simple_spinner_item,
                                courseList
                        );

                adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);

                spCourse.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        EditModuleActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void updateModule() {

        Course selectedCourse = (Course) spCourse.getSelectedItem();

        if (selectedCourse == null) {
            Toast.makeText(this,
                    "Please select a course",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String title = etModuleTitle.getText().toString().trim();
        String description = etModuleDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etModuleTitle.setError("Module title is required");
            return;
        }

        if (description.isEmpty()) {
            etModuleDescription.setError("Module description is required");
            return;
        }


        Module module = new Module(
                moduleId,
                selectedCourse.getId(),
                title,
                description,
                true,
                createdAt
        );

        moduleRepository.updateModule(module,
                new ModuleRepository.ModuleCallback() {

                    @Override
                    public void onSuccess() {

                        Toast.makeText(
                                EditModuleActivity.this,
                                "Module updated successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(
                                EditModuleActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}