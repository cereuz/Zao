package com.onezao.zao.practices.youdao0306;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.onezao.zao.utils.LogZ;
import com.onezao.zao.zaov.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/14 14:13
 */
public class YoudaoActivity extends AppCompatActivity {
    TextView tv_iciba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iciba_view);

        tv_iciba = (TextView) findViewById(R.id.tv_iciba);
    }

    public void click(View view) {
        request();
        // 使用Retrofit封装的方法
    }

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<TranslationYD> call = request.getCall("I love you");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<TranslationYD>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<TranslationYD> call, Response<TranslationYD> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                TranslationYD text = response.body();
                LogZ.e(text.getTranslateResult().get(0).get(0).getTgt());
                tv_iciba.setText(text.getTranslateResult().get(0).get(0).toString() );
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<TranslationYD> call, Throwable throwable) {
                LogZ.e("请求失败   -------  " + throwable.getMessage());
            }
        });
    }


}
