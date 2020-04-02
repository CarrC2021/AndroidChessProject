package edu.up.cs301.androidchessproject;

import android.app.Activity;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.R;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the current activity
    private Activity myActivity;

    //the current state
    private ChessState state;

    //the current surfaceView
    private ChessSurfaceView surfaceView;

    private String touch1;
    private String touch2;

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

        if (info instanceof IllegalMoveInfo){
            //figure out later
        }
        else if (info instanceof NotYourTurnInfo){
            //figure out
        }
        else if (info instanceof ChessState){
            surfaceView.setState((ChessState)info);
            surfaceView.invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        String move = convertCoordinateToSquare(x, y);
        if (move != null){
            if (touch1 != null){
                touch1 = move;
            }
            else if(touch2 != null){
                touch2 = move;
            }
            else {
                surfaceView.flash(Color.RED, 100);
                touch2 = null;
                touch1 = null;
            }
        }
        return true;
    }

    private class ResignButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChessResignAction resignAction = new ChessResignAction(ChessHumanPlayer.this);
        }
    }

    private class DrawButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChessDrawAction drawAction = new ChessDrawAction(ChessHumanPlayer.this);
        }
    }

    private class ConfirmButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (touch1 != null && touch2 != null){
                ChessMoveAction move = new ChessMoveAction(ChessHumanPlayer.this, touch1 + touch2);
                game.sendAction(move);
            }
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        activity.setContentView(R.layout.game_chess);

        surfaceView = activity.findViewById(R.id.chessSurfaceView);
        surfaceView.setOnClickListener((View.OnClickListener)this);

        Button confirm = activity.findViewById(R.id.Confirm);
        confirm.setOnClickListener(new ConfirmButtonListener());

        Button resign = activity.findViewById(R.id.Resign);
        confirm.setOnClickListener(new ResignButtonListener());

        Button draw = activity.findViewById(R.id.Draw);
        confirm.setOnClickListener(new DrawButtonListener());

        Button pause = activity.findViewById(R.id.Pause);
        //need to create a listener for this button

        //will need to add some stuff for the timers so they can be drawn in as the time ticks.
    }

    public String convertCoordinateToSquare(float x, float y){
        int row = (int)Math.floor((double)(x/110));
        int col = (int)Math.floor((double)(y/110));

        String temp;
        char r = (char) (97 + row);

        temp = r + "" + col;

        return temp;
    }
}
