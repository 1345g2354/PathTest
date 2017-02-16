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
public class TwoCurveView extends View {
    private float startX,startY;
    private float endX,endY ;
    private float contorlX = 200,contorlY = 60;//默认值
    private Paint paint;
    private float t;
    private Path path;
    public TwoCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        startX = 60;
        startY = 350;
        endX = 450;
        endY = 350;
        path = new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(startX,startY);
        path.quadTo(contorlX,contorlY,endX,endY);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_MOVE){
//            contorlX = event.getX();
//            contorlY = event.getY();
            contorlX = event.getRawX();
            contorlY = event.getRawY();

            invalidate();
        }
        return true;
    }
}