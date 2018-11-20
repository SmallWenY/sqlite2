package com.nuaa233.sqlite.Usr_Info;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.io.File;
import java.io.IOException;

public class ChangeUsrIconActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id = new String("test");

    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    private Button takePhoto, chooseAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_usr_icon);
        initView();
        initData();
        onClick();
    }

    private void initView() {
        takePhoto = (Button) findViewById(R.id.take_photo);
        chooseAlbum = (Button) findViewById(R.id.choose_from_album);
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");

    }

    private void onClick() {
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ChangeUsrIconActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
    }
}
