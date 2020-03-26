package edu.up.cs301.chess;

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
