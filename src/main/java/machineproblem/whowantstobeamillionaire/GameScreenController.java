package machineproblem.whowantstobeamillionaire;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Game Screen Partition
public class GameScreenController {

    private MainGame newGame;
    final int DRUM_ROLL_DURATION = 3;
    final int HIGHLIGHT_WRONG_AND_CORRECT_DURATION = 3;
    final int WRONG_CLIP_DURATION = 2;
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


    @FXML
    public void initialize() {
        // Stop previous background music immediately (main menu)
        AudioManager.getInstance().stopBackground();

        // Play transition stinger first
        AudioManager.getInstance().playClip("Transition1"); // your WAV stinger

        // Wait for stinger duration before starting game background
        PauseTransition stingerDelay = new PauseTransition(Duration.seconds(4)); // adjust to your stinger length
        stingerDelay.setOnFinished(event -> {
            // Start new background music for game screen
            AudioManager.getInstance().playBackground("/Audio/Background2-Stage1.mp3", 0.5);
        });
        stingerDelay.play();

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
    }

    public void startNewGame() {
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
            btn.setStyle(""); //  CLEAR previous color
            btn.setDisable(false); // optional safety
        }
    }

    // Answer Handling
    @FXML protected void answerA() { handleAnswer(0); }
    @FXML protected void answerB() { handleAnswer(1); }
    @FXML protected void answerC() { handleAnswer(2); }
    @FXML protected void answerD() { handleAnswer(3); }

    private void handleAnswer(int answerNumber) {
        // close graph regardless
        AudienceChart.closeGraph();

        if (lifeline4Status) {
            lifeline4Logic(answerNumber);
            return;
        }

        for (Button btn : answerButtons) btn.setDisable(true);


        AudioManager.getInstance().playClip("DrumRoll");
        AudioManager.getInstance().setBackgroundVolume(0.0);  // pause for drum roll

        PauseTransition revealDelay = new PauseTransition(Duration.seconds(DRUM_ROLL_DURATION));
        revealDelay.setOnFinished(event -> {

            AudioManager.getInstance().stopClip("DrumRoll");

            boolean[] verified = newGame.answerChecker(answerNumber);

            if (verified[0]) { // Correct
                answerButtons[answerNumber].setStyle("-fx-background-color: green;");

                AudioManager.getInstance().playClip("Correct");
                PauseTransition correctDelay = getPauseTransition(verified);
                correctDelay.play();
                AudioManager.getInstance().setBackgroundVolume(0.5);  // after correct
                } else {
                    answerButtons[newGame.getCorrectAnswer()].setStyle("-fx-background-color: green;");
                    answerButtons[answerNumber].setStyle("-fx-background-color: red;");

                    AudioManager.getInstance().playClip("Wrong");
                    PauseTransition wrongDelay = new PauseTransition(Duration.seconds(WRONG_CLIP_DURATION));
                    wrongDelay.setOnFinished(e -> {
                                AudioManager.getInstance().stopClip("Wrong");
                                endGame(false);
                            });
                    wrongDelay.play();
                }
        });
        revealDelay.play();

    }

    private PauseTransition getPauseTransition(boolean[] verified) {
        PauseTransition correctDelay = new PauseTransition(Duration.seconds(HIGHLIGHT_WRONG_AND_CORRECT_DURATION)); // let player see green
        correctDelay.setOnFinished(e -> {

            AudioManager.getInstance().stopClip("Correct");
            if (verified[1]) {
                endGame(true);
            } else {
                loadNextQuestion();
                for (Button btn : answerButtons) btn.setDisable(false);
            }
        });
        return correctDelay;
    }

    @FXML private void lifeline1() {
        lifeline1.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int result = newGame.callAFriend();
        System.out.println(result);
    }

    @FXML private void lifeline2() {
        lifeline2.setVisible(false);
        System.out.println("TRIGGERED LIFELINE!!");
        int[] results = newGame.audienceVote();
        for (int result : results) System.out.println(result);
        AudienceChart.showGraph(results);
    }

    @FXML private void lifeline3() {
        System.out.println("TRIGGERED LIFELINE!!");
        lifeline3.setVisible(false);
        int[] results = newGame.fiftyFifty();
        for (int result : results) answerButtons[result].setVisible(false);
    }

    @FXML private void lifeline4() {
        lifeline4Status = true;
        lifeline4.setVisible(false);
    }

    private void lifeline4Logic(int answerNumber) {
        lifeline4Status = false;
        if (newGame.landMine(answerNumber)) {
            answerButtons[answerNumber].setStyle("-fx-background-color:green");
            System.out.println("THAT SHIT TRUE NOCAP FRFR");
        } else {
            answerButtons[answerNumber].setStyle("-fx-background-color:red");
            System.out.println("TURN BACK. THAT SHIT CAP");
        }
    }

    private void endGame(boolean won) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
            Parent root = loader.load();

            EndScreenController controller = loader.getController();
            int finalPrize = won ? newGame.score : newGame.getGuaranteedPrize();
            controller.setEndScreenData(won, finalPrize);

            Stage stage = (Stage) questionLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLadder(int roundsWon) {
        for (Rectangle box : ladderBoxes) box.setVisible(false);
        for (int i = 0; i < roundsWon; i++) ladderBoxes.get(i).setVisible(true);
    }
}
