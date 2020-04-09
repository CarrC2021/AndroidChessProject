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
import java.util.concurrent.BlockingDeque;


import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
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
    private ArrayList<ChessPiece> whitePieces = new ArrayList<>();
    private ArrayList<ChessPiece> blackPieces = new ArrayList<>();

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
            if(isValidMove(state.getBoard().getSquares()[act.getRowStart()][act.getColStart()].getPiece(), act.getRowStart(),act.getColStart(), act.getRowEnd(), act.getColEnd())){
                ChessPiece piece = state.getBoard().getSquares()[act.getRowStart()][act.getColStart()].getPiece();
                state.getBoard().getSquares()[act.getRowEnd()][act.getColEnd()].setPiece(piece);
                state.getBoard().getSquares()[act.getRowStart()][act.getColStart()].setPiece(null);

                //update all necessary piece information
                //eventually we should write a method to update this piece's valid moves
                piece.setCol(act.getColEnd());
                piece.setRow(act.getRowEnd());
                piece.setHasMoved(true);

                state.nextPlayerMove();
                System.out.println("player to move: "+(state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                Logger.log("update move",
                        "player to move: "+(state.getPlayerToMove() == 0 ? "WHITE" : "BLACK"));
                sendAllUpdatedState();
                //humanPlayer.getSurface().invalidate(); - veg said the game should not access a human player
                return true;
            }
            else{
                return false;
            }
        }
        if (action instanceof ChessResignAction){
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

    public boolean isValidMove(ChessPiece pieceEnd, int rowStart, int colStart, int rowEnd, int colEnd){

        //the piece at the starting square
        ChessPiece piece = state.getBoard().getSquares()[rowStart][colStart].getPiece();

        if (piece instanceof Pawn){
            return Pawn.isValidPawnMove(state, pieceEnd, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Knight){
            return Knight.isValidKnightMove(state, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Rook){
            return Rook.isValidRookMove(state, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Bishop){
            return Bishop.isValidBishopMove(state, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof Queen){
            return Queen.isValidQueenMove(state, rowStart, colStart, rowEnd, colEnd);
        }
        else if (piece instanceof King){
            return King.isValidKingMove(state,rowStart, colStart, rowEnd, colEnd);
        }
        return false;
    }

    public boolean isCheck(){
        if (state.getPlayerToMove() == 0 && state.getBoard().getSquares()[getWhiteKing().getRow()][getWhiteKing().getCol()].isThreatenedByBlack()){
            return isCheckMate();
        }
        if (state.getPlayerToMove() == 1 && state.getBoard().getSquares()[getBlackKing().getRow()][getBlackKing().getCol()].isThreatenedByWhite()){
            return isCheckMate();
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


    // returns positional value [0-63] for squares [a8-h1]
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

    public ArrayList<ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    public void setBlackPieces(ArrayList<ChessPiece> blackP) {
        this.blackPieces = blackP;
    }

    public void setWhitePieces(ArrayList<ChessPiece> whiteP) {
        this.whitePieces = whiteP;
    }

    public ChessPiece getWhiteKing(){
        for (ChessPiece piece : whitePieces){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

    public ChessPiece getBlackKing(){
        for (ChessPiece piece : blackPieces){
            if (piece instanceof King){
                return piece;
            }
        }
        return null;
    }

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


