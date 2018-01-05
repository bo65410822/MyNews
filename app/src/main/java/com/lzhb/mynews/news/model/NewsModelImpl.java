package com.lzhb.mynews.news.model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.lzhb.mynews.R;
import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.beans.NewsDetailBean;
import com.lzhb.mynews.commons.Urls;
import com.lzhb.mynews.news.NewsJsonUtil;
import com.lzhb.mynews.news.widget.NewsFragment;
import com.lzhb.mynews.utils.OkHttpUtil;

import java.util.List;

/**
 * 创建时间：2017/12/26 15:52
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsModelImpl implements NewsModel {

    /**
     * 加载新闻列表
     *
     * @param url
     * @param type
     * @param listener
     */
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtil.ResultCallBack<String> loadNewsCallBack = new OkHttpUtil.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList = NewsJsonUtil.readJsonNewsBeans(
                        response, getID(type));
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("加载失败", e);
            }
        };
        OkHttpUtil.get(url, loadNewsCallBack);
    }

    /**
     * 加载新闻详情
     *
     * @param docid
     * @param listener
     */
    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        OkHttpUtil.ResultCallBack<String> loadNewsCallback = new OkHttpUtil.ResultCallBack<String>() {

            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtil.readJsonNewsDetailBeans(response
                        , docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("加载失败", e);
            }
        };
        OkHttpUtil.get(url,loadNewsCallback);
    }

    private String getDetailUrl(String docid) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docid).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
}
