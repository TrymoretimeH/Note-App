package com.example.noteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.model.Note;
import com.example.noteapp.model.PassNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class PasswordDetailsActivity extends AppCompatActivity {

    private EditText etPassTitle, etPassContent;
    private ImageButton ibSave;
    private TextView tvTitle, tvDelete;
    private String title, content, docId;
    private boolean isEdited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_details);

        etPassTitle = findViewById(R.id.etPassTitle);
        etPassContent = findViewById(R.id.etPassContent);
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
            tvTitle.setText("Sửa mật khẩu");
            etPassTitle.setText(title);
            etPassContent.setText(content);
            tvDelete.setVisibility(View.VISIBLE);

        }

        ibSave.setOnClickListener(v -> savePass());
        tvDelete.setOnClickListener(v -> deletePass());
    }

    private void deletePass() {
        deletePassFromFirebase();
    }

    private void deletePassFromFirebase() {
        DocumentReference documentReference;
        documentReference= Utility.getCollectionReferenceForPasses()
                .document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    note is deleted
                    Utility.showToast(PasswordDetailsActivity.this,
                            "Xóa mật khẩu thành công");
                    finish();
                } else {
                    Utility.showToast(PasswordDetailsActivity.this,
                            "Xóa mật khẩu thất bại");
                }
            }
        });
    }

    private void savePass() {
        String passTitle = etPassTitle.getText().toString().trim();
        String passContent = etPassContent.getText().toString().trim();
        if (passTitle.isEmpty() || passContent.isEmpty()) {
            Utility.showToast(this, "Vui lòng nhập tên ứng dụng và mật khẩu");
            return;
        }
        PassNote pass = new PassNote(passTitle, passContent, Timestamp.now());
        savePassToFirebase(pass);
    }

    private void savePassToFirebase(PassNote note) {
        DocumentReference documentReference;
        if (isEdited) {
//            update note
            documentReference= Utility.getCollectionReferenceForPasses()
                    .document(docId);
        } else {
//            add new note
            documentReference = Utility.getCollectionReferenceForPasses()
                    .document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(PasswordDetailsActivity.this,
                            getMessageSuccessful());
                    finish();
                } else {
                    Utility.showToast(PasswordDetailsActivity.this,
                            "Thêm mật khẩu thất bại");
                }
            }
        });
    }

    private String getMessageSuccessful() {
        if (isEdited) {
            return "Sửa mật khẩu thành công";
        } else {
            return "Thêm mật khẩu thành công";
        }
    }
}