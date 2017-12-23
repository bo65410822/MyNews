package com.lzhb.mynews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzhb.mynews.R;

/**
 * 创建时间：2017/12/22 10:01
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsFragment extends Fragment {
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("新闻"));
        mTabLayout.addTab(mTabLayout.newTab().setText("娱乐"));
        mTabLayout.addTab(mTabLayout.newTab().setText("体育"));
        mTabLayout.addTab(mTabLayout.newTab().setText("笑话 "));
        return view;
    }
}
