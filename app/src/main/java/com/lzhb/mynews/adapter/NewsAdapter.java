package com.lzhb.mynews.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhb.mynews.R;
import com.lzhb.mynews.beans.NewsBean;
import com.lzhb.mynews.utils.ImageLoaderUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * 创建时间：2017/12/26 13:34
 * 作者：Li zhb
 * 功能描述：
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    private Context context;
    private List<NewsBean> beans;

    private boolean mShowFooter = true;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setBeans(List<NewsBean> beans) {
        this.beans = beans;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,
                    parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_news, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new MyFooterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            NewsBean news = beans.get(position);
            if (news == null) {
                return;
            }
            ((MyViewHolder) holder).mTitle.setText(news.getTitle());
            ((MyViewHolder) holder).mDesc.setText(news.getDigest());
            ImageLoaderUtil.display(context, ((MyViewHolder) holder).mNewImg, news.getImgsrc());
        }
    }

    @Override
    public int getItemCount() {
        // 计算加上底部局
        int begin = mShowFooter ? 1 : 0;
        if (beans == null) {
            return begin;
        }
        return beans.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public NewsBean getItem(int position) {
        return beans == null ? null : beans.get(position);
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }

    public class MyFooterViewHolder extends RecyclerView.ViewHolder {
        public MyFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDesc;
        private ImageView mNewImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_news_title);
            mDesc = itemView.findViewById(R.id.tv_news_desc);
            mNewImg = itemView.findViewById(R.id.iv_news);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return onItemClickListener.onItemLongClick(view, getPosition());
                }
            });
        }
    }
}
