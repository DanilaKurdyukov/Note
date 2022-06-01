package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RecyclerView notesView;
NoteAdapter adapter;
List<Note> notes = new ArrayList<>();
IApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesView = findViewById(R.id.recycler_view_notes);
        api = AppData.getClient().create(IApi.class);
        getNotes();
        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddEditActivity.class));
            }
        });
    }
    private void getNotes(){

        Call<List<Note>> noteCall = api.getNotes();
        noteCall.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful()){
                    notes = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new NoteAdapter(notes,getApplicationContext());
                            notesView.setAdapter(adapter);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                call.cancel();
            }
        });
    }
}