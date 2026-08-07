package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.Category;

import java.util.List;

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

    public interface CategoryListCallback {
        void onSuccess(List<Category> categories);
        void onFailure(String message);
    }

    public void getCategories(CategoryListCallback callback) {

        firestore.collection(FirestoreConstants.CATEGORIES)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<Category> categories = queryDocumentSnapshots.toObjects(Category.class);

                    callback.onSuccess(categories);

                })
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void updateCategory(Category category,
                               CategoryCallback callback) {

        firestore.collection(FirestoreConstants.CATEGORIES)
                .document(category.getId())
                .set(category)
                .addOnSuccessListener(unused ->
                        callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void deleteCategory(String categoryId,
                               CategoryCallback callback) {

        firestore.collection(FirestoreConstants.CATEGORIES)
                .document(categoryId)
                .delete()
                .addOnSuccessListener(unused ->
                        callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

}