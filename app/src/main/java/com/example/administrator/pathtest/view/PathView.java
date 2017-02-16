package com.example.administrator.pathtest.view;

/**
 * Created by xuyongchao
 * on 2017/1/21.
 * 邮箱:18036699151@163.com
 * QQ：870867914
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by admin on 2016/12/7.
 */
public class PathView extends View {
    private Paint paint;
    private Path path,path1;
    private Path mEndPath;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path = new Path();
        path1 = new Path();
        mEndPath = new Path();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawCircleAndLine(canvas);
//        drawRect(canvas);
//        drawFillType(canvas);
//        drawOP(canvas);
    }

    private void drawOP(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(150, 150, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(200, 200, 100, Path.Direction.CW);
        path2.op(path1, Path.Op.DIFFERENCE);
        canvas.drawPath(path2, paint);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(2);
        canvas.drawCircle(150, 150, 100,paint);
        canvas.drawCircle(200, 200, 100,paint);
    }

    private void drawFillType(Canvas canvas) {
        mEndPath.offset(100,100);
        mEndPath.addCircle(200, 200, 100, Path.Direction.CW);
        mEndPath.addCircle(300, 300, 100, Path.Direction.CW);
        mEndPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        paint.setColor(Color.RED);
        canvas.drawPath(mEndPath,paint);
    }

    private void drawRect(Canvas canvas) {
        RectF rectF = new RectF(60,60,400,400);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path,paint);
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(24);
        paint.setStrokeWidth(2);
        canvas.drawTextOnPath("杭州今天天气不错",path,0,0,paint);
    }

    private void drawCircleAndLine(Canvas canvas) {
        path.moveTo(10,10);
        path.lineTo(200,200);
//        path.reset();
        path.addCircle(200,200,150, Path.Direction.CCW);
        canvas.drawPath(path,paint);
        paint.setColor(Color.BLUE);
        path1.rMoveTo(100,10);
        path1.rLineTo(300,10);
        canvas.drawPath(path1,paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        //绘制2个点测试
        canvas.drawPoint(200,200,paint);
        canvas.drawPoint(400,20,paint);
    }
}
