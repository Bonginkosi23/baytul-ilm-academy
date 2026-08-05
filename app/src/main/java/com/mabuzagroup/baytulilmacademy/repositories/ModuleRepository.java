package com.mabuzagroup.baytulilmacademy.repositories;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mabuzagroup.baytulilmacademy.constants.FirestoreConstants;
import com.mabuzagroup.baytulilmacademy.models.Module;

import java.util.List;

public class ModuleRepository {

    private final FirebaseFirestore firestore;

    public ModuleRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface ModuleCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public interface ModuleListCallback {
        void onSuccess(List<Module> modules);
        void onFailure(String message);
    }

    public String generateModuleId() {
        return firestore.collection(FirestoreConstants.MODULES)
                .document()
                .getId();
    }

    public void saveModule(Module module, ModuleCallback callback) {

        firestore.collection(FirestoreConstants.MODULES)
                .document(module.getId())
                .set(module)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }

    public void getModules(ModuleListCallback callback) {

        firestore.collection(FirestoreConstants.MODULES)
                .whereEqualTo("active", true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<Module> modules =
                            queryDocumentSnapshots.toObjects(Module.class);

                    callback.onSuccess(modules);

                })
                .addOnFailureListener(e ->
                        callback.onFailure(e.getMessage()));
    }
}