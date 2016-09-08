package com.tianwotian.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 2016/9/8.
 */
public class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
    String url;
    private InputStream inputStream;

    private Bitmap bitmap;
    ImageView imageView;
    Context context;
    public LoadImageTask(Context context,String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
        this.context=context;
    }

    @Override
    protected Bitmap doInBackground(Void... arg0) {
        Log.d("url", url);
        try {
            Log.d("url2", url);
            inputStream = LoadImageFromServer.getImageViewInputStream(url);
            Log.e("input", "inputStream");
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap = BitmapFactory.decodeStream(inputStream);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            bitmapUtils.display(imageView,url);
            Log.d("wrong", "图片未加载");
        }
    }
}
