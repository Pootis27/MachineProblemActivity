package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndScreenController {

    @FXML protected Label endMessage;
    @FXML protected Label finalScore;
    @FXML protected Button playAgainButton;
    @FXML protected Button mainMenuButton;

    public void setEndScreenData(boolean won, int score) {
        endMessage.setText(won ? "Congratulations!" : "Game Over!");
        finalScore.setText("You won: $" + score);
    }

    @FXML
    protected void playAgain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
        Parent root = loader.load();

        GameScreenController controller = loader.getController();
        controller.startNewGame();

        Stage stage = (Stage) endMessage.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    protected void goToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) endMessage.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
