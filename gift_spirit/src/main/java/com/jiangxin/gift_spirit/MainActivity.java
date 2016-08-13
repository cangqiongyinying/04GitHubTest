package com.jiangxin.gift_spirit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiangxin.gift_spirit.ImageLoader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL_1 = "http://www.1688wan.com/majax.action?method=getGiftList";
//    private String postParam;
    private  MyGiftHandler mHandler;
    private ListView mListView;
    private List<Gift> giftList;
    private Context mContext;
    public  static ImageLoader mImageLoader;
    private  GiftAdapter mGiftAdapter;
//    private MyPagerAdapter mPagerAdapter;
    private Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView= (ListView) findViewById(R.id.gift_listview);
        mContext=this;
        mImageLoader=mImageLoader.init(mContext);
//        initView();
        mHandler = new MyGiftHandler();
        loadNetDatas();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.gift_listview);
        List<String> datas=new ArrayList<>();
        for(int i=0;i<30;i++){
            datas.add("item"+i);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,datas);
        mListView.setAdapter(arrayAdapter);

    }


    private void loadNetDatas() {
        new Thread(new Runnable() {
                @Override
            public void run() {
                try {
                    URL url = new URL(URL_1);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    //传参数
       //             postParam="pageno=1";
                    connection.getOutputStream().write("pageno=1".getBytes());
                    connection.getOutputStream().flush();
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        StringBuilder builder = new StringBuilder();
                        while ((len = inputStream.read(buffer)) != -1) {
                            builder.append(new String(buffer,0,len));
                        }
                        String json = builder.toString();
                        mMessage=mHandler.obtainMessage();
                        mMessage.obj=json;
                        mHandler.sendMessage(mMessage);
//                        Log.d("androidxx", "run: " + MainActivity.this.giftList.size());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(mContext);
            mImageLoader.load(giftList.get(position%5).picUrl,imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class MyGiftHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObj = new JSONObject(msg.obj.toString());
                Log.d("androidxx", "handler json:" +msg.obj.toString());
                JSONArray jsonArray = jsonObj.getJSONArray("list");
                int length = jsonArray.length();
                List<Gift> list=new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("gname");
                    String giftType = jsonObject.getString("giftname");
                    String count = "剩余：" + jsonObject.getInt("number");
                    String time = jsonObject.getString("addtime");
                    String picUrl = "http://www.1688wan.com" + jsonObject.getString("iconurl");
                    Gift gift = new Gift(title, giftType, count, time, picUrl);
                    list.add(gift);
                }
                giftList=list;
                mGiftAdapter = new GiftAdapter();
                mListView.setAdapter(mGiftAdapter);
                Log.d("androidxx", "handler get:" + MainActivity.this.giftList.size());
                mGiftAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GiftAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return giftList == null ? 0 : giftList.size();
        }

        @Override
        public Object getItem(int i) {
            return giftList.get(i);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View itemView=null;
            final ViewHolder viewHolder;
            if (view==null){
                itemView= LayoutInflater.from(mContext).inflate(R.layout.simple_item,viewGroup,false);
                viewHolder = new ViewHolder(itemView);
            }else {
                itemView=view;
                viewHolder= (ViewHolder) itemView.getTag();
            }
            Gift gift = giftList.get(position);
            viewHolder.title_tv.setText(gift.title);
            viewHolder.gift_tv.setText(gift.giftType);
            viewHolder.count_tv.setText(gift.count);
            viewHolder.time_tv.setText(gift.time);
            mImageLoader.load(gift.picUrl,viewHolder.pic_iv);
            return view;
        }

        class ViewHolder{
            public ImageView pic_iv;
            public TextView title_tv;
            public TextView gift_tv;
            public TextView count_tv;
            public TextView time_tv;

            public ViewHolder(View view){
                view.setTag(this);
                pic_iv= (ImageView) view.findViewById(R.id.simple_item_image);
                title_tv= (TextView) view.findViewById(R.id.simple_item_title);
                gift_tv= (TextView) view.findViewById(R.id.simple_item_giftType);
                count_tv= (TextView) view.findViewById(R.id.simple_item_count);
                time_tv= (TextView) view.findViewById(R.id.simple_item_time);
            }
        }
    }
}
