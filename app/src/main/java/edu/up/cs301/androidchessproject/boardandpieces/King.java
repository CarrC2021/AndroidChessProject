package edu.up.cs301.androidchessproject.boardandpieces;

import edu.up.cs301.androidchessproject.ChessState;

public class King extends ChessPiece {

    public King(int l, int c,int b) {
        super(l,c,b);
    }

    public static boolean isValidKingMove(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){
        int color = state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite();
        int rowDiff = (rowEnd - rowStart)*(rowEnd - rowStart);
        int colDiff = (colEnd - colStart)*(colEnd - colStart);

        //if the piece moves as expected
        if (color == WHITE) {
            if (rowDiff + colDiff == 1 || rowDiff + colDiff == 2 && !state.getBoard().getSquares()[rowEnd][colEnd].isThreatenedByBlack()) {
                if (Math.abs(color - state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite()) == 1 || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    return true;
                }
            }
        }
        else if (rowDiff + colDiff == 1 || rowDiff + colDiff == 2 && !state.getBoard().getSquares()[rowEnd][colEnd].isThreatenedByWhite()) {
            if (Math.abs(color - state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite()) == 1 || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                return true;
            }
        }
        return false;
    }

}
