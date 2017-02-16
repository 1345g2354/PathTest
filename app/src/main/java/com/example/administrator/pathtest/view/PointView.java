package com.example.administrator.pathtest.view;

/**
 * Created by xuyongchao
 * on 2017/1/22.
 * 邮箱:18036699151@163.com
 * QQ：870867914
 */

import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.view.View;
/**
 * Created by admin on 2016/12/13.
 */
public class PointView extends View {
    private float startX,startY;
    private float endX,endY ;
    private Paint paint;
    private float t;
    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        startX = 100;
        startY = 100;
        endX = 300;
        endY = 300;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#e5e5e5"));
        canvas.drawLine(startX,startY,endX,endY,paint);
        if(t>=1.0){
            t = 1.0f;
        }
        float pointX = (1-t)*startX+t*endX;
        float pointY =(1-t)*startY+t*endY;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.drawPoint(pointX,pointY,paint);
//        canvas.drawLine(100,100,pointX,pointY,paint);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                t+=0.01;
                invalidate();
            }
        },50);
    }
}