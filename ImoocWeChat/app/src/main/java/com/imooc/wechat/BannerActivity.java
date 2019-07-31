package com.imooc.wechat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.imooc.wechat.fragment.SplashFragment;
import com.imooc.wechat.view.transformer.RotateTransformer;
import com.imooc.wechat.view.transformer.ScaleTransformer;

public class BannerActivity extends AppCompatActivity {

    private ViewPager mVpBanner;

    private int[] mResIds = {
            R.drawable.banner_image1,
            R.drawable.banner_image2,
            R.drawable.banner_image3,
            R.drawable.banner_image4,
            R.drawable.banner_image5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mVpBanner = findViewById(R.id.vp_banner);
        // 解决滑动时图片闪现的问题
        mVpBanner.setOffscreenPageLimit(3);
        // 设置间隔，使用Transformer时此处可以不用设置，因为Transformer具有margin效果
        mVpBanner.setPageMargin(40); // 实际应使用 use dp！
        // 布局简单，可以使用PagerAdapter，没有必要使用fragment
        // fragment可以看作是一个比较大的复用单元
        mVpBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mResIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = new View(container.getContext());
                view.setBackgroundResource(mResIds[position]);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(lp);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                super.destroyItem(container, position, object); // 覆写会报错
                container.removeView((View) object);
            }
        });

        // 设置切换动画
//        mVpBanner.setPageTransformer(true, new ScaleTransformer());
        mVpBanner.setPageTransformer(true, new RotateTransformer());
    }

}
