package com.onezao.zao.zaov;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.recycleview0306.MyPicAdapter;
import com.onezao.zao.utils.LogZ;
import com.onezao.zao.view.BGView;
import com.onezao.zao.view.Lauar;
import com.onezao.zao.view.TimeUtils;


/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class ProfileFragment extends Fragment {

    private Context mContext;
    private Button btnMain;
    private RecyclerView mRecyclerView;
    private MyPicAdapter mMyAdapter;

    private BGView          mBgView;
    private Button          mBt;
    private TextView        mTvNowDate;
    private TextView        mTvOldDate;
    private Button          select;

    private EditText        year;
    private EditText        month;
    private EditText        day;

    private SensorManager   mSensorManager;// 传感器管理对象
    private Sensor          mOrientationSensor;// 传感器对象
    private LocationManager mLocationManager;// 位置管理对象
    private String          mLocationProvider;// 位置提供者名称，GPS设备还是网络

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext=getActivity();
        //初始化传感器
        initServices();
        View view = inflater.inflate(R.layout.fragment_onezao_profile02bg, null);
        initView(view);
        initData();
        initClick();
        return view;

    }

    /**
     * 初始化数据
     */
    private void initData() {
        String time = TimeUtils.formatTime(System.currentTimeMillis(), TimeUtils.DATE_TYPE6);
        mTvNowDate.setText(time);
        int    nowYear  = TimeUtils.getNowYear();
        int    nowMonth = TimeUtils.getNowMonth();
        int    nowDate  = TimeUtils.getNowDate();
        String lunar    = Lauar.getLunar(nowYear + "", nowMonth + "", nowDate + "");
        mTvOldDate.setText(lunar);
    }

    private void initClick() {
        mBgView.setOnDownActionListener(new BGView.OnDownActionListener() {
            @Override
            public void OnDown(int x, int y) {
                // TODO Auto-generated method stub
                LogZ.e("down x = " + x + " y = " + y);
            }
        });
        mBgView.setOnMoveActionListener(new BGView.OnMoveActionListener() {
            @Override
            public void OnMove(int x, int y) {
                // TODO Auto-generated method stub
                LogZ.e("move x = " + x + " y = " + y);
            }
        });
        mBgView.setOnUpActionListener(new BGView.OnUpActionListener() {
            @Override
            public void OnUp(int x, int y) {
                // TODO Auto-generated method stub
                LogZ.e("up x = " + x + " y = " + y);
            }
        });
    }


    // 初始化传感器和位置服务
    private void initServices() {
        // sensor manager
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        // location manager
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();// 条件对象，即指定条件过滤获得LocationProvider
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 较高精度
        criteria.setAltitudeRequired(true);// 是否需要高度信息
        criteria.setBearingRequired(false);// 是否需要方向信息
        criteria.setCostAllowed(true);// 是否产生费用
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 设置电耗最高的
        mLocationProvider = mLocationManager.getBestProvider(criteria, true);// 获取条件最好的Provider

    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        mBgView = (BGView) view.findViewById(R.id.bgview);
        mBt = (Button) view.findViewById(R.id.bt);
        mTvNowDate = (TextView) view.findViewById(R.id.tv_now_date);
        mTvOldDate = (TextView) view.findViewById(R.id.tv_old_date);
        select = (Button) view.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectBirth();
            }
        });
    }

    private void selectBirth() {
        //选择生日
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View inflate = View.inflate(mContext, R.layout.time_layout, null);
        builder.setView(inflate);

        year = (EditText) inflate.findViewById(R.id.year);
        month = (EditText) inflate.findViewById(R.id.month);
        day = (EditText) inflate.findViewById(R.id.day);

        builder.setTitle("请设置生日");
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String submit = submit();
                if(TextUtils.isEmpty(submit)){
                    Toast.makeText(mContext, "你没有填写任何内容！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(mContext, submit, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private String submit() {
        // validate
        String yearString = year.getText()
                .toString()
                .trim();
        if (TextUtils.isEmpty(yearString)) {
            Toast.makeText(mContext, "请输入年份", Toast.LENGTH_SHORT)
                    .show();
            return yearString;
        }

        String monthString = month.getText()
                .toString()
                .trim();
        if (TextUtils.isEmpty(monthString)) {
            Toast.makeText(mContext, "请输入月份", Toast.LENGTH_SHORT)
                    .show();
            return yearString;
        }

        String dayString = day.getText()
                .toString()
                .trim();
        if (TextUtils.isEmpty(dayString)) {
            Toast.makeText(mContext, "请输入日", Toast.LENGTH_SHORT)
                    .show();
            return yearString;
        }

        // TODO validate success, do something
        if (Integer.valueOf(monthString) < 1 || Integer.valueOf(monthString) > 12){
            Toast.makeText(mContext, "月份不合法", Toast.LENGTH_SHORT)
                    .show();
            return yearString;
        }
        if (Integer.valueOf(dayString) < 1 || Integer.valueOf(dayString) > 31){
            Toast.makeText(mContext, "日期不合法", Toast.LENGTH_SHORT)
                    .show();
            return yearString;
        }

        return yearString + monthString + dayString;
    }
}