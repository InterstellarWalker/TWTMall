package com.tianwotian.mytaobao.home;

import com.bumptech.glide.Glide;
import com.tianwotian.mytaobaotest.R;
import com.tianwotian.tools.LocalCacheUtils;
import com.tianwotian.tools.MemoryCacheUtils;
import com.tianwotian.tools.MyBitmapUtils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * 显示大图的实现，并且可以放大缩小
 *
 * @author http://yecaoly.taobao.com
 */
@SuppressLint("ValidFragment")
public class PictrueFragment extends Fragment {

	String url;

	@SuppressLint("ValidFragment")

	public PictrueFragment(String url) {

		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.scale_pic_item, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		ImageView imageView = (ImageView) view.findViewById(R.id.scale_pic_item);
//		LocalCacheUtils localCacheUtils = new LocalCacheUtils();
//		MemoryCacheUtils memoryCacheUtils = new MemoryCacheUtils();
		MyBitmapUtils myBitmapUtils = new MyBitmapUtils();
		myBitmapUtils.display(imageView,url);

	}


}
