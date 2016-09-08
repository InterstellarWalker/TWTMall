package com.tianwotian.mytaobao.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by user on 2016/9/5.
 */


public class SearchItem {

    private Drawable itemPict;

    private String name;

    private String price;

    private String credits;

    private String salesVolume;

    public String getCredits() {
        return credits;
    }
    public String getPrice() {
        return price;
    }
    public String getSalesVolume(){
        return salesVolume;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public void setCredits(String credits){
        this.credits = credits;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return itemPict;
    }

    public void setIcon(Drawable icon) {
        this.itemPict = icon;
    }
}
