package cn.lanhu.android_growth_plan.widget;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.lanhu.android_growth_plan.utils.uirelated.DensityUtils;

/**
 * Created by lq on 2017/10/18.
 */

public class ArcWidget extends View {

    private Paint paint;

    public ArcWidget(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(DensityUtils.dip2px(2));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public ArcWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColor(String color,Animator.AnimatorListener listener) {
        paint.setColor(Color.parseColor(color));
        currentAngleLength = 90;
        setAnimation(0, currentAngleLength, 1000,listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(DensityUtils.dip2px(1),DensityUtils.dip2px(1),getHeight(),getHeight());
        canvas.drawArc(rect,135, currentAngleLength, false, paint);

    }

    private float currentAngleLength = 0;
    private void setAnimation(float last, float current, int length,Animator.AnimatorListener listener) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(animation -> {
                /**每次要绘制的圆弧角度**/
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            });
        progressAnimator.start();
        progressAnimator.addListener(listener);
    }
}
