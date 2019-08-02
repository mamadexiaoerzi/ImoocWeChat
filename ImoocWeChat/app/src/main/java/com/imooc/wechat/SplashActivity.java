package com.imooc.wechat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.imooc.wechat.fragment.SplashFragment;
import com.imooc.wechat.view.transformer.ScaleTransformer;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mVpSplash;

    private int[] mResIds = {
            R.drawable.guide_image1,
            R.drawable.guide_image2,
            R.drawable.guide_image3,
            R.drawable.guide_image4,
            R.drawable.guide_image5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVpSplash = findViewById(R.id.vp_splash);
        mVpSplash.setOffscreenPageLimit(mResIds.length);
        // 此处每个Fragment都非常简单
        // FragmentStatePagerAdapter可以更好地管理内存，即占用尽可能少的内存
        mVpSplash.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SplashFragment.newInstance(mResIds[position]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        // ViewPager左右切换时添加动画
        // reverseDrawingOrder: 控制View在绘制其内部子View时的顺序，
        //                      若制作一种动画-上面盖住下面或下面盖住上面时，此参数可能有用
        // PageTransformer：接口，内部方法 void transformPage(@NonNull View page, float position);
        //                  在transformPage()内部决定动画的实际编码
        // 一行代码设置切换动画：参考(https://developer.android.google.cn/training/animation/screen-slide)
        mVpSplash.setPageTransformer(true, new ScaleTransformer());
    }

}
