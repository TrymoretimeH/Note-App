package com.example.noteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    private Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteContent.setText(note.getContent());
        holder.tvNoteTimestamp.setText(Utility.timestampToString(note.getTimestamp()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_note_item,
                parent, false);

        return new NoteViewHolder(view);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoteTitle, tvNoteContent, tvNoteTimestamp;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            tvNoteTimestamp = itemView.findViewById(R.id.tvNoteTimestamp);
        }
    }
}
