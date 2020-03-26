package edu.up.cs301.androidchessproject;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the current activity
    private Activity myActivity;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
    }
}
