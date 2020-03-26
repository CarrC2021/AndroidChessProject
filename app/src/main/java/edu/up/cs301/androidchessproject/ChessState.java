package edu.up.cs301.chess;

import java.util.ArrayList;

import edu.up.cs301.chess.boardandpieces.ChessPiece;
import edu.up.cs301.chess.boardandpieces.GameBoard;
import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;

public class ChessState extends GameState {

    //Tag for logging
    private static final String TAG = "ChessState";
    private static final long serialVersionUID = 7552321013488624386L;

    // the 8x8 array for all ChessPieces
    private GameBoard board;

    //the timers for the individual players
    private TimerInfo player1Timer;
    private TimerInfo player2Timer;

    // an int that tells whose move it is
    private int playerToMove;

    //info to keep track of captured pieces
    private ArrayList<ChessPiece> whiteCaptured = new ArrayList<>();
    private ArrayList<ChessPiece> blackCaptured = new ArrayList<>();

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn, TimerInfo player1, TimerInfo player2){
        board = b;
        playerToMove = turn;
        player1Timer = player1;
        player2Timer = player2;
    }

    public GameBoard getBoard() {
        return board;
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public TimerInfo getPlayer1Timer() {
        return player1Timer;
    }

    public static String getTAG() {
        return TAG;
    }

    public TimerInfo getPlayer2Timer() {
        return player2Timer;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public void setPlayer1Timer(TimerInfo player1Timer) {
        this.player1Timer = player1Timer;
    }

    public void setPlayer2Timer(TimerInfo player2Timer) {
        this.player2Timer = player2Timer;
    }

    public void setPlayerToMove(int playerToMove) {
        this.playerToMove = playerToMove;
    }

    @Override
    public void setGame(Game g) {
        super.setGame(g);
    }
}
