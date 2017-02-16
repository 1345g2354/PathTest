package com.example.administrator.pathtest.view;

/**
 * Created by xuyongchao
 * on 2017/1/22.
 * 邮箱:18036699151@163.com
 * QQ：870867914
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.pathtest.R;

/**
 * Created by admin on 2016/12/13.
 */
public class PathMeasureView extends View {
    private float startX,startY;
    private float endX,endY ;
    private float contorlX = 200,contorlY = 60;//默认值
    private Paint paint;
    private float t;
    private Path path;
    private int measuredWidth;
    private int measuredHeight;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    public PathMeasureView(Context context, AttributeSet attrs) {
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
        getScreenWidthTwo(context);

        init(context);
    }
    private void init(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.arrow, options);
        mMatrix = new Matrix();
    }
    public  void getScreenWidthTwo(Context context){
        WindowManager manager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        measuredWidth = dm.widthPixels;
        measuredHeight = dm.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(measuredWidth / 2, measuredHeight / 2);      // 平移坐标系


        drawGetmetrix(canvas);
//        drawPostan(canvas);
//        drawSegment(canvas);
//        drawNextContour(canvas);
    }

    private void drawGetmetrix(Canvas canvas) {
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

// 获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength() * currentValue, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);

        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)

        canvas.drawPath(path, paint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, paint);                     // 绘制箭头

        invalidate();
    }

    private void drawPostan(Canvas canvas) {
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        //pos[0] 当前位置的坐标
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, paint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix,paint);                     // 绘制箭头

        invalidate();
    }

    private void drawNextContour(Canvas canvas) {
        Path path = new Path();

        path.addRect(-100, -100, 100, 100, Path.Direction.CW);  // 添加小矩形
        path.addRect(200, 200, 300, 300, Path.Direction.CW);  // 添加大矩形

        canvas.drawPath(path,paint);                    // 绘制 Path

        PathMeasure measure = new PathMeasure(path, false);     // 将Path与PathMeasure关联

        float len1 = measure.getLength();                       // 获得第一条路径的长度

        measure.nextContour();                                  // 跳转到下一条路径

        float len2 = measure.getLength();                       // 获得第二条路径的长度

        Log.i("LEN","len1="+len1);                              // 输出两条路径的长度
        Log.i("LEN","len2="+len2);
    }

    private void drawSegment(Canvas canvas) {

        canvas.translate(measuredWidth / 2, measuredHeight / 2);          // 平移坐标系

        Path path = new Path();                                     // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
        dst.lineTo(-300, -300);                                     // <--- 在 dst 中添加一条线段

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

        measure.getSegment(200, 600, dst, true);                   // 截取一部分 并使用 moveTo 保持截取得到的 Path 第一个点的位置不变

        canvas.drawPath(dst, paint);
    }

}