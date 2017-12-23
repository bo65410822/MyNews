package com.lzhb.mynews.images.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzhb.mynews.R;

/**
 * 创建时间：2017/12/22 10:09
 * 作者：Li zhb
 * 功能描述：
 */

public class ImageFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image,null);
        
        return view;
    }
}
