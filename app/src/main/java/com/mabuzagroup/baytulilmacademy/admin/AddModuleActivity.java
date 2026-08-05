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

public class AddModuleActivity extends AppCompatActivity {

    private Spinner spCourse;
    private EditText etModuleTitle;
    private EditText etModuleDescription;
    private Button btnSaveModule;

    private CourseRepository courseRepository;
    private ModuleRepository moduleRepository;

    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        spCourse = findViewById(R.id.spCourse);
        etModuleTitle = findViewById(R.id.etModuleTitle);
        etModuleDescription = findViewById(R.id.etModuleDescription);
        btnSaveModule = findViewById(R.id.btnSaveModule);

        courseRepository = new CourseRepository();
        moduleRepository = new ModuleRepository();

        courseList = new ArrayList<>();

        loadCourses();

        btnSaveModule.setOnClickListener(v -> saveModule());
    }

    private void loadCourses() {

        courseRepository.getCourses(new CourseRepository.CourseListCallback() {

            @Override
            public void onSuccess(List<Course> courses) {

                courseList.clear();
                courseList.addAll(courses);

                ArrayAdapter<Course> adapter =
                        new ArrayAdapter<>(
                                AddModuleActivity.this,
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
                        AddModuleActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void saveModule() {

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

        String moduleId = moduleRepository.generateModuleId();

        Module module = new Module(
                moduleId,
                selectedCourse.getId(),
                title,
                description,
                true,
                System.currentTimeMillis()
        );

        moduleRepository.saveModule(module,
                new ModuleRepository.ModuleCallback() {

                    @Override
                    public void onSuccess() {

                        Toast.makeText(
                                AddModuleActivity.this,
                                "Module saved successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(
                                AddModuleActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}