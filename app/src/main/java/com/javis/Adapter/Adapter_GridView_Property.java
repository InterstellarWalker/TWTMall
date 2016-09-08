package com.javis.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianwotian.mytaobaotest.R;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/8.
 */
public class Adapter_GridView_Property extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public Adapter_GridView_Property(Context context,ArrayList<String> list){

        this.context=context;
        this.list=list;
        Log.d("type", list.get(0));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup arg2) {
        HolderView holderView=null;
        if (currentView==null) {
            holderView=new HolderView();
            currentView= LayoutInflater.from(context).inflate(R.layout.adapter_property, null);
            holderView.textView=(TextView) currentView.findViewById(R.id.tv_property);
            currentView.setTag(holderView);
        }else {
            holderView=(HolderView) currentView.getTag();
        }
        holderView.textView.setText(list.get(position));
//        Log.d("type", list.get(position));
        return currentView;
    }


    public class HolderView {

        private TextView textView;

    }
}
