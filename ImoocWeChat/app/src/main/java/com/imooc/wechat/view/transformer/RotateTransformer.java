package com.imooc.wechat.view.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.imooc.wechat.utils.L;

/**
 * 下方旋转动画
 * 自己思考：上方旋转
 *
 * @author PanJ
 * @date 2019/7/31 16:14
 */
public class RotateTransformer implements ViewPager.PageTransformer {

    private static final int MAX_ROTATE = 15; // 最大旋转角度

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

        // 重点:  设置旋转中心 - setPivotX
        //        设置旋转角度 - setRotation

        if (position < -1) { // (-∞, -1)
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
            page.setRotation(-MAX_ROTATE);
        } else if (position <= 1) { // [0, 1]
            if (position < 0) { // 左边的页面 a

                // a -> b :
                //      position:[0, -1]
                // a    PivotX: [0.5w, w] = 0.5w * [1, 2]
                //      PivotY: 不变
                //      Rotation: [0, -MAX_ROTATE] = MAX_ROTATE * [0, -1]
                page.setPivotX(0.5f * (1 - position) * page.getWidth());
                page.setPivotY(page.getHeight());
                page.setRotation(MAX_ROTATE * position);

                // b -> a : 同上
                //      position:[-1, 0]
                // a    PivotX: [w, 0.5w] = 0.5w * [2, 1]
                //      PivotY: 不变
                //      Rotation: [-MAX_ROTATE, 0] = MAX_ROTATE * [-1, 0]

            } else { // 右边的页面 b

                // a -> b :
                //      position:[1, 0]
                // b    PivotX: [0w, 0.5w] = 0.5w * [0, 1]
                //      PivotY: 不变
                //      Rotation: [MAX_ROTATE, 0] = MAX_ROTATE * [1, 0]
                page.setPivotX(0.5f * (1 - position) * page.getWidth());
                page.setPivotY(page.getHeight());
                page.setRotation(MAX_ROTATE * position);

                // b -> a : 同上
                //      position:[0, 1]
                // b    PivotX: [0.5w, 0w] = 0.5w * [1, 0]
                //      PivotY: 不变
                //      Rotation: [0, MAX_ROTATE] = MAX_ROTATE * [0, 1]

            }
        } else { // (1, +∞)
            page.setPivotX(0);
            page.setPivotY(page.getHeight());
            page.setRotation(MAX_ROTATE);
        }
    }
}
