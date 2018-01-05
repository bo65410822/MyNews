package com.lzhb.mynews.news.model;

import com.lzhb.mynews.beans.NewsBean;

import java.util.List;

/**
 * 创建时间：2017/12/26 15:41
 * 作者：Li zhb
 * 功能描述：
 */

public interface OnLoadNewsListListener {

    void onSuccess(List<NewsBean> newsBeans);

    void onFailure(String msg, Exception e);
}
