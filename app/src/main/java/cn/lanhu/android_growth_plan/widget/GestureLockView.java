package cn.lanhu.android_growth_plan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";

    /**
     * GestureLockView的三种状态
     */
    public enum Mode {
        //没有手指
        STATUS_NO_FINGER,
        //手指在上面
        STATUS_FINGER_ON,
        //手指离开
        STATUS_FINGER_UP;
    }

    /**
     * GestureLockView的当前状态
     */
    private Mode mCurrentStatus = Mode.STATUS_NO_FINGER;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 外圆半径
     */
    private int mRadius;
    /**
     * 画笔的宽度
     */
    private int mStrokeWidth = 2;

    private boolean isFrist = true;

    /**
     * 圆心坐标
     */
    private int mCenterX;
    private int mCenterY;
    private Paint mPaint;

    /**
     * 内圆的半径 = mInnerCircleRadiusRate * mRadus
     */
    private float mInnerCircleRadiusRate = 0.45F;

    private int flag;//0 设置密码 1 还是登陆

    /**
     * 四个颜色，可由用户自定义，初始化时由GestureLockViewGroup传入
     */
    private int mColorNoFingerInner;
    private int mColorNoFingerOutter;
    private int mColorFingerOn;
    private int mColorFingerUp;

    /**
     * 箭头（小三角最长边的一半长度 = mArrawRate * mWidth / 2 ）
     */
    private float mArrowRate = 0.1f;
    private int mArrowDegree = -1;
    private Path mArrowPath;

    public GestureLockView(Context context, int mRadius , int colorNoFingerInner, int colorNoFingerOutter, int colorFingerOn, int colorFingerUp, int flag) {
        super(context);
        this.mColorNoFingerInner = colorNoFingerInner;
        this.mColorNoFingerOutter = colorNoFingerOutter;
        this.mColorFingerOn = colorFingerOn;
        this.mColorFingerUp = colorFingerUp;
        this.mRadius = mRadius;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.flag = flag;
        mArrowPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 取长和宽中的小值
        mWidth = mWidth < mHeight ? mWidth : mHeight;
        //确定最中间园的圆心
        mCenterX = mCenterY = mWidth / 2;
//        mRadius = mWidth
        float mArrowLength = mWidth / 2 * mArrowRate;
        mArrowPath.moveTo(mWidth / 2, mCenterY / 3);
        mArrowPath.lineTo(mWidth / 2 - mArrowLength,mCenterY / 3 + mArrowLength);
        mArrowPath.lineTo(mWidth / 2 + mArrowLength, mCenterY / 3 + mArrowLength);
        mArrowPath.close();
        mArrowPath.setFillType(Path.FillType.WINDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (mCurrentStatus) {
            case STATUS_FINGER_ON:
                // 绘制外圆
                mPaint.setStyle(Style.STROKE);
                mPaint.setColor(mColorFingerOn);
                mPaint.setStrokeWidth(2);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                //绘制圆环
                mPaint.setStyle(Style.FILL);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(mCenterX, mCenterY, mRadius - 2, mPaint);
                // 绘制内圆
                mPaint.setStyle(Style.FILL);
                mPaint.setColor(mColorFingerOn);
                canvas.drawCircle(mCenterX, mCenterY, mRadius
                        * mInnerCircleRadiusRate, mPaint);
                drawArrow(canvas);
                break;
            case STATUS_FINGER_UP:
                mPaint.setColor(mColorFingerUp);
                // 绘制外圆
                mPaint.setStyle(Style.STROKE);
                mPaint.setStrokeWidth(2);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                //绘制圆环
                mPaint.setStyle(Style.FILL);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(mCenterX, mCenterY, mRadius - 2, mPaint);
                // 绘制内圆
                mPaint.setStyle(Style.FILL);
                mPaint.setColor(mColorFingerUp);
                canvas.drawCircle(mCenterX, mCenterY, mRadius
                        * mInnerCircleRadiusRate, mPaint);
                drawArrow(canvas);
                break;
            case STATUS_NO_FINGER:
                // 绘制外圆
                if (flag == 1) {
                    mPaint.setStyle(Style.FILL);
                    mPaint.setColor(mColorNoFingerOutter);
                    canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                }
                // 绘制内圆
                mPaint.setColor(Color.parseColor("#D3DAE5"));
                canvas.drawCircle(mCenterX, mCenterY, mRadius
                        * mInnerCircleRadiusRate, mPaint);
                isFrist = false;
                break;

        }

    }

    /**
     * 设置当前模式并重绘界面
     *
     * @param mode
     */
    public void setMode(Mode mode) {
        this.mCurrentStatus = mode;
        invalidate();
    }

    /**
     * 绘制箭头
     *
     * @param canvas
     */
    private void drawArrow(Canvas canvas) {
        if (mArrowDegree != -1) {
            mPaint.setStyle(Style.FILL);

            canvas.save();
            //旋转画布
            canvas.rotate(mArrowDegree, mCenterX, mCenterY);
            canvas.drawPath(mArrowPath, mPaint);

            canvas.restore();
        }

    }

    public void setArrowDegree(int degree) {
        this.mArrowDegree = degree;
    }

    public int getArrowDegree() {
        return this.mArrowDegree;
    }
}
