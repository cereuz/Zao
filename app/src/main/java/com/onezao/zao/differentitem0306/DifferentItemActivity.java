package com.onezao.zao.differentitem0306;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.onezao.zao.recycleview0306.OnItemClickListener;
import com.onezao.zao.zaov.R;

import java.util.ArrayList;
import java.util.List;

public class DifferentItemActivity extends Activity {
    private RecyclerView recycler_view;
    private List<String> mData;
    private Context mContext;
    private DifferentItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_item_0306);

        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        //设置为垂直的样式
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        //使用的是系统默认的分割线
        recycler_view.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置适配器
        recycler_view.setAdapter(adapter=new DifferentItemAdapter(mContext,mData));
        //设置默认动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " long click",
                        Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void initData() {
        mData=new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++)
        {
            mData.add("" + (char) i);
        }
    }
}
