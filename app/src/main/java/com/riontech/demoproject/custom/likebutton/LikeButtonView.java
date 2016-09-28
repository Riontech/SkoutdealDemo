package com.riontech.demoproject.custom.likebutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.riontech.demoproject.R;

/**
 * Created by Miroslaw Stanek on 20.12.2015.
 */
public class LikeButtonView extends FrameLayout {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR =
            new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR
            = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR
            = new OvershootInterpolator(4);

    ImageView mImgHeart;
    DotsView mDotsView;
    CircleView mCircleView;

    private AnimatorSet animatorSet;

    public LikeButtonView(Context context) {
        super(context);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LikeButtonView(Context context, AttributeSet attrs,
                          int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.like_button, this, true);
        mImgHeart = (ImageView) view.findViewById(R.id.ivStar);
        mDotsView = (DotsView) view.findViewById(R.id.vDotsView);
        mCircleView = (CircleView) view.findViewById(R.id.vCircle);
        //setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mImgHeart.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
                mImgHeart.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;
        }
        return true;
    }

    public void startAnimation() {

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        mImgHeart.animate().cancel();
        mImgHeart.setScaleX(0);
        mImgHeart.setScaleY(0);
        mCircleView.setInnerCircleRadiusProgress(0);
        mCircleView.setOuterCircleRadiusProgress(0);
        mDotsView.setCurrentProgress(0);

        animatorSet = new AnimatorSet();

        ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(mCircleView,
                CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        outerCircleAnimator.setDuration(250);
        outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(mCircleView,
                CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        innerCircleAnimator.setDuration(200);
        innerCircleAnimator.setStartDelay(200);
        innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(mImgHeart,
                ImageView.SCALE_Y, 0.2f, 1f);
        starScaleYAnimator.setDuration(350);
        starScaleYAnimator.setStartDelay(250);
        starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(mImgHeart,
                ImageView.SCALE_X, 0.2f, 1f);
        starScaleXAnimator.setDuration(350);
        starScaleXAnimator.setStartDelay(250);
        starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(mDotsView,
                DotsView.DOTS_PROGRESS, 0, 1f);
        dotsAnimator.setDuration(900);
        dotsAnimator.setStartDelay(50);
        dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

        animatorSet.playTogether(
                outerCircleAnimator,
                innerCircleAnimator,
                starScaleYAnimator,
                starScaleXAnimator,
                dotsAnimator
        );

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                mCircleView.setInnerCircleRadiusProgress(0);
                mCircleView.setOuterCircleRadiusProgress(0);
                mDotsView.setCurrentProgress(0);
                mImgHeart.setScaleX(1);
                mImgHeart.setScaleY(1);
            }
        });

        animatorSet.start();
    }

    public void setImageDrawable(Context ctx, int id) {
        mImgHeart.setImageDrawable(ContextCompat.getDrawable(ctx, id));

    }

}
