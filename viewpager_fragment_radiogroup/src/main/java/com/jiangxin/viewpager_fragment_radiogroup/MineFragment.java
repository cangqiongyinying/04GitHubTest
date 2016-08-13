package com.jiangxin.viewpager_fragment_radiogroup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by my on 2016/8/1.
 */
public class MineFragment extends Fragment {

    private Context mContext;

    static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mine_fragment,container,false);
        return view;
    }
}
