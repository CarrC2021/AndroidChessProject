package edu.up.cs301.androidchessproject.boardandpieces;

import android.graphics.Paint;
import android.util.Log;

import edu.up.cs301.androidchessproject.ChessState;

public class Pawn extends ChessPiece {

    private ChessPiece pieceToPromoteTo;


    public Pawn(int row, int col,int b) {

        super(row,col,b);
        pieceToPromoteTo = null;
    }

    public Pawn(Pawn p){
        super(p);
    }

    //a method to see if a given move of a pawn was a valid one
    public static boolean isValidPawnMove(ChessState state, ChessPiece pieceEnd,
                                          int rowStart, int colStart, int rowEnd, int colEnd){
        ChessPiece piece = state.getBoard().getSquares()[rowStart][colStart].getPiece();
        if (piece == null) return false;
        int color = piece.getBlackOrWhite();
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        boolean endSquarePiece = state.getBoard().getSquares()[rowEnd][colEnd].hasPiece();
        boolean hasNotMoved = !state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved();

        try {
            if(isValidEnPassant(state,rowStart, colStart, rowEnd, colEnd)) return true;

            if (color == WHITE) {
                //if they move 2 spaces forward on their first move of that pawn
                // make sure that there is no piece hindering the path
                if (rowDiff == - 2 && colDiff == 0 && hasNotMoved &&
                        !state.getBoard().getSquares()[rowEnd + 1][colEnd].hasPiece()) {
                    return !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == - 1 && colEnd == colStart && endSquarePiece == false) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (endSquarePiece == true && rowDiff == - 1 && colDiff == -1) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK)
                        return true;
                }
                else if (endSquarePiece == true && rowDiff == - 1 && colDiff == 1) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK)
                        return true;
                }
            }
            if (color == BLACK) {
                //if they move 2 spaces forward on their first move of that pawn
                // make sure that there is no piece hindering the path
                if (rowDiff == 2 && colDiff == 0 && hasNotMoved &&
                        !state.getBoard().getSquares()[rowEnd - 1][colEnd].hasPiece()) {
                    return !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == 1 && colEnd == colStart && endSquarePiece == false) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (endSquarePiece == true && rowDiff == 1 && (colDiff == -1 || colDiff == 1)) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == WHITE)
                        return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception){
            Log.i("pawn move", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }

    /**
     * returns true if diagonally one space over since that is the space that a pawn threatens
     */
    public boolean giveSquaresPawnThreatens(int row, int col){
        int rowDiff = row - getRow();
        int colDiff = col - getCol();
        if (getBlackOrWhite() == WHITE){
            if (rowDiff == -1 && (colDiff == -1 || colDiff == 1)){
                return true;
            }
        }
        else if (rowDiff == 1 && (colDiff == -1 || colDiff == 1)){
            return true;
        }
        return false;
    }


    public static boolean isValidEnPassant(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;

        //if the state says an enPassant move is possible then check that they are
        //moving one square diagonally and check they are moving behind a piece
        if (state.isEnPassantCapable() && (Math.abs(rowDiff) + Math.abs(colDiff)) == 2){
            if (colEnd == state.getEnPassantPiece().getCol() &&
                    Math.abs(rowEnd - state.getEnPassantPiece().getRow()) == 1){
                return true;
            }
        }
        return false;
    }

}
