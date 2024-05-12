package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.adapter.PasswordAdapter;
import com.example.noteapp.model.Note;
import com.example.noteapp.model.PassNote;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private RecyclerView rvNotes, rvPasswords;
    private ImageButton ibMenu;
    private NoteAdapter adapter;
    private PasswordAdapter pwAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        fabAdd.setOnClickListener(v -> startActivity(
                new Intent(this, NoteDetailsActivity.class)));
        ibMenu.setOnClickListener(v -> showMenu());

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForNotes()
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(options, this);
        rvNotes.setAdapter(adapter);

        Query q = Utility.getCollectionReferenceForPasses()
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<PassNote> opts = new FirestoreRecyclerOptions.Builder<PassNote>()
                .setQuery(q, PassNote.class)
                .build();
        rvPasswords.setLayoutManager(new LinearLayoutManager(this));
        pwAdapter = new PasswordAdapter(opts, this);
        rvPasswords.setAdapter(pwAdapter);
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, ibMenu);
        popupMenu.getMenu().add("Đăng xuất");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("Đăng xuất")) {
                Utility.signOut(this);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void initView() {
        rvNotes = findViewById(R.id.rvNotes);
        rvPasswords = findViewById(R.id.rvPasswords);
        ibMenu = findViewById(R.id.ibMenu);
        fabAdd = findViewById(R.id.fabAdd);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}