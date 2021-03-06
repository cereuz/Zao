package com.onezao.zao.zaov;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.onezao.zao.gridrecycleview0306.BaseAdapter;
import com.onezao.zao.gridrecycleview0306.DividerGridItemDecoration;
import com.onezao.zao.recycleview0306.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class GroupFragment extends Fragment {

    private RecyclerView recycler_view;
    private List<String> mData;
    private Context mContext;
    private BaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onezao_group0306, null);

        mContext=getActivity();
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        //设置为垂直网格样式
        recycler_view.setLayoutManager(new GridLayoutManager(mContext,4));
        //设置为水平网格样式
//        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));
        ////使用的是自定义的网格分割线
        recycler_view.addItemDecoration(new DividerGridItemDecoration(mContext));
        //设置默认动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recycler_view.setAdapter(adapter=new BaseAdapter(mContext,mData));

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