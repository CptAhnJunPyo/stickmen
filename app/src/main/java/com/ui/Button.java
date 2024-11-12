package com.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Button {
    private final RectF rect;
    private final String text;

    public Button(float left, float top, float right, float bottom, String text) {
        this.rect = new RectF(left, top, right, bottom);
        this.text = text;
    }

    public boolean contains(float x, float y) {
        return rect.contains(x, y);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, rect.centerX(), rect.centerY() + 10, paint);
    }
}
