package com.lzhb.mynews.main.persenter;

import com.lzhb.mynews.beans.ThemeBean;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

/**
 * 创建时间：2017/12/21 18:09
 * 作者：Li zhb
 * 功能描述：
 */

public interface MainPresenter {

    /**
     * @param id
     */
    void switchNavigation(int id);

    /**
     * 选择menu点击事件
     *
     * @param id
     */
    void switchMenu(int id);

    /**
     * 获取每一个Item
     *
     * @param item
     * @return
     */
    ResideMenuItem getItemView(String item);

    /**
     * 更改主题
     */
    void showChangeTheme();

    /**
     * 取消显示主题界面
     */
    void dimsDialogTheme();

    void loadData(List<ThemeBean> themeBeans);
}
