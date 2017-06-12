package com.hongzhiyuanzj.newercm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.hongzhiyuanzj.newercm.application.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.internal.http.HeaderParser;

/**
 * Created by hongzhiyuanzj on 2017/6/5.
 */
public class FileHelper {

    public static final String HEADPHOTO = "headphoto.jpg";

    public static FileHelper instance;
    private Context context;

    private FileHelper(){
        context = MyApplication.getContext();
    }

    public static FileHelper getInstance(){

        if(instance==null){
            instance = new FileHelper();
        }
        return instance;
    }

    public void saveHeadphoto(Bitmap bitmap){
        try {
            FileOutputStream fos = context.openFileOutput(Prefer.getUserId()+HEADPHOTO, Context.MODE_PRIVATE);
            if(bitmap==null){
                Log.e("bitmap","isnull");
                return;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveHeadphoto(Uri uri){
        saveHeadphoto(ratio(uri, 200, 200));
    }

    public Uri getHeadphoto(){
        File root = context.getFilesDir();
        File img = new File(root, Prefer.getUserId()+HEADPHOTO);
        return Uri.fromFile(img);
    }

    public void clearCache(){
        ImagePipeline pipeline = Fresco.getImagePipeline();
        pipeline.evictFromCache(Uri.fromFile(new File(context.getFilesDir(),Prefer.getUserId()+HEADPHOTO)));
    }

    public Bitmap ratio(Uri uri,  int width, int height) {

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(inputStream, null, options);

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int w = options.outWidth;
            int h = options.outHeight;

            int ratio = 1;
            if(w>h){
                ratio = (int)(h*1f/height);
            }else{
                ratio = (int)(w*1f/width);
            }
            if(ratio<1){
                ratio = 1;
            }
            Log.e("ratio", ratio+"");

            options.inSampleSize = ratio;
            options.inJustDecodeBounds = false;

            inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;



    }
}
