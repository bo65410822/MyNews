package com.lzhb.mynews.news.persenter;

import android.util.Log;

import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.commons.Urls;
import com.lzhb.mynews.news.model.NewsModel;
import com.lzhb.mynews.news.model.NewsModelImpl;
import com.lzhb.mynews.news.model.OnLoadNewsListListener;
import com.lzhb.mynews.news.view.NewsView;
import com.lzhb.mynews.news.widget.NewsFragment;

import java.util.List;

/**
 * 创建时间：2017/12/24 11:20
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsPersenterImpl implements NewsPersenter, OnLoadNewsListListener {

    private static final String TAG = "NewsPersenterImpl";

    private NewsView newsView;
    private NewsModel newsModel;

    public NewsPersenterImpl(NewsView newsView) {
        this.newsView = newsView;
        this.newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(int type, int page) {
        String url = getUrl(type, page);
        //只有第一页或者刷新的时候才显示刷新进度条
        if (page == 0) {
            newsView.showProgress();
        }
        newsModel.loadNews(url, type, this);
    }

    /**
     * 根据类别和页面索引创建URL
     *
     * @param type
     * @param page
     * @return
     */
    private String getUrl(int type, int page) {

        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(page).append(Urls.END_URL);
        Log.e(TAG, "getUrl: " + sb.toString());
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> newsBeans) {
        newsView.hideProgress();
        newsView.addNews(newsBeans);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsView.hideProgress();
        newsView.showLoadFailMsg();
    }
}
