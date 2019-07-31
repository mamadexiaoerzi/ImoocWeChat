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
 * 可复用
 *
 * @author PanJ
 * @date 2019/7/26 11:17
 */
public class TabFragment extends Fragment {
    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public interface OnTitleClickListener {
        void onClick(String title);
    }

    private OnTitleClickListener mListener;

    public void setOnTitleClickListener(OnTitleClickListener listener) {
        mListener = listener;
    }

    /**
     * 使用Arguments，于界面的销毁与恢复中有重要的作用
     */
    public static TabFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE, title);
        TabFragment tabFragment = new TabFragment();
        // 应用进入后台，可能被系统杀死，系统恢复TabFragment时，并不知道我们之前通过tabFragment做了什么事情
        // tabFragment.mTitle = title; // 系统在重建时，已被销毁
        tabFragment.setArguments(bundle); // 系统在重建时，Arguments里的内容还能够被恢复
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_KEY_TITLE, "");
        }
//        L.d("onCreate, title = " + mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        L.d("onCreateView, title = " + mTitle);
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 activity 对象
//                // 写法1：BUG
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.changeWeChatTab("微信changed!");
//                // 写法2：可行-恶心
//                Activity activity = getActivity();
//                if (activity instanceof MainActivity) {
//                    ((MainActivity) activity).changeWeChatTab("微信changed!");
//                }
                // 问题在于：我们Fragment会触发一些事件，Activity去响应这些事件
                if (mListener != null) {
                    mListener.onClick("微信changed!");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        L.d("onDestroyView, title = " + mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        L.d("onDestroy, title = " + mTitle);
    }

    public void changeTitle(String title) {
        // changeTitle()在Fragment的onCreateView()或onViewCreated()还未创建时执行是没有意义的
        // 强制执行可能会报错：空指针或Fragment not attach
        if (!isAdded()) { // 或isResumed()
            return;
        }
        mTvTitle.setText(title);
    }
}
