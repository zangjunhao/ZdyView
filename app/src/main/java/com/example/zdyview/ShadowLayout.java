package com.example.zdyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;

public class ShadowLayout extends FrameLayout {
    private Context context;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 4;
    private final static int LEFT = 8;
    private final static int ALL = 15;
    private PorterDuffXfermode porterDuffXfermode;
    private float cornerRadius = 0f;
    private float shadowRadius = 0f;
    private int shadowColor = 0;
    private float dx = 0f;
    private float dy = 0f;
    private int borderColor = 0;
    private float borderWidth = 0f;
    private int shadowSides = ALL;

    private Paint shadowPaint;
    private Paint borderPaint;

    private RectF contentRect;
    private RectF borderRect;

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        initPaint(context);
        realPadding();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        initPaint(context);
        realPadding();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initAttrs(context, attrs);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        initPaint(context);
        realPadding();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void initPaint(Context context) {
        shadowPaint = new Paint();
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStrokeWidth(0f);
        shadowPaint.setColor(Color.WHITE);
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(0f);
        borderPaint.setColor(Color.RED);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImage);
        cornerRadius = a.getDimension(R.styleable.ShadowLayout_sl_cornerRadius, dip2px(context, 0f));
        shadowRadius = a.getDimension(R.styleable.ShadowLayout_sl_shadowRadius, dip2px(context, 0f));
        shadowColor = a.getColor(R.styleable.ShadowLayout_sl_shadowColor, Color.BLACK);
        dx = a.getDimension(R.styleable.ShadowLayout_sl_dx, dip2px(context, 0f));
        dy = a.getDimension(R.styleable.ShadowLayout_sl_dy, dip2px(context, 0f));
        borderColor = a.getColor(R.styleable.ShadowLayout_sl_borderColor, Color.RED);
        borderWidth = a.getDimension(R.styleable.ShadowLayout_sl_borderWidth, dip2px(context, 0f));
        shadowSides = a.getInt(R.styleable.ShadowLayout_sl_shadowSides, ALL);
        a.recycle();
    }

    private void realPadding() {
        int xPadding = (int) (shadowRadius + Math.abs(dx));
        int yPadding = (int) (shadowRadius + Math.abs(dy));
        int top = 0;
        int right = 0;
        int bottom = 0;
        int left = 0;
        if ((shadowSides & TOP) != 0) {
            top = yPadding;
        }
        if ((shadowSides & RIGHT) != 0) {
            right = xPadding;
        }
        if ((shadowSides & BOTTOM) != 0) {
            bottom = yPadding;
        }
        if ((shadowSides & LEFT) != 0) {
            left = xPadding;
        }
        setPadding(left, top, right, bottom);
    }


    private void drawShadow(Canvas canvas){
        canvas.save();
        shadowPaint.setShadowLayer(shadowRadius,dx,dy,shadowColor);
        canvas.drawRoundRect(contentRect,cornerRadius,cornerRadius,shadowPaint);
        shadowPaint.reset();
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentRect = new RectF((float) getPaddingLeft(), (float) getPaddingTop(), (float) (w - getPaddingRight()), (float) (h - getPaddingBottom()));
        float bw = borderWidth / 3;
        if(bw > 0)
        borderRect = new RectF(contentRect.left + bw, contentRect.top + bw, contentRect.right - bw, contentRect.bottom - bw);

    }

    private void drawChild(Canvas canvas ){

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
