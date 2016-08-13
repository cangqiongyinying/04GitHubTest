package com.jiangxin.viewpager_update;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by my on 2016/7/29.
 */
public class UpdateActivity extends AppCompatActivity {
    private static String  text;
    private JsonAsync mJsonAsync;
    private static Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mContext=this;
        mJsonAsync=new JsonAsync();
        mJsonAsync.setOnJsonSuccessListener(listener);
        mJsonAsync.execute();
    }

    private JsonAsync.onJsonSuccessListener listener =new JsonAsync.onJsonSuccessListener() {

        @Override
        public void onSuccessed(UpdateMessage update) {
            text="当前版本:"+update.proto_ver+"\n检测到新版本:"+update.version+"\n是否升级？";
            MyDialogFragment myDialogFragment =new MyDialogFragment();
            myDialogFragment.show(getFragmentManager(),"dialog");
        }
    };

    public static class MyDialogFragment extends DialogFragment implements View.OnClickListener{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =inflater.inflate(R.layout.dialog_fragment_layout,container,false);
            TextView mainTv= (TextView) view.findViewById(R.id.main_tv);
            mainTv.setText(text);
            Button cancelBtn= (Button) view.findViewById(R.id.cancel_btn);
            Button updateBtn= (Button) view.findViewById(R.id.update_btn);
            updateBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
            getDialog().setTitle("提示");
            return view;
        }

        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.cancel_btn){
                dismiss();
            }else if (view.getId()==R.id.update_btn){
                Toast.makeText(mContext,"正在升级",Toast.LENGTH_SHORT).show();
                dismiss();
            }

        }
    }
}