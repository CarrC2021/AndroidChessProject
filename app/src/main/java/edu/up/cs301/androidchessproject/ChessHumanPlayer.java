/**
 * class ChessHumanPlayer
 *
 * is a class for a chess player that is controlled by a human. This game will
 * have access to the state in order to display it and will handle any
 * actions the human player takes.
 *
 * @author Casey Carr, Vegdahl,
 * @version March 2020
 *
 */

        package edu.up.cs301.androidchessproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.animation.AnimationSurface;
import edu.up.cs301.game.GameFramework.animation.Animator;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;
import edu.up.cs301.game.R;

public class ChessHumanPlayer extends GameHumanPlayer implements Animator {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final int WHITE_KING_MAP = 0;
    public static final int BLACK_KING_MAP = 1;
    public static final int WHITE_QUEEN_MAP = 2;
    public static final int BLACK_QUEEN_MAP = 3;
    public static final int WHITE_KNIGHT_MAP = 4;
    public static final int BLACK_KNIGHT_MAP = 5;
    public static final int WHITE_BISHOP_MAP = 6;
    public static final int BLACK_BISHOP_MAP = 7;
    public static final int WHITE_ROOK_MAP = 8;
    public static final int BLACK_ROOK_MAP = 9;
    public static final int WHITE_PAWN_MAP = 10;
    public static final int BLACK_PAWN_MAP = 11;
    public static final int SQUARE_SIZE = 140;

    //timer
    private TextView timerp1;
    private TextView timerp2;

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the current activity
    private Activity myActivity;

    //the current state
    private ChessState state = new ChessState();

    //the current surfaceView
    private AnimationSurface surface;
    private TextView BPMoves;
    private TextView WPMoves;

    //these values will be the location on the "gameboard" the touches occurred
    //not the float values of the event
    private int[] locationOfTouch1;
    private int[] locationOfTouch2;

    //paint for drawing the board
    private Paint brownPaint;
    private Paint whitePaint;

    private ArrayList<int[]> humanPlayerValidMoves;

    private String currentPiece = "";

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name) {
        super(name);
        playerNum = 0;
        brownPaint = new Paint();
        brownPaint.setARGB(255,255,228,196);
        whitePaint = new Paint();
        whitePaint.setARGB(255,139,69,19);
        humanPlayerValidMoves = new ArrayList<>();
    }


    //This method will set the ChessHumanPlayer to be the GUI player when this method is called
    //it will add all the button listeners and it will create the bitmaps
    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        activity.setContentView(R.layout.game_chess);

        surface = activity.findViewById(R.id.animationSurface);
        surface.setAnimator(this);

        Button confirm = activity.findViewById(R.id.Confirm);
        confirm.setOnClickListener(new ConfirmButtonListener());

        Button resign = activity.findViewById(R.id.Resign);
        resign.setOnClickListener(new ResignButtonListener());

        Button draw = activity.findViewById(R.id.Draw);
        draw.setOnClickListener(new DrawButtonListener());

        WPMoves = (TextView)myActivity.findViewById(R.id.whitePlayerMoves);
        WPMoves.setText("testing");

        BPMoves = (TextView)myActivity.findViewById(R.id.blackPlayerMoves);
        BPMoves.setText("testing");

        timerp1 = (TextView)myActivity.findViewById(R.id.P1Timer);

        timerp2 = (TextView)myActivity.findViewById(R.id.P2Timer);


        //will need to add some stuff for the timers so they can be drawn in as the time ticks.

        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.kw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.kb));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.qw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.qb));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.nw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.nb));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bb));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.rw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.rb));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.pw));
        bitmaps.add(BitmapFactory.decodeResource(activity.getResources(), R.drawable.pb));

        if (state != null) {
            receiveInfo(state);
        }
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info instanceof IllegalMoveInfo){
            //figure out later
            surface.flash(Color.RED, 400);
        }
        else if (info instanceof NotYourTurnInfo){
            //figure out
            surface.flash(Color.RED, 400);
        }
        else if (info instanceof ChessState){
            state = null;
            state = ((ChessState)info);

            updateHumanPlayerValidMoves();

            String wMoves = state.printMoves(WHITE);
            Logger.log("list", wMoves);
            String bMoves = state.printMoves(BLACK);
            Logger.log("list", bMoves);

            //need to print out the new move lists here
//            whiteMoveList.setText(wMoves);
//            blackMoveList.setText(bMoves);

            //TextView WPMoves = (TextView)myActivity.findViewById(R.id.whitePlayerMoves);
            //WPMoves.setText(wMoves);

            // TextView BPMoves = (TextView)myActivity.findViewById(R.id.blackPlayerMoves);
            //BPMoves.setText(bMoves);

            WPMoves.setText(wMoves);
            BPMoves.setText(bMoves);

            int min;
            int sec;
            min = state.getPlayer1Timer()/60;
            sec = state.getPlayer1Timer() % 60;
            String time = String.format("%02d:%02d", min,sec);
            timerp1.setText(time);
            //timerp2.setText(""+state.getPlayer2Timer());
            int minP2;
            int secP2;
            minP2 = state.getPlayer2Timer()/60;
            secP2 = state.getPlayer2Timer() % 60;
            String timeP2 = String.format("%02d:%02d", minP2,secP2);
            timerp2.setText(timeP2);
            surface.invalidate();
        }
    }

    @Override
    public int interval() {
        return 0;
    }

    @Override
    public int backgroundColor() {
        return 0;
    }

    @Override
    public boolean doPause() {
        return false;
    }

    @Override
    public boolean doQuit() {
        return false;
    }

    @Override
    public void tick(Canvas canvas) {
        if (state == null) return;
        drawBoard(canvas, SQUARE_SIZE);
        drawPieces(canvas, SQUARE_SIZE);
        drawHighlights(canvas, SQUARE_SIZE);

    }

    @Override
    //fix this
    public void onTouch(MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) { // added
            float x = event.getX();
            float y = event.getY();
            int[] tempArray = convertCoordinateToSquare(x, y);
            if (tempArray != null) {
                if (locationOfTouch1 == null) {
                    locationOfTouch1 = tempArray;
                } else if (locationOfTouch2 == null) {
                    locationOfTouch2 = tempArray;
                    //if touch two is a pawn promotion we need to prompt the player what they want to promote to
                    //if ()
                } else {
                    surface.flash(Color.RED, 100);
                    surface.flash(Color.BLACK, 100);
                    Logger.log("flash", "flash");
                    locationOfTouch2 = null;
                    locationOfTouch1 = null;
                }
            }
        }
    }

    private class ResignButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final ChessResignAction resignAction = new ChessResignAction(ChessHumanPlayer.this);
            AlertDialog.Builder resignAlert = new AlertDialog.Builder(myActivity);
            resignAlert.setMessage(R.string.resignAlert);
            resignAlert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    game.sendAction(resignAction);
                    //Toast.makeText(myActivity, "OK", Toast.LENGTH_LONG).show();
                }
            });
            resignAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            resignAlert.create().show();

        }
    }

    private class DrawButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final ChessDrawAction drawAction = new ChessDrawAction(ChessHumanPlayer.this);
            AlertDialog.Builder drawAlert = new AlertDialog.Builder(myActivity);
            drawAlert.setMessage(R.string.drawAlert);
            drawAlert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    game.sendAction(drawAction);
                    //Toast.makeText(myActivity, "OK", Toast.LENGTH_LONG).show();
                }
            });
            drawAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            drawAlert.create().show();
        }
    }

    private class ConfirmButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (locationOfTouch1 != null && locationOfTouch2 != null){
                game.sendAction(convertToChessMoveAction(ChessHumanPlayer.this,
                        locationOfTouch1, locationOfTouch2));
                currentPiece = "";
            }
            locationOfTouch1 = null;
            locationOfTouch2 = null;
        }
    }

    public int[] convertCoordinateToSquare(float x, float y){
        int row = (int)Math.floor((double)(y/SQUARE_SIZE));
        int col = (int)Math.floor((double)(x/SQUARE_SIZE));

        if (col > 7){
            int[] array = new int[2];
            array[0] = row;
            array[1] = 7;
            return array;
        }
        int[] array = new int[2];
        array[0] = row;
        array[1] = col;
        return array;
    }



    public ChessMoveAction convertToChessMoveAction(GamePlayer player, int[] startLocation,
                                                    int [] endLocation, ChessPiece pieceEnd){
        return new ChessMoveAction(player, pieceEnd, startLocation[0], startLocation[1],
                endLocation[0], endLocation[1]);
    }

    public ChessMoveAction convertToChessMoveAction(GamePlayer player, int[] startLocation,
                                                    int [] endLocation) throws NullPointerException{

        if (startLocation == null || endLocation == null){
            throw new NullPointerException();
        }
        else{
            return new ChessMoveAction(player, startLocation[0], startLocation[1],
                    endLocation[0], endLocation[1]);
        }
    }

    public ChessMoveAction convertToChessMoveAction(GamePlayer player, int[] startLocation,
                                                    int [] endLocation, String s) throws NullPointerException{
        if (startLocation == null || endLocation == null || s == null){
            throw new NullPointerException();
        }
        else{
            return new ChessMoveAction(player, s, startLocation[0], startLocation[1],
                    endLocation[0], endLocation[1]);
        }
    }

    //This method will draw the "squares"
    public void drawBoard(Canvas c, int sizeOfSquare){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((i+j)%2 == 0){
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,
                            (i+1)*sizeOfSquare,whitePaint); //white
                }
                else{
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,
                            (i+1)*sizeOfSquare,brownPaint); //brown
                }
            }
        }
    }

    //This method will draw the "squares"
    public void drawHighlights(Canvas c, int sizeOfSquare){
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);

        if (locationOfTouch1 != null) {
            c.drawCircle(locationOfTouch1[1]*sizeOfSquare+sizeOfSquare/2,
                    locationOfTouch1[0]*sizeOfSquare+sizeOfSquare/2,sizeOfSquare/5,redPaint);
        }

        if (locationOfTouch2 != null) {
            c.drawCircle(locationOfTouch2[1]*sizeOfSquare+sizeOfSquare/2,
                    locationOfTouch2[0]*sizeOfSquare+sizeOfSquare/2,sizeOfSquare/5,redPaint);
        }
    }

    //This method will draw the pieces where they are in the chess state
    public void drawPieces(Canvas c, int SQUARE_SIZE) {
        c.getHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = state.getBoard().getSquares()[i][j].getPiece();
                if (piece != null) {
                    if (piece instanceof King) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KING_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KING_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (piece instanceof Queen) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_QUEEN_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_QUEEN_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (piece instanceof Knight) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KNIGHT_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KNIGHT_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (piece instanceof Bishop) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_BISHOP_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_BISHOP_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (piece instanceof Rook) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_ROOK_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_ROOK_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (piece instanceof Pawn) {
                        if (piece.getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_PAWN_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                        if (piece.getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_PAWN_MAP), j * SQUARE_SIZE - 20,
                                    i * SQUARE_SIZE - 30, null);
                        }
                    }

                }
            }
        }
    }

    /**
     * This method will be called after this player receives a new ChessState, it will
     * update this player's allValidMoves array list which represents all of the legal
     * moves this player can make
     */
    private void updateHumanPlayerValidMoves() {
        humanPlayerValidMoves.clear();
        if (this.playerNum == 1){
            for (ChessPiece piece : state.getBlackPieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        int[] move = {piece.getRow(), piece.getCol(),i,j};
                        if (piece.getValidMoves()[i][j]) {
                            humanPlayerValidMoves.add(move);
                        }
                    }
                }
            }
        }
        else if (this.playerNum == 0){
            for (ChessPiece piece : state.getWhitePieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        int[] move = {piece.getRow(), piece.getCol(),i,j};
                        if (piece.getValidMoves()[i][j]){
                            humanPlayerValidMoves.add(move);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<int[]> getHumanPlayerValidMoves() {
        return humanPlayerValidMoves;
    }

    public void setHumanPlayerValidMoves(ArrayList<int[]> humanPlayerValidMoves) {
        this.humanPlayerValidMoves = humanPlayerValidMoves;
    }

    public AnimationSurface getSurface() {
        return surface;
    }
}
