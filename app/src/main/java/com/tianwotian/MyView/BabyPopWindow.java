package com.tianwotian.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.javis.Adapter.Adapter_GridView;
import com.javis.Adapter.Adapter_GridView_Property;
import com.tianwotian.mytaobao.Data.Data;
import com.tianwotian.mytaobao.bean.KeyProperty;
import com.tianwotian.mytaobao.bean.SearchItemBean;
import com.tianwotian.mytaobaotest.R;
import com.tianwotian.tools.MyBitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 宝贝详情界面的弹窗
 *
 * @author http://yecaoly.taobao.com
 */
@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener {
//    @BindView(R.id.type_container)
//    FrameLayout typeContainer;
//    @BindView(R.id.size_container)
//    FrameLayout sizeContainer;
//    @BindView(R.id.pop_picture)
//    ImageView popPicture;
//    @BindView(R.id.pop_name)
//    TextView popName;
//    @BindView(R.id.pop_price)
//    TextView popPrice;
//    @BindView(R.id.pop_credits)
//    TextView popCredits;

    private TextView pop_add, pop_reduce, pop_num, pop_ok;
    private ImageView pop_del;

    private PopupWindow popupWindow;
    private OnItemClickListener listener;
    private final int ADDORREDUCE = 1;
    private Context context;
    private Bitmap bitmap;
    /**
     * 保存选择的颜色的数据
     */
    private String str_color = "";
    /**
     * 保存选择的类型的数据
     */
    private String str_type = "";
    private ChangeBackgroundListener changeBackgroundListener;
    private KeyProperty kp;
    String keys;
    List<KeyProperty.SumProperty.Property> list;

    private final TextView popname;
    private final TextView popprice;
    private final TextView popcredits;
    private final ImageView popPicture;
    SearchItemBean.SearchItemList.TableBean bean;
    private final FrameLayout typeContainer;
    private final FrameLayout sizeContainer;

    public BabyPopWindow(final Context context, KeyProperty kp, Bitmap bitmap, SearchItemBean.SearchItemList.TableBean bean) {
        this.context = context;
        this.kp = kp;
        this.bitmap = bitmap;
        this.bean = bean;

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_popwindow, null);
        Log.d("kp", kp.getTable().getProperty().get(0).getCTCA_Content());
        pop_add = (TextView) view.findViewById(R.id.pop_add);
        pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
        pop_num = (TextView) view.findViewById(R.id.pop_num);
        pop_ok = (TextView) view.findViewById(R.id.pop_ok);
        pop_del = (ImageView) view.findViewById(R.id.pop_del);
        popname = (TextView) view.findViewById(R.id.pop_name);
        popprice = (TextView) view.findViewById(R.id.pop_price);
        popcredits = (TextView) view.findViewById(R.id.pop_credits);
        popPicture = (ImageView) view.findViewById(R.id.pop_picture);
        typeContainer = (FrameLayout) view.findViewById(R.id.type_container);
        sizeContainer = (FrameLayout) view.findViewById(R.id.size_container);
        String CIAID = null;

        pop_add.setOnClickListener(this);
        pop_reduce.setOnClickListener(this);
        pop_ok.setOnClickListener(this);
        pop_del.setOnClickListener(this);
//        Log.d("String", pop_name+pop_credits+pop_price);
        popname.setText(bean.getC_Name());
        popprice.setText(bean.getC_Price());
        popcredits.setText(bean.getC_Integral());
        popPicture.setImageBitmap(bitmap);
        List<KeyProperty.SumProperty.Property> list = kp.getTable().getProperty();
        GridView gridView = new GridView(context);
        ArrayList<String> type = new ArrayList<String>();
        type.add(list.get(0).getCTCA_Content());
        for (int i = 0; i < list.size()-1; i++) {
            Log.d("list", list.get(i).getCTCA_Content()+list.get(list.size()-1).getCTCA_Content());
            if (list.get(i).getCTCA_Content().equals(list.get(i + 1).getCTCA_Content())) {
            }else {
                type.add(list.get(i + 1).getCTCA_Content());
            }

//            Log.d("type", type.get(i));
        }
        gridView.setAdapter(new Adapter_GridView_Property(context, type));
        typeContainer.addView(gridView);
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //设置popwindow的动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听

    }

    public interface OnItemClickListener {
        /**
         * 设置点击确认按钮时监听接口
         */
        public void onClickOKPop();
    }

    public interface ChangeBackgroundListener {
        void onChangeBackground();
    }

    /**
     * 设置监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnChangeBackgroundListener(ChangeBackgroundListener listener) {
        this.changeBackgroundListener = listener;
    }

    // 当popWindow消失时响应
    @Override
    public void onDismiss() {
        changeBackgroundListener.onChangeBackground();
    }

    /**
     * 弹窗显示的位置
     */
    public void showAsDropDown(View parent) {
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    /**
     * 消除弹窗
     */
    public void dissmiss() {
        popupWindow.dismiss();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//		case R.id.pop_choice_16g:
//
//			pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao_choice);
//			pop_choice_32g.setBackgroundResource(R.drawable.yuanjiao);
//			pop_choice_16m.setBackgroundResource(R.drawable.yuanjiao);
//			pop_choice_32m.setBackgroundResource(R.drawable.yuanjiao);
//			str_type=pop_choice_16g.getText().toString();
//			break;

            case R.id.pop_add:
                if (!pop_num.getText().toString().equals("750")) {

                    String num_add = Integer.valueOf(pop_num.getText().toString()) + ADDORREDUCE + "";
                    pop_num.setText(num_add);
                } else {
                    Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.pop_reduce:
                if (!pop_num.getText().toString().equals("1")) {
                    String num_reduce = Integer.valueOf(pop_num.getText().toString()) - ADDORREDUCE + "";
                    pop_num.setText(num_reduce);
                } else {
                    Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pop_del:
//            changeBackgroundListener.onChangeBackground();
                dissmiss();
                break;
            case R.id.pop_ok:
                listener.onClickOKPop();
                if (str_color.equals("")) {
                    Toast.makeText(context, "亲，你还没有选择颜色哟~", Toast.LENGTH_SHORT).show();
                } else if (str_type.equals("")) {
                    Toast.makeText(context, "亲，你还没有选择类型哟~", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> allHashMap = new HashMap<String, Object>();

                    allHashMap.put("color", str_color);
                    allHashMap.put("type", str_type);
                    allHashMap.put("num", pop_num.getText().toString());
                    allHashMap.put("id", Data.arrayList_cart_id += 1);

                    Data.arrayList_cart.add(allHashMap);
                    setSaveData();
                    dissmiss();

                }
                break;

            default:

                break;
        }
    }

    /**
     * 保存购物车的数据
     */
    private void setSaveData() {
        SharedPreferences sp = context.getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt("ArrayCart_size", Data.arrayList_cart.size());
        for (int i = 0; i < Data.arrayList_cart.size(); i++) {
            editor.remove("ArrayCart_type_" + i);
            editor.remove("ArrayCart_color_" + i);
            editor.remove("ArrayCart_num_" + i);
            editor.putString("ArrayCart_type_" + i, Data.arrayList_cart.get(i).get("type").toString());
            editor.putString("ArrayCart_color_" + i, Data.arrayList_cart.get(i).get("color").toString());
            editor.putString("ArrayCart_num_" + i, Data.arrayList_cart.get(i).get("num").toString());

        }


    }

    //使用Gson解析 属性json
    public KeyProperty processProperty(String json) {
        Gson gson = new Gson();
        KeyProperty kp = gson.fromJson(json, KeyProperty.class);
        return kp;
    }
}
