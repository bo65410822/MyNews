package com.lzhb.mynews.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间：2017/12/26 15:56
 * 作者：Li zhb
 * 功能描述：
 */

public class OkHttpUtil {

    private static final String TAG = "OkHttpUtil";
    private static OkHttpUtil mInstance;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setCookieHandler(new CookieManager(null,
                CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        handler = new Handler(Looper.getMainLooper());
    }

    private synchronized static OkHttpUtil getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtil();
        }
        return mInstance;
    }

    /**
     * get请求
     *
     * @param url      地址
     * @param callBack 回调
     */
    private void getRequest(String url, ResultCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callBack, request);
    }

    /**
     * post请求
     *
     * @param url      地址
     * @param callBack 回调
     */
    private void postRequest(String url, ResultCallBack callBack, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callBack, request);
    }

    /**
     * POST提交键值对
     *
     * @param url    请求地址
     * @param params 参数（键值对）
     * @return
     */
    private Request buildPostRequest(String url, List<Param> params) {

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * 请求数据
     *
     * @param callBack 回调
     * @param request  Request
     */
    private void deliveryResult(final ResultCallBack callBack, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) { //失败
                sendFailCallback(callBack, e);
            }

            @Override
            public void onResponse(Response response) throws IOException { //成功
                try {
                    String str = response.body().string();
                    if (callBack.type == String.class) { //是字符串
                        sendSuccessCallBack(callBack, str);
                        Log.e(TAG, "deliveryResult str = " + str);
                    } else {
                        Object object = JsonUtil.deserialize(str, callBack.type);
                        sendSuccessCallBack(callBack, object);
                        Log.e(TAG, "deliveryResult object = " + object.toString());
                    }
                } catch (final Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    sendFailCallback(callBack, e);
                }
            }
        });
    }

    /**
     * 成功
     *
     * @param callBack 回调
     * @param str      Object
     */
    private void sendSuccessCallBack(final ResultCallBack callBack, final Object str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onSuccess(str);
                }
            }
        });
    }

    /**
     * 失败
     *
     * @param callBack 回调
     * @param e        异常
     */
    private void sendFailCallback(final ResultCallBack callBack, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(e);
                }
            }
        });
    }

    //////////////   对外接口   ///////////////////////

    /**
     * get请求
     *
     * @param url      请求地址
     * @param callBack 回调
     */
    public static void get(String url, ResultCallBack callBack) {
        getmInstance().getRequest(url, callBack);
    }

    /**
     * post请求
     *
     * @param url      请求地址
     * @param callBack 回调
     * @param params   请求参数
     */
    public static void post(String url, ResultCallBack callBack, List<Param> params) {
        getmInstance().postRequest(url, callBack, params);
    }

    /**
     * 请求回调类
     *
     * @param <T>
     */
    public static abstract class ResultCallBack<T> {

        Type type;

        public ResultCallBack() {
            type = getSupperClassParameter(getClass());
        }

        static Type getSupperClassParameter(Class<?> subClass) {
            Type superClass = subClass.getGenericSuperclass();
            if (superClass instanceof Class) {
                throw new RuntimeException("Missing type parameter");
            }
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

        /**
         * 成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * POST请求参数
     */
    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }
}
