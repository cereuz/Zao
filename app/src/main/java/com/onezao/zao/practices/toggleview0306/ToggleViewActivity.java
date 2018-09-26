package com.onezao.zao.practices.toggleview0306;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.onezao.zao.zaov.R;

public class ToggleViewActivity extends AppCompatActivity {

    private ToggleView toggleView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggleview_view);
        
        toggleView = (ToggleView) findViewById(R.id.toggleView);
//        toggleView.setSwitchBackgroundResource(R.drawable.switch_background);
//        toggleView.setSlideButtonResource(R.drawable.slide_button);
//        toggleView.setSwitchState(true);
//        
        // 设置开关更新监听
        toggleView.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener(){

			@Override
			public void onStateUpdate(boolean state) {
				Toast.makeText(getApplicationContext(), "state: " + state, 0).show();
			}
        	
        });
    }

//	@Override
//	protected void onResume() {
//		super.onResume();
//	}
//    
}
