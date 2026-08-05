package com.mabuzagroup.baytulilmacademy.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.adapters.LessonAdapter;
import com.mabuzagroup.baytulilmacademy.models.Lesson;
import com.mabuzagroup.baytulilmacademy.repositories.LessonRepository;

import java.util.ArrayList;
import java.util.List;

public class LessonListActivity extends AppCompatActivity {

    private RecyclerView recyclerLessons;
    private LessonAdapter adapter;
    private List<Lesson> lessonList;
    private LessonRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        recyclerLessons = findViewById(R.id.recyclerLessons);

        FloatingActionButton fabAddLesson =
                findViewById(R.id.fabAddLesson);

        fabAddLesson.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                LessonListActivity.this,
                                AddLessonActivity.class
                        ))

        );

        lessonList = new ArrayList<>();

        adapter = new LessonAdapter(lessonList);

        recyclerLessons.setLayoutManager(new LinearLayoutManager(this));
        recyclerLessons.setAdapter(adapter);

        repository = new LessonRepository();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLessons();      // LessonListActivity
    }

    private void loadLessons() {

        repository.getLessons(new LessonRepository.LessonListCallback() {

            @Override
            public void onSuccess(List<Lesson> lessons) {

                lessonList.clear();
                lessonList.addAll(lessons);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        LessonListActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}