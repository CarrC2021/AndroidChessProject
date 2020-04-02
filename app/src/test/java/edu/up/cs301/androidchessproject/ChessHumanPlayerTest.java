package edu.up.cs301.androidchessproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChessHumanPlayerTest {

    @Test
    public void getTopView() {
    }

    @Test
    public void receiveInfo() {
    }

    @Test
    public void onTouch() {
    }

    @Test
    public void setAsGui() {
    }

    @Test
    public void convertCoordinateToSquare(){
        ChessHumanPlayer player = new ChessHumanPlayer("human");
        String hey = player.convertCoordinateToSquare(21, 220);

        assertEquals("a2", hey);

        hey = player.convertCoordinateToSquare(221, 441);
        assertEquals("c4", hey);
    }
}