package com.day16.jiangxin.homework.async;

/**
 * Created by my on 2016/7/28.
 */
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.day16.jiangxin.homework.Utils.Utils;
import com.day16.jiangxin.homework.data.Food;
import com.day16.jiangxin.homework.data.FoodEntity;
import com.google.gson.Gson;

public class JSONAsync extends AsyncTask<String, Void, List<Food>>{
    public onJsonSuccessListener mListener = null;
    public interface onJsonSuccessListener{
        public void onSuccessed(List<Food> list);
    }

    public void setOnJsonSuccessListener(onJsonSuccessListener listener){
        mListener = listener;
    }

    @Override
    protected List<Food> doInBackground(String... params) {
        List<Food> foodList = new ArrayList<>();
        String jsonStr = Utils.getTextFromUrl(params[0]);
        if(TextUtils.isEmpty(jsonStr)){
            return foodList;
        }
        Gson gson = new Gson();
        FoodEntity foodEntity = gson.fromJson(jsonStr, FoodEntity.class);
        for(int i = 0; i < foodEntity.data.size(); i++){
            Food food = new Food();
            food.title=foodEntity.data.get(i).title;
            food.pic=foodEntity.data.get(i).pic;
            foodList.add(food);
        }

        return foodList;
    }

    @Override
    protected void onPostExecute(List<Food> result) {
        if(mListener != null){
            mListener.onSuccessed(result);
        }
        super.onPostExecute(result);
    }
}
