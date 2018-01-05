package com.lzhb.mynews.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzhb.mynews.R;

/**
 * 创建时间：2017/12/27 14:38
 * 作者：Li zhb
 * 功能描述：
 */

public class ImageLoaderUtil {
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher_foreground).crossFade().into(imageView);
    }
}

