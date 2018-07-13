package com.onezao.zao.recycleview0306;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onezao.zao.differentitem0306.DifferentItemActivity;
import com.onezao.zao.fourlevel0306.FourLevelActivity;
import com.onezao.zao.gridrecycleview0306.GridRecycleActivity;
import com.onezao.zao.gridrecycleview0306.GridRecycleVerticalActivity;
import com.onezao.zao.okhttp0306.OkHttpActivity;
import com.onezao.zao.practices.alertdialog0306.AlertDialogActivity;
import com.onezao.zao.practices.handlerdelay0306.DelayHandlerActivity;
import com.onezao.zao.practices.paintboard0306.PaintBoardActivity;
import com.onezao.zao.practices.picgallery0306.PicGalleryActivity;
import com.onezao.zao.practices.pressurediagram0306.PressureDiagramActivity;
import com.onezao.zao.practices.sendbroadcast0306.SendBroadcastActivity;
import com.onezao.zao.practices.viewanim0306.ViewAnimationActivity;
import com.onezao.zao.refreshrecycleview0306.RefreshActivity;
import com.onezao.zao.staggeredrecycleview0306.StaggeredActivity;
import com.onezao.zao.zaov.R;

public class RecycleViewActivity extends Activity {
    private Button btnMain;
    private RecyclerView mRecyclerView;
    private MyPicAdapter mMyAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_0306);

        initView();
        initItem();
    }

    private void initItem() {
//        (RecyclerView)findViewById(R.id)
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
/*        String[] data = new String[20];
        for (int i = 0; i < 20; i++) {
            data[i] = "item" + i;
        }*/

        String[] data = {this.getResources().getString(R.string.toGrid),"GridVertical","FourLevel","Staggered","DifferentItem","Refresh","OkHttp0626","AlertDialogActivity","PicGalleryActivity","DelayHandlerActivity", "ViewAnimationActivity","PaintBoardActivity",
                "SendBroadcastActivity","PressureDiagramActivity",
                "Zneo","Zsky","Zour","Zneo","Zsky","Zour","Zneo","Zsky","Zour",        "GridVertical","FourLevel","Staggered","DifferentItem","Zneo","Zsky","GridVertical","FourLevel","Staggered","DifferentItem","Zneo","Zsky"};
        mMyAdapter = new MyPicAdapter(this, data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMyAdapter);

        mMyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tag =(String)view.getTag();
                doItemClick(tag,position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecycleViewActivity.this,"TestItemOnLongClick", Toast.LENGTH_SHORT).show();
            }
        });


        btnMain = (Button) findViewById(R.id.btn_recycle);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyAdapter.data[0] = "葡萄";
                mMyAdapter.data[2] = "蜗牛";
                mMyAdapter.notifyItemChanged(0);
                mMyAdapter.notifyItemChanged(2);
            }
        });
        //长按恢复初始化状态
        btnMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                initView();
                return true;
            }
        });
    }

    //条目点击事件
    private void doItemClick(String text, int position) {
        Toast.makeText(this,text + "    " +position, Toast.LENGTH_SHORT).show();
        switch (text) {
            case "Grid":
                Intent intent = new Intent(this,GridRecycleActivity.class);
                startActivity(intent);
                return;
           case "GridVertical" :
                Intent intent2 = new Intent(this,GridRecycleVerticalActivity.class);
                startActivity(intent2);
                return;
            case "FourLevel" :
                Intent intent3 = new Intent(this,FourLevelActivity.class);
                startActivity(intent3);
                return;
             case "Staggered" :
                Intent intent4 = new Intent(this,StaggeredActivity.class);
                startActivity(intent4);
                return;
            case "DifferentItem" :
                Intent intent5 = new Intent(this,DifferentItemActivity.class);
                startActivity(intent5);
                return;
            case "Refresh" :
                Intent intent6 = new Intent(this,RefreshActivity.class);
                startActivity(intent6);
                return;
                //"AlertDialogActivity","ArrayAdapterActivity","ListViewActivity","MoreListViewActivity","SimpleAdapterActivity",
            case "OkHttp0626" :
                Intent intent7 = new Intent(this,OkHttpActivity.class);
                startActivity(intent7);
                return;
            case "AlertDialogActivity" :
                Intent intent8 = new Intent(this,AlertDialogActivity.class);
                startActivity(intent8);
                return;
            case "PicGalleryActivity" :
                Intent intent9 = new Intent(this,PicGalleryActivity.class);
                startActivity(intent9);
                return;
            case "DelayHandlerActivity" :
                Intent intent10 = new Intent(this,DelayHandlerActivity.class);
                startActivity(intent10);
                return;
            case "ViewAnimationActivity" :
                Intent intent11 = new Intent(this,ViewAnimationActivity.class);
                startActivity(intent11);
                return;
            case "PaintBoardActivity" :
                Intent intent12 = new Intent(this,PaintBoardActivity.class);
                startActivity(intent12);
                return;
            case "SendBroadcastActivity" :
                Intent intent13 = new Intent(this,SendBroadcastActivity.class);
                startActivity(intent13);
                return;
            case "PressureDiagramActivity" :
                Intent intent14 = new Intent(this,PressureDiagramActivity.class);
                startActivity(intent14);
                return;

        }
    }
}
