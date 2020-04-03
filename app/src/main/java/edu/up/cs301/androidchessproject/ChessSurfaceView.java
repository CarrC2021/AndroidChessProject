/**
 * class ChessSurfaceView
 *
 * This class will have access to a ChessState object in order to draw updated states.
 * It will also hold all the bitmaps required to draw the pieces and
 *
 * @author Ally Hikido, Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.GameBoard;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;
import edu.up.cs301.game.R;


public class ChessSurfaceView extends FlashSurfaceView implements View.OnTouchListener, View.OnClickListener {

    public static final int WHITE_KING_MAP = 1;
    public static final int BLACK_KING_MAP = 2;
    public static final int WHITE_QUEEN_MAP = 3;
    public static final int BLACK_QUEEN_MAP = 4;
    public static final int WHITE_KNIGHT_MAP = 5;
    public static final int BLACK_KNIGHT_MAP = 6;
    public static final int WHITE_BISHOP_MAP = 7;
    public static final int BLACK_BISHOP_MAP = 8;
    public static final int WHITE_ROOK_MAP = 9;
    public static final int BLACK_ROOK_MAP = 10;
    public static final int WHITE_PAWN_MAP = 11;
    public static final int BLACK_PAWN_MAP = 12;


    public ArrayList<ChessPiece> BlackPieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> WhitePieces = new ArrayList<ChessPiece>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    private ChessState state;

    public ChessSurfaceView(Context context) {
        super(context);
        init();
    }
    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //initializing the pieces and background color
        setBackgroundColor(Color.YELLOW);

    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {

        }
    }

    private Bitmap ChessBoardComplete;

    //we will need to change this to different colors so that you can actually see the pieces
    private Paint whitePaint;
    private Paint blackPaint;
    final static float SCALE_IN_VIEW = 0.9f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        drawBoard(c,110);
        drawPieces(c);
    }

    //This method will draw the "squares"
    public void drawBoard(Canvas c, int sizeOfSquare){
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((i+j)%2 == 0){
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,(i+1)*sizeOfSquare,whitePaint); //white
                }
                else{
                    c.drawRect(j*sizeOfSquare,i*sizeOfSquare,(j+1)*sizeOfSquare,(i+1)*sizeOfSquare,blackPaint); //black
                }
            }
        }
    }

    //This method will draw the pieces where they are in the chess state
    public void drawPieces(Canvas c) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state.getBoard().getSquares()[i][j].getPiece() != null) {
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof King) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KING_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KING_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Queen) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_QUEEN_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_QUEEN_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Knight) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_KNIGHT_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_KNIGHT_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Bishop) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_BISHOP_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_BISHOP_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Rook) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            c.drawBitmap(bitmaps.get(WHITE_ROOK_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            c.drawBitmap(bitmaps.get(BLACK_ROOK_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    if (state.getBoard().getSquares()[i][j].getPiece() instanceof Pawn) {
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                                c.drawBitmap(bitmaps.get(WHITE_PAWN_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                        if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                                c.drawBitmap(bitmaps.get(BLACK_PAWN_MAP), i * 110 - 60, j * 110 - 60, null);
                        }
                    }
                }
            }
        }
    }
    }

    public void setBlackPieces(ArrayList<ChessPiece> blackPieces) {
        BlackPieces = blackPieces;
    }

    public void setWhitePieces(ArrayList<ChessPiece> whitePieces) {
        WhitePieces = whitePieces;
    }

    public void setState(ChessState state) {
        this.state = state;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}

