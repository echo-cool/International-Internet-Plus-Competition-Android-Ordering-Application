package com.app.myapplication.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.app.myapplication.R;
import com.app.utils.CommonUtils;


/**
 * 这是仿滴滴上车点的VIEW。
 *
 * <p>包括圆点和描边文字效果。
 *
 * @author mjzuo
 * @since 19/12/17
 */
public class SpotTextView extends View {
    /**
     * 上车点的原点属性
     */
    private Paint mBitmapPaint;
    private Bitmap mCircleBitmap;
    private int mRadius;

    /**
     * 上车点文字及描边
     */
    private String mTitleTxt;
    private int mTxtColor;
    private int mTxtSize;
    private int mTxtStrokeWidth;
    private int mTxtStrokeColor;
    private Paint mTxtPaint;
    private Paint mTxtStrokePaint;
    private Rect mTxtRect;

    /**
     * 方向。
     * <ul>
     * <li>0：圆点+文字
     * <li>1：文字+圆点
     * </ul>
     */
    private int mDirectionType;

    /**
     * 上车点1行的最大字数
     */
    public static int MAX_TXT_SIZE = 8;

    /**
     * 圆点与图片的距离
     */
    private int mDistance;

    /**
     * 行间距
     */
    private int mTxtMargin;

    /**
     * 基本UI配置类
     */
    private UIStyle uiStyle;

    public SpotTextView(Context context) {
        this(context, null);
    }

    public SpotTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpotTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUiStyle(context);
        init();
    }

    /**
     * 设置默认上车点的样式。
     */
    public void setUiStyle(Context context) {
        this.uiStyle = new UIStyle()
                .setTextSize(CommonUtils.sp2px(context.getApplicationContext(), 13))
                .setTextColor(0xFF3cbca3)
                .setBorderSize(CommonUtils.dip2px(context.getApplicationContext(), 2))
                .setBorderColor(0xFFFFFFFF)
                .setDotIcon(R.mipmap.pickup_cirlce_icon
                        , CommonUtils.dip2px(context.getApplicationContext(), 3))
                .setDistance(CommonUtils.dip2px(context.getApplicationContext(), 3))
                .setMaxWordsPerLine(8);
    }

    /**
     * 设置上车点的内容。
     *
     * @param title 上车点描述
     */
    public void setTitle(String title) {
        this.mTitleTxt = title;
    }

    /**
     * 设置上车点的方向。
     *
     * @param type 上车点方向
     *             <ul>
     *             <li>0：圆点+文字
     *             <li>1：文字+圆点
     *             </ul>
     */
    public void setDirectionType(int type) {
        this.mDirectionType = type;
    }

    /**
     * 手动刷新重绘。
     */
    public void statrInvalidata() {
        if (uiStyle == null || mTxtPaint == null)
            return;

        initData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mTitleTxt == null || uiStyle == null) {
            return;
        }

        if (mDirectionType == 0) {
            canvas.drawBitmap(mCircleBitmap
                    , 0
                    , (mTxtRect.height() - 2 * mRadius)/2 + mTxtStrokeWidth + getPaddingTop()
                    , mBitmapPaint);

            Paint.FontMetrics fontMetrics = mTxtStrokePaint.getFontMetrics();
            mTxtPaint.setTextAlign(Paint.Align.CENTER);
            mTxtStrokePaint.setTextAlign(Paint.Align.CENTER);

            if (mTitleTxt.length() <= MAX_TXT_SIZE) {
                mTxtStrokePaint.getTextBounds
                        (mTitleTxt, 0, mTitleTxt.length(), mTxtRect);

                canvas.drawText(mTitleTxt
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + getPaddingTop()
                                + mTxtStrokeWidth + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);

                canvas.drawText(mTitleTxt
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

            } else {
                String line1 = mTitleTxt.substring(0, MAX_TXT_SIZE);
                String line2 = mTitleTxt.substring(MAX_TXT_SIZE);
                mTxtStrokePaint.getTextBounds(line1, 0, line1.length(), mTxtRect);

                canvas.drawText(line1
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);
                canvas.drawText(line1
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

                mTxtPaint.setTextAlign(Paint.Align.LEFT);
                mTxtStrokePaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(line2
                        , 2 * mRadius + mDistance + mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin
                                + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtStrokePaint);
                canvas.drawText(line2
                        , 2 * mRadius + mDistance + mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin
                                + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtPaint);
            }
        } else if(mDirectionType == 1) {

            Paint.FontMetrics fontMetrics = mTxtStrokePaint.getFontMetrics();
            mTxtPaint.setTextAlign(Paint.Align.CENTER);
            mTxtStrokePaint.setTextAlign(Paint.Align.CENTER);

            if (mTitleTxt.length() <= MAX_TXT_SIZE) {
                mTxtStrokePaint.getTextBounds(mTitleTxt, 0, mTitleTxt.length(), mTxtRect);

                canvas.drawText(mTitleTxt
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);

                canvas.drawText(mTitleTxt
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

            } else {
                String line1 = mTitleTxt.substring(0, MAX_TXT_SIZE);
                String line2 = mTitleTxt.substring(MAX_TXT_SIZE);
                mTxtStrokePaint.getTextBounds(line1, 0, line1.length(), mTxtRect);

                canvas.drawText(line1
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);
                canvas.drawText(line1
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop()
                                + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

                mTxtPaint.setTextAlign(Paint.Align.LEFT);
                mTxtStrokePaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(line2
                        , mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin
                                + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtStrokePaint);
                canvas.drawText(line2
                        , mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin
                                + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtPaint);
            }

            canvas.drawBitmap(mCircleBitmap
                    , mTxtRect.width() + 2 * mTxtStrokeWidth + mDistance
                    , (mTxtRect.height() - 2 * mRadius)/2
                            + mTxtStrokeWidth + getPaddingTop()
                    , mBitmapPaint);
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
                String title = mTitleTxt;
                if (title == null)
                    return;
                if (title.length() <= MAX_TXT_SIZE) {
                    mTxtStrokePaint.getTextBounds(title, 0, title.length(), mTxtRect);
                } else {
                    mTxtStrokePaint.getTextBounds(title, 0, MAX_TXT_SIZE, mTxtRect);

                }
                widthSize = mTxtRect.width() + 2 * mRadius + mDistance + 2 * mTxtStrokeWidth
                        + getPaddingLeft() + getPaddingRight();
                break;

            case MeasureSpec.EXACTLY:
                break;
        }

        switch (heightModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                String title = mTitleTxt;
                if (title == null)
                    return;
                mTxtStrokePaint.getTextBounds(title, 0, title.length(), mTxtRect);
                if (title.length() <= MAX_TXT_SIZE) {
                    heightSize = mTxtRect.height() + 2 * mTxtStrokeWidth
                            + getPaddingTop() +getPaddingBottom();
                } else {
                    heightSize = mTxtRect.height() * 2 + mTxtMargin + 4 * mTxtStrokeWidth
                            + getPaddingTop() +getPaddingBottom();
                }
                break;

            case MeasureSpec.EXACTLY:
                break;

        }

        setMeasuredDimension(widthSize, heightSize);
    }

    private void init() {
        mTxtPaint = new Paint();
        mTxtStrokePaint = new Paint();
        mBitmapPaint = new Paint();
        if(mTxtRect == null)
            mTxtRect = new Rect();
    }

    private void initData() {
        mRadius = uiStyle.getDotIconRadius();
        mTxtColor = uiStyle.getTextColor();
        mTxtSize = (int)(uiStyle.getTextSize());
        mTxtStrokeWidth = uiStyle.getBorderSize();
        mTxtStrokeColor = uiStyle.getBorderColor();
        mDistance = uiStyle.getDistance();
        MAX_TXT_SIZE = uiStyle.getMaxWordsPerLine();

        mTxtPaint.setColor(mTxtColor);
        mTxtPaint.setTextSize(mTxtSize);
        mTxtPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mTxtStrokePaint.setColor(mTxtStrokeColor);
        mTxtStrokePaint.setTextSize(mTxtSize);
        mTxtStrokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTxtStrokePaint.setStyle(Paint.Style.STROKE);
        mTxtStrokePaint.setStrokeWidth(mTxtStrokeWidth);
        mTxtStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        if(mBitmapPaint == null)
            mBitmapPaint = new Paint();

        mCircleBitmap = BitmapFactory.decodeResource
                (getContext().getResources(), uiStyle.getDotIcon());
        mCircleBitmap = CommonUtils.conversionBitmap(mCircleBitmap
                , mRadius * 2
                , mRadius * 2);
    }

    /**
     * 上车点样式UI的配置类。
     */
    class UIStyle {

        private int dotIcon;
        private int dotIconRadius;
        private float textSize;
        private int textColor;
        private int borderSize;
        private int borderColor;
        private int distanceFromdotToWord;
        private int maxWordsPerLine;

        /**
         * 设置上车点的圆点图片icon。
         *
         * @param dotIcon 园点图片的资源ID
         *                如：R.drawable.xxx
         * @param dotIconRadius 圆点半径
         * @return {@code UIStyle}
         */
        public UIStyle setDotIcon(int dotIcon, int dotIconRadius) {
            this.dotIcon = dotIcon;
            this.dotIconRadius = dotIconRadius;
            return this;
        }

        /**
         * 设置上车点字体大小，单位sp。
         *
         * @param textSize 文字大小，默认值12sp
         * @return {@code UIStyle}
         */
        public UIStyle setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置上车点的颜色值。
         *
         * @param textColor 颜色值，16进制值
         * @return {@code UIStyle}
         */
        public UIStyle setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * 设置上⻋点文字边界宽度，单位dp。
         *
         * @param borderSize 边距，默认1dp
         * @return {@code UIStyle}
         */
        public UIStyle setBorderSize(int borderSize) {
            this.borderSize = borderSize;
            return this;
        }

        /**
         * 设置上车点边界颜色值。
         *
         * @param borderColor 颜色值，16进制值
         * @return {@code UIStyle}
         */
        public UIStyle setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        /**
         * 设置圆点与文字间间距离。
         *
         * @param distance
         * @return {@code UIStyle}
         */
        public UIStyle setDistance(int distance) {
            this.distanceFromdotToWord = distance;
            return this;
        }

        /**
         * 设置单行的最大字数。
         *
         * <p>超过则需要换行，但最多2行，以..结尾。
         *
         * @param maxWordsPerLine 最大字数，默认8
         * @return {@code UIStyle}
         */
        public UIStyle setMaxWordsPerLine(int maxWordsPerLine) {
            this.maxWordsPerLine = maxWordsPerLine;
            return this;
        }

        /**
         * 获取单行的最大字数。
         *
         * @return 单行最大字数
         */
        public int getMaxWordsPerLine() {
            return maxWordsPerLine;
        }

        /**
         * 获取圆点与文字间间距离。
         *
         * @return 间距值
         */
        public int getDistance() {
            return distanceFromdotToWord;
        }

        /**
         * 获取上车点的圆点图片ResID。
         *
         * @return 资源ID
         */
        public int getDotIcon() {
            return dotIcon;
        }

        /**
         * 获取上车点圆点半径。
         *
         * @return 半径
         */
        public int getDotIconRadius() {
            return dotIconRadius;
        }

        /**
         * 获取上车点字体大小。
         *
         * @return 字体大小
         */
        public float getTextSize() {
            return textSize;
        }

        /**
         * 获取上车点字体颜色。
         *
         * @return 颜色值
         */
        public int getTextColor() {
            return textColor;
        }

        /**
         * 获取上⻋点文字边界宽度。
         *
         * @return 边界宽度值
         */
        public int getBorderSize() {
            return borderSize;
        }

        /**
         * 获取上车点边界颜色值。
         *
         * @return 边界颜色值
         */
        public int getBorderColor() {
            return borderColor;
        }

    }
}

