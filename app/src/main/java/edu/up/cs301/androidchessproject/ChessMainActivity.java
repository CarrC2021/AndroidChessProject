package edu.up.cs301.androidchessproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.R;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.CheckBox;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;


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

    @Override
    public LocalGame createLocalGame() {
        return new ChessLocalGame();
    }

//    /**
//     * onCreate
//     *
//     * "main" for the game framework
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.chess_game_main.xml);
//    }// onCreate
}
