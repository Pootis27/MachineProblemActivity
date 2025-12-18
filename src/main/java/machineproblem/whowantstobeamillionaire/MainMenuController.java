package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML protected Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Play main menu background music at 50% volume
        AudioManager.getInstance().playBackground("/audio/Background1.mp3", 0.5);
    }

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
