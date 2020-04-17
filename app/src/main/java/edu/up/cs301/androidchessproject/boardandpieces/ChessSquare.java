package edu.up.cs301.androidchessproject.boardandpieces;

/**
 * This class will be used to hold a ChessPiece or not and will represent a single square of the board
 *
 * @author Casey Carr
 * @author
 * @version March 2020
 */
public class ChessSquare {
    //each square will have the capability of holding one chess piece or no chess piece
    private ChessPiece piece;

    //If squares are threatened is really useful to know for an isCheckmate() function or isCheck().
    // Also, for determining a valid castle or a valid move in general.
    private boolean isThreatenedByBlack;
    private boolean isThreatenedByWhite;

    public ChessSquare(ChessPiece p){
        piece = p;
        isThreatenedByBlack = false;
        isThreatenedByWhite = false;
    }

    public ChessSquare() {
        piece = null;
        isThreatenedByBlack = false;
        isThreatenedByWhite = false;
    }


    public ChessSquare(ChessSquare square){
        piece = new ChessPiece(square.getPiece());
        isThreatenedByBlack = square.isThreatenedByBlack();
        isThreatenedByWhite = square.isThreatenedByWhite();
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public boolean isThreatenedByBlack() {
        return isThreatenedByBlack;
    }

    public void setThreatenedByBlack(boolean threatened) {
        isThreatenedByBlack = threatened;
    }

    public boolean isThreatenedByWhite() {
        return isThreatenedByWhite;
    }

    public void setThreatenedByWhite(boolean threatenedByWhite) {
        isThreatenedByWhite = threatenedByWhite;
    }

    /**
     * returns true if the square has a piece, returns false if the square has a null object
     * for a piece
     */
    public boolean hasPiece(){
        if(piece instanceof ChessPiece) {
            return true;
        } else{
            return false;
        }
    }
}
