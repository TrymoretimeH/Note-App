package com.example.noteapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noteapp.model.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteContent;
    private ImageButton ibSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        ibSave = findViewById(R.id.ibSave);

        ibSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteTitle = etNoteTitle.getText().toString().trim();
        String noteContent = etNoteContent.getText().toString().trim();
        if (noteTitle.isEmpty() || noteContent.isEmpty()) {
            Utility.showToast(this, "Vui lòng nhập tiêu đề và nội dung");
            return;
        }
        Note note = new Note(noteTitle, noteContent, Timestamp.now());
        saveNoteToFirebase(note);
    }

    private void saveNoteToFirebase(Note note) {
        DocumentReference documentReference = Utility.getCollectionReferenceForNotes().document();

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(NoteDetailsActivity.this,
                            "Thêm ghi chú thành công");
                    finish();
                } else {
                    Utility.showToast(NoteDetailsActivity.this,
                            "Thêm ghi chú thất bại");
                }
            }
        });
    }
}