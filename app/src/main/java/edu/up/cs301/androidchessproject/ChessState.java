/**
 *
 * ChessState is a class which will hold all information necessary to create a
 * "snapshot" of the game. For chess this is the board and piece locations along with
 * the timers for the respective players.
 *
 * @author Casey Carr, Vegdahl
 * @version April 2020
 */

package edu.up.cs301.androidchessproject;

import java.util.ArrayList;

import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessSquare;
import edu.up.cs301.androidchessproject.boardandpieces.GameBoard;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;

public class ChessState extends GameState {

    //Tag for logging
    private static final String TAG = "ChessState";
    private static final long serialVersionUID = 7552321013488624386L;

    // the 8x8 array for all ChessPieces
    private GameBoard board;

    private int player1Timer;
    private int player2Timer;

    private ArrayList<ChessPiece> whitePieces = new ArrayList<>();
    private ArrayList<ChessPiece> blackPieces = new ArrayList<>();

    // an int that tells whose move it is
    private int playerToMove;

    private boolean gameWon = false;


    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn){
        board = b;
        playerToMove = turn;
        player1Timer = 0;
        player2Timer = 0;
        fillPiecesList();
    }

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn, int p1, int p2){
        board = b;
        playerToMove = turn;
        player1Timer = p1;
        player2Timer = p2;
        fillPiecesList();
    }

    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(){
        board = new GameBoard();
        playerToMove = 0;
        player1Timer = 0;
        player2Timer = 0;
        fillPiecesList();
    }

    public ChessState(ChessState state){
        board = state.board;
        playerToMove = state.playerToMove;
        player1Timer = state.player1Timer;
        player2Timer = state.player2Timer;
        gameWon = state.gameWon;
        fillPiecesList();
    }

    public GameBoard getBoard() {
        return board;
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    public int getPlayer1Timer() {
        return player1Timer;
    }

    public int getPlayer2Timer() {
        return player2Timer;
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

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
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

    public void nextPlayerMove(){
        setPlayerToMove(1 - getPlayerToMove());
    }

    @Override
    public void setGame(Game g) {
        super.setGame(g);
    }

    public String returnPieceAsChar(ChessSquare square){
        if (square.getPiece() instanceof King){
            return "K";
        }
        else if (square.getPiece() instanceof Bishop){
            return "B";
        }
        else if (square.getPiece() instanceof Rook){
            return "R";
        }
        else if (square.getPiece() instanceof Queen){
            return "Q";
        }
        else if (square.getPiece() instanceof Knight){
            return "N";
        }
        else {
            return "";
        }
    }

    public String squareToString(int row, int col){
        String temp;
        if (getBoard().getSquares()[row][col].getPiece() instanceof Pawn) {
            char r = (char) (97 + row);
            temp = r + "" + col;
        }
        else{
            temp = returnPieceAsChar(getBoard().getSquares()[row][col]) + "";
            char r = (char) (97 + row);
            temp = temp + r + col;
        }
        return temp;
    }

    public ArrayList<ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<ChessPiece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public void setBlackPieces(ArrayList<ChessPiece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public void updateValidMoves(){
        for (ChessPiece piece : whitePieces){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if(ChessLocalGame.isValidMove(this, piece, piece.getRow(), piece.getCol(), i, j)) {
                        piece.setAValidMove(i,j);
                    }
                }
            }
        }
        for (ChessPiece piece : blackPieces){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if(ChessLocalGame.isValidMove(this, piece, piece.getRow(), piece.getCol(), i, j)) {
                        piece.setAValidMove(i,j);
                    }
                }
            }
        }
    }

    public void fillPiecesList(){
        whitePieces.clear();
        blackPieces.clear();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (getBoard().getSquares()[i][j].hasPiece()){
                    if (getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0){
                        whitePieces.add(getBoard().getSquares()[i][j].getPiece());
                    }
                    if (getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1){
                        blackPieces.add(getBoard().getSquares()[i][j].getPiece());
                    }
                }
            }
        }
    }
}
