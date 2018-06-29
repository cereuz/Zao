package com.onezao.zao.zaov;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.onezao.zao.recycleview0306.MyPicAdapter;
import com.onezao.zao.recycleview0306.OnItemClickListener;


/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class ProfileFragment extends Fragment {

    private Button btnMain;
    private RecyclerView mRecyclerView;
    private MyPicAdapter mMyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onezao_profile0306, null);
        initView(view);
        return view;
    }

        private void initView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_main_profile);
        String[] data = new String[200];
        for (int i = 0; i < 200; i++) {
            data[i] = "item" + i;
        }
        mMyAdapter = new MyPicAdapter(getActivity(), data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMyAdapter);

        //设置条目的点击事件
        mMyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                int position2 = (Integer)view.getTag(); //通过tag获取position
/*                MyPicAdapter.ViewHolder viewHolder = (MyPicAdapter.ViewHolder)mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(position));
                String textItem = viewHolder.tvItem.getText().toString();*/
                Toast.makeText(getActivity(),"TestItemOnClick ："+ view.getTag() + " ， " + position + " ， "  , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"TestItemOnLongClick ："+view.getTag()+ " ， " +position, Toast.LENGTH_SHORT).show();
            }
        });

        btnMain = (Button) view.findViewById(R.id.btn_recycle_profile);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyAdapter.data[0] = "葡萄";
                mMyAdapter.data[2] = "蜗牛";
                mMyAdapter.notifyItemChanged(0);
                mMyAdapter.notifyItemChanged(2);
            }
        });
    }
}