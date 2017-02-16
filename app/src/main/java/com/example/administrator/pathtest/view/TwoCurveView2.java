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
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by admin on 2016/12/13.
 */
public class TwoCurveView2 extends View {
    private float startX,startY;
    private float endX,endY ;
    private float contorlX = 200,contorlY = 60;//默认值
    private Paint paint;
    private float t;
    private Path path;
    private List<PointF> points;//存储曲线上运动的点
    public TwoCurveView2(Context context, AttributeSet attrs) {
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
        points = new ArrayList<>();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(t>=1.0f){
            t=1.0f;
        }
        //绘制2根线  起点-控制点  控制点-终点
        paint.setColor(Color.parseColor("#e5e5e5"));
        canvas.drawLine(startX,startY,contorlX,contorlY,paint);
        canvas.drawLine(contorlX,contorlY,endX,endY,paint);
        ////这是绘制辅助线说明轨迹的运动情况 轨迹的运动情况
        float p3x = (1 - t) * startX+ t* contorlX;
        float p3y = (1 - t) *startY + t * contorlY;
        float p4x = (1 - t) * contorlX + t * endX;
        float p4y = (1 - t) * contorlY + t * endY;
        paint.setColor(Color.GREEN);
        canvas.drawLine(p3x, p3y, p4x, p4y, paint);
        //绘制曲线轨迹
        float p5x = (1 - t) * p3x + t * p4x;
        float p5y = (1 - t) * p3y + t * p4y;
        points.add(new PointF(p5x, p5y));
        paint.setColor(Color.RED);
        PointF ps, pe;
        for (int i = 1; i < points.size(); i++) {//其实这个二阶曲线就是由无数个点构成,然后把前后2个点构成一条直线
            ps = points.get(i - 1);
            pe = points.get(i);
            canvas.drawLine(ps.x, ps.y, pe.x, pe.y, paint);//绘制贝塞尔在时间0到1内运动轨迹
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                t+=0.01;
                invalidate();
            }
        },50);
//        if(t==1.0f){//绘制曲线轨迹曲线
//            path.reset();//记得一定要重置path
//            path.moveTo(startX,startY);
//            path.quadTo(contorlX,contorlY,endX,endY);
//            canvas.drawPath(path,paint);
//        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                contorlX = event.getX();
                contorlY = event.getY();
                t = 0;
                points.clear();
                invalidate();
                break;
        }
        return true;
    }
}
