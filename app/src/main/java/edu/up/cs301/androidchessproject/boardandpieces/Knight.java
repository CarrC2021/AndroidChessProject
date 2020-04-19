package edu.up.cs301.androidchessproject.boardandpieces;

import android.util.Log;

import edu.up.cs301.androidchessproject.ChessState;



public class Knight extends ChessPiece {
    public Knight(int l, int c,int b) {
        super(l,c,b);
    }

    public Knight(Knight kn){
        super(kn);
    }

    public static boolean isValidKnightMove(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){
        int colDiff = (colEnd-colStart)*(colEnd-colStart);
        int rowDiff = (rowEnd-rowStart)*(rowEnd-rowStart);
        ChessPiece piece = state.getBoard().getSquares()[rowStart][colStart].getPiece();
        if (piece == null) return false;

        try {
            //you must move in an "L" which means if you square the rowDiff and colDiff you should always get 5 when you add those together.
            if (rowDiff + colDiff == 5) {
                //if the white knight moves make sure the square has a black piece or is empty
                if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == WHITE) {
                    if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK) {
                        return true;
                    }
                }
                //if the black knight moves make sure the square has a white piece or is empty
                else if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == BLACK) {
                    if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == WHITE) {
                        return true;
                    }
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
