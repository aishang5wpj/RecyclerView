package com.xiaohongshu.demo4copy;

import android.graphics.Color;
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
        mRecyclerView.addItemDecoration(
                new DoubleRowStaggerdGridItemDecoration()
                        .setColor(Color.YELLOW)
                        .setHeight(16));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SimpleAdapter adapter = new SimpleAdapter(createList());
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
