/**
 * class ChessLocalGame
 *
 * the class that will handle enforcement of all of chess's rules and some
 * other helpful methods. It will also hold the timer objects that will represent
 * the clock for each player.
 *
 * @author Casey Carr
 * @version March 2020
 *
 */


package edu.up.cs301.androidchessproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;


import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;
import edu.up.cs301.game.GameFramework.utilities.GameTimer;
import edu.up.cs301.game.GameFramework.utilities.Logger;

public class ChessLocalGame extends LocalGame {

    private static final int TIME_LIMIT = 36000;

    //Tag for logging
    private static final String TAG = "ChessLocalGame";
    // the game's state
    protected ChessState state;
    //the state from the previous move
    private ChessState prevState;

    private ChessHumanPlayer humanPlayer;
    private ChessComputerPlayerEasy playerEasy;
    private ChessComputerPlayerHard playerHard;

    //the timers for the individual players
    private TimerInfo player1Timer;
    private TimerInfo player2Timer;

    //checkIfGameOver will return this with the appropriate string
    private String gameOver = null;

    //the white and black pieces respectively
    private ArrayList<ChessPiece> capturedPieces = new ArrayList<>();


    public static final int WHITE = 0;
    public static final int BLACK = 1;


    public ChessLocalGame(TimerInfo timer1, TimerInfo timer2) {
        super();
        player1Timer = timer1;
        player2Timer = timer2;
        state = new ChessState();
        this.playerEasy = new ChessComputerPlayerEasy("easy");

        gameOver = null;

        GameTimer timer = this.getTimer();
        timer.setInterval(1000);
        timer.start();
    }

    public ChessLocalGame(ChessState state1, TimerInfo timer1, TimerInfo timer2) {
        super();
        player1Timer = timer1;
        player2Timer = timer2;
        state = state1;
        gameOver = null;
        this.playerEasy = new ChessComputerPlayerEasy("easy");
        GameTimer timer = this.getTimer();
        timer.setInterval(1000);
        timer.start();
    }


    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if (state != null) p.sendInfo(new ChessState(state.getBoard(), state.getPlayerToMove(),
                state.getPlayer1Timer(), state.getPlayer2Timer()));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if (state.getPlayerToMove() == playerIdx) {
            return true;
        } else return false;
    }

    @Override
    protected String checkIfGameOver() {
        if(gameOver != null){
            return gameOver;
        }
        if (state.getPlayer1Timer() > TIME_LIMIT) {
            return "The White Player ran out of time. ";
        }
        else if (state.getPlayer2Timer() > TIME_LIMIT) {
            return "The Black Player ran out of time. ";
        }
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof ChessDrawAction) {
            //state.nextPlayerMove();
            state.setGameWon(true);
            gameOver = "Player " + state.getPlayerToMove() + " offered a draw. ";
            return true;
        }
        if (action instanceof ChessMoveAction) {
            ChessMoveAction act = (ChessMoveAction) action;
            ChessPiece piece = state.getBoard().getSquares()[act.getRowStart()][act.getColStart()].getPiece();

            try {
                int[] array = {piece.getRow(), piece.getCol(), act.getRowEnd(), act.getColEnd()};
                if (isValidMove(state, piece, array)) {
                    //push to the moveList stack
                    state.pushToStack(array);


                    /**
                     * for some bizarre reason here the rook can have its position change even
                     * though you are moving another piece. To recreate move the knight on the right
                     * in front of the pawns, then move a pawn so that your bishop on the right can move
                     * then move the bishop and watch as the rook moves to where the bishop was
                     */
                    //now update the state and see if it is check or checkmate
                    state.updateState();

                    state.updateStringMoveList();

                    if (isCheck()) {
                        Logger.log(state.getPlayerToMove() + "",
                                "this player has put their opponent under check");
                        if (isCheckMate()){
                            gameOver = state.getPlayerToMove() + " won the game by checkmate.";
                        }
                    }

                    //set there to be nothing under check
                    state.nextPlayerMove();

                    System.out.println("player to move: " +
                            (state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                    Logger.log("update move",
                            "player to move: " +
                                    (state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                    sendAllUpdatedState();

                    return true;
                } else {
                    return false;
                }
            } catch (NullPointerException nullPointer) {
                return false;
            }
        }

        if (action instanceof ChessResignAction) {
            //the other player has won the game
            //state.nextPlayerMove();
            state.setGameWon(true);
            if(state.getPlayerToMove() == 0){
                gameOver = "The White Player has resigned. ";
            }
            else {
                gameOver = "The Black Player has resigned. ";
            }
            return true;
        }
        return false;
    }

    public static String getTAG() {
        return TAG;
    }

    public ChessState getState() {
        return state;
    }

    public void setState(ChessState state) {
        this.state = state;
    }

    public static boolean isBasicallyValidMove(ChessState chessState, ChessPiece pieceEnd,
                                               int rowStart, int colStart, int rowEnd, int colEnd){
        try {
            //if there is no piece where they selected return false
            if (!chessState.getBoard().getSquares()[rowStart][colStart].hasPiece()) {
                return false;
            }
            else {
                ChessPiece piece = chessState.getBoard().getSquares()[rowStart][colStart].getPiece();
                //if the piece selected is not the proper color
                if (false && chessState.getPlayerToMove() != piece.getBlackOrWhite()) {
                    return false;
                } else if (piece instanceof Pawn) {
                    return Pawn.isValidPawnMove(chessState, pieceEnd, rowStart, colStart, rowEnd, colEnd);
                } else if (piece instanceof Knight) {
                    return Knight.isValidKnightMove(chessState, rowStart, colStart, rowEnd, colEnd);
                } else if (piece instanceof Rook) {
                    return Rook.isValidRookMove(chessState, rowStart, colStart, rowEnd, colEnd);
                } else if (piece instanceof Bishop) {
                    return Bishop.isValidBishopMove(chessState, rowStart, colStart, rowEnd, colEnd);
                } else if (piece instanceof Queen) {
                    return Queen.isValidQueenMove(chessState, rowStart, colStart, rowEnd, colEnd);
                } else if (piece instanceof King) {
                    return King.isValidKingMove(chessState, rowStart, colStart, rowEnd, colEnd);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.log("Array index out of bounds", "not a valid move, out of bounds");
            return false;
        }
        return false;
    }

    /**
     * wrapper method which calls the basically valid move checker
     */
    public static boolean isValidMove(ChessState chessState, ChessPiece pieceEnd, int[] move) {

        if(isBasicallyValidMove(chessState, pieceEnd, move[0], move[1], move[2], move[3])){

            ChessState temp = new ChessState(chessState);
            temp.pushToStack(move);
            temp.updateState();

            //is the piece putting its own king under threat after the move has gone through
            //if it is return false, if it is not return true
            if (temp.getBoard().getSquares()[move[2]][move[3]].getPiece().getBlackOrWhite() == WHITE){
                return !temp.pieceUnderThreat(temp.getWhiteKing());
            }
            else {
                return !temp.pieceUnderThreat(temp.getBlackKing());
            }
        }
        return false;
    }

    /**
     * method that sees if someone's king is under check
     */
    public boolean isCheck() {
        ChessSquare WhiteKingSquare = state.getBoard().getSquares()
                [getWhiteKing().getRow()][getWhiteKing().getCol()];
        ChessSquare BlackKingSquare = state.getBoard().getSquares()
                [getBlackKing().getRow()][getBlackKing().getCol()];

        //check if the king is under threat by the opposite color
        if (WhiteKingSquare.isThreatenedByBlack()) {
            state.setWhiteKingUnderCheck(true);
            return true;
        }
        if (BlackKingSquare.isThreatenedByWhite()) {
            state.setBlackKingUnderCheck(true);
            return true;
        }
        return false;
    }

    public boolean isCheckMate() {
        //would be very useful to write a method to revert back one move that way we could
        //push a move and then pop to revert.
        if (state.getPlayerToMove() == 0) {
            //loop through all pieces
            for (ChessPiece piece : state.getBlackPieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (isValidMove(state, piece, new int[]{piece.getRow(), piece.getCol(), i,j})){
                            //because isValidMove can look at the move exposing the king
                            //all we need to do is loop through and see if there are any valid moves
                            //if none then we will have checkmate
                            return false;
                        }
                    }
                }
            }
        }
        else {
            //loop through all pieces
            for (ChessPiece piece : state.getWhitePieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (isValidMove(state, piece, new int[]{piece.getRow(), piece.getCol(), i,j})){
                            //because isValidMove can look at the move exposing the king
                            //all we need to do is loop through and see if there are any valid moves
                            //if none then we will have checkmate
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void timerTicked() {
        if(getState().getPlayerToMove() == 0){
            state.setPlayer1Timer(state.getPlayer1Timer()-1);
        }
        else {
            state.setPlayer2Timer(state.getPlayer2Timer()-1);
        }
        String s = checkIfGameOver();
        if (s != null) {
            this.finishUpGame(s);
        }
        sendAllUpdatedState();
    }


    /**
     * returns an integer array if given a square in string representation
     */
    public static int[] fromString(final String s) {
        char c = s.charAt(0);

        int row = Integer.parseInt(s.substring(1));
        int col = (int)c - (int)'a';

        int[] array = new int[2];
        array[0] = row;
        array[1] = col;

        return array;
    }

    public void setPlayer1Timer(TimerInfo player1Timer) {
        this.player1Timer = player1Timer;
    }

    public void setPlayer2Timer(TimerInfo player2Timer) {
        this.player2Timer = player2Timer;
    }

    public void startTimer(int playerToMove){
        if(playerToMove == 0){
            player1Timer.getTimer().start();
        }   else { player2Timer.getTimer().start(); }
    }

    public void stopTimer(int playerToMove){
        if(playerToMove == 0){
            player1Timer.getTimer().stop();
        }   else { player2Timer.getTimer().stop(); }
    }

    public TimerInfo getPlayer2Timer() {
        return player2Timer;
    }

    public TimerInfo getPlayer1Timer() {
        return player1Timer;
    }

    /**
     * returns the whiteKing
     */
    public ChessPiece getWhiteKing(){
        for (ChessPiece piece : state.getWhitePieces()){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    /**
     * returns the blackKing
     */
    public ChessPiece getBlackKing(){
        for (ChessPiece piece : state.getBlackPieces()){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    /**
     * returns true if the move suggested will expose the king, this should result in the move
     * being considered invalid
     */
    public static boolean moveExposesKing(ChessState state1, int[] move){
        ChessState tempState = new ChessState(state1);

        tempState.pushToStack(move);
        tempState.updateValidMoves();
        ChessPiece whiteKing = tempState.getWhiteKing();
        ChessPiece blackKing = tempState.getBlackKing();

        if (tempState.getPlayerToMove() == WHITE){
            if(tempState.getBoard().getSquares()
                    [whiteKing.getRow()][whiteKing.getCol()].isThreatenedByBlack()){
                tempState = null;
                return true;
            }
        }
        else if (tempState.getPlayerToMove() == BLACK){
            if(tempState.getBoard().getSquares()
                    [blackKing.getRow()][blackKing.getCol()].isThreatenedByWhite()){
                tempState = null;
                return true;
            }
        }
        tempState = null;
        return false;
    }


}


