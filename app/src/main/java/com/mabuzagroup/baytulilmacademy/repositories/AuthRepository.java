package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public interface AuthCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public void registerUser(String email,
                             String password,
                             AuthCallback callback) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException() != null
                                ? task.getException().getMessage()
                                : "Registration failed");
                    }

                });

    }

    public void loginUser(String email,
                          String password,
                          AuthCallback callback) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {

                        callback.onFailure(
                                task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Login failed"
                        );
                    }

                });
    }

    public String getCurrentUserId() {

        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }

        return null;
    }
}