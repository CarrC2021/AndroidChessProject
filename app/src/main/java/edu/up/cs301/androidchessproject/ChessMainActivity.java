/**
 * class ChessMainActivity
 *
 * This is a class which will create a default configuration for chess and will have a method
 * to make a new ChessLocalGame object. It will add one human player and one computer player
 * for a local game.
 *
 * @author Casey Carr
 * @version March 2020
 *
 */

package edu.up.cs301.androidchessproject;


import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import android.content.res.Resources;


import java.util.ArrayList;


import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;
import edu.up.cs301.game.GameFramework.utilities.GameTimer;
import edu.up.cs301.game.GameFramework.utilities.Tickable;


public class ChessMainActivity extends GameMainActivity {

    //Tag for logging
    private static final String TAG = "ChessMainActivity";
    public static final int PORT_NUMBER = 5213;

    /**
     * a chess game is for two players. The default is human vs. computer
     */
    @Override
    public GameConfig createDefaultConfig() {
        ChessHumanPlayer player = new ChessHumanPlayer("human");
        ChessComputerPlayerEasy easy = new ChessComputerPlayerEasy("easy");
        ChessComputerPlayerHard hard = new ChessComputerPlayerHard("hard");

        ArrayList<GamePlayerType> list = new ArrayList();
        list.add(new GamePlayerType("ChessHumanPlayer") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new ChessHumanPlayer("human");
            }
        });
        list.add(new GamePlayerType("ChessComputerPlayerEasy") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayerEasy("easy");
            }
        });
        list.add(new GamePlayerType("ChessComputerPlayerHard") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayerHard("hard");
            }
        });

        //random port number
        GameConfig config = new GameConfig(list, 2, 2, "chess", PORT_NUMBER);

        // Add the default players
        config.addPlayer("Human", 0); // GUI
        config.addPlayer("Computer", 1); // dumb computer player

        return config;
    }


    //this is the one method I need the most help with
    @Override
    public LocalGame createLocalGame() {
        return new ChessLocalGame(new TimerInfo(new GameTimer(new Tickable() {
            @Override
            public void tick(GameTimer timer) {

            }
        })), new TimerInfo(new GameTimer(new Tickable() {
            @Override
            public void tick(GameTimer timer) {

            }
        })));
    }
}
