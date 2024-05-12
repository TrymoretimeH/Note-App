package com.example.noteapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(user.getUid()).collection("my_notes");
    }

    public static CollectionReference getCollectionReferenceForPasses() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("passwords")
                .document(user.getUid()).collection("my_passwords");
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());
    }

    public static void signOut(Context context) {
        FirebaseAuth.getInstance().signOut();
        showToast(context, "Đã đăng xuất");
    }
}
