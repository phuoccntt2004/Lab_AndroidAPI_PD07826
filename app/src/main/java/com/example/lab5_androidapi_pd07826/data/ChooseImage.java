package com.example.lab5_androidapi_pd07826.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lab5_androidapi_pd07826.R;
import com.example.lab5_androidapi_pd07826.Register;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChooseImage {

    private Context context;

    public ChooseImage( Context context) {
        this.context = context;
    }

    public void getChooseImage(ActivityResultLauncher<Intent> getImage) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getImage.launch(intent);
    }

    public File createFileFromUri(Uri path, String name){
        File _file = new File(context.getCacheDir(),name+".png");
        try {
            InputStream in  = context.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
            return _file;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void chooseImages(ActivityResultLauncher<Intent> getImage){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow multiple selections
        intent.setAction(Intent.ACTION_PICK);
        getImage.launch(intent);
    }
}
