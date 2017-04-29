package com.baway.yuekao3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 任珏
 * @类的用途
 * @date 2017/4/28 15:14
 */
public class MyView extends View {

    private int outRadius;
    private int inRadius;
    private int ringColor;
    private String text;
    private int textSize;
    private Paint paint;
    private int centerX;
    private int centerY;
    private float pointX;
    private float pointY;
    private OnViewClickListener listener;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.myView);
        outRadius = typedArray.getInt(R.styleable.myView_outRadius, -1);
        inRadius = typedArray.getInt(R.styleable.myView_inRadius, -1);
        ringColor = typedArray.getColor(R.styleable.myView_ringColor, -1);
        text = typedArray.getString(R.styleable.myView_text);
        textSize = typedArray.getInt(R.styleable.myView_textSize, -1);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnViewClickListener {
        void onClick(String text);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(outRadius * 2, outRadius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        canvas.drawColor(Color.GREEN);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ringColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, outRadius, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, inRadius, paint);

        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, centerX - text.length() / 2 * textSize, centerY + textSize / 2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointX = event.getX();
                pointY = event.getY();
                double range = Math.sqrt((centerX - pointX) * (centerX - pointX) + (centerY - pointY) * (centerY - pointY));
                String text = "";
                if (range < inRadius) {
                    text = "在小圆内";
                } else if (range > inRadius && range < outRadius) {
                    text = "在圆环内";
                } else if (range > outRadius) {
                    text = "在圆环外";
                }
                if (listener != null) {
                    listener.onClick(text);
                }
                break;
        }
        return true;
    }
}
