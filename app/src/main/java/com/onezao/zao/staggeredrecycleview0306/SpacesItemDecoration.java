package com.onezao.zao.staggeredrecycleview0306;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * user:HRobbie
 * Date:2017/2/16
 * Time:14:02
 * 邮箱：hwwyouxiang@163.com
 * Description:Page Function.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceL;
    private int spaceR;
    private int spaceT;
    private int spaceB;

    public SpacesItemDecoration(int spaceL,int spaceR,int spaceT,int spaceB) {
        this.spaceL=spaceL;
        this.spaceR=spaceR;
        this.spaceT=spaceT;
        this.spaceB=spaceB;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=spaceL;
        outRect.right=spaceR;
        outRect.bottom=spaceB;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=spaceT;
        }
    }
}
