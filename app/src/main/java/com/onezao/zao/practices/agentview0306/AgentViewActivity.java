package com.onezao.zao.practices.agentview0306;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;
import com.onezao.zao.utils.LogZ;
import com.onezao.zao.zaov.R;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/15 11:13
 */
public class AgentViewActivity extends AppCompatActivity {

    LinearLayout m_agentview_ll;
    EditText et_agentview_web;
    String url = "https://www.douban.com";
    AgentWeb mAgentWeb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentview_view);

        initView();
        initAgentView();
    }

    private void initView() {
        m_agentview_ll = (LinearLayout)findViewById(R.id.m_agentview_ll);
        et_agentview_web = (EditText)findViewById(R.id.et_agentview_web);
    }


    /**
     * 初始化界面
     */
    private void initAgentView() {
        mAgentWeb = AgentWeb.with(AgentViewActivity.this)//传入Activity
                .setAgentWebParent(m_agentview_ll, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
//                .defaultProgressBarColor() // 使用默认进度条颜色
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url);
        }

    /**
     * 按钮的点击事件
     * @param view
     */
    public void go_web(View view) {
          LogZ.e("点击了加载按钮");
    }
}
