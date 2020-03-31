package edu.up.cs301.androidchessproject;

import java.util.ArrayList;

import edu.up.cs301.androidchessproject.boardandpieces.GameBoard;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;

public class ChessState extends GameState {

    //Tag for logging
    private static final String TAG = "ChessState";
    private static final long serialVersionUID = 7552321013488624386L;

    // the 8x8 array for all ChessPieces
    private GameBoard board;

    private int player1Timer;
    private int player2Timer;

    // an int that tells whose move it is
    private int playerToMove;

    //info to keep track of captured pieces
    private ArrayList<ChessPiece> whiteCaptured = new ArrayList<>();
    private ArrayList<ChessPiece> blackCaptured = new ArrayList<>();

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn){
        board = b;
        playerToMove = turn;
        player1Timer = 0;
        player2Timer = 0;
    }

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn, int p1, int p2){
        board = b;
        playerToMove = turn;
        player1Timer = p1;
        player2Timer = p2;
    }

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(){
        board = new GameBoard();
        playerToMove = 0;
        player1Timer = 0;
        player2Timer = 0;
    }

    public GameBoard getBoard() {
        return board;
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    public ArrayList<ChessPiece> getBlackCaptured() {
        return blackCaptured;
    }

    public ArrayList<ChessPiece> getWhiteCaptured() {
        return whiteCaptured;
    }

    public int getPlayer1Timer() {
        return player1Timer;
    }

    public int getPlayer2Timer() {
        return player2Timer;
    }

    public void setBlackCaptured(ArrayList<ChessPiece> blackCaptured) {
        this.blackCaptured = blackCaptured;
    }

    public void setWhiteCaptured(ArrayList<ChessPiece> whiteCaptured) {
        this.whiteCaptured = whiteCaptured;
    }

    public void setPlayer1Timer(int player1Timer) {
        this.player1Timer = player1Timer;
    }

    public void setPlayer2Timer(int player2Timer) {
        this.player2Timer = player2Timer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static String getTAG() {
        return TAG;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public void setPlayerToMove(int playerToMove) {
        this.playerToMove = playerToMove;
    }

    @Override
    public void setGame(Game g) {
        super.setGame(g);
    }
}
