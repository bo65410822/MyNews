package com.lzhb.mynews.news.view;

/**
 * 创建时间：2018/1/5 14:20
 * 作者：Li zhb
 * 功能描述：
 */

public interface NewsDetailView {

    void showNewsDetailContent(String content);

    void showProgress();

    void hideProgress();
}
