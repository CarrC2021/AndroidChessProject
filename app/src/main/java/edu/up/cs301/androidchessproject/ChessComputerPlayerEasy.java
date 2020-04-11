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

import android.app.VoiceInteractor;

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
        if (info instanceof NotYourTurnInfo) {
            return;

        } else if (info instanceof IllegalMoveInfo || info instanceof ChessState) {
            if (info instanceof ChessState) {
                setState((ChessState) info);
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
                Logger.log("blackSize", "#black pieces: " + BlackPieces.size());
                randPiece = BlackPieces.get(randomIntWithinBounds(0, BlackPieces.size() - 1));
            } else {
                if (WhitePieces.size() <= 0) {
                    Logger.log("no white", "no white pieces");
                    return;
                }
                Logger.log("whiteSize", "#white pieces: " + WhitePieces.size());
                randPiece = WhitePieces.get(randomIntWithinBounds(0, WhitePieces.size() - 1));
            }
            int row = randomIntWithinBounds(MIN, MAX);
            int col = randomIntWithinBounds(MIN, MAX);
            if (randPiece instanceof Pawn) {
                Logger.log("CPE pawn",
                        "computer player chooses pawn to move");
                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek pawn",
                                "looking for a pawn move");
                    } else {
                        break;
                    }
                }
            }
            if (randPiece instanceof Queen) {
                Logger.log("CPE queen",
                        "computer player chooses queen to move");
                row = randomIntWithinBounds(MIN, MAX);
                col = randomIntWithinBounds(MIN, MAX);

                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek queen",
                                "looking for a queen move");
                    } else {
                        break;
                    }
                }
            }
            if (randPiece instanceof Knight) {
                Logger.log("CPE knight",
                        "computer player chooses knight to move");
                row = randomIntWithinBounds(MIN, MAX);
                col = randomIntWithinBounds(MIN, MAX);
                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek knight",
                                "looking for a knight move");
                    } else {
                        break;
                    }
                }
            }
            if (randPiece instanceof Bishop) {
                Logger.log("CPE bishop",
                        "computer player chooses bishop to move");
                row = randomIntWithinBounds(MIN, MAX);
                col = randomIntWithinBounds(MIN, MAX);

                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek bishop",
                                "looking for a bishop move");
                    }
                }
            }
            if (randPiece instanceof King) {
                Logger.log("CPE king",
                        "computer player chooses king to move");
                row = randomIntWithinBounds(MIN, MAX);
                col = randomIntWithinBounds(MIN, MAX);

                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek king",
                                "looking for a \n" +
                                        "                    Logger.log(\"seek king\",\n" +
                                        "                            \"looking for a king move\"); move");
                    }
                    else {
                        break;
                    }
                }
            }
            if (randPiece instanceof Rook) {
                Logger.log("CPE rook",
                        "computer player chooses rook to move");
                row = randomIntWithinBounds(MIN, MAX);
                col = randomIntWithinBounds(MIN, MAX);
                for (int i = 0; i < 20; i++) {
                    if (!randPiece.getValidMoves()[row][col]) {
                        row = randomIntWithinBounds(MIN, MAX);
                        col = randomIntWithinBounds(MIN, MAX);
                        Logger.log("seek rook",
                                "looking for a rook move");
                    }
                    else {
                        break;
                    }
                }
            }
            ChessMoveAction action = new ChessMoveAction(this, randPiece, randPiece.getRow(), randPiece.getCol(), row, col);
            Logger.log("CPE move",
                    "computer player move: " + randPiece.getRow() + " " + randPiece.getCol() + " " + row + " " + col);
            game.sendAction(action);
            return;
        }
    }

    public int randomIntWithinBounds(int min, int max){
        if (max < min) return min;
        return min+ran.nextInt(1+max-min);
    }

    public void fillPiecesList(){
        WhitePieces.clear();
        BlackPieces.clear();
        WhitePieces = state.getWhitePieces();
        BlackPieces = state.getBlackPieces();
    }

    public void setState(ChessState state){
        this.state = state;
    }
}
