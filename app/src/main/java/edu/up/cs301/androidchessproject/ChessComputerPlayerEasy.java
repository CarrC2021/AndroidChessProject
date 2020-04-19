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

    private ArrayList<int[]> computerPlayerValidMoves;
    private ChessState state;

    private Random ran;


    /*
     * Constructor for the ChessComputerPlayerEasy class
     */
    public ChessComputerPlayerEasy(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
        state = new ChessState();
        ran = new Random();
        computerPlayerValidMoves = new ArrayList<>();
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
//need to add an overall loop over all of this.
        if (info instanceof NotYourTurnInfo) {
            return;

        } else if (info instanceof IllegalMoveInfo || info instanceof ChessState) {
            if (info instanceof ChessState) {
                state = null;
                setState((ChessState) info);
            }
            if (state.getPlayerToMove() != playerNum) return;

            //update this state's valid moves
            state.updateValidMoves();

            //update the relevant information in this class
            updateComputerPlayerValidMoves();

            //find a new move from the valid moves list
            int[] moveToReturn;
            moveToReturn = computerPlayerValidMoves.get(randomIntWithinBounds(0, computerPlayerValidMoves.size()));

            //create the chess move action
            ChessMoveAction action = new ChessMoveAction(this, moveToReturn[0],
                    moveToReturn[1], moveToReturn[2], moveToReturn[3]);

            Logger.log("CPE move",
                    "computer player move: " + moveToReturn[0] + " "
                    + moveToReturn[1] + " " + moveToReturn[2] + " " + moveToReturn[3]);
            game.sendAction(action);
            
            return;
        }
    }

    /**
     * returns a random integer within the specified bounds
     */
    public int randomIntWithinBounds(int min, int max){
        if (max < min) return min;
        return min+ran.nextInt(1+max-min);
    }


    public void setState(ChessState state){
        this.state = state;
    }

    /**
     * This method will be called after this player receives a new ChessState, it will
     * update this player's allValidMoves array list which represents all of the legal
     * moves this player can make
     */
    private void updateComputerPlayerValidMoves() {
        computerPlayerValidMoves.clear();
        if (this.playerNum == 1){
            for (ChessPiece piece : state.getBlackPieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (piece.getValidMoves()[i][j]) {
                            computerPlayerValidMoves.add(new int[]
                                    {piece.getRow(), piece.getCol(), i, j});
                        }
                    }
                }
            }
        }
        else if (this.playerNum == 0){
            for (ChessPiece piece : state.getWhitePieces()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (piece.getValidMoves()[i][j]){
                            computerPlayerValidMoves.add(new int[]
                                    {piece.getRow(),piece.getCol(),i,j});
                        }
                    }
                }
            }
        }
    }
}
