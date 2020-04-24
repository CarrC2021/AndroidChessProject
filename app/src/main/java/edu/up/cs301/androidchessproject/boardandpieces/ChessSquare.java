package edu.up.cs301.androidchessproject.boardandpieces;

import android.graphics.Paint;

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
        if (square.hasPiece()) {
            if (square.getPiece() instanceof Pawn) {
                piece = new Pawn((Pawn) square.getPiece());
            }
            else if (square.getPiece()  instanceof Rook){
                piece = new Rook((Rook) square.getPiece());
            }
            else if (square.getPiece() instanceof Bishop){
                piece = new Bishop((Bishop) square.getPiece());
            }
            else if (square.getPiece() instanceof Queen){
                piece = new Queen((Queen) square.getPiece());
            }
            else if (square.getPiece() instanceof King){
                piece = new King((King) square.getPiece());
            }
            else if (square.getPiece() instanceof Knight){
                piece = new Knight((Knight) square.getPiece());
            }
        } else {
            piece = null;
        }
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
