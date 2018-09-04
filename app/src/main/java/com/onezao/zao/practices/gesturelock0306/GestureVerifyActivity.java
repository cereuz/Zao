package com.onezao.zao.practices.gesturelock0306;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.practices.gesturelock0306.widget.GestureLockViewGroup;
import com.onezao.zao.zaov.R;

import java.util.List;


/**
 * Created by zhangshuqing on 16/9/24.
 */
public class GestureVerifyActivity extends AppCompatActivity {

    GestureLockViewGroup gestureLockViewGroup;

    TextView gesture_verify_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        initView();
        gestureLockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {

            }

            @Override
            public void onGestureEvent(boolean matched) {
                if(!matched){
                    //设置动画
                    Animation anim= AnimationUtils.loadAnimation(GestureVerifyActivity.this,R.anim.shake);
                    gesture_verify_tip.setVisibility(View.VISIBLE);
                    gesture_verify_tip.startAnimation(anim);
                }else {
                    gesture_verify_tip.setVisibility(View.INVISIBLE);
                    Toast.makeText(GestureVerifyActivity.this,"校验成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUnmatchedExceedBoundary() {

            }

            @Override
            public void onChooseWay(List<Integer> list) {

            }
        });


    }

    private void initView() {
        gestureLockViewGroup= (GestureLockViewGroup) findViewById(R.id.gesture_verify_lockView);
        gesture_verify_tip= (TextView) findViewById(R.id.gesture_verify_tip);
    }

}
