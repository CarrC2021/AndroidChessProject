package edu.up.cs301.androidchessproject;

import org.junit.Test;

import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.GameBoard;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;
import edu.up.cs301.game.GameFramework.utilities.GameTimer;
import edu.up.cs301.game.GameFramework.utilities.Tickable;

import static org.junit.Assert.*;

public class ChessStateTest {

    @Test
    public void getBoard() {
        //it says here that is requires a tickable object but this is an interface I guess I just do not understand this class structure.
        //the ChessState constructor requires two TimerInfo objects so before I can test any of the methods this needs to be done.
        TimerInfo timer1 = new TimerInfo(new GameTimer(new Tickable() {
            @Override
            public void tick(GameTimer timer) {

            }
        }));
        TimerInfo timer2 = new TimerInfo(new GameTimer(new Tickable() {
            @Override
            public void tick(GameTimer timer) {
                timer.start();
            }
        }));
        ChessState state = new ChessState();

        GameBoard board = new GameBoard();
        board.getSquares()[2][1].setPiece(new Pawn(2,1, 1));
        state.setBoard(board);

        assertEquals(2, state.getBoard().getSquares()[2][1].getPiece().getRow());
    }

    @Test
    public void getPlayerToMove() {
    }

    @Test
    public void getSerialVersionUID() {
    }

    @Test
    public void getPlayer1Timer() {
    }

    @Test
    public void getTAG() {
    }

    @Test
    public void getPlayer2Timer() {
    }

    @Test
    public void setBoard() {
        //it says here that is requires a tickable object but this is an interface I guess I just do not understand this class structure.
        //the ChessState constructor requires two TimerInfo objects so before I can test any of the methods this needs to be done.
        ChessState state = new ChessState();

        GameBoard board = new GameBoard();
        board.getSquares()[2][1].setPiece(new Pawn(2,1, 1));
        state.setBoard(board);

        assertEquals(2, state.getBoard().getSquares()[2][1].getPiece().getRow());
    }

    @Test
    public void setPlayer1Timer() {

    }

    @Test
    public void setPlayer2Timer() {

    }

    @Test
    public void setPlayerToMove() {
        ChessState state = new ChessState();
        state.setPlayerToMove(1);
        assertEquals(1, state.getPlayerToMove());
    }

    @Test
    public void setGame() {

    }
}