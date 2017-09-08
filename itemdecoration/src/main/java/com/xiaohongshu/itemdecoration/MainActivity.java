package com.xiaohongshu.itemdecoration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mRecyclerView.addItemDecoration(new DoubleRowStaggerdGridItemDecoration());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SimpleAdapter adapter = new SimpleAdapter(createList());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
//                mRecyclerView.invalidateItemDecorations();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
//                mRecyclerView.invalidateItemDecorations();
            }
        });
        adapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    private List<SimpleText> createList() {
        List<SimpleText> list = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            list.add(new SimpleText(c + ""));
        }
        return list;
    }
}
