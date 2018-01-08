package com.lzhb.mynews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lzhb.mynews.R;
import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.news.persenter.NewsDetailPresenter;
import com.lzhb.mynews.news.persenter.NewsDetailPresenterImpl;
import com.lzhb.mynews.news.view.NewsDetailView;
import com.lzhb.mynews.utils.ImageLoaderUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * 创建时间：2017/12/26 18:07
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView {

    private Toolbar toolbarTitle;
    private HtmlTextView newsContent;
    private ProgressBar mProgressBar;
    private NewsBean mNews;
    private NewsDetailPresenter mNewsDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        toolbarTitle = findViewById(R.id.news_title);
        mProgressBar = findViewById(R.id.progress);
        newsContent = findViewById(R.id.news_content);

        setSupportActionBar(toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mNews = (NewsBean) getIntent().getSerializableExtra("news");
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getTitle());
        ImageLoaderUtil.display(getApplicationContext(), (ImageView) findViewById(R.id.news_img)
                , mNews.getImgsrc());
        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        mNewsDetailPresenter.loadNewsDetail(mNews.getDocid());
    }

    @Override
    public void showNewsDetailContent(String content) {
        newsContent.setHtmlFromString(content, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
