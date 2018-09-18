package com.onezao.zao.zaov;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onezao.zao.adapter.MyNotesAdapter;
import com.onezao.zao.bean.DailyNotesInfo;
import com.onezao.zao.database.DailyNotesDao;
import com.onezao.zao.recycleview0306.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class StatusFragment extends Fragment {

    private Context mContext;
    RecyclerView mRecyclerView;
    List<DailyNotesInfo> mDailyNotesInfoList;
    private MyNotesAdapter mMyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onezao_status0306, null);

        mContext=getActivity();
        initData();
        initUI(view);
        return view;
    }

    /**
     * 初始化界面数据
     * @param view
     */
     private void initUI(View view) {

/*       //打印显示的数据
         for(DailyNotesInfo info : mDailyNotesInfoList){
             Log.i("Zao","Title = " + info.getTitle() + " Author = " + info.getAuthor() + " Content = " + info.getContent() + " Email = " + info.getEmail() + " Picpath = " + info.getPicpath() + " Time = " + info.getTime() );
         }*/

            //通过findViewById拿到RecyclerView实例
         mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_status);
         mMyAdapter = new MyNotesAdapter(mContext, mDailyNotesInfoList);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
    }

    private void initData() {
        DailyNotesDao dao = DailyNotesDao.getInstance(mContext);
        mDailyNotesInfoList = dao.findAll();
    }
}
