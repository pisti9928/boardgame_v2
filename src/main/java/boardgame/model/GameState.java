package boardgame.model;

import boardgame.jdbi.LeaderboardController;
import boardgame.player.PlayerState;
import org.tinylog.Logger;

/**
 * jatekallapotokat tartalmazi osztaly.
 */
public class GameState {
    /**
     * A kek jatekos nyert, adatbazisba irja az eredmenyt.
     */
    public static void blueWin() {
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertWinner(PlayerState.getBluePlayerName());
        leaderboardController.insertLoser(PlayerState.getRedPlayerName());
        Logger.debug("Blue Win");
    }

    /**
     * A piros jatekos nyert, adatbazisba irja az eredmenyt.
     */
    public static void redWin(){
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertWinner(PlayerState.getRedPlayerName());
        leaderboardController.insertLoser(PlayerState.getBluePlayerName());
        Logger.debug("Red Win");

    }

    /**
     * Dontetlen lett az eredmeny, adatbazisba irja az eredmenyt.
     */
    public static void draw(){
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertDrawer(PlayerState.getRedPlayerName());
        leaderboardController.insertDrawer(PlayerState.getBluePlayerName());
        Logger.debug("Draw");

    }
}
