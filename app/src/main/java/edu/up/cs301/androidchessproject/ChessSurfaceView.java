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

    public ChessSurfaceView(Context context) {
        super(context);
        init();

        ChessSquare[][] squares = new ChessSquare[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                squares[i][j] = new ChessSquare();
            }
        }
        GameBoard chessBoard = new GameBoard(squares);
        Rook blackRook = new Rook( 0,0);
        BlackPieces.add(blackRook);
        Rook whiteRook = new Rook( 0,1);
        WhitePieces.add(whiteRook);

        King blackKing= new King( 0,0);
        BlackPieces.add(blackKing);
        King whiteKing = new King( 0,1);
        WhitePieces.add(whiteKing);

        Knight blackKnight = new Knight( 0,0);
        BlackPieces.add(blackKnight);
        Knight whiteKnight = new Knight( 0,1);
        WhitePieces.add(whiteKnight);

        Pawn blackPawn = new Pawn( 0,0);
        BlackPieces.add(blackPawn);
        Pawn whitePawn = new Pawn( 0,1);
        WhitePieces.add(whitePawn);

        Queen blackQueen = new Queen( 0,0);
        BlackPieces.add(blackQueen);
        Queen whiteQueen = new Queen( 0,1);
        WhitePieces.add(whiteQueen);

        Bishop blackBishop = new Bishop( 0,0);
        BlackPieces.add(blackBishop);
        Bishop whiteBishop = new Bishop( 0,1);
        WhitePieces.add(whiteBishop);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(squares[i][j].getPiece() != null){
                    if(squares[i][j].getPiece() instanceof King){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(squares[i][j].getPiece() instanceof Knight){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(squares[i][j].getPiece() instanceof Bishop){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(squares[i][j].getPiece() instanceof Pawn){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(squares[i][j].getPiece() instanceof Queen){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
                            //R.drawable.kb;
                        }
                    }
                    if(squares[i][j].getPiece() instanceof Rook){
                        if(squares[i][j].getPiece().getBlackOrWhite() == 0) {
                            //R.drawable.kb;
                        }
                        if(squares[i][j].getPiece().getBlackOrWhite() == 1) {
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
    }// ctor

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
}

