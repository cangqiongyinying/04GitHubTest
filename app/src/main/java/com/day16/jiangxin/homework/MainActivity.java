package com.day16.jiangxin.homework;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.day16.jiangxin.homework.Adapter.MyAdapter;
import com.day16.jiangxin.homework.async.JSONAsync;
import com.day16.jiangxin.homework.data.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String URL = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=1";
    private ListView mLv;
    private JSONAsync mJsonAsync;
    private List<Food> mList;
    private MyAdapter mAdapter;
    private FragmentManager mFragmentManager;
    private MyFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv= (ListView) findViewById(R.id.check_lv);
        mJsonAsync=new JSONAsync();
        mJsonAsync.setOnJsonSuccessListener(listener);
        mJsonAsync.execute(URL);
        mLv.setOnItemClickListener(itemClicked);
        mFragmentManager = getSupportFragmentManager();
    }

    private JSONAsync.onJsonSuccessListener listener =new JSONAsync.onJsonSuccessListener() {
        @Override
        public void onSuccessed(List<Food> list) {
            mList=list;
            mAdapter=new MyAdapter(MainActivity.this, mList);
            mLv.setAdapter(mAdapter);
        }
    };

    private AdapterView.OnItemClickListener itemClicked=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String picUrl=mList.get(position).pic;
            passValueToFragment(picUrl);
        }
    };
    private void passValueToFragment(String content) {

        //传值到Fragment
        mFragment = MyFragment.newInstance(content);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment_change, this.mFragment);
        fragmentTransaction.commit();
        mFragment.downLoadPic(content);
    }
}
