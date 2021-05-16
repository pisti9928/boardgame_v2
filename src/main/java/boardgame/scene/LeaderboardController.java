package boardgame.scene;

import boardgame.jdbi.PlayerSet;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;


public class LeaderboardController {
    @FXML
    TextFlow leaderboardTextFlow;

    @FXML
    private void  initialize (){
        boardgame.jdbi.LeaderboardController leaderboardController = new boardgame.jdbi.LeaderboardController();
        leaderboardController.createTable();
        ArrayList <PlayerSet> lista = leaderboardController.getFirstTenRecord();
        int szamlalo = 1;
        for (var player: lista){
            leaderboardTextFlow.getChildren().add(new Text(szamlalo+".\tNév: "+ player.getName() +"\tGyőzelem: "+player.getWins()+"\tDöntetlen: "+player.getDraws()+"\tVereség: "+player.getLoses()+"\n\n"));
            szamlalo+=1;
        }

    }

    @FXML
    private void delete(){
        boardgame.jdbi.LeaderboardController leaderboardController = new boardgame.jdbi.LeaderboardController();
        leaderboardController.deleteLeaderboard();

    }

}

