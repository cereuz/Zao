package com.onezao.zao.practices.handlerdelay0306;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;
import com.onezao.zao.utils.ZaoUtils;

public class DelayHandlerActivity extends Activity {
    private static final long delayMillis = 1000;
    private TextView tv_handler_time;
    private TextView tv_handler_date;
    public String date;
    public  int count = 0;
    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            countDown();
        };
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_handler_1127);

        tv_handler_time = (TextView) findViewById(R.id.tv_handler_time);
        tv_handler_time.setText(0+"");
        tv_handler_date = (TextView) findViewById(R.id.tv_handler_date);
        tv_handler_date.setText(ZaoUtils.getSystemTime());
/*            	Message  msg =  Message.obtain();
            	//sendMessageDelayed 发送一条延迟执行的消息，这条消息会延迟1000毫秒执行
               handler.sendMessageDelayed(msg, delayMillis);*/
        //发送一条空消息，空消息，不是没有消息，而是消息不携带数据，传入的第一个参数是what，就是msg.what，可以用来区分不同的消息
        handler.sendEmptyMessageDelayed(1, delayMillis);
    }

    public void countDown(){
        date = ZaoUtils.getSystemTime();
        tv_handler_time.setText(count+1+"");
        tv_handler_date.setText(date);
        count++;
        Message  msg  =  Message.obtain();
        //sendMessageDelayed 发送一条延迟执行的消息，这条消息会延迟1000毫秒执行
        handler.sendMessageDelayed(msg, delayMillis);
    }


    //加载顶部菜单，添加菜单的点击事件。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置左上角的图标的点击事件  ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdminUtils.AdminMenu(DelayHandlerActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}
