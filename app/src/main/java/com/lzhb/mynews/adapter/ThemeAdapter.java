package com.lzhb.mynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzhb.mynews.R;
import com.lzhb.mynews.beans.ThemeBean;

import java.util.List;

/**
 * 创建时间：2017/12/25 13:42
 * 作者：Li zhb
 * 功能描述：
 */

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {

    private List<ThemeBean> mThemeBeans;
    private Context mContext;

    public ThemeAdapter(List<ThemeBean> mThemeBeans, Context mContext) {
        this.mThemeBeans = mThemeBeans;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.theme_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.themeItem.setImageResource(mThemeBeans.get(position).getId());
        holder.themeItem.setColorFilter(mThemeBeans.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return mThemeBeans != null ? mThemeBeans.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView themeItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            themeItem = itemView.findViewById(R.id.theme_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItemClickListener.onItemClick(getPosition());
                }
            });
        }
    }

    public interface MyItemClickListener {
        void onItemClick(int position);
    }

    private MyItemClickListener myItemClickListener;

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }
}
