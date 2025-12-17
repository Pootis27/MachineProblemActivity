package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML protected Button startButton;

    @FXML
    protected void startGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
        Parent root = loader.load();

        GameScreenController controller = loader.getController();
        controller.startNewGame(); // initialize game here

        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
