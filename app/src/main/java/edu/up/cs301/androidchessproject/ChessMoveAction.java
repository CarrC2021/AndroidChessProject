/**
 * class ChessMoveAction
 *
 * A class which will represent one player's move.
 *
 * @author Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;


import edu.up.cs301.androidchessproject.boardandpieces.ChessPiece;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class ChessMoveAction extends GameAction {

    private String moveAsString;
    private ChessPiece pieceEnd;
    private int rowStart;
    private int colStart;
    private int rowEnd;
    private int colEnd;

    /**
     * constructor for ChessMoveAction, this will hold the necessary information to make a move
     *
     * @param player the player who created the action
     */
    public ChessMoveAction(GamePlayer player, ChessPiece p, int r, int c, int r2, int c2) {
        super(player);
        pieceEnd = p;
        rowStart = r;
        colStart = c;
        rowEnd = r2;
        colEnd = c2;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColStart() {
        return colStart;
    }

    public int getColEnd() {
        return colEnd;
    }

    public ChessPiece getPieceEnd() {
        return pieceEnd;
    }
}
