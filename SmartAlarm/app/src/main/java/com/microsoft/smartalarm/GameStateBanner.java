package com.microsoft.smartalarm;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

public class GameStateBanner extends TextView {
    public interface Command{
        void execute();
    }
    private AnimatorSet mEnterLeftAnimation;
    private int mWidth;
    private int mSuccessColor, mFailureColor;

    public GameStateBanner(Context context) {
        this(context, null);
    }
    public GameStateBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameStateBanner(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ContextCompat contextCompat = new ContextCompat();
        mSuccessColor = contextCompat.getColor(context, R.color.green3);
        mFailureColor = contextCompat.getColor(context, R.color.dark3);

        mEnterLeftAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context,
                R.animator.game_success_animator);
        mEnterLeftAnimation.setTarget(this);
    }

    public void success(String message, final Command onAnimationEnd){
        setBackgroundColor(mSuccessColor);
        animate(message, onAnimationEnd);

    }
    public void failure(String message, final Command onAnimationEnd){
        setBackgroundColor(mFailureColor);
        animate(message, onAnimationEnd);
    }

    private void animate(String message, final Command onAnimationEnd){
        setText(message);
        mEnterLeftAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(VISIBLE);
                bringToFront();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onAnimationEnd != null) {
                    onAnimationEnd.execute();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        mEnterLeftAnimation.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    public void setXPercentage(float value){
        value /= 100f;
        setX((mWidth > 0) ? (value * mWidth) : 0);
    }
}