package edu.up.cs301.chess;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;

public class ChessComputerPlayerHard extends GameComputerPlayer {


    /*
     * Constructor for the ChessComputerPlayerHard class
     */
    public ChessComputerPlayerHard(String name) {
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

    }
}
