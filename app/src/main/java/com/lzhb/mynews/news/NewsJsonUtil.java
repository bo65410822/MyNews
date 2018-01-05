package com.lzhb.mynews.news;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.beans.NewsDetailBean;
import com.lzhb.mynews.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017/12/26 17:26
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsJsonUtil {
    private static final String TAG = "NewsJsonUtil";

    /**
     * json转化新闻对象
     *
     * @param res
     * @param value
     * @return
     */
    public static List<NewsBean> readJsonNewsBeans(String res, String value) {
        List<NewsBean> beans = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(res).getAsJsonObject();
        JsonElement element = object.get(value);
        if (element == null) {
            return null;
        }
        JsonArray jsonArray = element.getAsJsonArray();
        for (int i = 1; i < jsonArray.size(); i++) {
            JsonObject jo = jsonArray.get(i).getAsJsonObject();
            if (jo.has("skipType")
                    && "special".equals(jo.get("skipType").getAsString())) {
                continue;
            }
            if (jo.has("TAGS") && !jo.has("TAG")) {
                continue;
            }
            if (!jo.has("imgextra")) {
                NewsBean news = JsonUtil.deserialize(jo, NewsBean.class);
                beans.add(news);
            }
        }
        return beans;
    }

    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(res).getAsJsonObject();
        JsonElement element = object.get(docId);
        if (element == null) {
            return null;
        }
        newsDetailBean = JsonUtil.deserialize(element.getAsJsonObject(),
                NewsDetailBean.class);
        return newsDetailBean;
    }
}
