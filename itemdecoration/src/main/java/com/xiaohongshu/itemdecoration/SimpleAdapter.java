package com.xiaohongshu.itemdecoration;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wupengjian on 17/8/14.
 */
public class SimpleAdapter extends RecyclerView.Adapter {

    private List<SimpleText> mStrList;
    private RecyclerView mRecyclerView;

    public SimpleAdapter(List<SimpleText> strList) {
        if (strList == null) {
            mStrList = new ArrayList<>();
        } else {
            mStrList = new ArrayList<>(strList);
        }
    }

    public void setRecyclerView(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        return new SimpleHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SimpleHolder simpleHolder = (SimpleHolder) holder;
        final SimpleText text = mStrList.get(position);
        simpleHolder.textView.setHeight(text.height);
//        模拟每次高度发生变化的情况，这样item每次回收重排列后相对位置都会发生改变，可以测试双倍间距的情况
//        simpleHolder.textView.setHeight(text.randomHeight());
        simpleHolder.textView.setText(text.text);
        simpleHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mStrList.indexOf(text);
                mStrList.remove(index);
                notifyItemRemoved(index);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.invalidateItemDecorations();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrList.size();
    }

    private class SimpleHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView textView;

        public SimpleHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
        }
    }
}
