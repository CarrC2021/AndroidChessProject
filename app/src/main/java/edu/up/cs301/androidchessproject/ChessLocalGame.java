package edu.up.cs301.androidchessproject;

import java.util.ArrayList;

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

public class ChessLocalGame extends LocalGame {
    //Tag for logging
    private static final String TAG = "ChessLocalGame";
    // the game's state
    protected ChessState state;

    //the timers for the individual players
    private TimerInfo player1Timer;
    private TimerInfo player2Timer;

    //the white and black pieces respectively
    private ArrayList<ChessPiece> whitePieces = new ArrayList<>();
    private ArrayList<ChessPiece> blackPieces = new ArrayList<>();

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    private boolean gameWon = false;

    public ChessLocalGame(ChessState state1, TimerInfo timer1, TimerInfo timer2){
        super();
        player1Timer = timer1;
        player2Timer = timer2;
        state = state1;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

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

            return true;
        }
        if (action instanceof ChessResignAction){
            state.nextPlayerMove();
            setGameWon(true);
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

    public String convertMoveToString(int locationStart, int locationEnd){
        String temp = "";

        //make sure this position has a piece before proceeding
        if(state.getBoard().getSquares()[locationStart].hasPiece()) {
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof Pawn) {
                temp = temp + locationToString(locationStart) + locationToString(locationEnd);
            }
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof King) {
                temp = temp + "K" + locationToString(locationStart) + locationToString(locationEnd);
            }
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof Queen) {
                temp = temp + "Q" + locationToString(locationStart) + locationToString(locationEnd);
            }
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof Rook) {
                temp = temp + "R" + locationToString(locationStart) + locationToString(locationEnd);
            }
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof Bishop) {
                temp = temp + "B" + locationToString(locationStart) + locationToString(locationEnd);
            }
            if (state.getBoard().getSquares()[locationStart].getPiece() instanceof Knight) {
                temp = temp + "N" + locationToString(locationStart) + locationToString(locationEnd);
            }
        }
        else return "invalid move";

        return temp;
    }

    public boolean isValidMove(ChessPiece pieceEnd, int locationStart, int locationEnd){
        return false;
    }

    public boolean isValidCastle(int locationStart, int locationEnd){
        //lets you know if king or queen side castle
        int remainder = locationEnd % 8;
        boolean isWhitePiece;

        if(state.getBoard().getSquares()[locationStart].getPiece().getBlackOrWhite() == WHITE){
            isWhitePiece = true;
        } else isWhitePiece = false;

        if(state.getBoard().getSquares()[locationStart].getPiece().isHasMoved()) return false;

        if(remainder == 2){
            if(state.getBoard().getSquares()[locationEnd - 2].getPiece().isHasMoved() || !state.getBoard().getSquares()[locationEnd - 2].hasPiece()) return false;
            for(int i = 0; i < 5; i++){
                //if any square between the king and the rook is threatened you can no longer castle
                if(state.getBoard().getSquares()[locationStart - i].isThreatenedByBlack() && isWhitePiece || state.getBoard().getSquares()[locationStart - i].isThreatenedByWhite() && !isWhitePiece){
                    return false;
                }
            }
        }

        if(remainder == 6){
            if(state.getBoard().getSquares()[locationEnd + 1].getPiece().isHasMoved() || !state.getBoard().getSquares()[locationEnd + 1].hasPiece()) return false;
            for(int i = 0; i < 4; i++){
                //if any square between the king and the rook is threatened you can no longer castle
                if(state.getBoard().getSquares()[locationStart + i].isThreatenedByBlack() && isWhitePiece || state.getBoard().getSquares()[locationStart + i].isThreatenedByWhite() && !isWhitePiece){
                    return false;
                }
            }
        }

        return true;
    }


    public boolean isCheck(){
        if (state.getPlayerToMove() == 0 && state.getBoard().getSquares()[getWhiteKing().getLocation()].isThreatenedByBlack()){
            return isCheckMate();
        }
        if (state.getPlayerToMove() == 1 && state.getBoard().getSquares()[getBlackKing().getLocation()].isThreatenedByWhite()){
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

    //this method is to convert our 0-63 location data into the proper algebraic notation. For example, 0 = a8, 17 = b6.
    public static String locationToString(final int location){
        String temp = "";

        int remainder = location % 8;
        int rowPosition = -(location/8 - 8);

        if(remainder == 0){
            temp = "a" + rowPosition;
        }
        if(remainder == 1){
            temp = "b" + rowPosition;
        }
        if(remainder == 2){
            temp = "c" + rowPosition;
        }
        if(remainder == 3){
            temp = "d" + rowPosition;
        }
        if(remainder == 4){
            temp = "e" + rowPosition;
        }
        if(remainder == 5){
            temp = "f" + rowPosition;
        }
        if(remainder == 6){
            temp = "g" + rowPosition;
        }
        if(remainder == 7){
            temp = "h" + rowPosition;
        }

        return temp;
    }

    // returns positional value [0-63] for squares [a8-h1]
    public static int fromString(final String s) {
        char c = s.charAt(0);

        int col, row = Integer.parseInt(s.substring(1));
        col = (int)c - (int)'a';

        return ((8-row) * 8) + col;
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

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

}


