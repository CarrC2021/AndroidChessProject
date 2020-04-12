package edu.up.cs301.androidchessproject.boardandpieces;


import edu.up.cs301.game.GameFramework.utilities.Logger;

/**
 * This class will be used to hold all necessary information about a piece. We can keep an array of all valid moves in the piece to use both for the gui and when we check
 * that the move suggested by the human or computer was valid.
 *
 * @author Casey Carr
 * @author
 * @version March 2020
 */
public class ChessPiece {

    public static int WHITE = 0;
    public static int BLACK = 1;

    //we might want to have the validMoves list stored in the piece or the square I am not sure yet
    private boolean[][] validMoves;

    //might be useful to have a boolean for whether or not a piece has been captured
    private boolean isCaptured;

    //will definitely be useful to know that a piece has been moved at least once
    private boolean hasMoved;

    //location of the square represented as row and column integer we will make constants that define each square's name as an integer
    private int row;
    private int col;

    //is this piece blackOrWhite
    private int blackOrWhite;

    public ChessPiece(int r, int c, int b){

        validMoves = new boolean[8][8];
        //this will be the entire list of moves a boolean array seems to be a logical choice to represent valid moves.
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                validMoves[i][j] = false;
            }
        }

        blackOrWhite = b;
        row = r;
        col = c;
        isCaptured = false;
        hasMoved = false;
    }

    public ChessPiece(ChessPiece piece){
        new ChessPiece(piece.row, piece.col, piece.getBlackOrWhite());
    }

    public boolean[][] getValidMoves() {
        return validMoves;
    }

    public int getBlackOrWhite() throws NullPointerException{
        if (this == null) {
            Logger.log("Null Pointer on Piece", "could not get black or white for a null object.");
            throw new NullPointerException();
        }
        else return blackOrWhite;
    }

    public int getRow() {
        return row;
    }

    public void setBlackOrWhite(int blackOrWhite) {
        this.blackOrWhite = blackOrWhite;
    }

    public void setRow(int location) {
        this.row = location;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setValidMoves(boolean[][] validMoves) {
        this.validMoves = validMoves;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setAValidMove(int r, int column){
        validMoves[r][column] = true;
    }
}
