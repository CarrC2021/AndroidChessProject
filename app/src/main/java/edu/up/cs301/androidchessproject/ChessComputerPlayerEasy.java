/**
 * class ChessComputerPlayerEasy
 *
 * A class which will be capable of randomly making chess moves.
 *
 *
 * @author Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.androidchessproject.boardandpieces.Bishop;
import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.androidchessproject.boardandpieces.King;
import edu.up.cs301.androidchessproject.boardandpieces.Knight;
import edu.up.cs301.androidchessproject.boardandpieces.Pawn;
import edu.up.cs301.androidchessproject.boardandpieces.Queen;
import edu.up.cs301.androidchessproject.boardandpieces.Rook;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;

public class ChessComputerPlayerEasy extends GameComputerPlayer {
    private static int MIN = 0;
    private static int MAX = 7;

    public ArrayList<ChessPiece> BlackPieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> WhitePieces = new ArrayList<ChessPiece>();

    private ChessState state;

    private Random ran = new Random();


    /*
     * Constructor for the ChessComputerPlayerEasy class
     */
    public ChessComputerPlayerEasy(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
        state = new ChessState();
//        playerNum = 1;
    }


    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        sleep(2);
//need to add an overall loop over all of this.
        if (info instanceof NotYourTurnInfo){
            return;

        }

        else if (info instanceof IllegalMoveInfo || info instanceof ChessState){
            if (info instanceof ChessState) {
                setState((ChessState)info);
            }
            if (state.getPlayerToMove() != playerNum) return;

            fillPiecesList();

            Logger.log("CPE illegal",
                    "computer player notified of illegal move");
            ChessPiece randPiece;
            //take a random piece
            if (this.playerNum == 1) {
                if (BlackPieces.size() <= 0) {
                    Logger.log("no black", "no black pieces");
                    return;
                }
                Logger.log("blackSize", "#black pieces: "+BlackPieces.size());
                randPiece = BlackPieces.get(randomIntWithinBounds(0, BlackPieces.size()-1));
            }
            else {
                if (WhitePieces.size() <= 0) {
                    Logger.log("no white", "no white pieces");
                    return;
                }
                Logger.log("whiteSize", "#white pieces: "+WhitePieces.size());
                randPiece = WhitePieces.get(randomIntWithinBounds(0, WhitePieces.size()-1));
            }
            int row = randomIntWithinBounds(MIN,MAX);
            int col = randomIntWithinBounds(MIN,MAX);
            if(randPiece instanceof Pawn){
                Logger.log("CPE pawn",
                        "computer player chooses pawn to move");
                while(!Pawn.isValidPawnMove(state,randPiece,randPiece.getRow(),randPiece.getCol(),row,col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek pawn",
                            "looking for a pawn move");
                }
            }
            if(randPiece instanceof Queen){
                Logger.log("CPE queen",
                        "computer player chooses queen to move");
                row = randomIntWithinBounds(MIN,MAX);
                col = randomIntWithinBounds(MIN,MAX);
                while(!Queen.isValidQueenMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek queen",
                            "looking for a queen move");
                }
            }
            if(randPiece instanceof Knight){
                Logger.log("CPE knight",
                        "computer player chooses knight to move");
                row = randomIntWithinBounds(MIN,MAX);
                col = randomIntWithinBounds(MIN,MAX);
                while(!Knight.isValidKnightMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek knight",
                            "looking for a knight move");
                }
            }
            if(randPiece instanceof Bishop){
                Logger.log("CPE bishop",
                        "computer player chooses bishop to move");
                row = randomIntWithinBounds(MIN,MAX);
                col = randomIntWithinBounds(MIN,MAX);
                //make this into a for loop of i = 20, so it does not search for
                //possible locations to move indefinitely. If it cannot find a
                //valid move, it should randomly choose a new piece to move.
                //if a valid move is found, exit out of the loop and moveaction.
                while(!Bishop.isValidBishopMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek bishop",
                            "looking for a bishop move");
                }
            }
            if(randPiece instanceof King){
                Logger.log("CPE king",
                        "computer player chooses king to move");
                row = randomIntWithinBounds(MIN,MAX);
                col = randomIntWithinBounds(MIN,MAX);
                //make this into a for loop of i = 20, so it does not search for
                //possible locations to move indefinitely. If it cannot find a
                //valid move, it should randomly choose a new piece to move.
                //if a valid move is found, exit out of the loop and moveaction.
                while(!King.isValidKingMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek king",
                            "looking for a \n" +
                                    "                    Logger.log(\"seek knight\",\n" +
                                    "                            \"looking for a knight move\"); move");
                }
            }
            if(randPiece instanceof Rook){
                Logger.log("CPE rook",
                        "computer player chooses rook to move");
                row = randomIntWithinBounds(MIN,MAX);
                col = randomIntWithinBounds(MIN,MAX);
                //make this into a for loop of i = 20, so it does not search for
                //possible locations to move indefinitely. If it cannot find a
                //valid move, it should randomly choose a new piece to move.
                //if a valid move is found, exit out of the loop and moveaction.
                while(!Rook.isValidRookMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
                    row = randomIntWithinBounds(MIN,MAX);
                    col = randomIntWithinBounds(MIN,MAX);
                    Logger.log("seek rook",
                            "looking for a rook move");
                }
            }
            ChessMoveAction action = new ChessMoveAction(this, randPiece, randPiece.getRow(), randPiece.getCol(), row, col);
            Logger.log("CPE move",
                    "computer player move: "+randPiece.getRow()+" "+randPiece.getCol()+" "+row+" "+col);
            game.sendAction(action);
            return;
        }
//        else if (info instanceof ChessState){
//
//            setState((ChessState)info);
//            fillPiecesList();
//            ChessPiece randPiece;
//            //take a random piece
//            if (this.playerNum == 1) {
//                randPiece = BlackPieces.get(randomIntWithinBounds(0, BlackPieces.size()-1));
//            }
//            else {
//                randPiece = WhitePieces.get(randomIntWithinBounds(0, WhitePieces.size()-1));
//            }
//            int row = randomIntWithinBounds(MIN,MAX);
//            int col = randomIntWithinBounds(MIN,MAX);
//            if(randPiece instanceof Pawn){
//                row = randomIntWithinBounds(MIN,MAX);
//                col = randomIntWithinBounds(MIN,MAX);
//                while(!Pawn.isValidPawnMove(state,randPiece,randPiece.getRow(),randPiece.getCol(),row,col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            if(randPiece instanceof Queen){
//               row = randomIntWithinBounds(MIN,MAX);
//                col = randomIntWithinBounds(MIN,MAX);
//                while(!Queen.isValidQueenMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            if(randPiece instanceof Knight){
//                row = randomIntWithinBounds(MIN,MAX);
//                col = randomIntWithinBounds(MIN,MAX);
//                while(!Knight.isValidKnightMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            if(randPiece instanceof Bishop){
//                row = randomIntWithinBounds(MIN,MAX);
//                col = randomIntWithinBounds(MIN,MAX);
//                while(!Bishop.isValidBishopMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            if(randPiece instanceof King){
//                row = randomIntWithinBounds(MIN,MAX);
//                col = randomIntWithinBounds(MIN,MAX);
//                while(!King.isValidKingMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            if(randPiece instanceof Rook){
//               row = randomIntWithinBounds(MIN,MAX);
//               col = randomIntWithinBounds(MIN,MAX);
//                while(!Rook.isValidRookMove(state, randPiece.getRow(), randPiece.getCol(), row, col)){
//                    row = randomIntWithinBounds(MIN,MAX);
//                    col = randomIntWithinBounds(MIN,MAX);
//                }
//            }
//            //make a random move using that piece, in the future we should grab a move from the validMoves list in the piece's data
//            ChessMoveAction action = new ChessMoveAction(this, randPiece, randPiece.getRow(), randPiece.getCol(), row,col);
//
//
//            game.sendAction(action);
//        }
    }

    public int randomIntWithinBounds(int min, int max){
        if (max < min) return min;
        return min+ran.nextInt(1+max-min);
    }

    public void fillPiecesList(){
        WhitePieces.clear();
        BlackPieces.clear();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (state.getBoard().getSquares()[i][j].hasPiece()){
                    if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 0){
                        WhitePieces.add(state.getBoard().getSquares()[i][j].getPiece());
                    }
                    if (state.getBoard().getSquares()[i][j].getPiece().getBlackOrWhite() == 1){
                        BlackPieces.add(state.getBoard().getSquares()[i][j].getPiece());
                    }
                }
            }
        }
    }

    public void setState(ChessState state){
        this.state = state;
    }
}
