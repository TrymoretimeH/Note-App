package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, NoteDetailsActivity.class)));
    }

    private void initView() {
        fabAdd = findViewById(R.id.fabAdd);
    }
}