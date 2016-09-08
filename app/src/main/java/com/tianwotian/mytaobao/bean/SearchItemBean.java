package com.tianwotian.mytaobao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by user on 2016/9/5.
 */
public class SearchItemBean {

    private SearchItemList Table;

    public SearchItemList getTable() {
        return Table;
    }

    public void setTable(SearchItemList Table) {
        this.Table = Table;
    }

    public static class SearchItemList {

        private List<TableBean> Table;

        public List<TableBean> getTable() {
            return Table;
        }

        public void setTable(List<TableBean> Table) {
            this.Table = Table;
        }

        public static class TableBean implements Parcelable{
            private String CS_Is_Buy;
            private String CS_Is_Show;
            private String CS_Name;
            private String C_Attention_Num;
            private String C_Brand_keys;
            private String C_Buy_Num;
            private String C_Cover_img;
            private String C_Description;
            private String C_Good_Num;
            private String C_ISIndex;
            private String C_ISVIP;
            private String C_IS_Postage;
            private String C_Img;
            private String C_Integral;
            private String C_Key;
            private String C_Key_Description;
            private String C_Keys;
            private String C_Label;
            private String C_Market_Value;
            private String C_Middle_Num;
            private String C_MoneyPostage;
            private String C_Name;
            private String C_NumBerPostage;
            private String C_Original_Price;
            private String C_OtherType;
            private String C_Poor_Num;
            private String C_Price;
            private String C_ProvincePostage;
            private String C_Quality;
            private String C_Remark;
            private String C_ShowRemark;
            private String C_State;
            private String C_Stock_Num;
            private String C_SupplierKeys;
            private String C_Time;
            private String C_Type;
            private String ID;
            private String S_province;

            protected TableBean(Parcel in) {
                CS_Is_Buy = in.readString();
                CS_Is_Show = in.readString();
                CS_Name = in.readString();
                C_Attention_Num = in.readString();
                C_Brand_keys = in.readString();
                C_Buy_Num = in.readString();
                C_Cover_img = in.readString();
                C_Description = in.readString();
                C_Good_Num = in.readString();
                C_ISIndex = in.readString();
                C_ISVIP = in.readString();
                C_IS_Postage = in.readString();
                C_Img = in.readString();
                C_Integral = in.readString();
                C_Key = in.readString();
                C_Key_Description = in.readString();
                C_Keys = in.readString();
                C_Label = in.readString();
                C_Market_Value = in.readString();
                C_Middle_Num = in.readString();
                C_MoneyPostage = in.readString();
                C_Name = in.readString();
                C_NumBerPostage = in.readString();
                C_Original_Price = in.readString();
                C_OtherType = in.readString();
                C_Poor_Num = in.readString();
                C_Price = in.readString();
                C_ProvincePostage = in.readString();
                C_Quality = in.readString();
                C_Remark = in.readString();
                C_ShowRemark = in.readString();
                C_State = in.readString();
                C_Stock_Num = in.readString();
                C_SupplierKeys = in.readString();
                C_Time = in.readString();
                C_Type = in.readString();
                ID = in.readString();
                S_province = in.readString();
            }

            public static final Creator<TableBean> CREATOR = new Creator<TableBean>() {
                @Override
                public TableBean createFromParcel(Parcel in) {
                    return new TableBean(in);
                }

                @Override
                public TableBean[] newArray(int size) {
                    return new TableBean[size];
                }
            };

            public String getCS_Is_Buy() {
                return CS_Is_Buy;
            }

            public void setCS_Is_Buy(String CS_Is_Buy) {
                this.CS_Is_Buy = CS_Is_Buy;
            }

            public String getCS_Is_Show() {
                return CS_Is_Show;
            }

            public void setCS_Is_Show(String CS_Is_Show) {
                this.CS_Is_Show = CS_Is_Show;
            }

            public String getCS_Name() {
                return CS_Name;
            }

            public void setCS_Name(String CS_Name) {
                this.CS_Name = CS_Name;
            }

            public String getC_Attention_Num() {
                return C_Attention_Num;
            }

            public void setC_Attention_Num(String C_Attention_Num) {
                this.C_Attention_Num = C_Attention_Num;
            }

            public String getC_Brand_keys() {
                return C_Brand_keys;
            }

            public void setC_Brand_keys(String C_Brand_keys) {
                this.C_Brand_keys = C_Brand_keys;
            }

            public String getC_Buy_Num() {
                return C_Buy_Num;
            }

            public void setC_Buy_Num(String C_Buy_Num) {
                this.C_Buy_Num = C_Buy_Num;
            }

            public String getC_Cover_img() {
                return C_Cover_img;
            }

            public void setC_Cover_img(String C_Cover_img) {
                this.C_Cover_img = C_Cover_img;
            }

            public String getC_Description() {
                return C_Description;
            }

            public void setC_Description(String C_Description) {
                this.C_Description = C_Description;
            }

            public String getC_Good_Num() {
                return C_Good_Num;
            }

            public void setC_Good_Num(String C_Good_Num) {
                this.C_Good_Num = C_Good_Num;
            }

            public String getC_ISIndex() {
                return C_ISIndex;
            }

            public void setC_ISIndex(String C_ISIndex) {
                this.C_ISIndex = C_ISIndex;
            }

            public String getC_ISVIP() {
                return C_ISVIP;
            }

            public void setC_ISVIP(String C_ISVIP) {
                this.C_ISVIP = C_ISVIP;
            }

            public String getC_IS_Postage() {
                return C_IS_Postage;
            }

            public void setC_IS_Postage(String C_IS_Postage) {
                this.C_IS_Postage = C_IS_Postage;
            }

            public String getC_Img() {
                return C_Img;
            }

            public void setC_Img(String C_Img) {
                this.C_Img = C_Img;
            }

            public String getC_Integral() {
                return C_Integral;
            }

            public void setC_Integral(String C_Integral) {
                this.C_Integral = C_Integral;
            }

            public String getC_Key() {
                return C_Key;
            }

            public void setC_Key(String C_Key) {
                this.C_Key = C_Key;
            }

            public String getC_Key_Description() {
                return C_Key_Description;
            }

            public void setC_Key_Description(String C_Key_Description) {
                this.C_Key_Description = C_Key_Description;
            }

            public String getC_Keys() {
                return C_Keys;
            }

            public void setC_Keys(String C_Keys) {
                this.C_Keys = C_Keys;
            }

            public String getC_Label() {
                return C_Label;
            }

            public void setC_Label(String C_Label) {
                this.C_Label = C_Label;
            }

            public String getC_Market_Value() {
                return C_Market_Value;
            }

            public void setC_Market_Value(String C_Market_Value) {
                this.C_Market_Value = C_Market_Value;
            }

            public String getC_Middle_Num() {
                return C_Middle_Num;
            }

            public void setC_Middle_Num(String C_Middle_Num) {
                this.C_Middle_Num = C_Middle_Num;
            }

            public String getC_MoneyPostage() {
                return C_MoneyPostage;
            }

            public void setC_MoneyPostage(String C_MoneyPostage) {
                this.C_MoneyPostage = C_MoneyPostage;
            }

            public String getC_Name() {
                return C_Name;
            }

            public void setC_Name(String C_Name) {
                this.C_Name = C_Name;
            }

            public String getC_NumBerPostage() {
                return C_NumBerPostage;
            }

            public void setC_NumBerPostage(String C_NumBerPostage) {
                this.C_NumBerPostage = C_NumBerPostage;
            }

            public String getC_Original_Price() {
                return C_Original_Price;
            }

            public void setC_Original_Price(String C_Original_Price) {
                this.C_Original_Price = C_Original_Price;
            }

            public String getC_OtherType() {
                return C_OtherType;
            }

            public void setC_OtherType(String C_OtherType) {
                this.C_OtherType = C_OtherType;
            }

            public String getC_Poor_Num() {
                return C_Poor_Num;
            }

            public void setC_Poor_Num(String C_Poor_Num) {
                this.C_Poor_Num = C_Poor_Num;
            }

            public String getC_Price() {
                return C_Price;
            }

            public void setC_Price(String C_Price) {
                this.C_Price = C_Price;
            }

            public String getC_ProvincePostage() {
                return C_ProvincePostage;
            }

            public void setC_ProvincePostage(String C_ProvincePostage) {
                this.C_ProvincePostage = C_ProvincePostage;
            }

            public String getC_Quality() {
                return C_Quality;
            }

            public void setC_Quality(String C_Quality) {
                this.C_Quality = C_Quality;
            }

            public String getC_Remark() {
                return C_Remark;
            }

            public void setC_Remark(String C_Remark) {
                this.C_Remark = C_Remark;
            }

            public String getC_ShowRemark() {
                return C_ShowRemark;
            }

            public void setC_ShowRemark(String C_ShowRemark) {
                this.C_ShowRemark = C_ShowRemark;
            }

            public String getC_State() {
                return C_State;
            }

            public void setC_State(String C_State) {
                this.C_State = C_State;
            }

            public String getC_Stock_Num() {
                return C_Stock_Num;
            }

            public void setC_Stock_Num(String C_Stock_Num) {
                this.C_Stock_Num = C_Stock_Num;
            }

            public String getC_SupplierKeys() {
                return C_SupplierKeys;
            }

            public void setC_SupplierKeys(String C_SupplierKeys) {
                this.C_SupplierKeys = C_SupplierKeys;
            }

            public String getC_Time() {
                return C_Time;
            }

            public void setC_Time(String C_Time) {
                this.C_Time = C_Time;
            }

            public String getC_Type() {
                return C_Type;
            }

            public void setC_Type(String C_Type) {
                this.C_Type = C_Type;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getS_province() {
                return S_province;
            }

            public void setS_province(String S_province) {
                this.S_province = S_province;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

                parcel.writeString(CS_Is_Buy);
                parcel.writeString(CS_Is_Show);
                parcel.writeString(CS_Name);
                parcel.writeString(C_Attention_Num);
                parcel.writeString(C_Brand_keys);
                parcel.writeString(C_Buy_Num);
                parcel.writeString(C_Cover_img);
                parcel.writeString(C_Description);
                parcel.writeString(C_Good_Num);
                parcel.writeString(C_ISIndex);
                parcel.writeString(C_ISVIP);
                parcel.writeString(C_IS_Postage);
                parcel.writeString(C_Img);
                parcel.writeString(C_Integral);
                parcel.writeString(C_Key);
                parcel.writeString(C_Key_Description);
                parcel.writeString(C_Keys);
                parcel.writeString(C_Label);
                parcel.writeString(C_Market_Value);
                parcel.writeString(C_Middle_Num);
                parcel.writeString(C_MoneyPostage);
                parcel.writeString(C_Name);
                parcel.writeString(C_NumBerPostage);
                parcel.writeString(C_Original_Price);
                parcel.writeString(C_OtherType);
                parcel.writeString(C_Poor_Num);
                parcel.writeString(C_Price);
                parcel.writeString(C_ProvincePostage);
                parcel.writeString(C_Quality);
                parcel.writeString(C_Remark);
                parcel.writeString(C_ShowRemark);
                parcel.writeString(C_State);
                parcel.writeString(C_Stock_Num);
                parcel.writeString(C_SupplierKeys);
                parcel.writeString(C_Time);
                parcel.writeString(C_Type);
                parcel.writeString(ID);
                parcel.writeString(S_province);
            }
        }
    }
}
