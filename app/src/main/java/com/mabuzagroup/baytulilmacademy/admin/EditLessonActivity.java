package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Lesson;
import com.mabuzagroup.baytulilmacademy.models.Module;
import com.mabuzagroup.baytulilmacademy.repositories.LessonRepository;
import com.mabuzagroup.baytulilmacademy.repositories.ModuleRepository;

import java.util.ArrayList;
import java.util.List;

public class EditLessonActivity extends AppCompatActivity {

    Spinner spModule;
    private EditText etLessonTitle;
    private EditText etLessonDescription;
    private EditText etYoutubeUrl;
    private Button btnSaveLesson;

    private LessonRepository lessonRepository;
    private ModuleRepository moduleRepository;
    private List<Module> moduleList;

    private String lessonId;
    private String moduleId;
    private String youtubeUrl;
    private long createdAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_lesson);

        spModule = findViewById(R.id.spModule);
        etLessonTitle = findViewById(R.id.etLessonTitle);
        etLessonDescription = findViewById(R.id.etLessonDescription);
        etYoutubeUrl = findViewById(R.id.etYoutubeUrl);
        btnSaveLesson = findViewById(R.id.btnSaveLesson);

        lessonRepository = new LessonRepository();
        moduleRepository = new ModuleRepository();

        moduleList = new ArrayList<>();

        lessonId = getIntent().getStringExtra("lessonId");
        moduleId = getIntent().getStringExtra("moduleId");
        youtubeUrl = getIntent().getStringExtra("youtubeUrl");
        createdAt = getIntent().getLongExtra(
                "createdAt",
                System.currentTimeMillis()
        );

        etLessonTitle.setText(
                getIntent().getStringExtra("lessonTitle")
        );

        etLessonDescription.setText(
                getIntent().getStringExtra("lessonDescription")
        );

// If you have an EditText for YouTube URL
        etYoutubeUrl.setText(
                getIntent().getStringExtra("youtubeUrl")
        );

        loadModules();

        btnSaveLesson.setOnClickListener(v -> updateLesson());
    }

    private String extractYoutubeVideoId(String input) {

        input = input.trim();

        if (input.contains("watch?v=")) {
            return input.substring(input.indexOf("watch?v=") + 8).split("&")[0];
        }

        if (input.contains("youtu.be/")) {
            return input.substring(input.lastIndexOf("/") + 1);
        }

        return input;
    }

    private void updateLesson() {

        Module selectedModule =
                (Module) spModule.getSelectedItem();

        if (selectedModule == null) {
            Toast.makeText(this,
                    "Please select a module",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String title = etLessonTitle.getText().toString().trim();
        String description = etLessonDescription.getText().toString().trim();
        String youtubeId =
                extractYoutubeVideoId(
                        etYoutubeUrl.getText().toString());

        if (title.isEmpty()) {
            etLessonTitle.setError("Lesson title is required");
            return;
        }

        if (description.isEmpty()) {
            etLessonDescription.setError("Lesson description is required");
            return;
        }


        Lesson lesson = new Lesson(
                lessonId,
                selectedModule.getId(),
                title,
                description,
                youtubeUrl,
                true,
                createdAt
        );

        lessonRepository.updateLesson(
                lesson,
                new LessonRepository.LessonCallback() {

                    @Override
                    public void onSuccess() {

                        Toast.makeText(
                                EditLessonActivity.this,
                                "Lesson updated successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }

                    @Override
                    public void onFailure(String message) {

                        Toast.makeText(
                                EditLessonActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void loadModules() {

        moduleRepository.getModules(new ModuleRepository.ModuleListCallback() {

            @Override
            public void onSuccess(List<Module> modules) {

                moduleList.clear();
                moduleList.addAll(modules);

                ArrayAdapter<Module> adapter =
                        new ArrayAdapter<>(
                                EditLessonActivity.this,
                                android.R.layout.simple_spinner_item,
                                moduleList
                        );

                adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);

                spModule.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        EditLessonActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}