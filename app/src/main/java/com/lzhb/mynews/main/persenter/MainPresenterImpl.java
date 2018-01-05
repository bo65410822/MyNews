package com.lzhb.mynews.main.persenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.lzhb.mynews.R;
import com.lzhb.mynews.beans.ThemeBean;
import com.lzhb.mynews.main.view.MainView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

/**
 * 创建时间：2017/12/21 18:10
 * 作者：Li zhb
 * 功能描述：
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private Activity context;
    private ResideMenu resideMenu;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        context = (Activity) mainView;
        setUpMenu();
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mainView.switch2News();
                break;
            case R.id.navigation_item_image:
                mainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mainView.switch2About();
                break;
            default:
                mainView.switch2News();
                break;
        }
    }

    @Override
    public void switchMenu(int id) {
        switch (id) {
            case R.id.action_setting:
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                break;
        }
    }

    @Override
    public ResideMenuItem getItemView(String item) {
        if (item.equals("主题")) return itemTheme;
        return null;
    }

    @Override
    public void showChangeTheme() {
        mainView.dialogThemeOK();
    }

    @Override
    public void dimsDialogTheme() {
        mainView.dialogThemeClear();
    }

    @Override
    public void loadData(List<ThemeBean> themeBeans){
        int resId = R.drawable.theme_sel;
        themeBeans.add(new ThemeBean(resId, Color.RED));
        themeBeans.add(new ThemeBean(resId, Color.GREEN));
        themeBeans.add(new ThemeBean(resId, Color.BLUE));
        themeBeans.add(new ThemeBean(resId, Color.GRAY));
    }

    private ResideMenuItem itemTheme;

    /**
     * 初始化设置resideMenu
     */
    private void setUpMenu() {
        resideMenu = new ResideMenu(context);
        resideMenu.setUse3D(true);
        resideMenu.attachToActivity(context);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setBackground(R.drawable.news_bg);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        itemTheme = new ResideMenuItem(context,
                R.drawable.theme, "主题");
//        getBitmapPal(itemTheme);
        itemTheme.setOnClickListener((View.OnClickListener) context);
        resideMenu.addMenuItem(itemTheme, ResideMenu.DIRECTION_RIGHT);
    }

    /**
     * 获取颜色
     */
    private void getBitmapPal(final ResideMenuItem theme) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.news_bg);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //有活力的，暗色
                Palette.Swatch vibrantDark = palette.getVibrantSwatch();
                theme.setBackgroundColor(vibrantDark.getRgb());
            }
        });

    }
}
