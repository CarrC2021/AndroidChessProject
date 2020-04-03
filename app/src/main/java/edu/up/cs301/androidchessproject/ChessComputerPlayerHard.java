/**
 * class ChessComputerPlayerHard
 *
 * A class which will be capable of playing chess at a better level then the ChessComputerPlayerEasy.
 * It will use the minimax algorithm to analyze the current GameState and make a move based off that.
 *
 *
 * @author Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;

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
        //write some of the algorithm here
    }
}
