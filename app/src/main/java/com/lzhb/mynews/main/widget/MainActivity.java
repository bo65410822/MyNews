package com.lzhb.mynews.main.widget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lzhb.mynews.R;
import com.lzhb.mynews.about.widget.AboutFragment;
import com.lzhb.mynews.images.widget.ImageFragment;
import com.lzhb.mynews.main.persenter.MainPresenter;
import com.lzhb.mynews.main.persenter.MainPresenterImpl;
import com.lzhb.mynews.main.view.MainView;
import com.lzhb.mynews.news.widget.NewsFragment;
import com.lzhb.mynews.theme.widget.ThemeActivity;
import com.lzhb.mynews.weather.widget.WeatherFragment;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;

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
        setupDrawerContent(mNavigationView);
        mMainPresenter = new MainPresenterImpl(this);
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
    public void onClick(View view) {
        if (view == mMainPresenter.getItemView("主题")) {
            startActivity(new Intent(MainActivity.this, ThemeActivity.class));
        }
    }
}
