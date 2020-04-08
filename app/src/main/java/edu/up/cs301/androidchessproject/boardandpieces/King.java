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

        if(isValidCastle(state, rowStart, colStart, rowEnd, colEnd)) return true;
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

    public static boolean isValidCastle(ChessState state, int rowStart, int colStart, int rowEnd, int colEnd){
        //lets you know if king or queen side castle
        boolean isWhitePiece;

        if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == WHITE){
            isWhitePiece = true;
        } else isWhitePiece = false;

        if (state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved()) return false;

        if (isWhitePiece && colEnd == 6){
            if (state.getBoard().getSquares()[7][7].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[7][7].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[7][7].getPiece().isHasMoved()) return false;
            for (int j = 4; j < 8; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByBlack()){
                    return false;
                }
            }
        }
        //check queenside castle conditions
        if (isWhitePiece && colEnd == 1){
            if (state.getBoard().getSquares()[7][0].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[7][0].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[7][0].getPiece().isHasMoved()) return false;
            for (int j = 0; j < 5; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByWhite()){
                    return false;
                }
            }
        }
        if (!isWhitePiece && colEnd == 6){
            if (state.getBoard().getSquares()[0][7].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[0][7].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[0][7].getPiece().isHasMoved()) return false;
            for (int j = 4; j < 8; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByBlack()){
                    return false;
                }
            }
        }
        //check queenside castle conditions
        if (!isWhitePiece && colEnd == 1){
            if (state.getBoard().getSquares()[0][0].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[0][0].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[0][0].getPiece().isHasMoved()) return false;
            for (int j = 0; j < 5; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByWhite()){
                    return false;
                }
            }
        }
        return true;
    }

}
