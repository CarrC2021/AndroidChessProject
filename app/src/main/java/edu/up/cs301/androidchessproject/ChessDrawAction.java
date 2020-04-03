/**
 * class ChessMoveAction
 *
 * A class which will represent a draw offer. Does not need any data because the only thing
 * that the opposite player needs to do is say yes or no.
 *
 * @author Casey Carr
 * @version March 2020
 *
 */
package edu.up.cs301.androidchessproject;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class ChessDrawAction extends GameAction {


    /**
     * constructor for ChessDrawAction, this will be sent when one player desires a draw.
     *
     * @param player the player who created the action
     */
    public ChessDrawAction(GamePlayer player) {
        super(player);
    }
}
