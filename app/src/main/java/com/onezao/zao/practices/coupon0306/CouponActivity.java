package com.onezao.zao.practices.coupon0306;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.onezao.zao.zaov.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/4/10 11:03
 */
public class CouponActivity extends AppCompatActivity {

    TabLayout mTab;
    ViewPager mPager;

    private List<String> titles = Arrays.asList("半圆边缘", "虚线边框", "半圆虚线", "自定义属性", "自定义ImageView");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        initView();

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTab.setupWithViewPager(mPager);
    }

    private void initView() {
        mTab = (TabLayout)findViewById(R.id.coupon_tab);
        mPager = (ViewPager)findViewById(R.id.coupon_pager);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CouponSemiCircleFragment();
                case 1:
                    return new CouponDashLineFragment();
                case 2:
                    return new CouponCombinationFragment();
                case 3:
                    return new CouponCustomFragment();
                case 4:
                    return new CouponImageViewFragment();
                default:
                    return new CouponSemiCircleFragment();
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }

}
