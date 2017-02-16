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
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * Created by admin on 2016/12/13.
 */
public class DemoCurveView extends View {
    private Path path;
    private Paint paint;
    private float contorlx1, contorly1;//控制点

    public DemoCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        path = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                contorlx1 = event.getX();
                contorly1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                path.quadTo(contorlx1, contorly1, endX, endY);
                contorlx1 = endX;
                contorly1 = endY;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}