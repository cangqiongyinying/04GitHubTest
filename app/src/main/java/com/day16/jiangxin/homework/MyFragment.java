package com.day16.jiangxin.homework;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.day16.jiangxin.homework.async.ImgAsync;


public class MyFragment extends Fragment {
    private ImageView mIv;
    public static final String ARGS1 = "content";
    private String content;

    public static MyFragment newInstance(String content) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS1,content);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString(ARGS1);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list,container);
        mIv= (ImageView) view.findViewById(R.id.fragment_iv);
        return view;
    }

    public void downLoadPic(String content){
        ImgAsync imgAsync = new ImgAsync();
        imgAsync.setImgDownloadListener(new ImgAsync.onImgDownloadListener() {
            @Override
            public void onImgDownloadSuccess(Bitmap bm) {
                mIv.setImageBitmap(bm);
            }
        });
        imgAsync.execute(content);
    }
}
