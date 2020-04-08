package edu.up.cs301.androidchessproject.boardandpieces;

import edu.up.cs301.androidchessproject.ChessState;

public class Bishop extends ChessPiece {
    public Bishop(int l, int c, int b) {
        super(l,c,b);
    }


    public static boolean isValidBishopMove(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){

        int color = state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite();
        boolean lastSquareHasPiece = state.getBoard().getSquares()[rowEnd][colEnd].hasPiece();

        //if the diagonal is not empty then we should return
        if (!state.getBoard().areSquaresOnDiagonalEmpty(rowStart,colStart,rowEnd,colEnd)) return false;

        //if there is no piece in the final square then we should be able to move there
        if (!lastSquareHasPiece) return true;

        //check if the last square has a piece of the opposite color
        if (color == WHITE){
            if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK){
                return true;
            }
        }
        else {
            if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == WHITE){
                return true;
            }
        }
        return false;
    }

}
