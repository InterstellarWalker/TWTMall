package com.javis.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.tianwotian.mytaobao.bean.SearchItem;
import com.tianwotian.mytaobao.bean.SearchItemBean;
import com.tianwotian.mytaobaotest.R;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/6.
 */
//商品列表ListView 适配器
public class Adapter_SearchItem extends BaseAdapter {
    BitmapUtils mBitmapUtils;
    Activity activity;
    ArrayList<SearchItemBean.SearchItemList.TableBean> itemList ;
    public Adapter_SearchItem(Activity mActivity, ArrayList<SearchItemBean.SearchItemList.TableBean> list) {
        this.itemList = list;
        this.activity = mActivity;
        mBitmapUtils = new BitmapUtils(activity);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MerchandiseHolder merholder = null;
        int currentType = getItemViewType(i);

        if (view == null) {
            merholder = new MerchandiseHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.search_item, null);
            merholder.goodsPicture = (ImageView) view.findViewById(R.id.goods_picture);
            merholder.goodsName = (TextView) view.findViewById(R.id.goods_name);
            merholder.goodsPrice = (TextView) view.findViewById(R.id.goods_price);
            merholder.credits = (TextView) view.findViewById(R.id.credits_can_get);
            merholder.salesVolume = (TextView) view.findViewById(R.id.sales_volume);
            view.setTag(merholder);
        } else {
            merholder= (MerchandiseHolder) view.getTag();
        }
        //           HomePagerLists.Merchandise hd = (HomePagerLists.Merchandise) getItem(i);
        mBitmapUtils.display(merholder.goodsPicture,"http://system.tianwotian.com/CommodityList/" +itemList.get(i).getC_Keys()+
                "/img/0.jpg");
        merholder.goodsName.setText(itemList.get(i).getC_Name());
        merholder.goodsPrice.setText("¥"+itemList.get(i).getC_Price());
        merholder.credits.setText("可获积分："+itemList.get(i).getC_Integral());
        merholder.salesVolume.setText("销量"+itemList.get(i).getC_Buy_Num());
        return view;
    }


    class MerchandiseHolder {
        public ImageView goodsPicture;
        public TextView goodsName;
        public TextView goodsPrice;
        public TextView credits;
        public TextView salesVolume;
    }
}


