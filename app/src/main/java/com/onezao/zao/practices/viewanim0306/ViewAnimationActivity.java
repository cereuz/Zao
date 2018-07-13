package com.onezao.zao.practices.viewanim0306;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.onezao.zao.zaov.R;

import java.io.IOException;

public class ViewAnimationActivity extends Activity {
    private ImageView iv_viewanim_0124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewanim_0124);
        iv_viewanim_0124 = (ImageView) findViewById(R.id.iv_viewanim_0124);
    }

    //选择相片
    public   void  gallery(View view){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, 0);
    }

    // 得到返回值，即选中的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photoBmp = null;
        if (data != null) {
            Uri uri = data.getData();
            iv_viewanim_0124.setImageURI(uri);
            try {
                photoBmp = ViewAnimUtils.getBitmapFormUri(this, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //透明
    public  void  alpha2(View view){
        Toast.makeText(getApplicationContext(), "alpha", Toast.LENGTH_SHORT).show();
        //AlphaAnimation透明度动画。
        //两个参数，第一个参数：动画开始的时候的透明度；第二个参数：动画结束的时候的透明度
        AlphaAnimation alphaAnim   =  new  AlphaAnimation(1.0f, 0.0f);
        //设置动画执行的时间，单位是毫秒值
        alphaAnim.setDuration(2000);
        //设置动画重复的模式,Animation.REVERSE反向执行，Animation.RESTART重复来一次
        alphaAnim.setRepeatMode(Animation.REVERSE);
        //设置动画重复次数，如果为0，就只执行一次动画。如果大于0这个设置才会生效
        alphaAnim.setRepeatCount(6);
        //设置动画执行之后执行动画的控件停留在结束的状态。默认是false。即执行完了恢复为初始状态。
        alphaAnim.setFillAfter(true);
        //把当前动画设置到ImageView上
        iv_viewanim_0124.setAnimation(alphaAnim);
        //开启动画
        alphaAnim.start();
    }

    //旋转
    public  void  rotate2(View  view){
        Toast.makeText(getApplicationContext(), "rotate", Toast.LENGTH_SHORT).show();
        //第一个参数：动画开始的时候处于的角度
        //第二个参数：动画结束的时候的角度
        RotateAnimation rotateAnim = new  RotateAnimation(0, 540);
        //设置执行的时长
        rotateAnim.setDuration(6000);
        //设置重复的模式
        rotateAnim.setRepeatMode(Animation.REVERSE);
        //设置重复的次数
        rotateAnim.setRepeatCount(3);
        //设置停留到结束的状态
        rotateAnim.setFillAfter(true);
        //把当前动画设置到ImageView上
        iv_viewanim_0124.setAnimation(rotateAnim);
        //开启动画
        rotateAnim.start();
    }

    //缩放
    public  void  scale2(View  view){
        Toast.makeText(getApplicationContext(), "scale", Toast.LENGTH_SHORT).show();
        ScaleAnimation scaleAnim = new  ScaleAnimation(1, 2,1,2);
        scaleAnim.setDuration(3000);
        scaleAnim.setRepeatCount(4);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        scaleAnim.setFillAfter(true);
        iv_viewanim_0124.setAnimation(scaleAnim);
        scaleAnim.start();
    }

    //平移
    public  void  translate2(View  view){
        Toast.makeText(getApplicationContext(), "translate", Toast.LENGTH_SHORT).show();
        TranslateAnimation translateAnim  =  new TranslateAnimation(10, 1000, 0, 600);
        translateAnim.setDuration(2000);
        translateAnim.setRepeatMode(Animation.REVERSE);
        translateAnim.setRepeatCount(4);
        translateAnim.setFillAfter(true);
        iv_viewanim_0124.setAnimation(translateAnim);
        translateAnim.start();
    }

    //组合
    public  void  set(View  view){
        Toast.makeText(getApplicationContext(), "set", Toast.LENGTH_SHORT).show();
        AnimationSet animSet   =  new  AnimationSet(false);

        //AlphaAnimation透明度动画。
        //两个参数，第一个参数：动画开始的时候的透明度；第二个参数：动画结束的时候的透明度
        AlphaAnimation    alphaAnim   =  new  AlphaAnimation(1.0f, 0.0f);
        //设置动画执行的时间，单位是毫秒值
        alphaAnim.setDuration(2000);
        //设置动画重复的模式,Animation.REVERSE反向执行，Animation.RESTART重复来一次
        alphaAnim.setRepeatMode(Animation.REVERSE);
        //设置动画重复次数，如果为0，就只执行一次动画。如果大于0这个设置才会生效
        alphaAnim.setRepeatCount(6);
        //设置动画执行之后执行动画的控件停留在结束的状态。默认是false。即执行完了恢复为初始状态。
        alphaAnim.setFillAfter(true);
        animSet.addAnimation(alphaAnim);

        //第一个参数：动画开始的时候处于的角度
        //第二个参数：动画结束的时候的角度
        RotateAnimation   rotateAnim = new  RotateAnimation(0, 540);
        //设置执行的时长
        rotateAnim.setDuration(6000);
        //设置重复的模式
        rotateAnim.setRepeatMode(Animation.REVERSE);
        //设置重复的次数
        rotateAnim.setRepeatCount(3);
        //设置停留到结束的状态
        rotateAnim.setFillAfter(true);
        animSet.addAnimation(rotateAnim);

        ScaleAnimation  scaleAnim = new  ScaleAnimation(1, 2,1,2);
        scaleAnim.setDuration(3000);
        scaleAnim.setRepeatCount(4);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        scaleAnim.setFillAfter(true);
        animSet.addAnimation(scaleAnim);

        TranslateAnimation   translateAnim  =  new TranslateAnimation(10, 1000, 0, 600);
        translateAnim.setDuration(2000);
        translateAnim.setRepeatMode(Animation.REVERSE);
        translateAnim.setRepeatCount(4);
        translateAnim.setFillAfter(true);
        animSet.addAnimation(translateAnim);

        iv_viewanim_0124.startAnimation(animSet);
    }




    //透明2
    public  void  alpha(View  view){
        Toast.makeText(getApplicationContext(), "alpha2", Toast.LENGTH_SHORT).show();
        //AlphaAnimation透明度动画。
        //两个参数，第一个参数：动画开始的时候的透明度；第二个参数：动画结束的时候的透明度
        AlphaAnimation    alphaAnim   =  new  AlphaAnimation(1.0f, 0.1f);
        //设置动画执行的时间，单位是毫秒值
        alphaAnim.setDuration(2000);
        //设置动画重复的模式,Animation.REVERSE反向执行，Animation.RESTART重复来一次
        alphaAnim.setRepeatMode(Animation.RESTART);
        //设置动画重复次数，如果为0，就只执行一次动画。如果大于0这个设置才会生效
        alphaAnim.setRepeatCount(6);
        //设置动画执行之后执行动画的控件停留在结束的状态。默认是false。即执行完了恢复为初始状态。
        alphaAnim.setFillAfter(true);
        //把当前动画设置到ImageView上
        iv_viewanim_0124.startAnimation(alphaAnim);
    }

    //旋转2
    public  void  rotate(View  view){
        Toast.makeText(getApplicationContext(), "rotate2", Toast.LENGTH_SHORT).show();
        //第一个参数：动画开始的时候处于的角度
        //第二个参数：动画结束的时候的角度
        float  fromDegrees = 0;
        float  toDegrees =  360;
        //旋转中心点X坐标的类型
        int pivoteXType  =  Animation.RELATIVE_TO_SELF;
        //旋转中心点X坐标的值
        float  pivotXValue  =  0.5f;
        //旋转中心点Y坐标的类型
        int pivoteYValue = Animation.RELATIVE_TO_SELF;
        //旋转中心点Y坐标的值
        float  pivotYValue  =  0.5f;
        RotateAnimation   rotateAnim = new  RotateAnimation(fromDegrees, toDegrees, pivoteXType, pivotXValue, pivoteYValue, pivotYValue);

        //设置执行的时长
        rotateAnim.setDuration(6000);
        //设置重复的模式
        rotateAnim.setRepeatMode(Animation.REVERSE);
        //设置重复的次数
        rotateAnim.setRepeatCount(3);
        //设置停留到结束的状态
        rotateAnim.setFillAfter(true);
        //把当前动画设置到ImageView上,开启动画
        iv_viewanim_0124.startAnimation(rotateAnim);
    }

    //缩放2
    public  void  scale(View  view){
        Toast.makeText(getApplicationContext(), "scale", Toast.LENGTH_SHORT).show();
        int  pivotXType  =  Animation.RELATIVE_TO_SELF;
        float  pivotXValue  = 0.5f;
        int  pivotYType  =  Animation.RELATIVE_TO_SELF;
        float  pivotYValue  = 0.5f;
        ScaleAnimation  scaleAnim = new  ScaleAnimation(1, 2,1,1,pivotXType,pivotXValue,pivotYType,pivotYValue);
        scaleAnim.setDuration(3000);
        scaleAnim.setRepeatCount(4);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        scaleAnim.setFillAfter(false);
        iv_viewanim_0124.startAnimation(scaleAnim);
    }

    //平移2
    public  void  translate(View  view){
        Toast.makeText(getApplicationContext(), "translate", Toast.LENGTH_SHORT).show();
        int  fromXType  =  Animation.RELATIVE_TO_PARENT;
        float  fromXValue = -0.5f;
        int  toXType  = Animation.RELATIVE_TO_PARENT;
        float  toXValue = 0.5f;
        int  fromYType  = Animation.RELATIVE_TO_PARENT;
        float fromYValue  =  - 0.5f;
        int  toYType  = Animation.RELATIVE_TO_PARENT;
        float  toYValue = 0.5f;
        TranslateAnimation   translateAnim  =  new TranslateAnimation(fromXType, fromXValue, toXType, toXValue,fromYType,fromYValue,toYType,toYValue);
        translateAnim.setDuration(2000);
        translateAnim.setRepeatMode(Animation.REVERSE);
        translateAnim.setRepeatCount(4);
        translateAnim.setFillAfter(false);
        //设置一个动画插入器，可以在执行的过程中加入加速，减速，重复等效果
        //接收参数Interpolator动画插入器，可以去找系统已经实现好的对象。
        translateAnim.setInterpolator(new BounceInterpolator());
        iv_viewanim_0124.startAnimation(translateAnim);
    }

    //组合2
    public  void  set2(View  view){

    }



    //加载顶部菜单，添加菜单的点击事件。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置左上角的图标的点击事件  ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anim_0124, menu);
        return true;
    }


    /*
     * 使用XML文件来定义动画     通过AnimationUtils的loadAnimation()方法
     */
    //透明
    public  void  alpha3(){
        Animation loadAlphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha0124);
        iv_viewanim_0124.startAnimation(loadAlphaAnimation);
    }
    //旋转
    public  void  rotate3(){
        Animation loadRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate0124);
        iv_viewanim_0124.startAnimation(loadRotateAnimation);
    }
    //缩放
    public  void  scale3(){
        Animation loadScaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale0124);
        iv_viewanim_0124.startAnimation(loadScaleAnimation);
    }
    //平移
    public  void  translate3(){
        Animation loadtranslateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate0124);
        iv_viewanim_0124.startAnimation(loadtranslateAnimation);
    }
    //组合
    public  void  animset3(){
        AnimationSet loadSetAnimation = (AnimationSet) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animset0124);
        iv_viewanim_0124.startAnimation(loadSetAnimation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.alpha) {
            Toast.makeText(this, "透明。。。", Toast.LENGTH_SHORT).show();
            alpha3();
      	 /*		Intent  intent  =  new  Intent(this,WritediaryActivity.class);
      	 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.rotate) {
            Toast.makeText(this, "旋转。。。", Toast.LENGTH_SHORT).show();
            rotate3();
      	 			/*			Intent  intent  =  new  Intent(this,CheckActivity.class);
      	 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.scale) {
            Toast.makeText(this, "缩放。。。", Toast.LENGTH_SHORT).show();
            scale3();
      	 /*			Intent  intent  =  new  Intent(this,SettingActivity.class);
      	 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.translate) {
            Toast.makeText(this, "平移。。。", Toast.LENGTH_SHORT).show();
            translate3();
      	 /*	    Intent  intent  =  new  Intent(this,AboutActivity.class);
      	 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.animset) {
            Toast.makeText(this, "组合", Toast.LENGTH_SHORT).show();
            animset3();
      	 			/*Intent  intent  =  new  Intent(this,AboutActivity.class);
      	 			startActivity(intent);*/
            return true;
        }   else  if (id == android.R.id.home) {
            Toast.makeText(this, "组合", Toast.LENGTH_SHORT).show();
            animset3();
      	 			/*Intent  intent  =  new  Intent(this,AboutActivity.class);
      	 			startActivity(intent);*/
            return true;
        }   else  if (id ==  R.id.action_search) {
            Toast.makeText(this, "搜索一下，onezao，去除重复", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
