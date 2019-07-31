package com.imooc.wechat.view.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.imooc.wechat.utils.L;

/**
 * @author PanJ
 * @date 2019/7/31 16:14
 */
public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    /* 左滑 -1，右滑 +1
       a -> b                  b -> c
       a - position:(0, -1)    a - position:(-1, -2)
       b - position:(1, 0)     b - position:(0, -1)
       c - position:(2, 1)     c - position:(1, 0)
       d - position:(3, 2)     d - position:(2, 1)
       e - position:(4, 3)     e - position:(3, 2)

       b -> a                  c -> b
       a - position:(-1, 0)    a - position:(-2, -1)
       b - position:(0, 1)     b - position:(-1, 0)
       c - position:(1, 2)     c - position:(0, 1)
       d - position:(2, 3)     d - position:(1, 2)
       e - position:(3, 4)     e - position:(2, 3)
    */

    @Override
    public void transformPage(@NonNull View page, float position) {
        L.d(page.getTag() + ", position = " + position);

        if (position < -1) { // (-∞, -1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        } else if (position <= 1) { // [0, 1]
            if (position < 0) { // 左边的页面 a

                // a -> b :
                //      position:[0, -1]
                // a    scale: [1, MIN_SCALE]
                //      alpha: [1, MIN_ALPHA]
                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);

                float alphaA = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                page.setAlpha(alphaA);

                // b -> a : 同上
                //      position:[-1, 0]
                // a    scale: [MIN_SCALE, 1]
                //      alpha: [MIN_ALPHA, 1]

            } else { // 右边的页面 b
                // a -> b :
                //      position:[1, 0]
                // b    scale: [MIN_SCALE, 1]
                //      alpha: [MIN_ALPHA, 1]
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);

                float alphaB = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                page.setAlpha(alphaB);

                // b -> a : 同上
                //      position:[0, 1]
                // b    scale: [1, MIN_SCALE]
                //      alpha: [1, MIN_ALPHA]
            }
        } else { // (1, +∞)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }
}
