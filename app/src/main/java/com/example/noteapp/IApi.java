package com.example.noteapp;

import java.nio.channels.NonReadableChannelException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IApi {
    @GET("api/Notes")
    Call<List<Note>> getNotes();
    @POST("api/Notes")
    Call<Note> postNote(@Body Note note);
    @DELETE("api/Notes/{id}")
    Call<Note> deleteNote(@Path("id") int id);
}
