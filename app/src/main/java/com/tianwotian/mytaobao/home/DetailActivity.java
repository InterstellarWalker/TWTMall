package com.tianwotian.mytaobao.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lesogo.cu.custom.ScaleView.HackyViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.tianwotian.MyView.BabyPopWindow;
import com.tianwotian.MyView.BabyPopWindow.OnItemClickListener;
import com.tianwotian.http.GetHttp;
import com.tianwotian.mytaobao.bean.KeyProperty;
import com.tianwotian.mytaobao.bean.SearchItemBean;
import com.tianwotian.mytaobao.view.ImageToast;
import com.tianwotian.mytaobaotest.R;
import com.tianwotian.tools.LoadImageFromServer;
import com.tianwotian.tools.LocalCacheUtils;
import com.tianwotian.tools.MemoryCacheUtils;
import com.tianwotian.tools.MyBitmapUtils;
import com.tianwotian.tools.NetCacheUtils;
import com.tianwotian.tools.RegexUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 单个商品详情界面
 *
 * @author 袁硕
 */
public class DetailActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {
    @BindView(R.id.online_service)
    TextView onlineService;
    @BindView(R.id.put_in_cart)
    TextView putInCart;
    @BindView(R.id.buy_now)
    TextView buyNow;
    @BindView(R.id.original_price)
    TextView originalPrice;
    @BindView(R.id.comm_name)
    TextView commName;
    @BindView(R.id.present_price)
    TextView presentPrice;
    @BindView(R.id.tv_credit)
    TextView tvCredit;
    @BindView(R.id.iv_baby_collection)
    ImageView ivBabyCollection;
    @BindView(R.id.choose_property)
    LinearLayout chooseProperty;

//	NfcAdapter nfcAdapter;

    private HackyViewPager viewPager;
    private ArrayList<View> allListView;
    private int[] resId = {R.drawable.detail_show_1, R.drawable.detail_show_2, R.drawable.detail_show_3, R.drawable.detail_show_4, R.drawable.detail_show_5, R.drawable.detail_show_6};
    private ListView listView;
    private ImageView iv_baby_collection;
    /**
     * 弹出商品订单信息详情
     */
    private BabyPopWindow popWindow;
    /**
     * 用于设置背景暗淡
     */
    private LinearLayout all_choice_layout = null;
    /**
     * 判断是否点击的立即购买按钮
     */
    boolean isClickBuy = false;
    /**
     * 是否添加收藏
     */
    private static boolean isCollection = false;
    /**
     * ViewPager当前显示页的下标
     */
    private int position = 0;
    private String keys;
    private String propertyUrl;
    private String propertyJson;
    private KeyProperty kp;
    Bitmap bitmap;
    private String c_name;
    private SearchItemBean.SearchItemList.TableBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_detail);
        ButterKnife.bind(this);
        //得到保存的收藏信息
        getSaveCollection();

        initView();
    }

    @SuppressLint("NewApi")
    private void initView() {
        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(this);
        putInCart.setOnClickListener(this);
        buyNow.setOnClickListener(this);
        originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //收藏
        iv_baby_collection = (ImageView) findViewById(R.id.iv_baby_collection);
        iv_baby_collection.setOnClickListener(this);
        all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
        //获取商品的具体信息
        Intent intent = getIntent();
        c_name = intent.getStringExtra("C_Name");
        String description = intent.getStringExtra("Description");
        Log.d("nima", description);
        bean = intent.getParcelableExtra("bean");
        commName.setText(c_name);
        presentPrice.setText(intent.getStringExtra("C_Price"));
        originalPrice.setText("原价" + intent.getStringExtra("C_Original_Price"));
        tvCredit.setText("可获积分：" + intent.getStringExtra("C_Integral"));
        keys = intent.getStringExtra("C_Keys");
        propertyUrl = "http://api.tianwotian.com/api/FindPropertyInformation.aspx?C_Keys=" + keys;
        getProperty(propertyUrl);
        listView = (ListView) findViewById(R.id.listView_Detail);
        listView.setFocusable(false);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        initViewPager();

        if (isCollection) {
            //如果已经收藏，则显示收藏后的效果
            iv_baby_collection.setImageResource(R.drawable.second_2_collection);
        }
    }

    //      网络请求具体属性
    public void getProperty(String url) {
        OkHttpClient codeHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = codeHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fail?", "yes");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                KeyProperty kp = processProperty(result);
                Log.d("bitmap", keys);
                String  imgUrl = "http://system.tianwotian.com/CommodityList/"+keys+"/img/0.jpg";
                MyBitmapUtils myBitmapUtils = new MyBitmapUtils();
                Bitmap bitmap = myBitmapUtils.backBitmap(imgUrl);

                popWindow = new BabyPopWindow(DetailActivity.this,kp,bitmap, bean);
                popWindow.setOnItemClickListener(DetailActivity.this);
                popWindow.setOnChangeBackgroundListener(new BabyPopWindow.ChangeBackgroundListener() {
                    @Override
                    public void onChangeBackground() {
                        setBackgroundBlack(all_choice_layout, 3);
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //返回
                finish();

                break;
            case R.id.iv_baby_collection:
                //收藏
                if (isCollection) {
                    //提示是否取消收藏
                    cancelCollection();
                } else {
                    isCollection = true;
                    setSaveCollection();
                    //如果已经收藏，则显示收藏后的效果
                    iv_baby_collection.setImageResource(R.drawable.second_2_collection);
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.put_in_cart:

                //添加购物车
                isClickBuy = false;
                setBackgroundBlack(all_choice_layout, 0);
                popWindow.showAsDropDown(view);

                break;
            case R.id.buy_now:
                //立即购买
                isClickBuy = true;
                setBackgroundBlack(all_choice_layout, 0);
                popWindow.showAsDropDown(view);
//                new GetJson(this, propertyUrl, view);
                break;
            case R.id.online_service:

                break;
        }
    }


    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        Intent intent = getIntent();
        String keys = intent.getStringExtra("C_Keys");
        String imgs = intent.getStringExtra("C_Img");
        List<String> list = new ArrayList<String>();
        list = RegexUtils.numofNum(imgs);
        String imgUrl;

        final ArrayList<String> urls = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            Log.d("num", list.get(i));
            imgUrl = "http://system.tianwotian.com/CommodityList/" + keys + "/img/" + list.get(i) + ".jpg";
            urls.add(imgUrl);
//            new LoadImageTask(imgUrl, imageView).execute();
//            Glide.with(this).load(imgUrl).into(imageView);
            //网络下载图片三级缓存
            MyBitmapUtils bitmapUtils = new MyBitmapUtils();
            bitmapUtils.display(imageView,imgUrl);

            allListView.add(view);
        }
        for (int i = 0; i < list.size(); i++) {
            allListView.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //挑战到查看大图界面
                    Intent intent = new Intent(DetailActivity.this, ShowBigPictrue.class);
                    intent.putExtra("position", position);
                    intent.putExtra("urllist", urls);
                    startActivity(intent);
                }
            });
        }

        viewPager = (HackyViewPager) findViewById(R.id.iv_baby);
        ViewPagerAdapter adapter = new ViewPagerAdapter(allListView);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                position = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        viewPager.setAdapter(adapter);

    }

    private class ViewPagerAdapter extends PagerAdapter {
        ArrayList<View> imageViews;

        public ViewPagerAdapter(ArrayList<View> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imgview = imageViews.get(position);
            container.addView(imgview);
            return imgview;
        }

    }

    //点击弹窗的确认按钮的响应
    @Override
    public void onClickOKPop() {
        setBackgroundBlack(all_choice_layout, 3);

        if (isClickBuy) {
            //如果之前是点击的立即购买，那么就跳转到立即购物界面
            Intent intent = new Intent(DetailActivity.this, BuynowActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "添加到购物车成功", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 控制背景变暗 0变暗 1变亮
     */
    public void setBackgroundBlack(View view, int what) {
        switch (what) {
            case 0:
                Log.d("Visible?", "Yes");
                view.setVisibility(View.VISIBLE);
                break;
            case 3:
                Log.d("Change?", "Yes");
                view.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 保存是否添加收藏
     */
    private void setSaveCollection() {
        SharedPreferences sp = getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean("isCollection", isCollection);
        editor.commit();
    }

    /**
     * 得到保存的是否添加收藏标记
     */
    private void getSaveCollection() {
        SharedPreferences sp = getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
        isCollection = sp.getBoolean("isCollection", false);

    }

    /**
     * 取消收藏
     */
    private void cancelCollection() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("是否取消收藏");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                isCollection = false;
                //如果取消收藏，则显示取消收藏后的效果
                iv_baby_collection.setImageResource(R.drawable.second_2);
                setSaveCollection();
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.create().show();

    }

    //使用Gson解析 属性json
    public KeyProperty processProperty(String json) {
        Gson gson = new Gson();
        KeyProperty kp = gson.fromJson(json, KeyProperty.class);
        return kp;
    }

}
