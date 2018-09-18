package com.onezao.zao.okhttp0306;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.zaov.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends Activity implements View.OnClickListener{

    TextView tv_code_0409;
    TextView tv_message_0409;
    TextView tv_body_0409;
    EditText et_okhttp_0626;

    String et_url_login_0626 = "http://59.110.166.176:8080/msr/login/register";
    String et_url_ranking_0626 = "http://59.110.166.176:8080/msr/Ranking/rank";
    String et_url_authorList_0626 = "http://59.110.166.176:8080/msr/index/authorList";

    private SharedPreferencesHelper sharedPreferencesHelper;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //通过Bundle发送
            Bundle bundle = msg.getData();
            int responseCode = bundle.getInt("responseCode");
            String responseMessage = bundle.getString("responseMessage");
            String responseBody = bundle.getString("responseBody");
            tv_code_0409.setText(String.valueOf(responseCode));
            tv_message_0409.setText(responseMessage);
            tv_body_0409.setText(responseBody);

            //选择发送消息
/*           switch (msg.what){
                case 100 :
                    String responseCode =(String)msg.obj;
//                    Toast.makeText(OkHttpActivity0409.this, responseCode, Toast.LENGTH_SHORT).show();
                    tv_code_0409.setText(responseCode);
                case 101 :
                    String responseMessage =(String)msg.obj;
//                    Toast.makeText(OkHttpActivity0409.this, responseMessage, Toast.LENGTH_SHORT).show();
                    tv_body_0409.setText(responseMessage);
                case 102 :
                    String responseBody =(String)msg.obj;
//                    Toast.makeText(OkHttpActivity0409.this, responseBody, Toast.LENGTH_SHORT).show();
                    tv_message_0409.setText(responseBody);
            }*/
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_0306);

        Button btn_okhttp_zero = (Button) findViewById(R.id.btn_okhttp_zero);
        btn_okhttp_zero.setOnClickListener(this);
        Button btn_okhttp_one = (Button) findViewById(R.id.btn_okhttp_one);
        btn_okhttp_one.setOnClickListener(this);
        Button btn_okhttp_two = (Button) findViewById(R.id.btn_okhttp_two);
        btn_okhttp_two.setOnClickListener(this);


        tv_code_0409 = (TextView) findViewById(R.id.tv_code_0626);
        tv_message_0409 = (TextView) findViewById(R.id.tv_message_0626);
        tv_body_0409 = (TextView) findViewById(R.id.tv_body_0626);


        et_okhttp_0626 = (EditText) findViewById(R.id.et_okhttp_0626);

        sharedPreferencesHelper = new SharedPreferencesHelper(OkHttpActivity.this, "auth0626");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_okhttp_zero:
                et_okhttp_0626.setText(et_url_login_0626);
                getLogin(et_url_login_0626);
                break;
            case R.id.btn_okhttp_one:  //主播列表
//                Toast.makeText(this,"okhttp0409----https://www.baidu.com",Toast.LENGTH_SHORT).show();
                et_okhttp_0626.setText(et_url_authorList_0626);
                getDatasync(et_url_authorList_0626);
                break;
            case R.id.btn_okhttp_two:    // 排行榜
                String auth = sharedPreferencesHelper.getSharedPreference("Authorization","").toString().trim();
                et_okhttp_0626.setText(et_url_ranking_0626);
                getRanking(et_url_ranking_0626,auth);
                break;
        }
    }

    //用户登录
    private void getLogin(final String et_url_login_0626) {
        {
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //POST传递请求参数
                            FormBody.Builder params=new FormBody.Builder();
                            params.add("Authorization", ConstantValue.AUTHORIZATION);
                            params.add("phone", "13282380039");
                            params.add("code", "012345");
                            params.add("version", "1.0.1");
                            params.add("device", "honor");

                            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                            Request request = new Request.Builder()
                                    .url(et_url_login_0626)//请求接口。如果需要传参拼接到接口后面。
                                    .post(params.build())
                                    .build();//创建Request 对象
                            Response response = null;
                            response = client.newCall(request).execute();//得到Response 对象
                            if (response.isSuccessful()) {
                                //直接obtain
                                Message msg = Message.obtain();
                                msg.what = 100;

/*                        msg.obj = "response.code()=="+response.code();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 101;
                        msg.obj = "response.message()=="+response.message();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 102;
                        msg.obj = "res=="+response.body().string();
                        handler.sendMessage(msg);//发送消息*/


                                int responseCode = response.code();
                                String responseMessage = response.message();
                                String responseBody =  response.body().string();
                                //传递一个数据量较大的数据
                                Bundle bundle = new Bundle();
                                bundle.putInt("responseCode", response.code());
                                bundle.putString("responseMessage", responseMessage);
                                bundle.putString("responseBody", responseBody);
                                msg.setData(bundle);
                                handler.sendMessage(msg);//发送消息

                                //存储  Authorization 到  SharePrefences
                                String Auth= parseJSON(responseBody);
                                sharedPreferencesHelper.put("Authorization",Auth);

                                Log.d("zneo","response.code()=="+responseCode);
                                Log.d("zneo","response.message()=="+responseMessage);
                                Log.d("zneo","resBody=="+responseBody);
                                //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

    }

    //排行榜
    public void getRanking(final String et_url, final String auth){
        //解析登录之后的JSON 的  Authorization值
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //POST传递请求参数
                        FormBody.Builder params=new FormBody.Builder();
                        params.add("Authorization", auth);
                        params.add("type", "3");
                        params.add("page", "0");
                        params.add("version", "1.0.1");
                        params.add("device", "honor");

                        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                        Request request = new Request.Builder()
                                .url(et_url)//请求接口。如果需要传参拼接到接口后面。
                                .post(params.build())
                                .build();//创建Request 对象
                        Response response = null;
                        response = client.newCall(request).execute();//得到Response 对象
                        if (response.isSuccessful()) {
                            //直接obtain
                            Message msg = Message.obtain();
                            msg.what = 100;

/*                        msg.obj = "response.code()=="+response.code();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 101;
                        msg.obj = "response.message()=="+response.message();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 102;
                        msg.obj = "res=="+response.body().string();
                        handler.sendMessage(msg);//发送消息*/

                            //传递一个数据量较大的数据
                            Bundle bundle = new Bundle();
                            bundle.putInt("responseCode", response.code());
                            bundle.putString("responseMessage", response.message());
                            bundle.putString("responseBody", response.body().string());
                            msg.setData(bundle);
                            handler.sendMessage(msg);//发送消息

                            Log.d("zneo","response.code()=="+response.code());
                            Log.d("zneo","response.message()=="+response.message());
                            Log.d("zneo","resBody=="+response.body().string());
                            //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    //查询主播列表
    public void getDatasync(final String et_url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //POST传递请求参数
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("Authorization", ConstantValue.AUTHORIZATION);
                    params.add("type", "2");
                    params.add("page", "0");
                    params.add("version", "1.0.1");
                    params.add("device", "honor");

                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(et_url)//请求接口。如果需要传参拼接到接口后面。
                            .post(params.build())
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        //直接obtain
                        Message msg = Message.obtain();
                        msg.what = 100;

/*                        msg.obj = "response.code()=="+response.code();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 101;
                        msg.obj = "response.message()=="+response.message();
//                        handler.sendMessage(msg);//发送消息

                        msg.what = 102;
                        msg.obj = "res=="+response.body().string();
                        handler.sendMessage(msg);//发送消息*/

                        //传递一个数据量较大的数据
                        Bundle bundle = new Bundle();
                        bundle.putInt("responseCode", response.code());
                        bundle.putString("responseMessage", response.message());
                        bundle.putString("responseBody", response.body().string());
                        msg.setData(bundle);
                        handler.sendMessage(msg);//发送消息

                        Log.d("zneo","response.code()=="+response.code());
                        Log.d("zneo","response.message()=="+response.message());
                        Log.d("zneo","resBody=="+response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析JSon字符串
    public String parseJSON(String json){
        Gson gson = new Gson();
        Login userLogin = gson.fromJson(json,Login.class);
        Data userData = userLogin.getData();
        String userAuthorization = userData.getAuthorization();
        return userAuthorization;
    }
}
