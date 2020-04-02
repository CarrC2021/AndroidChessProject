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
  
    public ArrayList<ChessPiece> BlackPieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> WhitePieces = new ArrayList<ChessPiece>();

    private ChessState state;

    public ChessSurfaceView(Context context) {
        super(context);
        init();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(state.getBoard().getSquares()[i][j].getPiece() != null){
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof King){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof Knight){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof Bishop){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof Pawn){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof Queen){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(state.getBoard().getSquares()[i][j].getPiece() instanceof Rook){
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                }
            }
        }




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
    private int ChessBoardSize;
    private float scale;
    private int xMargin;
    private int yMargin;
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
    }
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

    public void setBlackPieces(ArrayList<ChessPiece> blackPieces) {
        BlackPieces = blackPieces;
    }

    public void setWhitePieces(ArrayList<ChessPiece> whitePieces) {
        WhitePieces = whitePieces;
    }

    public void setState(ChessState state) {
        this.state = state;
    }
}

