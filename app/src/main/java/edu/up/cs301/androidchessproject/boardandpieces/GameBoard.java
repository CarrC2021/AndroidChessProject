/**
 *
 * GameBoard is the class which will represent the collection off all data about the chessboard, it will
 * hold an 8x8 array of ChessSquare objects. It will be capable of making both the "default"
 * chessboard aka the initial gamestate. It will also be able to create a specific GameBoard when a
 * ChessSquare[][] object is passed to it.
 *
 * @author Casey Carr, Vegdahl
 * @version April 2020
 */

package edu.up.cs301.androidchessproject.boardandpieces;

import android.util.Log;

import edu.up.cs301.game.GameFramework.Game;

public class GameBoard {

    private ChessSquare[][] squares = new ChessSquare[8][8];

    public GameBoard() {
        // create the array
        squares = new ChessSquare[8][8];

        // initialize each square
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // color is black if row < 4, otherwise white
                int color = i < 4 ? 1 : 0;
                // create the square; put an appropriate piece in it, if needed
                switch (i) {
                    // first or last rank: put piece in based on column
                    case 0:
                    case 7:
                        squares[i][j] = new ChessSquare(createPiece(j, i, j, color));
                        break;
                    // second or second-to-last rank: create pawn
                    case 1:
                    case 6:
                        squares[i][j] = new ChessSquare(new Pawn(i, j, color));
                        break;
                    // one of the four middle ranks: create without a piece
                    default:
                        squares[i][j] = new ChessSquare();
                }
            }
        }
    }

    public GameBoard(ChessSquare[][] board) {
        squares = board;
    }

    public ChessSquare[][] getSquares() {
        return squares;
    }

    public void setSquares(ChessSquare[][] squares) {
        this.squares = squares;
    }

    // creates a chess piece of the type specified according to what
    // piece would be there at the beginning of the game in column 'initColumnType'
    private ChessPiece createPiece(int initColumnType, int rank, int file, int color) {
        switch (initColumnType) {
            // first or last column: create rook
            case 0: case 7:
                return new Rook(rank, file, color);
            // one in from first or last column: create knight
            case 1: case 6:
                return new Knight(rank, file, color);
            // two in from first or last column: create bishop
            case 2: case 5:
                return new Bishop(rank, file, color);
            // fourth file: create queen
            case 3:
                return new Queen(rank, file, color);
            // fifth file: create king
            case 4:
                return new King(rank, file, color);
        }
        return null;
    }

    public boolean areSquaresOnLineEmpty(boolean lineOnRow, int startSquare, int endSquare, int colOrRow){
        int diff = endSquare - startSquare;
        //going up in value on the rows
        if (lineOnRow && diff > 0){
            for (int i = startSquare + 1; i < endSquare; i++){
                if (getSquares()[i][colOrRow].hasPiece()) return false;
            }
        }
        //going down in value on the rows
        else if (lineOnRow && diff < 0){
            for (int i = startSquare - 1; i > endSquare; i--){
                if (getSquares()[i][colOrRow].hasPiece()) return false;
            }
        }
        //going to the right
        else if (!lineOnRow && diff > 0){
            for (int i = startSquare + 1; i < endSquare; i++){
                if (getSquares()[colOrRow][i].hasPiece()) return false;
            }
        }
        //going to the left
        else if (!lineOnRow && diff < 0){
            for (int i = startSquare - 1; i > endSquare; i--){
                if (getSquares()[colOrRow][i].hasPiece()) return false;
            }
        }
        return true;
    }

    public boolean areSquaresOnDiagonalEmpty(int rowStart, int colStart, int rowEnd, int colEnd){
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        try {
            if (rowDiff != colDiff) {
                return false;
            }
            //down and to the right
            if (colDiff > 0 && rowDiff > 0){
                for (int i = 1; i < colDiff; i++){
                    if (getSquares()[rowStart+i][colStart+i].hasPiece()) return false;
                }
            }
            //up and to the right
            else if (colDiff > 0 && rowDiff < 0){
                for (int i = 1; i < colDiff; i++){
                    if (getSquares()[rowStart-i][colStart+i].hasPiece()) return false;
                }
            }
            //down and to the left
            else if (colDiff < 0 && rowDiff > 0){
                for (int i = 1; i < -1*colDiff; i++){
                    if (getSquares()[rowStart+i][colStart-i].hasPiece()) return false;
                }
            }
            //up and to the left
            else if (colDiff < 0 && rowDiff < 0){
                for (int i = 1; i < -1*colDiff; i++){
                    if (getSquares()[rowStart-i][colStart-i].hasPiece()) return false;
                }
            }
        }
        //if it goes off the board somehow catch that
        catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return true;
    }
}