// CHECKSTYLE:OFF
package boardgame.scene;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardGameApplication extends Application {

    private FXMLLoader fxmlLoader = new FXMLLoader();
    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/menu.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("JavaFX Board Game 2.16");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
