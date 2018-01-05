package com.lzhb.mynews.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * 创建时间：2017/12/26 16:30
 * 作者：Li zhb
 * 功能描述：
 */

public class JsonUtil {

    private static Gson mGson = new Gson();

    /**
     * 将对象转换为json字符串
     *
     * @param obj 对象
     * @param <T>
     * @return
     */
    public static <T> String serialize(T obj) {
        return mGson.toJson(obj);
    }

    /**
     * 将json字符串转化为对象
     *
     * @param json json字符串数据
     * @param clz  类
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转化为对象
     *
     * @param json json对象数据
     * @param clz  类
     * @param <T>
     * @return
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转化为对象
     *
     * @param json json字符串数据
     * @param clz  类
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }
}
