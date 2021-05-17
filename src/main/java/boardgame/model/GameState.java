package boardgame.model;

import boardgame.jdbi.LeaderboardController;
import boardgame.player.PlayerState;
import org.tinylog.Logger;


public class GameState {

    public static void blueWin() {
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertWinner(PlayerState.getBluePlayerName());
        leaderboardController.insertLoser(PlayerState.getRedPlayerName());
        Logger.debug("Blue Win");
    }

    public static void redWin(){
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertWinner(PlayerState.getRedPlayerName());
        leaderboardController.insertLoser(PlayerState.getBluePlayerName());
        Logger.debug("Red Win");

    }

    public static void draw(){
        LeaderboardController leaderboardController = new LeaderboardController();
        leaderboardController.createTable();
        leaderboardController.insertDrawer(PlayerState.getRedPlayerName());
        leaderboardController.insertDrawer(PlayerState.getBluePlayerName());
        Logger.debug("Draw");

    }
}
