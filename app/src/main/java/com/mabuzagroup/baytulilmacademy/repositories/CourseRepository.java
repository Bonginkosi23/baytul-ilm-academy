package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.Course;

import java.util.List;

public class CourseRepository {

    private final FirebaseFirestore firestore;

    public CourseRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface CourseCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public interface CourseListCallback {
        void onSuccess(List<Course> courses);
        void onFailure(String message);
    }

    public String generateCourseId() {
        return firestore.collection(FirestoreConstants.COURSES)
                .document()
                .getId();
    }

    public void saveCourse(Course course, CourseCallback callback) {

        firestore.collection(FirestoreConstants.COURSES)
                .document(course.getId())
                .set(course)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void getCourses(CourseListCallback callback) {

        firestore.collection(FirestoreConstants.COURSES)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<Course> courses =
                            queryDocumentSnapshots.toObjects(Course.class);

                    callback.onSuccess(courses);

                })
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }
}