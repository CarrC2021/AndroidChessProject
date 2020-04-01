package edu.up.cs301.androidchessproject;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class ChessResignAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessResignAction(GamePlayer player) {
        super(player);
    }
}
