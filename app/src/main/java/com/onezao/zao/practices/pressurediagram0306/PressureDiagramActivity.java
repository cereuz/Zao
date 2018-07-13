package com.onezao.zao.practices.pressurediagram0306;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;
import com.onezao.zao.zaov.ZaoUtils;

public class PressureDiagramActivity extends Activity {
    static {
        System.loadLibrary("PressureDiagram");
    }
    private ProgressBar pb_pressure_0130;
    private int pb_max_0130 = 800;
    private EditText et_pressure_0130;
    private Button btn_pdstart_0130;
    private MyPressureView pb_mypressure_0130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressurediagram_0130);

        btn_pdstart_0130 = (Button)findViewById(R.id.btn_pdstart_0130);
        et_pressure_0130 = (EditText) findViewById(R.id.et_pressure_0130);
        pb_pressure_0130 = (ProgressBar) findViewById(R.id.pb_pressure_0130);
        pb_mypressure_0130 = (MyPressureView) findViewById(R.id.pb_mypressure_0130);
        pb_pressure_0130.setMax(pb_max_0130);
    }

    public  void  startOrStop(View view){
        String btn_text = btn_pdstart_0130.getText().toString().trim();
        if(btn_text.equals("Start")|| btn_text.equals("开始")){
            btn_pdstart_0130.setText("Stop");
            //C方法运行在主线程，主线程不能做耗时操作。不然会堵塞
            new  Thread(){
                public void run(){
                    startMonitor();
                }
            }.start();
        } else if (btn_text.equals("Stop")||btn_text.equals("结束")){
            stopMonitor();
            btn_pdstart_0130.setText("Start");
        }
    }

/*    public  void  start(View view){
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        //C方法运行在主线程，主线程不能做耗时操作。不然会堵塞
        new  Thread(){
            public void run(){
                startMonitor();
            }
        }.start();
    }

    public  void  stop(View  view){
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        stopMonitor();
    }*/

    public void setPressure(final int pressure){
        pb_pressure_0130.setProgress(pressure);
        pb_mypressure_0130.setPressure(pressure);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_pressure_0130.setText(String.valueOf(pressure)+"|"+ ZaoUtils.getSystemTime());
            }
        });
    }

    //模拟开启，模拟停止
    public  native void  startMonitor();
    public  native void  stopMonitor();



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

        AdminUtils.AdminMenu(PressureDiagramActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}
