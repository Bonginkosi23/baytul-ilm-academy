package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.User;

public class UserRepository {

    private final FirebaseFirestore firestore;

    public UserRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface UserCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public void saveUser(User user, UserCallback callback) {

        firestore.collection(FirestoreConstants.USERS)
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void getUserById(String uid, UserDataCallback callback) {

        firestore.collection(FirestoreConstants.USERS)
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()) {

                        User user = documentSnapshot.toObject(User.class);

                        callback.onSuccess(user);

                    } else {

                        callback.onFailure("User profile not found.");

                    }

                })
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));

    }

    public interface UserDataCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }
}