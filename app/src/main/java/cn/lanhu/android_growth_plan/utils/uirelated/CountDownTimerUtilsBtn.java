package cn.lanhu.android_growth_plan.utils.uirelated;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.Button;

import cn.lanhu.android_growth_plan.R;

/**
 * Created by yx on 2016/7/27.
 * 倒计时工具类btn
 */
public class CountDownTimerUtilsBtn extends CountDownTimer {

    private Button mButton;
    private Button mButton2;
    private boolean clickMsg = true;

    /**
     * @param button            The Button
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receiver
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtilsBtn(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    public CountDownTimerUtilsBtn(Button button, Button button2, boolean clickMsg, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
        this.mButton2 = button2;
        this.clickMsg = clickMsg;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        if (clickMsg) {
            mButton.setClickable(false); //设置不可点击
            mButton.setText(millisUntilFinished / 1000 + "秒后重试");  //设置倒计时时间
            mButton.setBackgroundResource(R.drawable.btn_bg_yanzheng3);
            if (mButton2 != null) {
                mButton2.setClickable(false);
                mButton2.setBackgroundResource(R.drawable.btn_bg_yanzheng3);
            }
        } else {
            mButton.setClickable(false);
            mButton.setBackgroundResource(R.drawable.btn_bg_yanzheng3);
            mButton2.setClickable(false);
            mButton2.setText(millisUntilFinished / 1000 + "秒后重试");  //设置倒计时时间
            mButton2.setBackgroundResource(R.drawable.btn_bg_yanzheng3);
        }

    }


    @Override
    public void onFinish() {
        if (clickMsg) {
            mButton.setText("重新获取验证码");
            mButton.setClickable(true);//重新获得点击
            mButton.setBackgroundResource(R.drawable.btn_bg_yanzheng2);
            if (mButton2 != null) {
                mButton2.setClickable(true);
                mButton2.setBackgroundResource(R.drawable.btn_bg_yanzheng);
            }
        } else {
            mButton.setClickable(true);//重新获得点击
            mButton.setBackgroundResource(R.drawable.btn_bg_yanzheng2);
            mButton2.setClickable(true);
            mButton2.setText("重新获取验证码");
            mButton2.setBackgroundResource(R.drawable.btn_bg_yanzheng);
        }
    }

}