package com.example.zdyview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImage extends ImageView {
        private Paint paint1=new Paint(Paint.ANTI_ALIAS_FLAG);
        private Bitmap bitmap1;
        private BitmapShader bitmapShader;//着色器，后面用图片着色
        int  radius;
        public CircleImage(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (getDrawable() == null)
            {
                return;
            }
            myshader();//尽量把无关代码写在了外面
            canvas.drawCircle(radius,radius,radius,paint1);
        }

        private void myshader() {
            Matrix matrix = new Matrix();//矩阵用于后面缩放图片
            Bitmap rawBitmap = drawableToBitamp(getDrawable());
            if (rawBitmap != null) {
                int viewWidth = getWidth();
                int viewHeight = getHeight();
                int min = Math.min(viewWidth, viewHeight);
                float dstWidth = min;
                float dstHeight = min;
                if (bitmapShader == null || !rawBitmap.equals(bitmap1)) {
                    bitmap1 = rawBitmap;
                    bitmapShader = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                }
                if (bitmapShader != null) {
                    matrix.setScale(dstWidth / rawBitmap.getWidth(), dstHeight / rawBitmap.getHeight());
                    bitmapShader.setLocalMatrix(matrix);
                    //setScale和Local一起使用用于缩放图片
                }
                paint1.setShader(bitmapShader);//设置shader
                radius=min/2;
            }
        }
        private Bitmap drawableToBitamp(Drawable drawable)
        {//将drawable 转换为bitamp
            if (drawable instanceof BitmapDrawable)
            {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                return bitmapDrawable.getBitmap();
            }
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        }
    }



