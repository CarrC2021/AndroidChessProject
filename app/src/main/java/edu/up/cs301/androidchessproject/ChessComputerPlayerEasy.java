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
import java.util.concurrent.ThreadLocalRandom;

import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;

public class ChessComputerPlayerEasy extends GameComputerPlayer {
    private static int MIN = 0;
    private static int MAX = 8;

    public ArrayList<ChessPiece> BlackPieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> WhitePieces = new ArrayList<ChessPiece>();

    private ChessState state;


    /*
     * Constructor for the ChessComputerPlayerEasy class
     */
    public ChessComputerPlayerEasy(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
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
        if (info instanceof IllegalMoveInfo){
            ChessPiece randPiece = BlackPieces.get(randomIntWithinBounds(0,BlackPieces.size()));
            state.squareToString(randPiece.getRow(), randPiece.getCol());
            ChessMoveAction action = new ChessMoveAction(this, null, randPiece.getRow(), randPiece.getCol(), randomIntWithinBounds(MIN,MAX), randomIntWithinBounds(MIN,MAX));
            game.sendAction(action);
        }
        if (info instanceof ChessState){
            setState((ChessState)info);
            fillPiecesList();
            ChessPiece randPiece = BlackPieces.get(randomIntWithinBounds(0,BlackPieces.size()));
            state.squareToString(randPiece.getRow(), randPiece.getCol());
            ChessMoveAction action = new ChessMoveAction(this, null, randPiece.getRow(), randPiece.getCol(), randomIntWithinBounds(MIN,MAX), randomIntWithinBounds(MIN,MAX));
            game.sendAction(action);
        }
    }

    public int randomIntWithinBounds(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
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
