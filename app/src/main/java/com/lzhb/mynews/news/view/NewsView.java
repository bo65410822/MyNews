package com.lzhb.mynews.news.view;

import com.lzhb.mynews.beans.NewsBean;

import java.util.List;

/**
 * 创建时间：2017/12/24 11:23
 * 作者：Li zhb
 * 功能描述：
 */

public interface NewsView {

    void showProgress();

    void addNews(List<NewsBean> beanList);

    void hideProgress();

    void showLoadFailMsg();

}
