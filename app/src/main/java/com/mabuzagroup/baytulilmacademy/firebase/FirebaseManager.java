package com.mabuzagroup.baytulilmacademy.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseManager {

    private static FirebaseManager instance;
    private final FirebaseAuth auth;

    private FirebaseManager() {
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public void logout() {
        auth.signOut();
    }

    public void login(String email,
                      String password,
                      com.google.android.gms.tasks.OnCompleteListener<AuthResult> listener) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }
}