/**
 * class ChessHumanPlayer
 *
 * is a class for a chess player that is controlled by a human. This game will
 * have access to the state in order to display it and will handle any
 * actions the human player takes.
 *
 * @author Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
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

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the current activity
    private Activity myActivity;

    //the current state
    private ChessState state = new ChessState();

    //the current surfaceView
    private AnimationSurface surface;

    private String touch1 = null;
    private String touch2 = null;

    private String currentPiece = "";

    public ArrayList<ChessPiece> BlackPieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> WhitePieces = new ArrayList<ChessPiece>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name) {
        super(name);
        playerNum = 0;
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

        Button pause = activity.findViewById(R.id.Pause);
        //need to create a listener for this button

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
            state = ((ChessState)info);
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
    }

    @Override
    //fix this
    public void onTouch(MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) { // added
            float x = event.getX();
            float y = event.getY();
            String move = convertCoordinateToSquare(x, y);
            if (move != null) {
                if (touch1 == null) {
                    currentPiece = state.returnPieceAsChar(state.getBoard().getSquares()[(int)Math.floor((double)(y/SQUARE_SIZE))][(int)Math.floor((double)(x/SQUARE_SIZE))+1]);
                    touch1 =  currentPiece + move;
                } else if (touch2 == null) {
                    touch2 = currentPiece + move;
                    //if touch two is a pawn promotion we need to prompt the player what they want to promote to
                    //if ()
                } else {
                    surface.flash(Color.RED, 100);
                    surface.flash(Color.BLACK, 100);
                    Logger.log("flash", "flash");
                    touch2 = null;
                    touch1 = null;
                }
            }
        }
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
                game.sendAction(convertMoveToChessMoveAction(ChessHumanPlayer.this, touch1 + touch2, null));
            }
        }
    }

    public String convertCoordinateToSquare(float x, float y){
        int row = (int)Math.floor((double)(y/SQUARE_SIZE));
        int col = (int)Math.floor((double)(x/SQUARE_SIZE));

        String temp;
        char r = (char)(97 + col);
        row++;
        Character.toString(r);
        temp =  Character.toString(r) + row + "";

        return temp;
    }

    public ChessMoveAction convertMoveToChessMoveAction(GamePlayer player, String s, ChessPiece pieceEnd){
        int[] array1;
        int[] array2;
        String move;

        if (Character.isUpperCase(s.charAt(0))){
            String square1 = s.substring(0,2);
            String square2 = s.substring(3,5);

            array1 = fromString(square1.substring(1,2));
            array2 = fromString(square2.substring(4,5));
        }
        else {
            String square1 = s.substring(0, 2);
            String square2 = s.substring(2, 4);

            array1 = fromString(square1);
            array2 = fromString(square2);
        }

        return new ChessMoveAction(player, pieceEnd, array1[0], array1[1], array2[0], array2[1]);
    }

    // returns positional value [][] for squares [a8-h1]
    public static int[] fromString(final String s) {
        char c = s.charAt(0);

        int row = Integer.parseInt(s.substring(1));
        int col = (int)c - (int)'a';

        int[] array = new int[2];
        array[0] = row - 1;
        array[1] = col;

        return array;
    }

    //This method will draw the "squares"
    public void drawBoard(Canvas c, int sizeOfSquare){
        Paint brownPaint = new Paint();
        brownPaint.setARGB(255,255,228,196);
        Paint whitePaint = new Paint();
        whitePaint.setARGB(255,139,69,19);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((i+j)%2 == 0){
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,(i+1)*sizeOfSquare,whitePaint); //white
                }
                else{
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,(i+1)*sizeOfSquare,brownPaint); //black
                }
            }
        }
    }

    //This method will draw the pieces where they are in the chess state
    public void drawPieces(Canvas c, int SQUARE_SIZE) {
        c.getHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state.getBoard().getSquares()[i][j].getPiece() != null) {
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof King) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KING_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KING_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Queen) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_QUEEN_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_QUEEN_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Knight) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KNIGHT_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KNIGHT_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Bishop) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_BISHOP_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_BISHOP_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Rook) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_ROOK_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_ROOK_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Pawn) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_PAWN_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_PAWN_MAP), j * SQUARE_SIZE - 20, i * SQUARE_SIZE - 30, null);
                        }
                    }

                }
            }
        }
    }

    public AnimationSurface getSurface() {
        return surface;
    }
}
