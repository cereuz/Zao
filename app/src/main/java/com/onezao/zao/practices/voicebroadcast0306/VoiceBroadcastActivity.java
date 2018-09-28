package com.onezao.zao.practices.voicebroadcast0306;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.zaov.R;
import com.yzy.voice.VoiceBuilder;
import com.yzy.voice.VoicePlay;
import com.yzy.voice.VoiceTextTemplate;

public class VoiceBroadcastActivity extends AppCompatActivity {
    private boolean mCheckNum;

    private EditText editText;
    private Button btPlay;
    private Button btDel;
    private LinearLayout llMoneyList;
    private Switch switchView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_broadcast_view);
        mContext = VoiceBroadcastActivity.this;
        initView();
        initClick();
    }

    void initView() {
        editText = findViewById(R.id.edittext);
        btPlay = findViewById(R.id.bt_play);
        btDel = findViewById(R.id.bt_del);
        llMoneyList = findViewById(R.id.ll_money_list);
        switchView = findViewById(R.id.switch_view);
    }

    public void initClick(){
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = editText.getText().toString().trim();
                if (TextUtils.isEmpty(amount)) {
                    Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                VoicePlay.with(getApplicationContext()).play(amount, mCheckNum);

                llMoneyList.addView(getTextView(amount), 0);
                editText.setText("");
            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llMoneyList.removeAllViews();
            }
        });

        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckNum = b;
            }
        });
    }

    TextView getTextView(String amount) {
        VoiceBuilder voiceBuilder = new VoiceBuilder.Builder()
                .start("success")
                .money(amount)
                .unit("yuan")
                .checkNum(mCheckNum)
                .builder();

        StringBuffer text = new StringBuffer()
                .append("角标: ").append(llMoneyList.getChildCount())
                .append("\n")
                .append("输入金额: ").append(amount)
                .append("\n");
        if (mCheckNum) {
            text.append("全数字式: ").append(VoiceTextTemplate.genVoiceList(voiceBuilder).toString());
        } else {
            text.append("中文样式: ").append(VoiceTextTemplate.genVoiceList(voiceBuilder).toString());
        }

        TextView view = new TextView(mContext);
        view.setPadding(0, 8, 0, 0);
        view.setText(text.toString());
        return view;
    }
}
