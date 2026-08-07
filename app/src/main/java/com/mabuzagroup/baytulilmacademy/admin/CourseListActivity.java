package com.mabuzagroup.baytulilmacademy.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

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

        adapter = new CourseAdapter(

                courseList,

                new CourseAdapter.OnCourseActionListener() {

                    @Override
                    public void onEdit(Course course) {

                        Intent intent = new Intent(
                                CourseListActivity.this,
                                EditCourseActivity.class
                        );

                        intent.putExtra("courseId", course.getId());
                        intent.putExtra("categoryId", course.getCategoryId());
                        intent.putExtra("courseTitle", course.getTitle());
                        intent.putExtra("courseDescription", course.getDescription());
                        intent.putExtra("thumbnailUrl", course.getThumbnailUrl());
                        intent.putExtra("createdAt", course.getCreatedAt());

                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(Course course) {

                        new AlertDialog.Builder(CourseListActivity.this)
                                .setTitle("Delete Course")
                                .setMessage("Are you sure you want to delete \"" +
                                        course.getTitle() + "\"?")
                                .setPositiveButton("Delete", (dialog, which) ->

                                        repository.deleteCourse(
                                                course.getId(),

                                                new CourseRepository.CourseCallback() {

                                                    @Override
                                                    public void onSuccess() {

                                                        Toast.makeText(
                                                                CourseListActivity.this,
                                                                "Course deleted successfully!",
                                                                Toast.LENGTH_SHORT
                                                        ).show();

                                                        loadCourses();
                                                    }

                                                    @Override
                                                    public void onFailure(String message) {

                                                        Toast.makeText(
                                                                CourseListActivity.this,
                                                                message,
                                                                Toast.LENGTH_LONG
                                                        ).show();
                                                    }
                                                })

                                )

                                .setNegativeButton("Cancel", null)
                                .show();
                    }
                }

        );

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