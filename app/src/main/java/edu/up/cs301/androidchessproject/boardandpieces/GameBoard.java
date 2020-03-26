package edu.up.cs301.chess.boardandpieces;

public class GameBoard {

    private ChessSquare[] squares;

    public GameBoard(ChessSquare[] board){
        squares = board;
    }

    public ChessSquare[] getSquares() {
        return squares;
    }

    public void setSquares(ChessSquare[]squares) {
        this.squares = squares;
    }
}
