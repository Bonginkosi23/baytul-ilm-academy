package com.mabuzagroup.baytulilmacademy.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.adapters.ModuleAdapter;
import com.mabuzagroup.baytulilmacademy.models.Module;
import com.mabuzagroup.baytulilmacademy.repositories.ModuleRepository;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ModuleListActivity extends AppCompatActivity {

    private RecyclerView recyclerModules;
    private ModuleAdapter adapter;
    private List<Module> moduleList;
    private ModuleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        recyclerModules = findViewById(R.id.recyclerModules);

        FloatingActionButton fabAddModule =
                findViewById(R.id.fabAddModule);

        fabAddModule.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                ModuleListActivity.this,
                                AddModuleActivity.class
                        ))

        );

        moduleList = new ArrayList<>();
        adapter = new ModuleAdapter(moduleList);

        recyclerModules.setLayoutManager(new LinearLayoutManager(this));
        recyclerModules.setAdapter(adapter);

        repository = new ModuleRepository();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadModules();
    }

    private void loadModules() {

        repository.getModules(new ModuleRepository.ModuleListCallback() {

            @Override
            public void onSuccess(List<Module> modules) {

                moduleList.clear();
                moduleList.addAll(modules);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

                Toast.makeText(
                        ModuleListActivity.this,
                        message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}