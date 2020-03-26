package edu.up.cs301.chess.boardandpieces;


/**
 * This class will be used to hold all necessary information about a piece. We can keep an array of all valid moves in the piece to use both for the gui and when we check
 * that the move suggested by the human or computer was valid.
 *
 * @author Casey Carr
 * @author
 * @version March 2020
 */
public class ChessPiece {

    //we might want to have the validMoves list stored in the piece or the square I am not sure yet
   private boolean[][] validMoves;

    //might be useful to have a boolean for whether or not a piece has been captured
    private boolean isCaptured;

    //will definitely be useful to know that a piece has been moved at least once
    private boolean hasMoved;

    //location of the square represented as an integer we will make constants that define each square's name as an integer
    //for example one constant would be
    // public static final int a8 = 0
    // a8 is the top left square of the board, where one of the black rooks starts.
    private int location;

    //is this piece blackOrWhite
    private int blackOrWhite;

    public ChessPiece(int l, int b){

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                validMoves[i][j] = false;
            }
        }

        blackOrWhite = b;
        location = l;
        isCaptured = false;
        hasMoved = false;
    }

    public boolean[][] getValidMoves() {
        return validMoves;
    }

    public int getBlackOrWhite() {
        return blackOrWhite;
    }

    public int getLocation() {
        return location;
    }

    public void setBlackOrWhite(int blackOrWhite) {
        this.blackOrWhite = blackOrWhite;
    }

    public void setLocation(int location) {
        this.location = location;
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
}