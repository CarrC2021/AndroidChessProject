package edu.up.cs301.androidchessproject.boardandpieces;

import org.junit.Test;

import edu.up.cs301.game.GameFramework.Game;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void getSquares() {
        GameBoard board = new GameBoard();

        assertEquals(true, board.getSquares()[1].hasPiece());
        assertEquals(true, board.getSquares()[4].hasPiece());
        assertEquals(true, board.getSquares()[62].hasPiece());
        assertEquals(new Pawn(8,1).getLocation(), board.getSquares()[8].getPiece().getLocation());
    }

    @Test
    public void setSquares() {
        GameBoard board = new GameBoard();

        board.getSquares()[37].setPiece(new Queen(37, 1));
        assertEquals(1, board.getSquares()[37].getPiece().getBlackOrWhite());

        board.getSquares()[37].setPiece(new Queen(27, 0));
        assertEquals(0, board.getSquares()[37].getPiece().getBlackOrWhite());

        board.getSquares()[37].setPiece(new Queen(37, 1));
        assertEquals(37,board.getSquares()[37].getPiece().getLocation());
    }
}