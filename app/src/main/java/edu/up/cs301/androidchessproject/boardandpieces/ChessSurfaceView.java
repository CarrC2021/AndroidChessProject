package edu.up.cs301.androidchessproject.boardandpieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import edu.up.cs301.androidchessproject.ChessState;
import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;

public class ChessSurfaceView extends FlashSurfaceView {

    ChessState state;
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
    //final static float SCALE_IN_VIEW = 0.5f;

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    public ChessSurfaceView(Context context) {
        super(context);
        state = new ChessState();
    }


    public void onDraw(Canvas c){

        drawBoard(c, SQUARE_SIZE);
        drawPieces(c, SQUARE_SIZE);

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

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }
}
