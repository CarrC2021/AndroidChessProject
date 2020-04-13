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


import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;

public class ChessLocalGame extends LocalGame {

    //Tag for logging
    private static final String TAG = "ChessLocalGame";
    // the game's state
    protected ChessState state;

    private ChessHumanPlayer humanPlayer;
    private ChessComputerPlayerEasy playerEasy;
    private ChessComputerPlayerHard playerHard;

    //the timers for the individual players
    private TimerInfo player1Timer;
    private TimerInfo player2Timer;

    //the white and black pieces respectively
    private ArrayList<ChessPiece> capturedPieces = new ArrayList<>();

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public ChessLocalGame(ChessState state1, TimerInfo timer1, TimerInfo timer2){
        super();
        player1Timer = timer1;
        player2Timer = timer2;
        state = state1;
        this.playerEasy = new ChessComputerPlayerEasy("easy");
    }



    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if (state != null) p.sendInfo(new ChessState(state));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if(state.getPlayerToMove() == playerIdx) {
            return true;
        } else return false;
    }

    @Override
    protected String checkIfGameOver() {

        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof ChessDrawAction){

            return true;
        }
        if(action instanceof ChessMoveAction){
            ChessMoveAction act = (ChessMoveAction)action;
            ChessPiece piece = state.getBoard().getSquares()[act.getRowStart()][act.getColStart()].getPiece();

            //need a deep copy here
            ChessState currState = new ChessState(state);

            if(isValidMove(state, piece, piece.getRow(),
                    piece.getCol(), act.getRowEnd(), act.getColEnd())){
                int[] array = {piece.getRow(), piece.getCol(), act.getRowEnd(), act.getColEnd()};

                //push to the moveList stack
                state.pushToStack(array);

                //now update the state and see if it is check or checkmate
                state.updateState();

                //if the current state is check, and the player suggested a move that does not
                //get the king out of check then they need to make another move because this was
                //an illegal move. This would be much easier to write if there was a way to pop from
                //the stack and revert
                if (currState.isBlackKingUnderCheck() || currState.isWhiteKingUnderCheck()){
                    if (isCheck()){
                        //send illegal move info back to the player who sent this because they
                        //are still under check and need to make a new move.
                        //Dont know if this works

                        //need to write a method to check if this was checkmate
                        players[state.getPlayerToMove()].sendInfo(new IllegalMoveInfo());
                    }
                }
                if (isCheck()){
                    Logger.log(state.getPlayerToMove() + "",
                            "this player has put their opponent under check");
                }

                //set there to be nothing under check
                state.nextPlayerMove();
                System.out.println("player to move: "+
                        (state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                Logger.log("update move",
                        "player to move: "+
                                (state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                sendAllUpdatedState();
                return true;
            }
            else{
                return false;
            }
        }
        if (action instanceof ChessResignAction){
            //the other player has won the game
            state.nextPlayerMove();
            state.setGameWon(true);
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

    /**
     * wrapper method which calls the specific piece valid move checker
     */
    public static boolean isValidMove(ChessState chessState, ChessPiece pieceEnd,
                                      int rowStart, int colStart, int rowEnd, int colEnd){

        //the piece at the starting square
        ChessPiece piece = chessState.getBoard().getSquares()[rowStart][colStart].getPiece();

        if (piece instanceof Pawn){
            return Pawn.isValidPawnMove(chessState, pieceEnd, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Knight){
            return Knight.isValidKnightMove(chessState, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Rook){
            return Rook.isValidRookMove(chessState, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Bishop){
            return Bishop.isValidBishopMove(chessState, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Queen){
            return Queen.isValidQueenMove(chessState, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof King){
            return King.isValidKingMove(chessState, rowStart, colStart, rowEnd, colEnd);
        }
        return false;
    }

    /**
     * method that sees if someone's king is under check
     */
    public boolean isCheck(){
        ChessSquare WhiteKingSquare = state.getBoard().getSquares()
                [getWhiteKing().getRow()][getWhiteKing().getCol()];
        ChessSquare BlackKingSquare = state.getBoard().getSquares()
                [getBlackKing().getRow()][getBlackKing().getCol()];

        //check if the king is under threat by the opposite color
        if (WhiteKingSquare.isThreatenedByBlack()){
            state.setWhiteKingUnderCheck(true);
            return true;
        }
        if (BlackKingSquare.isThreatenedByWhite()){
            state.setBlackKingUnderCheck(true);
            return true;
        }
        return false;
    }

    public boolean isCheckMate(){
        return false;
    }

    @Override
    public void timerTicked() {
        if(getState().getPlayerToMove() == 0){

        }
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

    public ChessPiece getWhiteKing(){
        for (ChessPiece piece : state.getWhitePieces()){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    public ChessPiece getBlackKing(){
        for (ChessPiece piece : state.getBlackPieces()){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    /**
     * returns a String that represents the square using chess notation
     */
    public String squareToString(int row, int col){
        String temp;
        if (state.getBoard().getSquares()[row][col].getPiece() instanceof Pawn) {
            char r = (char) (97 + row);
            temp = r + "" + col;
        }
        else{
            temp = state.returnPieceAsChar(state.getBoard().getSquares()[row][col]) + "";
            char r = (char) (97 + row);
            temp = temp + r + col;
        }
        return temp;
    }

}


