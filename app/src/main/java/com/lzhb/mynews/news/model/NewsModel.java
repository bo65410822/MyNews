package com.lzhb.mynews.news.model;

/**
 * 创建时间：2017/12/26 15:39
 * 作者：Li zhb
 * 功能描述：
 */

public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);

}
