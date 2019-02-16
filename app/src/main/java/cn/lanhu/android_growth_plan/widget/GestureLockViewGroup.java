package cn.lanhu.android_growth_plan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.lanhu.android_growth_plan.R;


/**
 * 整体包含n*n个GestureLockView,每个GestureLockView间间隔mMarginBetweenLockView，
 * 最外层的GestureLockView与容器存在mMarginBetweenLockView的外边距
 * <p>
 * 关于GestureLockView的边长（n*n）： n * mGestureLockViewWidth + ( n + 1 ) *
 * mMarginBetweenLockView = mWidth ; 得：mGestureLockViewWidth = 4 * mWidth / ( 5
 * * mCount + 1 ) 注：mMarginBetweenLockView = mGestureLockViewWidth * 0.25 ;
 *
 * @author zhy
 */
public class GestureLockViewGroup extends RelativeLayout {

    private static final String TAG = "GestureLockViewGroup";
    /**
     * 保存所有的GestureLockView
     */
    private GestureLockView[] mGestureLockViews;
    /**
     * 每个边上的GestureLockView的个数
     */
    private int mCount = 3;
    /**
     * 存储答案
     */
    private int[] mAnswer = {};
    /**
     * 保存用户选中的GestureLockView的id
     */
    private List<Integer> mChoose = new ArrayList<Integer>();

    private List<GestureLockView> mSelected = new ArrayList<>();

    private Paint mPaint;

    private Canvas canvas;
    /**
     * 每个GestureLockView中间的间距 设置为：mGestureLockViewWidth * 25%
     */
    private int mMarginBetweenLockView = 30;
    /**
     * GestureLockView的边长 4 * mWidth / ( 5 * mCount + 1 )
     */
    private int mGestureLockViewWidth;

    //类型
    private int flag;

    /**
     * GestureLockView无手指触摸的状态下内圆的颜色
     */
    private int mNoFingerInnerCircleColor = 0xFF939090;
    /**
     * GestureLockView无手指触摸的状态下外圆的颜色
     */
    private int mNoFingerOuterCircleColor = 0xFFE0DBDB;
    /**
     * GestureLockView手指触摸的状态下内圆和外圆的颜色
     */
    private int mFingerOnColor = 0xFF378FC9;
    /**
     * GestureLockView手指抬起的状态下内圆和外圆的颜色
     */
    private int mFingerUpColor = 0xFFFF0000;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;

    private Path mPath;
    /**
     * 指引线的开始位置x
     */
    private int mLastPathX;
    /**
     * 指引线的开始位置y
     */
    private int mLastPathY;

    private int byX;
    private int byY;
    /**
     * 指引下的结束位置
     */
    private Point mTmpTarget = new Point();

    /**
     * 最大尝试次数
     */
    private int mTryTimes = 5;

    /**
     * 回调接口
     */
    private OnGestureLockViewListener mOnGestureLockViewListener;

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        /**
         * 获得所有自定义的参数的值
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GestureLockViewGroup, defStyle, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GestureLockViewGroup_gesture_color_no_finger_inner_circle:
                    mNoFingerInnerCircleColor = a.getColor(attr,
                            mNoFingerInnerCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_color_no_finger_outer_circle:
                    mNoFingerOuterCircleColor = a.getColor(attr,
                            mNoFingerOuterCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_color_finger_on:
                    mFingerOnColor = a.getColor(attr, mFingerOnColor);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_color_finger_up:
                    mFingerUpColor = a.getColor(attr, mFingerUpColor);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_count:
                    mCount = a.getInt(attr, 3);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_tryTimes:
                    mTryTimes = a.getInt(attr, 5);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_type:
                    flag = a.getInt(attr, 0);
                    break;
                case R.styleable.GestureLockViewGroup_gesture_radius:
                    break;
                default:
                    break;
            }
        }

        a.recycle();

        //初始化画板
        canvas = new Canvas();

        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        // mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // mPaint.setColor(Color.parseColor("#aaffffff"));
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);


        mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;
        // setMeasuredDimension(mWidth, mHeight);

        // 初始化mGestureLockViews
        if (mGestureLockViews == null) {
            mGestureLockViews = new GestureLockView[mCount * mCount];
            // 计算每个GestureLockView的宽度
            mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / (5 * mCount + 1));
            //计算每个GestureLockView的间距
            mMarginBetweenLockView = (int) (mGestureLockViewWidth * 0.4);

            // 设置画笔的宽度为GestureLockView的内圆直径稍微小点（不喜欢的话，随便设）
            mPaint.setStrokeWidth(5);

            for (int i = 0; i < mGestureLockViews.length; i++) {
                //初始化每个GestureLockView
                //设置参数，主要是定位GestureLockView间的位置
                LayoutParams lockerParams = new LayoutParams(
                        mGestureLockViewWidth, mGestureLockViewWidth);
                mGestureLockViews[i] = new GestureLockView(getContext(), mMarginBetweenLockView,
                        mNoFingerInnerCircleColor, mNoFingerOuterCircleColor,
                        mFingerOnColor, mFingerUpColor, flag);
                mGestureLockViews[i].setId(i + 1);

                // 不是每行的第一个，则设置位置为前一个的右边
                if (i % mCount != 0) {
                    lockerParams.addRule(RelativeLayout.RIGHT_OF,
                            mGestureLockViews[i - 1].getId());
                }
                // 从第二行开始，设置为上一行同一位置View的下面
                if (i > mCount - 1) {
                    lockerParams.addRule(RelativeLayout.BELOW,
                            mGestureLockViews[i - mCount].getId());
                }
                //设置右下左上的边距
                int rightMargin = (mWidth - mGestureLockViewWidth * 3) / 6;
                int bottomMargin = (mHeight - mGestureLockViewWidth * 3) / 6;
                int leftMagin = (mWidth - mGestureLockViewWidth * 3) / 6;
                int topMargin = (mHeight - mGestureLockViewWidth * 3) / 6;
                /**
                 * 每个View都有右外边距和底外边距 第一行的有上外边距 第一列的有左外边距
                 */
                if (i >= 0 && i < mCount)// 第一行
                {
//                    topMargin = mMarginBetweenLockView;
                }
                if (i % mCount == 0)// 第一列
                {
//                    leftMagin = mMarginBetweenLockView;
                }

                lockerParams.setMargins(leftMagin, topMargin, rightMargin,
                        bottomMargin);
                mGestureLockViews[i].setMode(GestureLockView.Mode.STATUS_NO_FINGER);
                addView(mGestureLockViews[i], lockerParams);
            }


        }
    }


    private GestureLockView child;
    int count = 1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (true) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 重置
                    if (mOnGestureLockViewListener != null) {
                        mOnGestureLockViewListener.touchGestureLock();
                    }
                    reset();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPaint.setColor(mFingerOnColor);
                    child = getChildIdByPos(x, y);
                    if (child != null) {
                        int cId = child.getId();
                        if (!mChoose.contains(cId)) {
                            mChoose.add(cId);
                            // 计算每个元素中箭头需要旋转的角度
                            int angle = 0;
                            for (int i = 0; i + 1 < mChoose.size(); i++) {
                                int childId = mChoose.get(i);
                                int nextChildId = mChoose.get(i + 1);

                                GestureLockView startChild = (GestureLockView) findViewById(childId);
                                GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);

                                int dx = nextChild.getLeft() - startChild.getLeft();
                                int dy = nextChild.getTop() - startChild.getTop();
                                // 计算角度
                                angle = (int) Math.toDegrees(Math.atan2(dy, dx)) + 90;
                                startChild.setArrowDegree(angle);
                            }
                            // 设置指引线的起点
                            mLastPathX = (child.getLeft() + child.getRight()) / 2;
                            mLastPathY = child.getTop() / 2 + child.getBottom() / 2;
                            if (mChoose.size() == 1) {// 当前添加为第一个
                                mPath.moveTo(mLastPathX, mLastPathY);
                            } else {
                                mPath.lineTo(mLastPathX, mLastPathY);
                            }
                        }
                        mPaint.setStrokeWidth(2);
                        canvas.drawLine(0, 0, mLastPathX, mLastPathY, mPaint);
                        child.setMode(GestureLockView.Mode.STATUS_FINGER_ON);
                    }
                    // 指引线的终点
                    mTmpTarget.x = x;
                    mTmpTarget.y = y;

                    changeItemMode(GestureLockView.Mode.STATUS_FINGER_ON);
                    break;
                case MotionEvent.ACTION_UP:
                    this.mTryTimes--;
                    // 回调是否成功
                    if (mOnGestureLockViewListener != null && mChoose.size() >= 0) {
                        mOnGestureLockViewListener.onGestureCodeInput(listToString(mChoose));
                    }
                    // 将终点设置位置为起点，即取消指引线
                    mTmpTarget.x = mLastPathX;
                    mTmpTarget.y = mLastPathY;
                    break;

            }

            invalidate();

            return true;
        } else if (mOnGestureLockViewListener != null) {
        }
        return false;
    }

    public String listToString(List list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    private void changeItemMode(GestureLockView.Mode mode) {
        for (GestureLockView gestureLockView : mGestureLockViews) {
            if (mChoose.contains(gestureLockView.getId())) {
                gestureLockView.setMode(mode);
            }
        }
    }

    /**
     * 做一些必要的重置
     */
    private void reset() {
        mSelected.clear();
        mChoose.clear();
        mPath.reset();
        for (GestureLockView gestureLockView : mGestureLockViews) {
            gestureLockView.setMode(GestureLockView.Mode.STATUS_NO_FINGER);
            gestureLockView.setArrowDegree(-1);
        }
    }

    /**
     * 检查当前左边是否在child中
     *
     * @param child
     * @param x
     * @param y
     * @return
     */
    private boolean checkPositionInChild(View child, int x, int y) {

        //设置了内边距，即x,y必须落入下GestureLockView的内部中间的小区域中，可以通过调整padding使得x,y落入范围不变大，或者不设置padding
        int padding = (int) (mGestureLockViewWidth * 0.15);

        if (x >= child.getLeft() + padding && x <= child.getRight() - padding
                && y >= child.getTop() + padding
                && y <= child.getBottom() - padding) {
            return true;
        }
        return false;
    }

    /**
     * 通过x,y获得落入的GestureLockView
     *
     * @param x
     * @param y
     * @return
     */
    private GestureLockView getChildIdByPos(int x, int y) {
        for (GestureLockView gestureLockView : mGestureLockViews) {
            if (checkPositionInChild(gestureLockView, x, y)) {
                return gestureLockView;
            }
        }

        return null;

    }

    /**
     * 设置回调接口
     *
     * @param listener
     */
    public void setOnGestureLockViewListener(OnGestureLockViewListener listener) {
        this.mOnGestureLockViewListener = listener;
    }

    /**
     * 设置最大实验次数
     *
     * @param boundary
     */
    public void setUnMatchExceedBoundary(int boundary) {
        this.mTryTimes = 5;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制GestureLockView间的连线
        mPaint.setStrokeWidth(2);
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);
        }


//        绘制指引线
        if (mChoose.size() > 0) {
            if (mLastPathX != 0 && mLastPathY != 0)
                mPaint.setColor(mFingerOnColor);
            canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
                    mTmpTarget.y, mPaint);
        }
    }

    public interface OnGestureLockViewListener {
        /**
         * 用户设置/输入了手势密码
         */
        void onGestureCodeInput(String inputCode);

//        /**
//         * 代表用户绘制的密码与传入的密码相同
//         */
//        void checkedSuccess(int type,String inputCode);
//
//        /**
//         * 代表用户绘制的密码与传入的密码不相同
//         */
//        void checkedFail(String str);

        void touchGestureLock();

    }

    /**
     * 保留路径delayTime时间长
     *
     * @param delayTime
     */
    public void clearDrawlineState(long delayTime) {
        mChoose.clear();
        mPath.reset();
        for (GestureLockView gestureLockView : mGestureLockViews) {
            gestureLockView.setMode(GestureLockView.Mode.STATUS_NO_FINGER);
        }
        mSelected.clear();

    }

    /**
     * 绘制成功
     */
    public void DrawRightState(long delayTime) {
        mPaint.setColor(mFingerOnColor);
        for (GestureLockView gestureLockView : mGestureLockViews) {
            if (mChoose.contains(gestureLockView.getId())) {
                gestureLockView.setMode(GestureLockView.Mode.STATUS_FINGER_ON);
            }
        }
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
    }

    /**
     * 绘制错误
     */

    public void DrawErrorState(long delayTime) {
        mPaint.setColor(mFingerUpColor);
        changeItemMode(GestureLockView.Mode.STATUS_FINGER_UP);
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
    }


    /**
     * 清除绘制状态的线程
     */
    final class clearStateRunnable implements Runnable {
        public void run() {
            reset();
            invalidate();
        }
    }


}
