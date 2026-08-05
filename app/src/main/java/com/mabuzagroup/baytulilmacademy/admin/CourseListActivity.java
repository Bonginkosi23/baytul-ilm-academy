package com.mabuzagroup.baytulilmacademy.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.adapters.CourseAdapter;
import com.mabuzagroup.baytulilmacademy.models.Course;
import com.mabuzagroup.baytulilmacademy.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView recyclerCourses;

    private CourseAdapter adapter;

    private List<Course> courseList;

    private CourseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        recyclerCourses = findViewById(R.id.recyclerCourses);

        FloatingActionButton fabAddCourse =
                findViewById(R.id.fabAddCourse);

        fabAddCourse.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                CourseListActivity.this,
                                AddCourseActivity.class
                        ))

        );

        courseList = new ArrayList<>();

        adapter = new CourseAdapter(courseList);

        recyclerCourses.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerCourses.setAdapter(adapter);

        repository = new CourseRepository();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();      // CourseListActivity
    }

    private void loadCourses() {

        repository.getCourses(new CourseRepository.CourseListCallback() {

            @Override
            public void onSuccess(List<Course> courses) {

                courseList.clear();

                courseList.addAll(courses);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        CourseListActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}