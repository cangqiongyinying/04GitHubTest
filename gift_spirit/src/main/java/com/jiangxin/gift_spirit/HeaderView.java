package com.jiangxin.gift_spirit;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by my on 2016/8/8.
 */
public class HeaderView {
    private int index;
    public HeaderView(View view , ListView listView,MainActivity.MyPagerAdapter adapter){
         final ViewPager viewPager= (ViewPager) view.findViewById(R.id.image_viewpager);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                viewPager.setCurrentItem(index%5);
                index ++;
                    sendEmptyMessageDelayed(1,2000);
            }
        };
        handler.sendEmptyMessageDelayed(1,2000);
        viewPager.setAdapter(adapter);
        listView.addHeaderView(view);
    }
}
