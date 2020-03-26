package edu.up.cs301.androidchessproject;


import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class ChessMoveAction extends GameAction {

    private String moveAsString;

    /**
     * constructor for ChessMoveAction, this will hold the necessary information to make a move
     *
     * @param player the player who created the action
     */
    public ChessMoveAction(GamePlayer player, String move) {
        super(player);
        moveAsString = move;
    }
}
