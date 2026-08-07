package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.Lesson;

import java.util.List;

public class LessonRepository {

    private final FirebaseFirestore firestore;

    public LessonRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface LessonCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public interface LessonListCallback {
        void onSuccess(List<Lesson> lessons);
        void onFailure(String message);
    }

    public String generateLessonId() {
        return firestore.collection(FirestoreConstants.LESSONS)
                .document()
                .getId();
    }

    public void saveLesson(Lesson lesson, LessonCallback callback) {

        firestore.collection(FirestoreConstants.LESSONS)
                .document(lesson.getId())
                .set(lesson)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void updateLesson(Lesson lesson,
                             LessonCallback callback) {

        firestore.collection(FirestoreConstants.LESSONS)
                .document(lesson.getId())
                .set(lesson)
                .addOnSuccessListener(unused ->
                        callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void deleteLesson(String lessonId,
                             LessonCallback callback) {

        firestore.collection(FirestoreConstants.LESSONS)
                .document(lessonId)
                .delete()
                .addOnSuccessListener(unused ->
                        callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void getLessons(LessonListCallback callback) {

        firestore.collection(FirestoreConstants.LESSONS)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<Lesson> lessons =
                            queryDocumentSnapshots.toObjects(Lesson.class);

                    callback.onSuccess(lessons);

                })
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }
}