package com.example.zdyview;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 67698 on 2018/3/28.
 */

public class BoLangView extends View{
    private static int BoLangColor=0x882E84FF;//半透明的，这样的更好看
    private static int ZhenFu=30;//振幅，就是波浪起伏的大小
    private static int BoLangHeight=100;//波浪的平均高度线
    private static int Speed1=7;//第一条波浪的速度
    private static int Speed2=5;//弟二条波浪的速度
    private int X1_YI;//记录第一个波浪移动距离
    private int X2_YI;//记录第二个波浪移动距离
    private Paint BoLangPaint;
    private int BoLang_width;//view总宽
    private int BoLang_height;//view总高
    private float[] WeiFen;
    public BoLangView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BoLangPaint=new Paint();
        BoLangPaint.setStyle(Paint.Style.STROKE);
        BoLangPaint.setAntiAlias(true);
        BoLangPaint.setColor(BoLangColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里用的是微分法，用数组平分x，每一个x都对应着一个y值
        BoLang_height=h;
        BoLang_width=w;
        WeiFen=new float[BoLang_width];
        float Wmg=(float)(2*Math.PI/BoLang_width);//皮一下，沃米嘎（手动滑稽）代表周期
        for(int i=0;i<BoLang_width;i++)
        {
            WeiFen[i]=(float)(ZhenFu*Math.sin(Wmg*i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG ));//canvans除去锯齿
        for(int i=0,j=0,k=0;i<BoLang_width;i++)
        {
            if(i+X1_YI<BoLang_width){
                canvas.drawLine(i,BoLang_height-WeiFen[X1_YI+i]-BoLangHeight,i,BoLang_height,BoLangPaint);
            }
            else {//如果已经过了一个周期，则重新开始，需要j的参与
                canvas.drawLine(i,BoLang_height-WeiFen[j]-BoLangHeight,i,BoLang_height,BoLangPaint);
                j++;
            }
            if(i+X2_YI<BoLang_width){
                canvas.drawLine(i,BoLang_height-WeiFen[X2_YI+i]-BoLangHeight,i,BoLang_height,BoLangPaint);
            }
            else {//同上需要x的参与
                canvas.drawLine(i,BoLang_height-WeiFen[k]-BoLangHeight,i,BoLang_height,BoLangPaint);
                k++;
            }

        }
        X1_YI+=Speed1;
        X2_YI+=Speed2;
        if(X1_YI>BoLang_width)X1_YI=0;
        if(X2_YI>BoLang_width)X2_YI=0;
        new Thread(runnable).start();
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(20);
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
