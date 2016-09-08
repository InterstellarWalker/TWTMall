package com.tianwotian.tools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tianwotian.mytaobaotest.R;

/**
 * Created by user on 2016/9/8.
 */
public class MyBitmapUtils {
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    public void display(ImageView imageView, String url) {
        // 设置默认图片
//        imageView.setImageResource(R.drawable.pic_item_list_default);


//        Bitmap bitmap = null;
//        if ((bitmap=mMemoryCacheUtils.getMemoryCache(url))!=null){
//            imageView.setImageBitmap(bitmap);
//        } else if ((bitmap = mLocalCacheUtils.getLocalCache(url)) != null) {
//            imageView.setImageBitmap(bitmap);
//        } else {
//            mNetCacheUtils.getBitmapFromNet(imageView, url);
//        }
        // 优先从内存中加载图片, 速度最快, 不浪费流量
        Bitmap bitmap = mMemoryCacheUtils.getMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

            return;
        }

        // 其次从本地(sdcard)加载图片, 速度快, 不浪费流量
        bitmap = mLocalCacheUtils.getLocalCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

            // 写内存缓存
            mMemoryCacheUtils.setMemoryCache(url, bitmap);
            return;
        }

        // 最后从网络下载图片, 速度慢, 浪费流量
        mNetCacheUtils.getBitmapFromNet(imageView, url);
    }

    public Bitmap backBitmap(String url) {
        Bitmap bitmap = null;
        if ((bitmap = mMemoryCacheUtils.getMemoryCache(url)) != null) {
            return bitmap;
        } else if ((bitmap = mLocalCacheUtils.getLocalCache(url)) != null) {
            return bitmap;
        } else {
            return mNetCacheUtils.download(url);
        }
    }
}
