package cn.lanhu.android_growth_plan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.utils.uirelated.DensityUtils;

/**
 * Created by lq on 2017/10/16.
 */

public class WeatherChart extends View{
//    private int radius = DensityUtils.dip2px(2f);//坐标点圆的半径

    private ArrayList<String> winds;//风向
    private ArrayList<String> grades;//风向大小 级别
    private ArrayList<String> day;//日期
    private String fangna;
    private ArrayList<String> dates;
    private ArrayList<String> dayWeathers;
    private ArrayList<String> nightWeathers;
    private ArrayList<Bitmap> topBitmaps;//最高气温图片
    private ArrayList<Bitmap> leastBitmaps;//最低气温图片
    // 坐标单位
    private String[] xLabel;
    private int[] yLabel = {};
    private int min;
    private int max;
    // 曲线数据
    private List<ArrayList<Float>> dataList;
    private boolean showValue;
    // 原点坐标
    private int yPoint;

    //第一个绘制点横坐标
    private float xPointFrist;
    // X,Y轴的单位长度
    private float xScale;
    private float yScale;

    private float linePoint;
    private float lineYScale;
    // 画笔

    private Paint paintCurve;//曲线画笔

    private Paint paintValueText;//文本值画笔

    private Paint paintText;//底部画笔

    private int curveColor = Color.argb(255, 255, 255, 255);//曲线透明度变化

    /**
     * 贝塞尔曲线的辅助点
     */
    float lineSmoothness = 0.2f;

    // 画笔
    private Paint paintTable;

    private Context mContext;


    //SurfaceView
    private SurfaceHolder mHolder;

    public WeatherChart(Context context,
                        List<ArrayList<Float>> dataList, boolean showValue, ArrayList<String> winds, ArrayList<String> grades, ArrayList<Bitmap> leastBitmaps, ArrayList<Bitmap> topBitmaps,
                        String fangna, ArrayList<String> week, ArrayList<String> dayWeather, ArrayList<String> nightWeather ,ArrayList<String> day) {
        super(context);
        this.mContext = context;
        this.dataList = dataList;
        xLabel = new String[dataList.get(0).size()];
        this.showValue = showValue;
        this.yLabel = handler(dataList);
        this.winds = winds;
        this.grades = grades;
        this.leastBitmaps = leastBitmaps;
        this.topBitmaps = topBitmaps;
        this.fangna = fangna;
        this.dates = week;
        this.dayWeathers = dayWeather;
        this.nightWeathers = nightWeather;
        this.day = day;
    }

    private int[] handler(List<ArrayList<Float>> dataList) {
        min = (int) Math.rint(dataList.get(0).get(0));
        max = (int) Math.rint(dataList.get(0).get(0));
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < dataList.get(i).size(); j++) {
                if (min >= Math.rint(dataList.get(i).get(j))) {
                    min = (int) Math.rint(dataList.get(i).get(j));
                }

                if (max <= Math.rint(dataList.get(i).get(j))) {
                    max = (int) Math.rint(dataList.get(i).get(j));
                }
            }
        }
        return new int[Math.max(Math.abs(max), Math.abs(min))];
    }

    public WeatherChart(Context context) {
        super(context);
    }

    /**
     * 初始化数据值和画笔
     */
    public void init() {
        yPoint = this.getHeight();
        DisplayMetrics display = mContext.getResources().getDisplayMetrics();
        xScale = (display.widthPixels) / (5);
        yScale = (this.getHeight()) / (yLabel.length - 1);
        if (min < 0 && max > 0) {
            //原点坐标在正中间
            //上面 yPoint - (yLabel.length - 1) * yScale + 100 yPoint - 70
            linePoint = yPoint / 2;
            lineYScale = (((yLabel.length - 1) * yScale - DensityUtils.dip2px(30)) / 2) / (Math.max(max, Math.abs(min)) + 30);
        } else if (min >= 0) {
            //原点坐标就是
            lineYScale = (((yLabel.length - 1) * yScale - DensityUtils.dip2px(50f)) / 2) / (Math.max(max, Math.abs(min)) + 30);
            linePoint = (yPoint - yScale) * 3 / 4;
        } else if (max <= 0) {
            lineYScale = (((yLabel.length - 1) * yScale - DensityUtils.dip2px(30)) / 2) / (Math.max(Math.abs(max), Math.abs(min)) + 30);
            linePoint = yPoint * 2 / 5 + yScale;
        }

        xPointFrist = xScale / 2;

        //贝塞尔曲线画笔
        paintCurve = new Paint();
        paintCurve.setStyle(Paint.Style.STROKE);
        paintCurve.setDither(true);
        paintCurve.setAntiAlias(true);
        paintCurve.setStrokeWidth(2);
        PathEffect pathEffect = new CornerPathEffect(25);
        paintCurve.setPathEffect(pathEffect);
        paintCurve.setColor(curveColor);


        //天气画笔
        paintValueText = new Paint();
        paintValueText.setStyle(Paint.Style.FILL);
        paintValueText.setAntiAlias(true);
        paintValueText.setDither(true);
        paintValueText.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paintValueText.setTextAlign(Paint.Align.CENTER);
        paintValueText.setTextSize(DensityUtils.dip2px(12));

//        //图片
//        paintValueBitmap = new Paint();
//        paintValueBitmap.setStyle(Paint.Style.FILL);
//        paintValueBitmap.setAntiAlias(true);
//        paintValueBitmap.setDither(true);
//        paintValueBitmap.setColor(ContextCompat.getColor(getContext(), R.color.colorCommitBg));
//        paintValueBitmap.setTextAlign(Paint.Align.CENTER);
//        paintValueBitmap.setTextSize(DensityUtils.dip2px(8));

        //表格画笔
        paintTable = new Paint();
        paintTable.setStyle(Paint.Style.STROKE);
        paintTable.setAntiAlias(true);
        paintTable.setDither(true);
        paintTable.setColor(ContextCompat.getColor(getContext(), R.color.colorCommitBg));
        paintTable.setStrokeWidth(1f);

        //底部画笔
        paintText = new Paint();
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setDither(true);
        paintText.setAntiAlias(true);
        paintText.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paintText.setTextSize(DensityUtils.dip2px(12));

//        //时间画笔
//        paintTopText = new Paint();
//        paintTopText.setStyle(Paint.Style.STROKE);
//        paintTopText.setDither(true);
//        paintTopText.setAntiAlias(true);
//        paintTopText.setColor(ContextCompat.getColor(getContext(), R.color.colorCommitBg));
//        paintTopText.setTextSize(DensityUtils.dip2px(12));

        //温度和天气的画笔
//        paintArgb = new Paint();
//        paintArgb.setStyle(Paint.Style.STROKE);
//        paintArgb.setDither(true);
//        paintArgb.setAntiAlias(true);
//        paintArgb.setColor(textColor);
//        paintArgb.setTextSize(DensityUtils.dip2px(12));
//
//        paintArgbWeather = new Paint();
//        paintArgbWeather.setStyle(Paint.Style.STROKE);
//        paintArgbWeather.setDither(true);
//        paintArgbWeather.setAntiAlias(true);
//        paintArgbWeather.setColor(textColor);
//        paintArgbWeather.setTextSize(DensityUtils.dip2px(12));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        init();
//        canvas.drawLine(0, linePoint, getHeight(), linePoint, paintCurve);
        drawTable(canvas, paintTable);
        drawTopText(canvas, paintText);
        for (int i = 0; i < dataList.size(); i++) {
            //绘制贝塞尔曲线
            drawCurve(canvas, paintCurve, dataList.get(i));
            if (showValue) {
                if (i == dataList.size() - 1) {
                    //绘制最低温度
                    drawLeastValue(canvas, dataList.get(i));
                } else {
                    //绘制最高温度
                    drawTopValue(canvas, dataList.get(i));
                }
            }
        }
    }

    /**
     * 绘制曲线
     */
    private void drawCurve(Canvas canvas, Paint paint, ArrayList<Float> data) {
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;
        Path path = new Path();
        for (int i = 0; i <= (xLabel.length - 1); i++) {
            if (Float.isNaN(currentPointX)) {
                currentPointX = xPointFrist + i * xScale;
                currentPointY = toY(data.get(i));
            }
            if (Float.isNaN(prePreviousPointX)) {
                //是否是第一个点
                if (i > 0) {
                    previousPointX = xPointFrist + (i - 1) * xScale;
                    previousPointY = toY(data.get(i - 1));
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }
            if (Float.isNaN(prePreviousPointX)) {
                if (i > 1) {
                    prePreviousPointX = xPointFrist + (i - 2) * xScale;
                    prePreviousPointY = toY(data.get(i - 2));
                } else {
                    //用上一个点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }
            if (i < (xLabel.length - 1)) {
                nextPointX = xPointFrist + (i + 1) * xScale;
                nextPointY = toY(data.get(i + 1));
            } else {
                nextPointY = currentPointY;
                nextPointX = currentPointX;
            }

            if (i == 0) {
                path.moveTo(xPointFrist, toY(data.get(0)));
//                path.addCircle(xPointFrist, toY(data.get(0)), radius, Path.Direction.CW);
            } else {
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                final float firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                final float secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                final float secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                //画出曲线
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
            }
            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制表格
     */
    private void drawTable(Canvas canvas, Paint paint) {
        final Path path = new Path();
        // 顶上横向线
        float startY = yPoint;
        float stopY = 0;
        path.moveTo(0, startY);
        path.lineTo(0, stopY);
        path.lineTo(getWidth(), stopY);
        path.lineTo(getWidth(), yPoint);
        path.close();
        canvas.drawPath(path, paint);

        // 纵向线

        for (int i = 0; i < xLabel.length - 1; i++) {
            float startX = (xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2;
            path.reset();
            path.moveTo(startX, startY);
            path.lineTo(startX, stopY);
            canvas.drawPath(path, paint);
        }
    }

    private void drawTopText(Canvas canvas, Paint paint) {
        float tempFontWidth = paint.measureText(day.get(0));//温度字体的宽度
        float dateFontWidth = paint.measureText(dates.get(0));//时间字体宽度
        if(fangna.equals("A")) {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorCommitBg));
        }
        if (dayWeathers.get(0).length() >= 4) {
            float weatherFontWidthUp = paint.measureText(dayWeathers.get(0).substring(0, 4));
            float weatherFontWidthDown = paint.measureText(dayWeathers.get(0).substring(4));
            float weatherStartXUp = ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidthUp) / 2;
            float weatherStartXDown = ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidthDown) / 2;
            canvas.drawText(dayWeathers.get(0).substring(0, 4), weatherStartXUp, DensityUtils.dip2px(63), paint);
            canvas.drawText(dayWeathers.get(0).substring(4), weatherStartXDown, DensityUtils.dip2px(74), paint);
        } else {
            float weatherFontWidth = paint.measureText(dayWeathers.get(0));//天气字体宽度
            float weatherStartX = ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidth) / 2;
            canvas.drawText(dayWeathers.get(0), weatherStartX, DensityUtils.dip2px(63), paint);
        }
        float tempStartX = ((xPointFrist + xPointFrist + xScale) / 2 - tempFontWidth) / 2;
        float dateStartX = ((xPointFrist + xPointFrist + xScale) / 2 - dateFontWidth) / 2;
        canvas.drawText(day.get(0), tempStartX, DensityUtils.dip2px(49), paint);
        canvas.drawText(dates.get(0), dateStartX, DensityUtils.dip2px(35), paint);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        for (int i = 0; i < xLabel.length - 1; i++) {
            tempFontWidth = paint.measureText(day.get(i + 1));
            dateFontWidth = paint.measureText(dates.get(i + 1));
            tempStartX = (((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + ((xPointFrist + xPointFrist + xScale) / 2 - tempFontWidth) / 2);
            dateStartX = (((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + ((xPointFrist + xPointFrist + xScale) / 2 - dateFontWidth) / 2);
            canvas.drawText(day.get(i + 1), tempStartX, DensityUtils.dip2px(49), paint);
            canvas.drawText(dates.get(i + 1), dateStartX, DensityUtils.dip2px(35), paint);
            if (dayWeathers.get(i + 1).length() >= 4) {
                float weatherFontWidthUp = paint.measureText(dayWeathers.get(i + 1).substring(0, 4));
                float weatherFontWidthDown = paint.measureText(dayWeathers.get(i + 1).substring(4));
                float weatherStartXUp = (((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidthUp) / 2);
                float weatherStartXDown = (((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidthDown) / 2);
                canvas.drawText(dayWeathers.get(i + 1).substring(0, 4), weatherStartXUp, DensityUtils.dip2px(63), paint);
                canvas.drawText(dayWeathers.get(i + 1).substring(4), weatherStartXDown, DensityUtils.dip2px(74), paint);
            } else {
                float weatherFontWidth = paint.measureText(dayWeathers.get(i + 1));//天气字体宽度
                float weatherStartX = (((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + ((xPointFrist + xPointFrist + xScale) / 2 - weatherFontWidth) / 2);
                canvas.drawText(dayWeathers.get(i + 1), weatherStartX, DensityUtils.dip2px(63), paint);
            }
        }
    }

    /**
     * 数据按比例转坐标
     */
    private float toY(float num) {
        float y;
        try {
            float a = num;
            y = linePoint - a * lineYScale;
        } catch (Exception e) {
            return 0;
        }
        return y;
    }

    /**
     * 绘制最高温度曲线和数值
     */
    private void drawTopValue(Canvas canvas, ArrayList<Float> data) {
        float offHeight = 0;
        float offTextHeight = 0;
        offHeight = -DensityUtils.dip2px(31.5f);
        offTextHeight = -DensityUtils.dip2px(7f);
        for (int i = 0; i <= (xLabel.length - 1); i++) {
            canvas.drawText(String.valueOf((int) Math.rint(data.get(i))) + "°", xPointFrist + i * xScale, toY(data.get(i)) + offTextHeight, paintValueText);
            canvas.drawCircle(xPointFrist + i * xScale, toY(data.get(i)), DensityUtils.dip2px(2f), paintValueText);
            //要根据counts的大小改变横纵坐标
            Rect des = new Rect();
            des.left = Math.round(xPointFrist + i * xScale - DensityUtils.dip2px(10.5f));
            des.right = Math.round(xPointFrist + i * xScale + DensityUtils.dip2px(10.5f));
            des.top = Math.round(toY(data.get(i)) + offHeight - DensityUtils.dip2px(7.25f));
            des.bottom = Math.round(toY(data.get(i)) + offHeight + DensityUtils.dip2px(13.75f));
            canvas.drawBitmap(topBitmaps.get(i), null, des, paintValueText);
        }
    }


    /**
     * 绘制最低温度曲线和数值
     */
    private void drawLeastValue(Canvas canvas, ArrayList<Float> data) {
        float offHeight = 0;
        float offTextHeight = 0;
        if(fangna.equals("A")) {
            paintText.setColor(ContextCompat.getColor(getContext(), R.color.colorCommitBg));
        }
        offHeight = DensityUtils.dip2px(10.5f);
        offTextHeight = DensityUtils.dip2px(7);
        float windsFontWidth = paintText.measureText(winds.get(0));
        float gradesFontWidth = paintText.measureText(grades.get(0));
        float nightWFontWidth = paintText.measureText(nightWeathers.get(0));
        float windsStartX = ((xPointFrist + xPointFrist + xScale) / 2 - windsFontWidth) / 2;
        float gradesStartX = ((xPointFrist + xPointFrist + xScale) / 2 - gradesFontWidth) / 2;
        float nightWStartX = ((xPointFrist + xPointFrist + xScale) / 2 - nightWFontWidth) / 2;
        canvas.drawText(nightWeathers.get(0),nightWStartX,yPoint - DensityUtils.dip2px(42f),paintText);
        canvas.drawText(winds.get(0), windsStartX, yPoint - DensityUtils.dip2px(24.5f), paintText);
        canvas.drawText(grades.get(0), gradesStartX, yPoint - DensityUtils.dip2px(7), paintText);
        paintText.setColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        for (int i = 0; i <= (xLabel.length - 1); i++) {
            if (i != (xLabel.length - 1)) {
                windsFontWidth = paintText.measureText(winds.get(i + 1));
                gradesFontWidth = paintText.measureText(grades.get(i + 1));
                nightWFontWidth = paintText.measureText(nightWeathers.get(i+1));
                windsStartX = ((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + (((xPointFrist + xPointFrist + xScale) / 2 - windsFontWidth) / 2);
                gradesStartX = ((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + (((xPointFrist + xPointFrist + xScale) / 2 - gradesFontWidth) / 2);
                nightWStartX = ((xPointFrist + i * xScale + xPointFrist + (i + 1) * xScale) / 2) + (((xPointFrist + xPointFrist + xScale) / 2 - nightWFontWidth) / 2);
                canvas.drawText(nightWeathers.get(i+1),nightWStartX,yPoint - DensityUtils.dip2px(42f),paintText);
                canvas.drawText(winds.get(i + 1), windsStartX, yPoint - DensityUtils.dip2px(24.5f), paintText);
                canvas.drawText(grades.get(i + 1), gradesStartX, yPoint - DensityUtils.dip2px(7), paintText);
            }
            canvas.drawText(String.valueOf((int) Math.rint(data.get(i))) + "°", xPointFrist + i * xScale, toY(data.get(i)) + offTextHeight + DensityUtils.dip2px(7f), paintValueText);
            canvas.drawCircle(xPointFrist + i * xScale, toY(data.get(i)), DensityUtils.dip2px(2f), paintValueText);

            //要根据counts的大小改变横纵坐标
            Rect des = new Rect();
            des.left = Math.round(xPointFrist + i * xScale - DensityUtils.dip2px(10.5f));
            des.right = Math.round(xPointFrist + i * xScale + DensityUtils.dip2px(10.5f));
            des.top = Math.round(toY(data.get(i)) + offHeight + DensityUtils.dip2px(9));
            des.bottom = Math.round(toY(data.get(i)) + offHeight + DensityUtils.dip2px(30));
            canvas.drawBitmap(leastBitmaps.get(i), null, des, paintValueText);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultWidth(), heightMeasureSpec);
    }

    private int getDefaultWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels / 5 * xLabel.length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}
