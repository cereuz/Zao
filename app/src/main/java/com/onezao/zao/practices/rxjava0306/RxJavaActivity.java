package com.onezao.zao.practices.rxjava0306;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.onezao.zao.utils.LogZ;
import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/14 15:12
 */
public class RxJavaActivity extends AppCompatActivity {
    TextView tv_rxjava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_view);

        tv_rxjava = (TextView) findViewById(R.id.tv_rxjava);

        // 步骤1：创建被观察者 Observable & 生产事件
        // 即 顾客入饭店 - 坐下餐桌 - 点菜

            // 1. 创建被观察者 Observable 对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生事件并通知观察者
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onComplete();
            }
        });

        // 步骤2：创建观察者 Observer 并 定义响应事件行为
        // 即 开厨房 - 确定对应菜式

        Observer<Integer> observer = new Observer<Integer>() {
            // 通过复写对应方法来 响应 被观察者
            @Override
            public void onSubscribe(Disposable d) {
                LogZ.e("开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                String text = "对Next事件 " + value + " 作出响应" + " ---- " + ZaoUtils.getSystemTimeMore(2);
                LogZ.e(text);
                tv_rxjava.setText(text);
            }

            @Override
            public void onError(Throwable e) {
                LogZ.e("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogZ.e("对Complete事件作出响应");
            }
        };

        // 步骤3：通过订阅（subscribe）连接观察者和被观察者
        // 即 顾客找到服务员 - 点菜 - 服务员下单到厨房 - 厨房烹调
        observable.subscribe(observer);



    /**
     * RxJava的流式操作
     * 链式调用
     */
        Observable.create(new ObservableOnSubscribe<String>() {
        // 1. 创建被观察者 & 生产事件
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("一");
            emitter.onNext("二");
            emitter.onNext("三");
            emitter.onComplete();
        }
    }).subscribe(new Observer<String>() {
        // 2. 通过通过订阅（subscribe）连接观察者和被观察者
        // 3. 创建观察者 & 定义响应事件的行为
        // 通过复写对应方法来 响应 被观察者
        @Override
        public void onSubscribe(Disposable d) {
            LogZ.e("2.开始采用subscribe连接");
        }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(String value) {
                String text = "2.对Next事件 " + value + " 作出响应" + " ---- " + ZaoUtils.getSystemTimeMore(2);
                LogZ.e(text);
                tv_rxjava.setText(text);
            }

            @Override
            public void onError(Throwable e) {
                LogZ.e("2.对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogZ.e("2.对Complete事件作出响应");
            }
       });
  }
}