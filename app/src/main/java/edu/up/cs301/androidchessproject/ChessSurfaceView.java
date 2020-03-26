package edu.up.cs301.androidchessproject;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;

public class ChessSurfaceView extends FlashSurfaceView implements View.OnTouchListener, View.OnClickListener {
    public ChessSurfaceView(Context context) {
        super(context);
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
}
