package edu.up.cs301.androidchessproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;

public class ChessSurfaceView extends FlashSurfaceView implements View.OnTouchListener, View.OnClickListener {
    public ChessSurfaceView(Context context) {
        super(context);
        init();
    }
    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    private void init() {
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button){

        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawCircle(300,300,200,p);
    }
}
