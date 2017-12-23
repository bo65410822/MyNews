package com.lzhb.mynews.main.persenter;

import com.special.ResideMenu.ResideMenuItem;

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
}
