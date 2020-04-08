package edu.up.cs301.androidchessproject.boardandpieces;

import edu.up.cs301.androidchessproject.ChessState;

public class Queen extends ChessPiece {
    public Queen(int l, int c,int b) { super(l,c,b);
    }

    public static boolean isValidQueenMove(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd) {
        int rowDiff = rowEnd -rowStart;
        int colDiff = colEnd - colStart;
        int color = state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite();
        if (rowDiff == 0 && state.getBoard().areSquaresOnLineEmpty(false, colStart, colEnd, rowEnd)){
            //if there are no pieces in the way of the suggested move and the last square is empty or is an opponents piece then it is a valid move
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        else if (colDiff == 0 && state.getBoard().areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)){
            //if there are no pieces in the way and the last square is empty or is an opponents piece then it is a valid move
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        //if it is not a straight line up, down, left, or right then it must be on a diagonal
        else if (state.getBoard().areSquaresOnDiagonalEmpty(rowStart, colStart, rowEnd, colEnd)){
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        return false;
    }
}
