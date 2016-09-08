package com.tianwotian.mytaobao.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tianwotian.mytaobaotest.R;


/**
 * 自定义一个带图片的吐司
 * Created by venus on 2016/9/1.
 */
public class ImageToast extends Toast {

    private static View v;

    public ImageToast(Context context) {
        super(context);
    }
    public static ImageToast makeImage(Context context, int imgId, int duration, String text){
        ImageToast imageToast = new ImageToast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.image_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_image);
        tv.setBackgroundResource(imgId);
        tv.setText(text);
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        imageToast.setView(v);
        imageToast.setDuration(duration);
        imageToast.setGravity(Gravity.CENTER,0,0);
        return imageToast;
    }
}
