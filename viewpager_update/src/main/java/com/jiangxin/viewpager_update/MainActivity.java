package com.jiangxin.viewpager_update;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Integer> imgList=new ArrayList<>();
    private ViewPager mVp;
    private LinearLayout mIndicatorLayout;
    private Context mContext;
    private MyPagerAdapter mPagerAdapter;
    private int count;
    private Boolean flag=false;
    private SharedPreferences mSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSp=getSharedPreferences("judge",Context.MODE_PRIVATE);
        //利用SharedPreference判断是否第一次打开
        if(mSp.getInt("count",0)!=0){
            startActivity(new Intent().setClass(MainActivity.this, UpdateActivity.class));
            MainActivity.this.finish();
        }else{
            setContentView(R.layout.activity_main);
            mContext=this;
            initView();
        }
    }

    private void initView(){
        loadDatas();
        mVp= (ViewPager) findViewById(R.id.hello_view_page);
        mIndicatorLayout= (LinearLayout) findViewById(R.id.indicator_layout);

        mPagerAdapter=new MyPagerAdapter();
        mVp.setAdapter(mPagerAdapter);
        count=mIndicatorLayout.getChildCount();
        controlIndicator(0);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                controlIndicator(position);
            }

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
                            Toast.makeText(MainActivity.this,"theLast",Toast.LENGTH_SHORT).show();
                            saveData();
                            startActivity(new Intent().setClass(MainActivity.this, UpdateActivity.class));
                            MainActivity.this.finish();
                        }
                        flag=true;
                        break;
                }
            }
        });
    }
    private void saveData(){
        SharedPreferences.Editor editor=mSp.edit();
        editor.putInt("count",1);
        editor.commit();
    }
    private void loadDatas(){
        imgList.add(R.drawable.app_start_1);
        imgList.add(R.drawable.app_start_2);
        imgList.add(R.drawable.app_start_3);
        imgList.add(R.drawable.app_start_4);
        imgList.add(R.drawable.app_start_5);
    }

    private void controlIndicator(int index) {
        ImageView view = (ImageView) mIndicatorLayout.getChildAt(index);
        //初始化所有的ImageView的enable属性为false
        for (int i = 0; i < count; i++) {
            ImageView childView = (ImageView) mIndicatorLayout.getChildAt(i);
            childView.setEnabled(false);
        }
        view.setEnabled(true);
    }


    public class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgList==null? 0:imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(mContext);
            imageView.setImageResource(imgList.get(position));
            imageView.setTag(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
