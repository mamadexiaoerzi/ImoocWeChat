package com.imooc.wechat;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.imooc.wechat.fragment.TabFragment;
import com.imooc.wechat.utils.L;
import com.imooc.wechat.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityWithTab extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现", "我"));

    private TabView mTabWeChat;
    private TabView mTabFriend;
    private TabView mTabFind;
    private TabView mTabMine;

    private List<TabView> mTabs = new ArrayList<>();

    // SparseArray[key-int,value-Object]，Android特有的
    // 类似于Map，但效率比Map高很多
    // 多Tab的情况下，Fragment的集合管理
    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";
    private int mCurrTabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_tab);

        if (savedInstanceState != null) {
            mCurrTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS, 0);
        }

        initViews();

        initViewPagerAdapter();

        initEvents();
    }

    /*
       屏幕旋转处理：存储数据，Activity重建时恢复
         屏幕旋转后   ViewPager记住了之前的状态
                    TabView未记住，默认选择第一个
      */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_POS, mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    // Tab点击事件
    private void initEvents() {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVpMain.setCurrentItem(finalI, false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    private void initViewPagerAdapter() {
        /*
         设置ViewPager的缓存，设置大一点，不希望这4个Fragment在用户切换时有任何不好的体验效果
         正常情况设置 2 ，这4个Fragment就能完全被缓存
         设置为mTitles.size()，扩展时就不用再修改
         */
        mVpMain.setOffscreenPageLimit(mTitles.size());
        /*
          FragmentPagerAdapter：左右滑动时Fragment并未被销毁。
          FragmentStatePagerAdapter：左右滑动时Fragment被销毁。
         */
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                // 屏幕旋转后，onCreate会重新执行，getItem没有执行
                // 因为Fragment特性：当Activity发生旋转/后台销毁重建时，可以恢复上一次的Fragment对象
                // 恢复的代码是由ViewPager和FragmentPagerAdapter去做的
                TabFragment fragment = TabFragment.newInstance(mTitles.get(position));
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            // 界面上的多个Fragment与mFragments一一对应
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                L.d("onPageScrolled pos = " + position + " , positionOffset = " + positionOffset);

                // 左->右：pos 0->1，left:pos，right:pos + 1；positionOffset 0 ~ 1
                // left progress: 1 ~ 0 (1 - positionOffset);right progress: 0 ~ 1 (positionOffset)

                // 右->左：pos 1->0，left:pos，right:pos + 1；positionOffset 1 ~ 0
                // left progress: 0 ~ 1 (1 - positionOffset);right progress: 1 ~ 0 (positionOffset)

                // left tab
                // right tab
                if (positionOffset > 0) {
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);

                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                L.d("onPageSelected pos = " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mVpMain = findViewById(R.id.vp_main);
        mTabWeChat = findViewById(R.id.tab_weChat);
        mTabFriend = findViewById(R.id.tab_friend);
        mTabFind = findViewById(R.id.tab_find);
        mTabMine = findViewById(R.id.tab_mine);

        mTabWeChat.setIconAndText(R.drawable.wechat, R.drawable.wechat_select, "微信");
        mTabFriend.setIconAndText(R.drawable.friend, R.drawable.friend_select, "通讯录");
        mTabFind.setIconAndText(R.drawable.find, R.drawable.find_select, "发现");
        mTabMine.setIconAndText(R.drawable.mine, R.drawable.mine_select, "我");

        mTabs.add(mTabWeChat);
        mTabs.add(mTabFriend);
        mTabs.add(mTabFind);
        mTabs.add(mTabMine);

        // 默认选中第一个
        setCurrentTab(mCurrTabPos);
    }

    private void setCurrentTab(int pos) {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            if (i == pos) {
                tabView.setProgress(1);
            } else {
                tabView.setProgress(0);
            }
        }
    }
}
