package com.example.noteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private TextView tvTitle, tvDelete;
    private String title, content, docId;
    private boolean isEdited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        ibSave = findViewById(R.id.ibSave);
        tvTitle = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);

//        receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()) {
            isEdited = true;
        }

        if (isEdited) {
            tvTitle.setText("Sửa ghi chú");
            etNoteTitle.setText(title);
            etNoteContent.setText(content);
            tvDelete.setVisibility(View.VISIBLE);

        }

        ibSave.setOnClickListener(v -> saveNote());
        tvDelete.setOnClickListener(v -> deleteNote());
    }

    private void deleteNote() {
        deleteNoteFromFirebase();
    }

    private void deleteNoteFromFirebase() {
        DocumentReference documentReference;
        documentReference= Utility.getCollectionReferenceForNotes()
                .document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    note is deleted
                    Utility.showToast(NoteDetailsActivity.this,
                            "Xóa ghi chú thành công");
                    finish();
                } else {
                    Utility.showToast(NoteDetailsActivity.this,
                            "Xóa ghi chú thất bại");
                }
            }
        });
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
        DocumentReference documentReference;
        if (isEdited) {
//            update note
            documentReference= Utility.getCollectionReferenceForNotes()
                    .document(docId);
        } else {
//            add new note
            documentReference = Utility.getCollectionReferenceForNotes()
                    .document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(NoteDetailsActivity.this,
                            getMessageSuccessful());
                    finish();
                } else {
                    Utility.showToast(NoteDetailsActivity.this,
                            "Thêm ghi chú thất bại");
                }
            }
        });
    }

    private String getMessageSuccessful() {
        if (isEdited) {
            return "Sửa ghi chú thành công";
        } else {
            return "Thêm ghi chú thành công";
        }
    }
}