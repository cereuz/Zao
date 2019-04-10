package com.onezao.zao.practices.coupon0306;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.onezao.zao.zaov.R;
import com.zao.coupon.CouponView;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/4/10 14:06
 */
public class CouponCustomFragment extends Fragment {

    CouponView mCouponView;
    CheckBox mSemicircleTop;
    CheckBox mSemicircleBottom;
     CheckBox mSemicircleLeft;
     CheckBox mSemicircleRight;
     CheckBox mDashLineTop;
     CheckBox mDashLineBottom;
     CheckBox mDashLineLeft;
     CheckBox mDashLineRight;
    SeekBar mSbSemicircleRadius;
    SeekBar mSbSemicircleCap;
    SeekBar mSbDashLineLength;
    SeekBar mSbDashLineGap;
    SeekBar mSbDashLineHeight;
    SeekBar mSbTopDashLineMargin;
    SeekBar mSbBottomDashLineMargin;
    SeekBar mSbLeftDashLineMargin;
    SeekBar mSbRightDashLineMargin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupon_custom, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view) {
        mCouponView =  view.findViewById(R.id.couponView);
        mSemicircleTop =  view.findViewById(R.id.semicircle_top);
        mSemicircleBottom =  view.findViewById(R.id.semicircle_bottom);
        mSemicircleLeft =  view.findViewById(R.id.semicircle_left);
        mSemicircleRight = view.findViewById(R.id.semicircle_right);
        mDashLineTop = view.findViewById(R.id.dash_line_top);
        mDashLineBottom = view.findViewById(R.id.dash_line_bottom);
        mDashLineLeft = view.findViewById(R.id.dash_line_left);
        mDashLineRight = view.findViewById(R.id.dash_line_right);
        mSbSemicircleRadius = view.findViewById(R.id.sbSemicircleRadius);
        mSbSemicircleCap = view.findViewById(R.id.sbSemicircleCap);
        mSbDashLineLength = view.findViewById(R.id.sbDashLineLength);
        mSbDashLineGap = view.findViewById(R.id.sbDashLineGap);
        mSbDashLineHeight = view.findViewById(R.id.sbDashLineHeight);
        mSbTopDashLineMargin = view.findViewById(R.id.sbTopDashLineMargin);
        mSbBottomDashLineMargin = view.findViewById(R.id.sbBottomDashLineMargin);
        mSbLeftDashLineMargin = view.findViewById(R.id.sbLeftDashLineMargin);
        mSbRightDashLineMargin = view.findViewById(R.id.sbRightDashLineMargin);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSemicircleTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleTop(isChecked);
            }
        });
        mSemicircleBottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleBottom(isChecked);
            }
        });
        mSemicircleLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleLeft(isChecked);
            }
        });
        mSemicircleRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleRight(isChecked);
            }
        });

        mDashLineTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineTop(isChecked);
            }
        });
        mDashLineBottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineBottom(isChecked);
            }
        });
        mDashLineLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineLeft(isChecked);
            }
        });
        mDashLineRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineRight(isChecked);
            }
        });
        mSbSemicircleRadius.setProgress((int) mCouponView.getSemicircleRadius());
        mSbSemicircleRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setSemicircleRadius(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSbSemicircleCap.setProgress((int) mCouponView.getSemicircleGap());
        mSbSemicircleCap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setSemicircleGap(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineLength.setProgress((int) mCouponView.getDashLineLength());
        mSbDashLineLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineLength(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineGap.setProgress((int) mCouponView.getDashLineGap());
        mSbDashLineGap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineGap(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineHeight.setProgress((int) mCouponView.getDashLineHeight() * 10);
        mSbDashLineHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineHeight(dp2Px(progress) / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbTopDashLineMargin.setProgress((int) mCouponView.getDashLineMarginTop());
        mSbTopDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginTop(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSbBottomDashLineMargin.setProgress((int) mCouponView.getDashLineMarginBottom());
        mSbBottomDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginBottom(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbLeftDashLineMargin.setProgress((int) mCouponView.getDashLineMarginLeft());
        mSbLeftDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginLeft(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbRightDashLineMargin.setProgress((int) mCouponView.getDashLineMarginRight());
        mSbRightDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginRight(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private int dp2Px(float dp) {
        return (int) (dp * getActivity().getResources().getDisplayMetrics().density + 0.5f);
    }

}

