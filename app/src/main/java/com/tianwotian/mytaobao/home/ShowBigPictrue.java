package com.tianwotian.mytaobao.home;

import com.tianwotian.mytaobaotest.R;
import com.lesogo.cu.custom.ScaleView.HackyViewPager;
import com.zdp.aseo.content.AseoZdpAseo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 显示大图界面
 * @author http://yecaoly.taobao.com
 *
 */
public class ShowBigPictrue extends FragmentActivity {

	private HackyViewPager viewPager;
	private int[] resId={R.drawable.detail_show_1,R.drawable.detail_show_2,R.drawable.detail_show_3,R.drawable.detail_show_4,R.drawable.detail_show_5,R.drawable.detail_show_6};
	/**得到上一个界面点击图片的位置*/
	private int position=0;
	private ArrayList<String> urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_big_pictrue_a);
		Intent intent=getIntent();
		position=intent.getIntExtra("position", 0);
		urls = intent.getStringArrayListExtra("urllist");
//		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		initViewPager();
	}
	
private void initViewPager(){
		
		viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_bigPic);
		ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		//跳转到第几个界面
		viewPager.setCurrentItem(position);
		
	}
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			String url=urls.get(position);
			return new PictrueFragment(url);
		}

		@Override
		public int getCount() {
			return urls.size();
		}

		
	}

}
