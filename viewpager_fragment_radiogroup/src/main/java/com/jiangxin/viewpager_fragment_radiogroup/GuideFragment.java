package com.jiangxin.viewpager_fragment_radiogroup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangxin.viewpager_fragment_radiogroup.MineFragment;
import com.jiangxin.viewpager_fragment_radiogroup.R;

/**
 * Created by my on 2016/8/1.
 */
public class GuideFragment extends Fragment {
    private Context mContext;

    static GuideFragment newInstance() {
        GuideFragment guideFragment = new GuideFragment();
        return guideFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.guide_fragment,container,false);
        return view;
    }
}
