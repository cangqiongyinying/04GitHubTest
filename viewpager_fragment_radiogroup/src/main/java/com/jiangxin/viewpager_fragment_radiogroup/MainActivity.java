package com.jiangxin.viewpager_fragment_radiogroup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVp;
    private List<Fragment> fragments = new ArrayList<>();
    private MyFragmentAdapter mFragmentAdapter;
    private RadioGroup mRg;
    private boolean mUserCheckRadio=false;
    private boolean mUserChangePage=false;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        loadDatas();
        mVp= (ViewPager) findViewById(R.id.fragment_viewpager);
        mFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mVp.setAdapter(mFragmentAdapter);
        mVp.addOnPageChangeListener(onPageChangeListener);
        mRg= (RadioGroup) findViewById(R.id.viewpager_change_radio);
        mRg.check(R.id.radio_change_hot);
        //RaidoGroup监听，点击改变Group内按钮时响应：将ViewPager设置为相对应的page
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
                mUserCheckRadio=true;
                if(mUserChangePage){
                    mUserChangePage=false;
                    return;
                }
                switch (radioId){
                    case R.id.radio_change_hot:
                       mVp.setCurrentItem(0);
                        break;
                    case R.id.radio_change_guide:
                        mVp.setCurrentItem(1);
                        break;
                    case R.id.radio_change_category:
                        mVp.setCurrentItem(2);
                        break;
                    case R.id.radio_change_mine:
                        mVp.setCurrentItem(3);
                        break;
                }
                mUserChangePage=false;
            }
        });
    }

    /*
    * 载入fragment，放入fragments链表中
    * */
    private void loadDatas(){
        fragments.add(HotFragment.newInstance());
        fragments.add(GuideFragment.newInstance());
        fragments.add(CategoryFragment.newInstance());
        fragments.add(MineFragment.newInstance());
    }

    /*
    * 页面改变监听，若点击按钮则不响应，若移动page则改变按钮的check
    * */
    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mUserChangePage=true;
            if (mUserCheckRadio){
                mUserCheckRadio=false;
                return;
            }
            changeRaido(position);
            Log.v("pageChange",position+"");
            mUserCheckRadio=false;
        }

        /*
        * 滑动状态监听，使页面可循环滑动
        * */
        @Override
        public void onPageScrollStateChanged(int state) {

            switch (state){
                case ViewPager.SCROLL_STATE_DRAGGING:
                    flag=false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    flag=true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (mVp.getCurrentItem() == mVp.getAdapter().getCount() - 1 &&!flag) {
                        mVp.setCurrentItem(0);
                    }else if (mVp.getCurrentItem() == 0 &&!flag){
                        mVp.setCurrentItem(mVp.getAdapter().getCount() - 1);
                    }
                    flag=true;
                    break;
            }

        }
    };

    /*
    * 传入position改变选择按钮
    * */
    private void changeRaido(int index){
        RadioButton radioButton= (RadioButton) mRg.getChildAt(index);
        mRg.check(radioButton.getId());
    }

    /*
    * 自定义fragmentAdapter，将Fragment加入ViewPager中
    * */
    class MyFragmentAdapter extends FragmentStatePagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

}
