package com.example.android_mobiledeveloper_homework2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Calendar;

public class ClockView extends View {
    private Paint paint = new Paint();
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    private final Bitmap backgroundImage;

    private void updateHands() {
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        invalidate();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateHands();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - 50;

        // 计算图片的绘制位置使其居中
        float left = centerX - (float) backgroundImage.getWidth() / 2;
        float top = centerY - (float) backgroundImage.getHeight() / 2;

        // 绘制背景图片
        canvas.drawBitmap(backgroundImage, left, top, paint);

        // Draw hour hand
        paint.setColor(Color.BLACK);
        canvas.drawLine(centerX, centerY,
                centerX + radius * 0.5f * (float)Math.cos(Math.toRadians((hour + minute / 60.0) * 30 - 90)),
                centerY + radius * 0.5f * (float)Math.sin(Math.toRadians((hour + minute / 60.0) * 30 - 90)),
                paint);

        // Draw minute hand
        paint.setColor(Color.BLUE);
        canvas.drawLine(centerX, centerY,
                centerX + radius * 0.8f * (float)Math.cos(Math.toRadians((minute + second / 60.0) * 6 - 90)),
                centerY + radius * 0.8f * (float)Math.sin(Math.toRadians((minute + second / 60.0) * 6 - 90)),
                paint);

        // Draw second hand
        paint.setColor(Color.RED);
        canvas.drawLine(centerX, centerY,
                centerX + radius * 0.9f * (float)Math.cos(Math.toRadians(second * 6 - 90)),
                centerY + radius * 0.9f * (float)Math.sin(Math.toRadians(second * 6 - 90)),
                paint);
    }
}
