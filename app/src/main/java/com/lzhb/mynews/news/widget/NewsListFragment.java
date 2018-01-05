package com.lzhb.mynews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzhb.mynews.R;
import com.lzhb.mynews.adapter.NewsAdapter;
import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.commons.Urls;
import com.lzhb.mynews.news.persenter.NewsPersenter;
import com.lzhb.mynews.news.persenter.NewsPersenterImpl;
import com.lzhb.mynews.news.view.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017/12/26 11:02
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter adapter;
    private List<NewsBean> mData;

    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;

    private NewsPersenter mNewsPersenter;

    public static NewsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPersenter = new NewsPersenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.primary_light, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(getActivity().getApplicationContext());
        adapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        mNewsPersenter.loadNews(mType, pageIndex);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                //加载更多
                Log.e("TAG", "加载更多");
                mNewsPersenter.loadNews(mType, pageIndex + Urls.PAZE_SIZE);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            Log.e("TAG", "onScrolled: lastVisibleItem = " + lastVisibleItem);
        }
    };

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            if (mData.size() <= 0) {
                return;
            }
            NewsBean news = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news", news);
            View transitionView = view.findViewById(R.id.iv_news);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), transitionView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
        @Override
        public boolean onItemLongClick(View view, int position) {
            return false;
        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addNews(List<NewsBean> beanList) {
        adapter.isShowFooter(true);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(beanList);
        if (pageIndex == 0) {
            adapter.setBeans(mData);
        } else {
            //如果没有更多数据了就隐藏footer布局
            if (beanList == null || beanList.size() == 0) {
                adapter.isShowFooter(false);
            }
            adapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (pageIndex == 0) {
            adapter.isShowFooter(false);
            adapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() :
                getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, "加载数据失败！", Snackbar.LENGTH_SHORT).show();
    }

}
