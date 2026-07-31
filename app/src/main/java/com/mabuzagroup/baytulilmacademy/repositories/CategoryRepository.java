package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.Category;

public class CategoryRepository {

    private final FirebaseFirestore firestore;

    public CategoryRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface CategoryCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public void saveCategory(Category category,
                             CategoryCallback callback) {

        firestore.collection(FirestoreConstants.CATEGORIES)
                .document(category.getId())
                .set(category)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public String generateCategoryId() {
        return firestore.collection(FirestoreConstants.CATEGORIES)
                .document()
                .getId();
    }

}