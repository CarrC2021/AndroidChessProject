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

            return true;
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

        if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof Pawn){
            isValidPawnMove(pieceEnd, rowStart, colStart, rowEnd, colEnd);
        }
        else if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof Knight){
            isValidKnightMove(rowStart, colStart, rowEnd, colEnd);
        }
        else if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof Rook){
            isValidRookMove(rowStart, colStart, rowEnd, colEnd);
        }
        else if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof Bishop){
            isValidBishopMove(rowStart, colStart, rowEnd, colEnd);
        }
        else if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof Queen){
            isValidQueenMove(rowStart, colStart, rowEnd, colEnd);
        }
        else if (state.getBoard().getSquares()[rowStart][colStart].getPiece() instanceof King){
            isValidKingMove(rowStart, colStart, rowEnd, colEnd);
        }
        return false;
    }

    public boolean isValidKingMove(int rowStart, int colStart, int rowEnd, int colEnd){
        int color = state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite();
        int rowDiff = (rowEnd - rowStart)*(rowEnd - rowStart);
        int colDiff = (colEnd - colStart)*(colEnd - colStart);

        //if the piece moves as expected
        if (color == WHITE) {
            if (rowDiff + colDiff == 1 || rowDiff + colDiff == 2 && !state.getBoard().getSquares()[rowEnd][colEnd].isThreatenedByBlack()) {
                if (Math.abs(color - state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite()) == 1 || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    return true;
                }
            }
        }
        else if (rowDiff + colDiff == 1 || rowDiff + colDiff == 2 && !state.getBoard().getSquares()[rowEnd][colEnd].isThreatenedByWhite()) {
            if (Math.abs(color - state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite()) == 1 || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                return true;
            }
        }
        return false;
    }

    //a method to see if a given move of a pawn was a valid one
    public boolean isValidPawnMove(ChessPiece pieceEnd, int rowStart, int colStart, int rowEnd, int colEnd){
        int color = state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite();
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        try {
            if (color == WHITE) {
                //if they move 2 spaces forward on their first move of that pawn make sure that there is no piece hindering the path
                if (rowDiff == - 2 && !state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved() && areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)) {
                    return state.getBoard().getSquares()[rowStart - 2][colStart].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == - 1 && colEnd == colStart && !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (rowDiff == - 1 && colEnd == colStart - 1 || colEnd == colStart + 1 && state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK)
                        return true;
                } else {
                    return false;
                }
            }
            if (color == BLACK) {
                //if they move 2 spaces forward on their first move of that pawn make sure that there is no piece hindering the path
                if (rowDiff == 2 && !state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved() && !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() && colEnd == colStart) {
                    return state.getBoard().getSquares()[rowStart - 2][colStart].hasPiece();
                }
                //check that the square they are moving into is empty when they move one space forward
                else if (rowDiff == 1 && colEnd == colStart && !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    return true;
                }
                //if the piece moved diagonally then we have to check if that capture was properly done
                else if (rowDiff == 1 && colDiff == -1 || colDiff == 1 && state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == WHITE)
                        return true;
                } else {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }

    public boolean isValidKnightMove(int rowStart, int colStart, int rowEnd, int colEnd){
        int colDiff = (colEnd-colStart)*(colEnd-colStart);
        int rowDiff = (rowEnd-rowStart)*(rowEnd-rowStart);

        try {
            //you must move in an "L" which means if you square the rowDiff and colDiff you should always get 5 when you add those together.
            if (rowDiff + colDiff == 5) {
                //if the white knight moves make sure the square has a black piece or is empty
                if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == WHITE) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == BLACK || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                        return true;
                    }
                }
                //if the black knight moves make sure the square has a white piece or is empty
                else if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == BLACK) {
                    if (state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() == WHITE || !state.getBoard().getSquares()[rowEnd][colEnd].hasPiece()) {
                        return true;
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }

    public boolean isValidQueenMove(int rowStart, int colStart, int rowEnd, int colEnd) {
        int rowDiff = rowEnd -rowStart;
        int colDiff = colEnd - colStart;
        int color = state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite();
        if (rowDiff == 0 && areSquaresOnLineEmpty(false, colStart, colEnd, rowEnd)){
            //if there are no pieces in the way of the suggested move and the last square is empty or is an opponents piece then it is a valid move
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        else if (colDiff == 0 && areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)){
            //if there are no pieces in the way and the last square is empty or is an opponents piece then it is a valid move
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        //if it is not a straight line up, down, left, or right then it must be on a diagonal
        else if (areSquaresOnDiagonalEmpty(rowStart, colStart, rowEnd, colEnd)){
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                return true;
            }
        }
        return false;
    }

    public boolean isValidRookMove(int rowStart, int colStart, int rowEnd, int colEnd){
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        int color = state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite();
        try{
            //are there no pieces in the way of this move along rows
            if (colDiff == 0 && areSquaresOnLineEmpty(true, rowStart, rowEnd, colEnd)){
                if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                    return true;
                }
            }
            //are there no pieces in the way of this move along cols
            else if (rowDiff == 0 && areSquaresOnLineEmpty(false, colStart, colEnd, rowEnd)){
                if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite() - color) == 1){
                    return true;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return false;
    }

    public boolean isValidBishopMove(int rowStart, int colStart, int rowEnd, int colEnd){
        int color = state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite();
        //is the diagonal between the starting and ending square completely empty
        if (areSquaresOnDiagonalEmpty(rowStart, colStart, rowEnd, colEnd)){
            //yes it is now we check if the ending square is occupied by a piece of the opposite color or if it is empty
            if (!state.getBoard().getSquares()[rowEnd][colEnd].hasPiece() || Math.abs(color - state.getBoard().getSquares()[rowEnd][colEnd].getPiece().getBlackOrWhite()) == 1){
                return true;
            }
        }
        return false;
    }

    public boolean areSquaresOnLineEmpty(boolean lineOnRow, int startSquare, int endSquare, int colOrRow){
        int diff = endSquare - startSquare;
        //going up in value on the rows
        if (lineOnRow && diff > 0){
            for (int i = startSquare + 1; i < endSquare; i++){
                if (state.getBoard().getSquares()[i][colOrRow].hasPiece()) return false;
            }
        }
        //going down in value on the rows
        else if (lineOnRow && diff < 0){
            for (int i = startSquare - 1; i > endSquare; i--){
                if (state.getBoard().getSquares()[i][colOrRow].hasPiece()) return false;
            }
        }
        //going to the right
        else if (!lineOnRow && diff > 0){
            for (int i = startSquare + 1; i < endSquare; i++){
                if (state.getBoard().getSquares()[colOrRow][i].hasPiece()) return false;
            }
        }
        //going to the left
        else if (!lineOnRow && diff < 0){
            for (int i = startSquare - 1; i > endSquare; i--){
                if (state.getBoard().getSquares()[colOrRow][i].hasPiece()) return false;
            }
        }
        return true;
    }

    public boolean areSquaresOnDiagonalEmpty(int rowStart, int colStart, int rowEnd, int colEnd){
        int rowDiff = rowEnd - rowStart;
        int colDiff = colEnd - colStart;
        try {
            if (rowDiff != colDiff) {
                return false;
            }
            //down and to the right
            if (colDiff > 0 && rowDiff > 0){
                for (int i = 1; i < colDiff; i++){
                    if (state.getBoard().getSquares()[rowStart+i][colStart+i].hasPiece()) return false;
                }
            }
            //up and to the right
            else if (colDiff > 0 && rowDiff < 0){
                for (int i = 1; i < colDiff; i++){
                    if (state.getBoard().getSquares()[rowStart-i][colStart+i].hasPiece()) return false;
                }
            }
            //down and to the left
            else if (colDiff < 0 && rowDiff > 0){
                for (int i = 1; i < -1*colDiff; i++){
                    if (state.getBoard().getSquares()[rowStart+i][colStart-i].hasPiece()) return false;
                }
            }
            //up and to the left
            else if (colDiff < 0 && rowDiff < 0){
                for (int i = 1; i < -1*colDiff; i++){
                    if (state.getBoard().getSquares()[rowStart-i][colStart-i].hasPiece()) return false;
                }
            }
        }
        //if it goes off the board somehow catch that
        catch (ArrayIndexOutOfBoundsException exception){
            Log.i("array", "array out of bounds error somewhere");
            return false;
        }
        return true;
    }

    public boolean isValidEnPassant(int rowStart, int colStart, int rowEnd, int colEnd){
        return false;
    }
    public boolean isValidCastle(int rowStart, int colStart, int rowEnd, int colEnd){
        //lets you know if king or queen side castle
        boolean isWhitePiece;

        if (state.getBoard().getSquares()[rowStart][colStart].getPiece().getBlackOrWhite() == WHITE){
            isWhitePiece = true;
        } else isWhitePiece = false;

        if (state.getBoard().getSquares()[rowStart][colStart].getPiece().isHasMoved()) return false;

        if (isWhitePiece && colEnd == 6){
            if (state.getBoard().getSquares()[7][7].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[7][7].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[7][7].getPiece().isHasMoved()) return false;
            for (int j = 4; j < 8; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByBlack()){
                    return false;
                }
            }
        }
        if (isWhitePiece && colEnd == 1){
            if (state.getBoard().getSquares()[7][0].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[7][0].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[7][0].getPiece().isHasMoved()) return false;
            for (int j = 0; j < 5; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByWhite()){
                    return false;
                }
            }
        }
        if (!isWhitePiece && colEnd == 6){
            if (state.getBoard().getSquares()[0][7].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[0][7].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[0][7].getPiece().isHasMoved()) return false;
            for (int j = 4; j < 8; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByBlack()){
                    return false;
                }
            }
        }
        if (!isWhitePiece && colEnd == 1){
            if (state.getBoard().getSquares()[0][0].getPiece() == null) return false;
            if (!(state.getBoard().getSquares()[0][0].getPiece() instanceof Rook)) return false;
            if (state.getBoard().getSquares()[0][0].getPiece().isHasMoved()) return false;
            for (int j = 0; j < 5; j++){
                if (state.getBoard().getSquares()[7][j].hasPiece() || state.getBoard().getSquares()[7][j].isThreatenedByWhite()){
                    return false;
                }
            }
        }
        return true;
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


