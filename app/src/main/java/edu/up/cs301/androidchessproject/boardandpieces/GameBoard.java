package edu.up.cs301.androidchessproject.boardandpieces;

import edu.up.cs301.game.GameFramework.Game;

public class GameBoard {

    private ChessSquare[][] squares = new ChessSquare[8][8];

    public GameBoard(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (i == 0 && j == 0 || i == 0 && j == 7)
                    squares[i][j] = new ChessSquare(new Rook(i, j,1));
                if (i == 7 && j == 0 || i == 7 && j == 7)
                    squares[i][j] = new ChessSquare(new Rook(i,j,0));
                if (i == 0 && j == 1 || i == 0 && j == 6)
                    squares[i][j] = new ChessSquare(new Knight(i,j,1));
                if (i == 7 && j == 1 || i == 7 && j == 6)
                    squares[i][j] = new ChessSquare(new Knight(i,j,0));
                if (i == 0 && j == 2 || i == 0 && j == 5)
                    squares[i][j] = new ChessSquare(new Bishop(i,j,1));
                if (i == 7 && j == 2 || i == 7 && j == 5)
                    squares[i][j] = new ChessSquare(new Bishop(i,j,0));
                if (i == 0 && j == 3)
                    squares[i][j] = new ChessSquare(new Queen(i,j,1));
                if (i == 7 && j == 3)
                    squares[i][j] = new ChessSquare(new Queen(i,j,0));
                if (i == 0 && j == 4)
                    squares[i][j] = new ChessSquare(new King(i,j,1));
                if (i == 7 && j == 4)
                    squares[i][j] = new ChessSquare(new King(i,j,0));
                if (i == 1)
                    squares[i][j] = new ChessSquare(new Pawn(i,j,1));
                if (i == 6)
                    squares[i][j] = new ChessSquare(new Pawn(i,j,0));

                squares[i][j] = new ChessSquare();
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

}