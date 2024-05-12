package com.example.noteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.NoteDetailsActivity;
import com.example.noteapp.R;
import com.example.noteapp.Utility;
import com.example.noteapp.model.Note;
import com.example.noteapp.model.PassNote;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PasswordAdapter extends FirestoreRecyclerAdapter<PassNote, PasswordAdapter.PasswordViewHolder> {
    private Context context;

    public PasswordAdapter(@NonNull FirestoreRecyclerOptions<PassNote> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PasswordViewHolder holder, int position, @NonNull PassNote note) {
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteContent.setText(note.getPass());
        holder.tvNoteTimestamp.setText(Utility.timestampToString(note.getTimestamp()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getPass());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_note_item,
                parent, false);

        return new PasswordViewHolder(view);
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoteTitle, tvNoteContent, tvNoteTimestamp;
        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            tvNoteTimestamp = itemView.findViewById(R.id.tvNoteTimestamp);
        }
    }
}
