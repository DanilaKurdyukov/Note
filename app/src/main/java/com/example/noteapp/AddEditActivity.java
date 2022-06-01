package com.example.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {
ImageView photo;
TextView description;
Uri imageUri;
IApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        photo=findViewById(R.id.image_view_loadPhoto);
        description = findViewById(R.id.text_view_descriptionWrite);
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNote();
            }
        });
        findViewById(R.id.button_addPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDilaog();
            }
        });
        api=AppData.getClient().create(IApi.class);
    }


    private void postNote(){
        Note note = new Note(
                description.getText().toString(),
                null
        );
        Call<Note> noteCall = api.postNote(note);
        noteCall.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Данные добавлены!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                call.cancel();
            }
        });
    }


    private void imagePickDilaog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditActivity.this);
        String[] options = {"Камера","Галерея"};
        builder.setTitle("Выберите способ получения изображения");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    pickFromCamera();
                }
                else pickFromStorage();
            }
        });
        builder.create().show();
    }

    private void pickFromStorage() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,101);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Название изображения");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Описание изображения");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1){
            switch (requestCode){
                case 100:
                    photo.setImageURI(imageUri);
                    break;
                case 101:
                    imageUri=data.getData();
                    photo.setImageURI(imageUri);
                    break;
            }
            photo.buildDrawingCache();
        }
    }
}