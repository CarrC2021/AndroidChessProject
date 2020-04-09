package edu.up.cs301.androidchessproject.boardandpieces;

import android.util.Log;

import edu.up.cs301.androidchessproject.ChessState;

public class Rook extends ChessPiece {
    public Rook(int l, int c, int b) {
        super(l, c, b);
    }

    public static boolean isValidRookMove(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        ChessPiece piece = state.getBoard().getSquares()[rowStart][colStart].getPiece();
        if (piece == null) return false;
        int color = piece.getBlackOrWhite();
        int colorDiff = Math.abs(piece.getBlackOrWhite() - color);
        try{
            //are there no pieces in the way of this move along rows
            if (colDiff == 0 && state.getBoard().areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)){
                if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || colorDiff == 1){
                    return true;
                }
            }
            //are there no pieces in the way of this move along cols
            else if (rowDiff == 0 && state.getBoard().areSquaresOnLineEmpty(false, colStart, colEnd, rowEnd)){
                if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || colorDiff == 1){
                    return true;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }
}
