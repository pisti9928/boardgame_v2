// CHECKSTYLE:OFF
package boardgame.scene;

import boardgame.jdbi.PlayerSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
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
    private void delete(ActionEvent event) throws IOException {
        boardgame.jdbi.LeaderboardController leaderboardController = new boardgame.jdbi.LeaderboardController();
        leaderboardController.deleteLeaderboard();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/leaderboard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    private void menu (ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}

