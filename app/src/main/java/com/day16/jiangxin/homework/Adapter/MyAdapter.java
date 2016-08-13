package com.day16.jiangxin.homework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.day16.jiangxin.homework.R;
import com.day16.jiangxin.homework.data.Food;

import java.util.List;

/**
 * Created by my on 2016/7/29.
 */
public class MyAdapter extends BaseAdapter {
    private List<Food> mFoodList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<Food> list) {
        mContext = context;
        mFoodList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mFoodList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.simple_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.title_tv = (TextView) view
                    .findViewById(R.id.food_title_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        final Food food = mFoodList.get(position);

        viewHolder.title_tv.setText(food.title);
        return view;
    }

    private class ViewHolder {
        public TextView title_tv;
    }

}
