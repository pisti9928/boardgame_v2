// CHECKSTYLE:OFF
package boardgame.scene;

import boardgame.player.Player;
import boardgame.player.PlayerState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.io.IOException;

public class PlayerNameController {



    @FXML
    private TextArea fPlayer;

    @FXML
    private TextArea sPlayer;


    @FXML
    private void nextToGame(ActionEvent event) throws IOException {
        PlayerState.setPlayername(Player.PLAYERRED,fPlayer.getText());
        PlayerState.setPlayername(Player.PLAYERBLUE,sPlayer.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
