package com.example.zdyview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by 67698 on 2018/6/21.
 */


public class MButton extends AppCompatButton{
    int mLastX=0;
    int mLastY=0;
    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = x - mLastX; // x方向移动量
                int deltaY = y - mLastY; // y方向移动量

                int translationX = (int) (getTranslationX() + deltaX); // x方向平移deltaX
                int translationY = (int) (getTranslationY() + deltaY); // y方向平移deltaY
                setTranslationX( translationX);
                setTranslationY( translationY);

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        // 更新位置
        mLastX = x;
        mLastY = y;
        return true;
    }
}
