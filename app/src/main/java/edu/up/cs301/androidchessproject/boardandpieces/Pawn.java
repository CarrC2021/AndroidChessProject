package edu.up.cs301.androidchessproject.boardandpieces;

import android.util.Log;

import edu.up.cs301.androidchessproject.ChessState;

public class Pawn extends ChessPiece {

    public Pawn(int l, int c,int b) {
        super(l,c,b);
    }

    //a method to see if a given move of a pawn was a valid one
    public static boolean isValidPawnMove(ChessState state, ChessPiece pieceEnd, int rowStart, int colStart, int rowEnd, int colEnd){
        ChessPiece piece = state.getBoard().getSquares()[rowStart][colStart].getPiece();
        if (piece == null) return false;
        int color = piece.getBlackOrWhite();
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        ChessPiece endSquarePiece = state.getBoard().getSquares()[rowEnd][colEnd].getPiece();

        boolean hasNotMoved = !state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved();
        try {
            if (color == WHITE) {
                //if they move 2 spaces forward on their first move of that pawn make sure that there is no piece hindering the path
                if (rowDiff == - 2 && hasNotMoved && state.getBoard().areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)) {
                    return !state.getBoard().getSquares()[rowStart - 2][colStart].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == - 1 && colEnd == colStart && endSquarePiece == null) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (endSquarePiece != null && (rowDiff == - 1 && colEnd == colStart - 1 || colEnd == colStart + 1)) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK)
                        return true;
                } else {
                    return false;
                }
            }
            if (color == BLACK) {
                //if they move 2 spaces forward on their first move of that pawn make sure that there is no piece hindering the path
                if (rowDiff == 2 && hasNotMoved && state.getBoard().areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)) {
                    return !state.getBoard().getSquares()[rowStart - 2][colStart].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == 1 && colEnd == colStart && endSquarePiece == null) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (endSquarePiece != null &&(rowDiff == 1 && colDiff == -1 || colDiff == 1)) {
                    if (endSquarePiece.getBlackOrWhite() == WHITE)
                        return true;
                } else {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }


    public boolean isValidEnPassant(int rowStart, int colStart, int rowEnd, int colEnd){
        return false;
    }
}
