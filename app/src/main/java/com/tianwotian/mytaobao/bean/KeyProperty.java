package com.tianwotian.mytaobao.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 通过key值查找属性信息
 * Created by user on 2016/9/8.
 */
public class KeyProperty {

    private SumProperty Table;

    public SumProperty getTable() {
        return Table;
    }

    public void setTable(SumProperty Table) {
        this.Table = Table;
    }

    public static class SumProperty {
        /**
         * ID : 41345
         * CTCA_CommodityKeys : GY22648619833693
         * CTCA_Content : 11英寸
         * CTCA_CTAID : 733
         * CTCA_Special_Attributes :
         */

        @SerializedName("Table")
        private List<Property> Property;

        public List<Property> getProperty() {
            return Property;
        }

        public void setProperty(List<Property> Property) {
            this.Property = Property;
        }

        public static class Property {
            private String ID;
            private String CTCA_CommodityKeys;
            private String CTCA_Content;
            private String CTCA_CTAID;
            private String CTCA_Special_Attributes;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getCTCA_CommodityKeys() {
                return CTCA_CommodityKeys;
            }

            public void setCTCA_CommodityKeys(String CTCA_CommodityKeys) {
                this.CTCA_CommodityKeys = CTCA_CommodityKeys;
            }

            public String getCTCA_Content() {
                return CTCA_Content;
            }

            public void setCTCA_Content(String CTCA_Content) {
                this.CTCA_Content = CTCA_Content;
            }

            public String getCTCA_CTAID() {
                return CTCA_CTAID;
            }

            public void setCTCA_CTAID(String CTCA_CTAID) {
                this.CTCA_CTAID = CTCA_CTAID;
            }

            public String getCTCA_Special_Attributes() {
                return CTCA_Special_Attributes;
            }

            public void setCTCA_Special_Attributes(String CTCA_Special_Attributes) {
                this.CTCA_Special_Attributes = CTCA_Special_Attributes;
            }
        }
    }
}
