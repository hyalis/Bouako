package com.m2dl.helloandroid.apnview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public abstract class ImageSender {


    public Context context;
    public Bitmap image;
    public String imageType;

    public ImageSender(Context c, Bitmap i, String type)
    {
        context = c;
        image = i;
        imageType = type;
    }

    public abstract void sendImage();

    public File savebitmap(Bitmap image) {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        Random r = new Random();
        String fileName = "imageTemp";

        File file = new File(extStorageDirectory, fileName + ".png");

        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, fileName + ".png");
        }

        try {
            outStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }
}
