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
import java.util.Stack;

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

    private boolean whiteKingUnderCheck;
    private boolean blackKingUnderCheck;

    private boolean gameWon = false;

    //There should be some serious thought into making the move list be a stack which you
    //can easily pop from to revert the game state
    Stack<int[]> moveList = new Stack<>();


    /**
     * Constructor for objects of class ChessState
     */
    public ChessState(GameBoard b, int turn){
        board = b;
        playerToMove = turn;
        player1Timer = 0;
        player2Timer = 0;
        whiteKingUnderCheck = false;
        blackKingUnderCheck = false;
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
        whiteKingUnderCheck = false;
        blackKingUnderCheck = false;
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
        board = state.getBoard();
        playerToMove = state.getPlayerToMove();
        player1Timer = state.getPlayer1Timer();
        player2Timer = state.getPlayer2Timer();
        fillPiecesList();
    }

    public void pushToStack(int[] array){
        moveList.push(array);
    }

    public void popFromStack(){
        moveList.pop();
    }

    /**
     * peeks at the stack and updates the state based off of that move
     */
    public void updateState(){
        //0 = row, 1 = column, 2 = row to move to, 3 = column to move to
        int[] move = moveList.peek();

        ChessPiece piece = getBoard().getSquares()[move[0]][move[1]].getPiece();
        getBoard().getSquares()[move[2]][move[3]].setPiece(piece);
        getBoard().getSquares()[move[0]][move[1]].setPiece(null);

        //update all the information stored in the pieces and the state
        piece.setRow(move[2]);
        piece.setCol(move[3]);
        piece.setHasMoved(true);
        updateValidMoves();
        updateSquaresThreatened();
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


    /**
     * returns the character that represents the piece in chess notation
     */
    public String returnPieceAsString(ChessPiece piece){
        if (piece instanceof King){
            return "K";
        }
        else if (piece instanceof Bishop){
            return "B";
        }
        else if (piece instanceof Rook){
            return "R";
        }
        else if (piece instanceof Queen){
            return "Q";
        }
        else if (piece instanceof Knight){
            return "N";
        }
        else {
            return "";
        }
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

    /**
     * updates the valid moves for all pieces in the array lists
     */
    public void updateValidMoves(){
        for (ChessPiece piece : whitePieces){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if(!piece.isCaptured() && ChessLocalGame.isValidMove(this, piece,
                            piece.getRow(), piece.getCol(), i, j)) {
                        piece.setAValidMove(i,j);
                    }
                }
            }
        }
        for (ChessPiece piece : blackPieces){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if(!piece.isCaptured() && ChessLocalGame.isValidMove(this, piece,
                            piece.getRow(), piece.getCol(), i, j)) {
                        piece.setAValidMove(i,j);
                    }
                }
            }
        }
    }


    /**
     * iterates over the board to find all pieces to go in the array lists
     */
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

    /**
     * iterates over the board and updates the squares to be threatened by white or black pieces
     */
    public void updateSquaresThreatened(){
        for (ChessPiece piece : whitePieces){
            if (piece instanceof Pawn){
                for (int i = 0; i < 8; i++){
                    for (int j = 0; j < 8; j++){
                        if(((Pawn) piece).giveSquaresPawnThreatens(i,j)) {
                            getBoard().getSquares()[i][j].setThreatenedByWhite(true);
                        }
                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (piece.getValidMoves()[i][j]) {
                            getBoard().getSquares()[i][j].setThreatenedByWhite(true);
                        }
                    }
                }
            }
        }
        for (ChessPiece piece : blackPieces) {
            if (piece instanceof Pawn) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (((Pawn) piece).giveSquaresPawnThreatens(i, j)) {
                            getBoard().getSquares()[i][j].setThreatenedByWhite(true);
                        }
                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (piece.getValidMoves()[i][j]) {
                            getBoard().getSquares()[i][j].setThreatenedByBlack(true);
                        }
                    }
                }
            }
        }
    }


    public void setBlackKingUnderCheck(boolean blackKingUnderCheck) {
        this.blackKingUnderCheck = blackKingUnderCheck;
    }

    public void setWhiteKingUnderCheck(boolean whiteKingUnderCheck) {
        this.whiteKingUnderCheck = whiteKingUnderCheck;
    }

    public boolean isBlackKingUnderCheck() {
        return blackKingUnderCheck;
    }

    public boolean isWhiteKingUnderCheck() {
        return whiteKingUnderCheck;
    }

    public Stack<int[]> getMoveList() {
        return moveList;
    }

    public void setMoveList(Stack<int[]> moveList) {
        this.moveList = moveList;
    }
}
