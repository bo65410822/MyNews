package com.lzhb.mynews.news.model;

import com.lzhb.mynews.beans.NewsDetailBean;


/**
 * 创建时间：2017/12/26 15:41
 * 作者：Li zhb
 * 功能描述：
 */

public interface OnLoadNewsDetailListener {

    void onSuccess(NewsDetailBean newsDetailBean);

    void onFailure(String msg, Exception e);
}
