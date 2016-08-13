package com.day16.jiangxin.homework.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.day16.jiangxin.homework.MainActivity;
import com.day16.jiangxin.homework.Utils.Utils;

/**
 * Created by my on 2016/7/28.
 */
public class ImgAsync extends AsyncTask<String, Void, Bitmap> {

    public onImgDownloadListener mListener;

    public void setImgDownloadListener(onImgDownloadListener listener) {
        mListener = listener;
    }

    public interface onImgDownloadListener {
        public void onImgDownloadSuccess(Bitmap bm);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bm = Utils.getBitmapFromUrl(params[0]);
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (mListener != null) {
            mListener.onImgDownloadSuccess(result);
        }
        super.onPostExecute(result);
    }

}

