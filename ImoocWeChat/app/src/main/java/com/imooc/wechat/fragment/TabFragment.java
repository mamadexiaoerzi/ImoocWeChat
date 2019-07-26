package com.imooc.wechat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.imooc.wechat.R;

/**
 * @author PanJ
 * @date 2019/7/26 11:17
 */
public class TabFragment extends Fragment {
    public static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static TabFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
            mTitle = arguments.getString(BUNDLE_KEY_TITLE,"");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
    }
}
