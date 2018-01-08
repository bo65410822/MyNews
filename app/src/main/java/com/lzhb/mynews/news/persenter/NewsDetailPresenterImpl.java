package com.lzhb.mynews.news.persenter;

import android.content.Context;

import com.lzhb.mynews.beans.NewsDetailBean;
import com.lzhb.mynews.news.model.NewsModel;
import com.lzhb.mynews.news.model.NewsModelImpl;
import com.lzhb.mynews.news.model.OnLoadNewsDetailListener;
import com.lzhb.mynews.news.view.NewsDetailView;

/**
 * 创建时间：2018/1/5 14:36
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsDetailPresenterImpl implements NewsDetailPresenter, OnLoadNewsDetailListener {

    private Context mContext;
    private NewsDetailView mNewsDetailView;
    private NewsModel mNewsModel;

    public NewsDetailPresenterImpl(Context mContext, NewsDetailView mNewsDetailView) {
        this.mContext = mContext;
        this.mNewsDetailView = mNewsDetailView;
        mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(String docId) {
        mNewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(docId, this);
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mNewsDetailView.showNewsDetailContent(newsDetailBean.getBody());
        }
        mNewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();
    }
}
