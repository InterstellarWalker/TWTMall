package com.tianwotian.mytaobao.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.javis.Adapter.Adapter_SearchItem;
import com.tianwotian.http.GetHttp;
import com.tianwotian.mytaobao.bean.SearchItemBean;
import com.tianwotian.mytaobaotest.R;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;

/**
 * 多个商品展示界面
 *
 * @author http://yecaoly.taobao.com
 */
@SuppressLint("SimpleDateFormat")
public class WareActivity extends Activity implements OnTouchListener, IXListViewListener {
    //显示所有商品的列表
    private XListView listView;
    //整个顶部搜索控件，用于隐藏和显示底部整个控件
    private LinearLayout ll_search;
    //返回按钮
    private ImageView iv_back;
    @SuppressWarnings("unused")
    private EditText ed_search;

    ListView lv_search;
    private AnimationSet animationSet;
    /**
     * 第一次按下屏幕时的Y坐标
     */
    float fist_down_Y = 0;
    /**
     * 请求数据的页数
     */
    private int pageIndex = 0;
    /**
     * 存储网络返回的数据
     */
    private HashMap<String, Object> hashMap;
    /**
     * 存储网络返回的数据中的data字段
     */
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    ArrayList<SearchItemBean.SearchItemList.TableBean> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ware_a);
        initView();
        //请求网络数据
        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//			   if (textView.hasFocus())
                new WareTask(ed_search.getText().toString()).execute();
                return false;
            }
        });
    }

    private void initView() {
        //AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
        ll_search = (LinearLayout) findViewById(R.id.ll_choice);
        ed_search = (EditText) findViewById(R.id.ed_Searchware);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        listView = (XListView) findViewById(R.id.listView_ware);
        listView.setOnTouchListener(this);
        listView.setXListViewListener(this);
        listView.setDivider(null);
        // 设置可以进行下拉加载的功能
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //第一次按下时的坐标
                fist_down_Y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 向上滑动，隐藏搜索栏
                if (fist_down_Y - y > 250 && ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(-100);
                    ll_search.setVisibility(View.GONE);
                }
                // 向下滑动，显示搜索栏
                if (y - fist_down_Y > 250 && !ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(0);
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;

        }
        return false;

    }

    /**
     * 使用Gson解析网络获取的数据
     **/
    public ArrayList<SearchItemBean.SearchItemList.TableBean> getGsonResult(String json) {
        Gson gson = new Gson();

        List<SearchItemBean.SearchItemList.TableBean> searchItemBean = gson.fromJson(json, SearchItemBean.class).getTable().getTable();

        return (ArrayList<SearchItemBean.SearchItemList.TableBean>) searchItemBean;
    }

    private class WareTask extends AsyncTask<Void, Void, ArrayList<SearchItemBean.SearchItemList.TableBean>> {

        ProgressDialog dialog = null;

        String content;

        public WareTask(String content) {
            this.content = content;
        }

        @Override
        protected void onPreExecute() {
            if (dialog == null) {
                dialog = ProgressDialog.show(WareActivity.this, "", "正在加载...");
                dialog.show();
            }
        }

        @Override
        protected ArrayList<SearchItemBean.SearchItemList.TableBean> doInBackground(Void... arg0) {
            String url = "http://api.tianwotian.com/api/CommListSeach.aspx?Search=" + content;
//			if (pageIndex == 0) {
//				url = "http://api.tianwotian.com/api/CommListSeach.aspx?Search=\n" +
//						content;
//			} else {
//				url = "http://api.tianwotian.com/api/CommListSeach.aspx?Search="+content + pageIndex;
//			}
            //请求数据，返回json
            String json = GetHttp.RequstGetHttp(url);
            //第一层的数组类型字段
            String[] LIST1_field = {"Table"};
            Log.d("json", json);
            //第二层的对象类型字段
            String[] STR2_field = {"id", "name", "price", "sale_num", "address", "pic"};
            List<SearchItemBean.SearchItemList.TableBean> table = getGsonResult(json);
            return (ArrayList<SearchItemBean.SearchItemList.TableBean>) table;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(final ArrayList<SearchItemBean.SearchItemList.TableBean> result) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }


            //如果网络数据请求失败，那么显示默认的数据
            if (result != null) {
                //得到data字段的数据
                //	arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
                listView.setAdapter(new Adapter_SearchItem(WareActivity.this, result));

                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(WareActivity.this, DetailActivity.class);
                        intent.putExtra("C_Name", result.get(position-1).getC_Name());
                        intent.putExtra("C_Integral", result.get(position-1).getC_Integral());
                        intent.putExtra("C_Price", result.get(position-1).getC_Price());
                        intent.putExtra("C_Stock_Num", result.get(position-1).getC_Stock_Num());
                        intent.putExtra("C_Label", result.get(position-1).getC_Label());
                        intent.putExtra("C_Original_Price", result.get(position-1).getC_Original_Price());
                        intent.putExtra("ID", result.get(position-1).getID());
                        intent.putExtra("C_Keys", result.get(position - 1).getC_Keys());
                        intent.putExtra("C_Img", result.get(position - 1).getC_Img());
                        intent.putExtra("Description", result.get(position - 1).getC_Description());
                        intent.putExtra("bean", (Parcelable) result.get(position - 1));
                        startActivity(intent);
                    }
                });

            } else {
                listView.setAdapter(new Adapter_SearchItem(WareActivity.this, result));
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(WareActivity.this, DetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
            // 停止刷新和加载
            onLoad();
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        // 刷新数据
        pageIndex = 0;
        arrayList.clear();
//		getDataFromServer();
        // 停止刷新和加载
        new WareTask(ed_search.getText().toString()).execute();
        onLoad();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        pageIndex += 1;
        if (pageIndex >= 4) {
            Toast.makeText(this, "已经最后一页了", Toast.LENGTH_SHORT).show();
            onLoad();
            return;
        }
    }

    /**
     * 停止加载和刷新
     */
    private void onLoad() {
        listView.stopRefresh();
        // 停止加载更多
        listView.stopLoadMore();

        // 设置最后一次刷新时间
        listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
    }

    /**
     * 简单的时间格式
     */
    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    public static String getCurrentTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));

    }


}
