package com.onezao.zao.zaov;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.ZaoUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class HomeFragment extends Fragment {

    private Context mContext;
    RecyclerView mRecyclerView;
    List<DailyNotesInfo> mDailyNotesInfoList;
    private MyNotesAdapter mMyAdapter;
    DailyNotesDao mDao;

    private boolean mIsLoad = false;// 加载状态
    private int firstIndex = 0;  //首次查询数据的起始序号
    int mCount;

    private boolean isGetData = false;

     private Handler mHandler;
     View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onezao_status0306, null);

        mContext=getActivity();
        initData();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        //如果没有初始化，就做第一次初始化操作，不然就只需要更新就可以了
                        if(mMyAdapter == null){
                            initUI(view);
                   Log.i(ConstantValue.TAG,"onCreateView + initUI(view) ：" + ZaoUtils.getSystemTimeMore(1));
                        }  else {
                            mMyAdapter.notifyDataSetChanged();
                   Log.i(ConstantValue.TAG,"onCreateView + mMyAdapter.notifyDataSetChanged(); ：" + ZaoUtils.getSystemTimeMore(1));
                        }
                        break;
                    case 1:
                        //4.通知适配器，数据改变了。
                        mMyAdapter.notifyDataSetChanged();
                   Log.i(ConstantValue.TAG,"onCreateView + 自动添加数据成功！：" + ZaoUtils.getSystemTimeMore(1));
                        break;
                }
            }
        };

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(ConstantValue.TAG,"onCreate ：" + ZaoUtils.getSystemTimeMore(1));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(ConstantValue.TAG,"onResume ：" + ZaoUtils.getSystemTimeMore(1));
    }


    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
/*        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
//            GetData();
        } else {
            isGetData = false;
        }*/
        //切换之后，数据被销毁了，这里将 mMyAdapter 置为 null，重新进行数据加载
        mMyAdapter = null;
        //3.通过消息机制，通知主线程可以使用获取到的数据
        mHandler.sendEmptyMessage(0);
        Log.i(ConstantValue.TAG,"onCreateAnimator ：" + ZaoUtils.getSystemTimeMore(1));
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    /**
     * 初始化界面数据
     * @param view
     */
    private void initUI(View view) {

        for(DailyNotesInfo info : mDailyNotesInfoList){
            Log.i("Zao","Title = " + info.getTitle() + " Author = " + info.getAuthor() + " Content = " + info.getContent() + " Email = " + info.getEmail() + " Picpath = " + info.getPicpath() + " Time = " + info.getTime() );
        }

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


        /**
         * 监听RecycleView的滚动状态
         */
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            //滚动过程中，状态发生改变的时候调用该方法
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                 */
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();

//                RecyclerView.SCROLL_STATE_IDLE; //飞速滚动
//                RecyclerView.SCROLL_STATE_DRAGGING;//拖拽状态
//                RecyclerView.SCROLL_STATE_SETTLING;  //设置数据状态
                    /**
                     * 条件一：滚动到停止状态
                     * 条件二：最后一个条目可见（最后一个条目的索引值 >= 数据适配器中集合的总条目数 - 1）
                     */
                    if(newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastItemPosition >= mDailyNotesInfoList.size() -1
                            && !mIsLoad){
                        /**
                         * 1.mIsLoad防止重复加载而设置的变量
                         * 2.如果当前正在加载mIsLoad就会为true，本次加载完毕后，再将mIsLoad改为false
                         * 3. 下一次加载需要去做执行的时候，会判断上述mIsLoad变量是否为false，
                         * 4. mIsLoad 如果为true，就需要等待上一次加载完成，将其值改为false之后再去加载
                         */
                        //如果条目总数大于集合大小的时候，才可以继续加载更多
                        if(mCount > mDailyNotesInfoList.size()){
                            //加载下一页数据
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    //获取操作黑名单数据库的对象
                                    mDao = DailyNotesDao.getInstance(mContext);
                                    //2.查询获取到所有数据   //传入参数表示查询部分数据，开始做分页查询
                                    List<DailyNotesInfo> mData = mDao.find(mDailyNotesInfoList.size() + firstIndex);
                                    //3.添加下一页数据的过程
                                    mDailyNotesInfoList.addAll(mData);
                                    //3.通过消息机制，通知主线程可以使用获取到的数据
                                    mHandler.sendEmptyMessage(0);
                                }
                            }.start();

                        }  else {
                            Toast.makeText(mContext,"没有更多数据了！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            //滚动过程中，调用该方法
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //获取操作黑名单数据库的对象
                mDao = DailyNotesDao.getInstance(mContext);
                //2.查询获取到所有数据   //传入参数表示查询部分数据，开始做分页查询
                mDailyNotesInfoList = mDao.find(firstIndex);
                //2.1 获取数据库的数据条目数
                mCount = mDao.queryTotal();
                //3.通过消息机制，通知主线程可以使用获取到的数据
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(ConstantValue.TAG,"onPause ：" + ZaoUtils.getSystemTimeMore(1));
    }

    @Override
    public void onDestroyView() {
        Log.i(ConstantValue.TAG,"onDestroyView ：" + ZaoUtils.getSystemTimeMore(1));
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(ConstantValue.TAG,"onDestroy ：" + ZaoUtils.getSystemTimeMore(1));
        super.onDestroy();
    }
}
