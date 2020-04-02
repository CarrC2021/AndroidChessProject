package edu.up.cs301.androidchessproject.boardandpieces;

import org.junit.Test;

import edu.up.cs301.game.GameFramework.Game;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void getSquares() {
        GameBoard board = new GameBoard();

        assertEquals(true, board.getSquares()[1][1].hasPiece());

    }

    @Test
    public void setSquares() {
        GameBoard board = new GameBoard();

    }
}