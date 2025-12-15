package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

// Adding notes to partition the controllers domain in fxml files.
public class MainGameController {

    private MainGame newGame;

    // resources Universal
    @FXML protected Label questionLabel;
    @FXML protected Label roundCounter;
    @FXML protected Button buttonA;
    @FXML protected Button buttonB;
    @FXML protected Button buttonC;
    @FXML protected Button buttonD;
    @FXML protected Button lifeline1;
    @FXML protected Button lifeline2;
    @FXML protected Button lifeline3;
    @FXML protected Button lifeline4; // to be added

    private Button[] answerButtons;

    // resources Main Menu
    @FXML protected Button startButton;

    // resources endscreen
    @FXML protected Label endMessage;
    @FXML protected Label finalScore;
    @FXML protected Button playAgainButton;
    @FXML protected Button mainMenuButton;

    // initialize (moved initialize here and set it so only the partitions are initialized first
    @FXML
    public void initialize() {
        if (startButton != null) {
            // Main Menu loaded
            setupMainMenu();
        } else if (questionLabel != null) {
            // Game Screen loaded
            setupGameScreen();
        } else if (endMessage != null) {
            // load end screen
        }
    }

    // Main Menu
    private void setupMainMenu() {
        // Nothing special yet
    }

    @FXML
    protected void startGame() throws IOException {
        loadFXML("GameScreen.fxml");
    }

    // Game Screen Partition
    private void setupGameScreen() {
        newGame = new MainGame();  // start new game
        answerButtons = new Button[]{buttonA, buttonB, buttonC, buttonD};
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        String[] qSetup = newGame.setupQuestion(); // use CSV generator
        questionLabel.setText(qSetup[0]);
        buttonA.setText(qSetup[1]);
        buttonB.setText(qSetup[2]);
        buttonC.setText(qSetup[3]);
        buttonD.setText(qSetup[4]);
        roundCounter.setText("Round " + newGame.round + " / " + newGame.MAX_ROUND);

        // Reset answer buttons visibility
        for (Button btn : answerButtons) btn.setVisible(true);

        // Reset lifelines visibility if needed
        if (lifeline1 != null) lifeline1.setVisible(true);
        if (lifeline2 != null) lifeline2.setVisible(true);
        if (lifeline3 != null) lifeline3.setVisible(true);
        if (lifeline4 != null) lifeline4.setVisible(true);
    }

    // Answer Handling (moved it all here)
    @FXML
    protected void answerA() {
        handleAnswer(0);
    }

    @FXML
    protected void answerB() {
        handleAnswer(1);
    }

    @FXML
    protected void answerC() {
        handleAnswer(2);
    }

    @FXML
    protected void answerD() {
        handleAnswer(3);
    }

    private void handleAnswer(int answerNumber) {
        boolean[] verified = newGame.answerChecker(answerNumber);

        if (verified[0]) {
            if (verified[1]) {
                // Game completed
                endGame(true);
            } else {
                loadNextQuestion();
            }
        } else {
            endGame(false);
        }
    }


    @FXML
    private void lifeline1() {
        lifeline1.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int result = newGame.lifeline1();
        System.out.println(result);
    }

    @FXML
    private void lifeline2() {
        lifeline2.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int[] results = newGame.lifeline2();
        for (int result : results) {
            System.out.println(result);
        }
    }

    @FXML
    private void lifeline3() {
        System.out.println("TRIGGERED LIFELINE!!");
        lifeline3.setVisible(false);
        //TODO: 50/50. hide or color two wrong option. Call newGame.lifeline3(). Implement the method inside MainGame class then handle ui stuff here.
        int[] results = newGame.lifeline3();
        for (int result : results) {
            answerButtons[result].setVisible(false);
        }
    }

    // End Screen
    @FXML
    private void lifeline4() {
        System.out.println("TRIGGERED LIFELINE!!");
        //TODO: idk think of another lifeline lmao
    }
    // End Screen
    private void endGame(boolean won) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
            Parent root = loader.load();

            MainGameController endController = loader.getController();
            endController.setEndScreenData(won, newGame.score);

            Stage stage = (Stage) questionLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEndScreenData(boolean won, int score) {
        endMessage.setText(won ? "Congratulations!" : "Game Over!");
        finalScore.setText("You won: $" + score);
    }

    @FXML protected void playAgain() throws IOException {
        loadFXML("GameScreen.fxml");
    }

    @FXML protected void goToMainMenu() throws IOException {
        loadFXML("MainMenu.fxml");
    }
    // Stuff
    private void loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) (startButton != null ? startButton.getScene().getWindow() :
                questionLabel != null ? questionLabel.getScene().getWindow() :
                        endMessage.getScene().getWindow());
        stage.setScene(new Scene(root));
    }
}
