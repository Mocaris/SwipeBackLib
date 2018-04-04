package mocaris.com.swipebacklib;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by mocaris on 2017/6/17.
 */

public class SwipeBackActivity extends AppCompatActivity {

    private View decorView;
    private int widthPixels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        decorView.setBackgroundColor(Color.parseColor("#FFFAFAFA"));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.3f;//设置暗度
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
    }

    protected void setSwipeFinishEnable(boolean enable) {
        this.swipeEnable = enable;
    }

    protected void setSwipeActivityBackgroundColor(int color) {
        decorView.setBackgroundColor(color);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (swipeEnable) {
            if (ev.getX() > SILDE_X) {
                return super.dispatchTouchEvent(ev);
            } else {
                return onTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    boolean swipeEnable = false;
    //===============================

    float downX = 0;
    private int status_sliding = 1;
    private int status_normal = 0;
    private int current_statue = status_normal;
    private static final int SILDE_X = 15;

    //====================================
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!swipeEnable) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        float offsetX = event.getX() - downX;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();//当前按下的位置
                break;
            case MotionEvent.ACTION_MOVE:
                //当在屏幕左边按下,并且向右滑动
                if (downX < SILDE_X && offsetX > 0) {
                    decorView.setX(offsetX);//设置view的位置

                    current_statue = status_sliding;//设置状态为滑动
                    return true;
                }
                current_statue = status_normal;
                break;
            case MotionEvent.ACTION_UP:
                //当前在滑动状态
                if (current_statue == status_sliding) {
                    //滑动距离小于屏幕1/3,返回
                    if (offsetX / widthPixels < 0.35f) {
                        resetAnim(offsetX);
                    } else {
                        finishAnim(offsetX);
                    }
                    current_statue = status_normal;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void finishAnim(float offsetX) {
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(offsetX, widthPixels);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                decorView.setX(animatedValue);
                if (animatedValue == widthPixels) {
                    finish();
                }
            }
        });
        valueAnimator.start();
    }

    private void resetAnim(float offset) {
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(offset, 0);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                decorView.setX(animatedValue);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
