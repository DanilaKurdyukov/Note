package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> notes;
    private Context mContext;
    private LayoutInflater inflater;
    IApi api;
    public NoteAdapter(List<Note> notes, Context mContext) {
        this.notes = notes;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        api = AppData.getClient().create(IApi.class);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item,parent,false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.photo.setImageBitmap(current.getPhotoData());
        holder.description.setText(current.getDescription());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote(current.getId());
            }
        });
    }


    private void deleteNote(int id){
        Call<Note> noteCall = api.deleteNote(id);
        noteCall.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {

            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView description;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.image_view_photo);
            description = itemView.findViewById(R.id.text_view_description);
            delete = itemView.findViewById(R.id.button_delete);
        }
    }
}
