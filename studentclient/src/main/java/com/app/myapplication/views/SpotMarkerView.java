package com.app.myapplication.views;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.app.myapplication.R;
import com.app.utils.CommonUtils;

public class SpotMarkerView extends View {
    private static final int MSG_TOP_DRAW = 0;
    private static final int MSG_RIPPLE_DRAW = 1;

    // 动画间隔时间
    private int animaTopIntervalTime = 100;
    private int animaBotIntervalTime = 75;

    // 顶部的加载圆的大圈
    private Paint mTopCirclePaint;
    private int mTopCircleMaxRadius;
    private int topIntervalDistance;

    // 顶部动画的加载圆
    private Paint mTopCircleAnimPaint;
    private int mTopCircleAnimaRadius;

    // 顶部的加载圆的小圆
    private Paint mTopSmallCirclePaint;
    private int mTopSmallCircleRadius;

    // 大头针的柱子
    private Paint mBitmapPaint;
    private Bitmap mRectBitmap;
    private int mRectHeight;
    private int mRectWidth;

    // 底部的扩散波纹圆
    private Paint mBotCirclePaint;
    private int mBotCircleMaxRadius;
    private int mBotCircleAnimaRadius;
    private int alphaValue;

    private DrawTimingThread drawTimingThread;

    public SpotMarkerView(Context context) {
        this(context, null);
    }

    public SpotMarkerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpotMarkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 开始加载动画。
     */
    public void startLoadingAnima() {
        if(drawTimingThread != null)
            drawTimingThread.sendEmptyMessage(MSG_TOP_DRAW);
    }

    /**
     * 结束加载动画。
     */
    public void stopLoadingAnima() {
        if(drawTimingThread != null)
            drawTimingThread.removeMessages(MSG_TOP_DRAW);
        mTopCircleAnimaRadius = 0;
        invalidate();
    }

    /**
     * 开始底部波纹。
     */
    public void startRippleAnima() {
        if(drawTimingThread != null)
            drawTimingThread.sendEmptyMessage(MSG_RIPPLE_DRAW);
    }

    /**
     * 结束底部波纹。
     */
    public void stopRippleAnima() {
        if(drawTimingThread != null)
            drawTimingThread.removeMessages(MSG_RIPPLE_DRAW);
    }

    /**
     * 给大头针添加跳动动画。
     */
    public ObjectAnimator transactionAnimWithMarker() {
        if(getVisibility() != View.VISIBLE)
            return null;

        ObjectAnimator mTAnimator1 = ObjectAnimator.ofFloat(this
                , "translationY"
                , getTranslationY()
                , getTranslationY() - CommonUtils.dip2px
                        (getContext().getApplicationContext(), 20));
        ObjectAnimator mTAnimator2 = ObjectAnimator.ofFloat(this
                , "translationY"
                , getTranslationY() - CommonUtils.dip2px
                        (getContext().getApplicationContext(), 20)
                , getTranslationY());

        mTAnimator1.setInterpolator(new DecelerateInterpolator());
        mTAnimator1.setDuration(400);
        mTAnimator2.setInterpolator(new AccelerateInterpolator());
        mTAnimator2.setDuration(200);

        AnimatorSet mSet1 = new AnimatorSet();
        mSet1.play(mTAnimator1).before(mTAnimator2);
        mSet1.start();

        return mTAnimator2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int cy = mBotCircleMaxRadius
                - mRectHeight
                - mTopCircleMaxRadius;

        canvas.drawCircle(mBotCircleMaxRadius
                , cy
                , mTopCircleMaxRadius
                , mTopCirclePaint);

        if (mTopCircleAnimaRadius != 0
                && mTopCircleAnimaRadius > mTopSmallCircleRadius) {
            canvas.drawCircle(mBotCircleMaxRadius
                    , cy
                    , mTopCircleAnimaRadius
                    , mTopCircleAnimPaint);
            mTopSmallCirclePaint.setColor(0x7F3cbca3);
        } else
            mTopSmallCirclePaint.setColor(0xFFffffff);

        canvas.drawCircle(mBotCircleMaxRadius
                , cy
                , mTopSmallCircleRadius
                , mTopSmallCirclePaint);

        canvas.drawBitmap(mRectBitmap
                , mBotCircleMaxRadius - mRectWidth/2
                , mBotCircleMaxRadius - mRectHeight
                , mBitmapPaint);

        if (mBotCircleAnimaRadius != 0
                && mBotCircleAnimaRadius < mBotCircleMaxRadius) {
            canvas.drawCircle(mBotCircleMaxRadius
                    , mBotCircleMaxRadius
                    , mBotCircleAnimaRadius
                    , mBotCirclePaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                widthSize = mBotCircleMaxRadius * 2;
                break;

            case MeasureSpec.EXACTLY:
                break;

        }

        switch (heightModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                heightSize = mBotCircleMaxRadius * 2;
                break;

            case MeasureSpec.EXACTLY:
                break;

        }

        setMeasuredDimension(widthSize, heightSize);
    }

    private class DrawTimingThread extends Handler {

        public DrawTimingThread(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                switch (msg.what) {
                    case MSG_TOP_DRAW:
                        if (mTopCircleAnimaRadius < mTopCircleMaxRadius - 2 * topIntervalDistance)
                            mTopCircleAnimaRadius += topIntervalDistance;
                        else
                            mTopCircleAnimaRadius = mTopSmallCircleRadius + topIntervalDistance;

                        drawTimingThread.removeMessages(MSG_TOP_DRAW); // handler循环刷新
                        drawTimingThread.sendEmptyMessageDelayed(MSG_TOP_DRAW, animaTopIntervalTime);

                        invalidate(); // 绘制
                        break;

                    case MSG_RIPPLE_DRAW:
                        if (mBotCircleAnimaRadius < mBotCircleMaxRadius) {
                            mBotCircleAnimaRadius += topIntervalDistance * 8;
                            drawTimingThread.removeMessages(MSG_RIPPLE_DRAW);
                            drawTimingThread.sendEmptyMessageDelayed(MSG_RIPPLE_DRAW, animaBotIntervalTime);

                            mBotCirclePaint.setAlpha(getAlphaOfRipple());
                            invalidate();
                        } else { // 波纹效果只执行一次
                            mBotCircleAnimaRadius = 0;
                            drawTimingThread.removeMessages(MSG_RIPPLE_DRAW);
                        }
                        break;

                }

            } catch (Exception e) {
                Log.e(">>tag1234", "point marker view error :" + msg);
            }
        }
    }

    private void init(Context context) {
        mTopCircleMaxRadius = CommonUtils.dip2px(context.getApplicationContext(), 12);
        mTopCirclePaint = new Paint();
        mTopCirclePaint.setColor(0xFF3cbca3);
        mTopCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mTopSmallCircleRadius = CommonUtils.dip2px(context.getApplicationContext(), 3);
        mTopSmallCirclePaint = new Paint();
        mTopSmallCirclePaint.setColor(0xFFffffff);
        mTopSmallCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mBotCircleMaxRadius = CommonUtils.dip2px(context.getApplicationContext(), 64);
        mBotCirclePaint = new Paint();
        mBotCirclePaint.setColor(0x333cbca3);
        mBotCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mRectHeight = CommonUtils.dip2px(context.getApplicationContext(), 14);
        mRectWidth = CommonUtils.dip2px(context.getApplicationContext(), 4);

        if (mBitmapPaint == null)
            mBitmapPaint = new Paint();
        mRectBitmap = BitmapFactory.decodeResource(context.getResources()
                , R.mipmap.point_rect_icon);
        mRectBitmap = CommonUtils.conversionBitmap(mRectBitmap
                , mRectWidth
                , mRectHeight);

        mTopCircleAnimPaint = new Paint();
        mTopCircleAnimPaint.setColor(0xFFffffff);
        topIntervalDistance = CommonUtils.dip2px(context.getApplicationContext(), 1);

        drawTimingThread = new DrawTimingThread(Looper.getMainLooper());
    }

    private int getAlphaOfRipple() {
        int alpha = 100 - (mBotCircleAnimaRadius * 100 / mBotCircleMaxRadius);
        if (alpha > 50)
            return 50;
        return alpha;
    }

}
