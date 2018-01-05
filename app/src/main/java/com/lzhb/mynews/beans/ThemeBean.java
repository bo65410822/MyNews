package com.lzhb.mynews.beans;


/**
 * 创建时间：2017/12/25 13:50
 * 作者：Li zhb
 * 功能描述：
 */

public class ThemeBean {

    private int id;
    private int color;

    public ThemeBean(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ThemeBean{" +
                "id=" + id +
                ", color=" + color +
                '}';
    }
}
