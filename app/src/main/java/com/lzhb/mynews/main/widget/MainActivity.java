package com.lzhb.mynews.main.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lzhb.mynews.R;
import com.lzhb.mynews.about.widget.AboutFragment;
import com.lzhb.mynews.beans.ThemeBean;
import com.lzhb.mynews.images.widget.ImageFragment;
import com.lzhb.mynews.adapter.ThemeAdapter;
import com.lzhb.mynews.main.persenter.MainPresenter;
import com.lzhb.mynews.main.persenter.MainPresenterImpl;
import com.lzhb.mynews.main.view.MainView;
import com.lzhb.mynews.news.widget.NewsFragment;
import com.lzhb.mynews.weather.widget.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView,
        View.OnClickListener, ThemeAdapter.MyItemClickListener {

    // 加载静态代码 ==》代码动态加载矢量图
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;
    private AlertDialog builder;
    private View dialogView;
    private Button dialaoBtnClear, dialaoBtnOK;
    private RecyclerView themeView;
    private ThemeAdapter mThemeAdapter;
    private List<ThemeBean> themeBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = findViewById(R.id.navigation_view);
        builder = new AlertDialog.Builder(MainActivity.this)
                .create();
        dialogView = LayoutInflater.from(this).inflate(
                R.layout.card_theme, null);
        builder.setView(dialogView);
        builder.setTitle("更换主题");
        dialaoBtnClear = dialogView.findViewById(R.id.btn_clear);
        dialaoBtnClear.setOnClickListener(this);
        dialaoBtnOK = dialogView.findViewById(R.id.btn_ok);
        dialaoBtnOK.setOnClickListener(this);
        setupDrawerContent(mNavigationView);
        mMainPresenter = new MainPresenterImpl(this);
        mMainPresenter.loadData(themeBeans);
        initAdapter();
        switch2News();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(TAG, "onOptionsItemSelected: id = " + id);
        mMainPresenter.switchMenu(id);
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mMainPresenter.switchNavigation(item.getItemId());
                item.setCheckable(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content
                , new NewsFragment()).commit();
        mToolbar.setTitle("新闻");
    }

    @Override
    public void switch2Images() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content
                , new ImageFragment()).commit();
        mToolbar.setTitle("图片");
    }

    @Override
    public void switch2Weather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content
                , new WeatherFragment()).commit();
        mToolbar.setTitle("天气");
    }

    @Override
    public void switch2About() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content
                , new AboutFragment()).commit();
        mToolbar.setTitle("关于");
    }

    @Override
    public void dialogThemeClear() {
        builder.dismiss();
    }

    @Override
    public void dialogThemeOK() {
        Log.e(TAG, "dialogThemeOK: change");
        builder.dismiss();
    }


    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mThemeAdapter = new ThemeAdapter(themeBeans, this);
        mThemeAdapter.setMyItemClickListener(this);
        themeView = dialogView.findViewById(R.id.card_item_theme);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1,
                LinearLayout.HORIZONTAL, false);
        themeView.setLayoutManager(layoutManager);
        themeView.setAdapter(mThemeAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == mMainPresenter.getItemView("主题")) {
            builder.show();
        }
        switch (view.getId()) {
            case R.id.btn_ok:
                mMainPresenter.showChangeTheme();
                break;
            case R.id.btn_clear:
                mMainPresenter.dimsDialogTheme();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Log.e(TAG, "onItemClick: theme = " + themeBeans.get(position).toString());
    }
}
