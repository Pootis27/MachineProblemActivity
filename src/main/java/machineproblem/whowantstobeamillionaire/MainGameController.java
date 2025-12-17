package machineproblem.whowantstobeamillionaire;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public boolean lifeline4Status;

    // resources Main Menu
    @FXML protected Button startButton;

    // Ladder Score

    @FXML protected Rectangle ladderBox1;
    @FXML protected Rectangle ladderBox2;
    @FXML protected Rectangle ladderBox3;
    @FXML protected Rectangle ladderBox4;
    @FXML protected Rectangle ladderBox5;
    @FXML protected Rectangle ladderBox6;
    @FXML protected Rectangle ladderBox7;
    @FXML protected Rectangle ladderBox8;
    @FXML protected Rectangle ladderBox9;
    @FXML protected Rectangle ladderBox10;
    @FXML protected Rectangle ladderBox11;
    @FXML protected Rectangle ladderBox12;
    @FXML protected Rectangle ladderBox13;
    @FXML protected Rectangle ladderBox14;
    @FXML protected Rectangle ladderBox15;
    private List<Rectangle> ladderBoxes;
    @FXML protected ImageView ladderImage;


    // resources endscreen
    @FXML protected Label endMessage;
    @FXML protected Label finalScore;
    @FXML protected Button playAgainButton;
    @FXML protected Button mainMenuButton;

    // initialize (moved initialize here and set it so only the partitions are initialized first
    @FXML
    public void initialize() {
        ladderBoxes = new ArrayList<>();
        ladderBoxes.add(ladderBox1);
        ladderBoxes.add(ladderBox2);
        ladderBoxes.add(ladderBox3);
        ladderBoxes.add(ladderBox4);
        ladderBoxes.add(ladderBox5);
        ladderBoxes.add(ladderBox6);
        ladderBoxes.add(ladderBox7);
        ladderBoxes.add(ladderBox8);
        ladderBoxes.add(ladderBox9);
        ladderBoxes.add(ladderBox10);
        ladderBoxes.add(ladderBox11);
        ladderBoxes.add(ladderBox12);
        ladderBoxes.add(ladderBox13);
        ladderBoxes.add(ladderBox14);
        ladderBoxes.add(ladderBox15);

        if (startButton != null) {
            // Main Menu loaded
            setupMainMenu();
        } else if (questionLabel != null) {
            // Game Screen loaded
            setupGameScreen();
        }  // load end screen

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
        updateLadder(newGame.round);

        // Reset answer buttons visibility
        for (Button btn : answerButtons) {
            btn.setVisible(true);
            btn.setStyle(""); // 👈 CLEAR previous color
            btn.setDisable(false); // optional safety
        }

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
        // close graph regardless
        TestChartClass.closeGraph();

        if(lifeline4Status) {
            lifeline4Logic(answerNumber);
            return;
        }


        boolean[] verified = newGame.answerChecker(answerNumber);
        if (verified[0]) {
            if (verified[1]) {
                // Game completed
                endGame(true);
            } else {
                loadNextQuestion();
            }
        } else {
            answerButtons[newGame.getCorrectAnswer()].setStyle("-fx-background-color: green;");
            answerButtons[answerNumber].setStyle("-fx-background-color: red;");

            // Wait 3 seconds before ending game
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> endGame(false));
            pause.play();
        }
    }


    @FXML
    private void lifeline1() {
        lifeline1.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int result = newGame.callAFriend();
        System.out.println(result);
    }

    @FXML
    private void lifeline2() {
        lifeline2.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int[] results = newGame.audienceVote();
        for (int result : results) {
            System.out.println(result);
        }
        TestChartClass.showGraph(results);
    }

    @FXML
    private void lifeline3() {
        System.out.println("TRIGGERED LIFELINE!!");
        lifeline3.setVisible(false);
        //TODO: 50/50. hide or color two wrong option. Call newGame.lifeline3(). Implement the method inside MainGame class then handle ui stuff here.
        int[] results = newGame.fiftyFifty();
        for (int result : results) {
            answerButtons[result].setVisible(false);
        }
    }

    // End Screen
    @FXML
    private void lifeline4() {
        lifeline4Status = true;
        lifeline4.setVisible(false);
    }

    private void lifeline4Logic(int answerNumber) {
        lifeline4Status = false;
        if(newGame.landMine(answerNumber)) {
            answerButtons[answerNumber].setStyle("-fx-background-color:green");
            System.out.println("THAT SHIT TRUE NOCAP FRFR");
        }
        else {
            answerButtons[answerNumber].setStyle("-fx-background-color:red");
            System.out.println("TURN BACK. THAT SHIT CAP");
        }
    }

    // End Screen
    private void endGame(boolean won) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
            Parent root = loader.load();

            MainGameController endController = loader.getController();
            int finalPrize;
            if (won) {
                finalPrize = newGame.score; //1 million grand prize
            } else {
                // safe haven prizes
                finalPrize = newGame.getGuaranteedPrize();
            }
            endController.setEndScreenData(won, finalPrize);

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

    public void updateLadder(int roundsWon) {
        for(Rectangle boxes : ladderBoxes) {
            boxes.setVisible(false);
        }
        for(int i = 0; i < roundsWon; i++) {
            ladderBoxes.get(i).setVisible(true);
        }
    }
}
