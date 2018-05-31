package com.example.zdyview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by 67698 on 2018/5/24.
 */

public class MaoProgress extends ProgressBar {
    public MaoProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaoProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        getProgress()
    }
    public int dp2px(int dp)//dp转px
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    public int sp2px(int sp)//sp转px
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }
}
