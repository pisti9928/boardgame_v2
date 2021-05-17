// CHECKSTYLE:OFF
package boardgame.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;


import java.io.IOException;

public class FirstController {
    @FXML
    private void sceneSwitch(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/playerName.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void leaveGame(ActionEvent event) throws IOException {
        Logger.debug("Leave Game");
        Platform.exit();
    }

    @FXML
    private void showLeaderboard(ActionEvent event)throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/leaderboard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
