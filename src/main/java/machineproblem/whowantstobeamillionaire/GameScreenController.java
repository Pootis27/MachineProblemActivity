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
import javafx.scene.paint.Color;

// Game Screen Partition
public class GameScreenController {

    private MainGame newGame;
    final int DRUM_ROLL_DURATION = 3;
    final int HIGHLIGHT_WRONG_AND_CORRECT_DURATION = 3;
    final int WRONG_CLIP_DURATION = 2;
    private boolean forfeited = false;

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
    @FXML protected Button lifeline4;

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
        AudioManager.getInstance().playClip("Transition1");

        // Wait for stinger duration before starting game background
        PauseTransition stingerDelay = new PauseTransition(Duration.seconds(4));
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
        answerButtons = new Button[]{buttonA, buttonB, buttonC, buttonD}; // for easier handling of option buttons later on
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        String[] qSetup;
        qSetup = newGame.setupQuestion();   // asks game for new questions and options. Then handle the display
        questionLabel.setText(qSetup[0]);
        buttonA.setText("A. " + qSetup[1]);
        buttonB.setText("B. " + qSetup[2]);
        buttonC.setText("C. " + qSetup[3]);
        buttonD.setText("D. " + qSetup[4]);
        roundCounter.setText("Round " + newGame.round + " / " + newGame.MAX_ROUND); // displays current and max round
        updateLadder(newGame.round); // Update the score ladder (very astetik) to reflect current score

        // Reset answer buttons visibility and opacity
        for (Button btn : answerButtons) {
            btn.setVisible(true);
            btn.setStyle(""); // CLEAR previous color

            // FIX: Make clickable and bright again for new round
            btn.setMouseTransparent(false);
            btn.setOpacity(1.0);
        }
    }
    // Handle Forfeit ruling
    @FXML private Button forfeitButton;

    @FXML
    private void forfeit() {
        forfeited = true;
        forfeitButton.setDisable(true); // prevent spamming
    }

    // Answer Handling
    @FXML protected void answerA() { handleAnswer(0); }
    @FXML protected void answerB() { handleAnswer(1); }
    @FXML protected void answerC() { handleAnswer(2); }
    @FXML protected void answerD() { handleAnswer(3); }

    private void handleAnswer(int answerNumber) {
        // close graph regardless from Audience Vote lifeline
        AudienceChart.closeGraph();

        // hijacks the answer handling if the landmine lifeline is triggered
        if (lifeline4Status) {
            lifeline4Logic(answerNumber);
            return;
        }

        // FIX: Lock all, but only dim the unselected ones
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setMouseTransparent(true); // Stop clicks
            if (i != answerNumber) {
                answerButtons[i].setOpacity(0.5); // Fade out the ones you didn't pick
            }
        }

        AudioManager.getInstance().playClip("DrumRoll");  // play drum roll
        AudioManager.getInstance().setBackgroundVolume(0.0);  // mute for drum roll

        // Thread.sleep() made it not play drum roll in time so PauseTransition is used instead.
        // delays activation of setOnFinished
        PauseTransition revealDelay = new PauseTransition(Duration.seconds(DRUM_ROLL_DURATION));
        revealDelay.setOnFinished(event -> {

            AudioManager.getInstance().stopClip("DrumRoll");

            boolean[] verified = newGame.answerChecker(answerNumber);

            if (verified[0]) { // Correct
                // Ensure the correct button is bright (opacity 1.0) and Green
                answerButtons[answerNumber].setOpacity(1.0);
                answerButtons[answerNumber].setStyle("-fx-background-color: green;");

                AudioManager.getInstance().playClip("Correct");
                // Same thing here but this time for correct sound effect
                PauseTransition correctDelay = getPauseTransition(verified);
                correctDelay.play();
                AudioManager.getInstance().setBackgroundVolume(0.5);
            } else {
                // Wrong

                // 1. Force the ACTUAL correct answer to be bright and Green
                answerButtons[newGame.getCorrectAnswer()].setOpacity(1.0);
                answerButtons[newGame.getCorrectAnswer()].setStyle("-fx-background-color: green;");

                // 2. Turn the User's Wrong answer Red
                answerButtons[answerNumber].setStyle("-fx-background-color: red;");

                AudioManager.getInstance().playClip("Wrong");
                // Same thing here but this time for wrong sound effect
                PauseTransition wrongDelay = new PauseTransition(Duration.seconds(WRONG_CLIP_DURATION));
                wrongDelay.setOnFinished(e -> {
                    AudioManager.getInstance().stopClip("Wrong");
                    AudioManager.getInstance().setBackgroundVolume(0.5);
                    endGame(false);
                });
                wrongDelay.play();
            }
        });
        revealDelay.play();

    }

    // sets a delay until setOnFinished is triggered
    // we used this as we noticed that Thread.sleep runs too fast that UI fails to update in time (wrong option red, correct green) before sleeping
    private PauseTransition getPauseTransition(boolean[] verified) {
        PauseTransition correctDelay = new PauseTransition(Duration.seconds(HIGHLIGHT_WRONG_AND_CORRECT_DURATION));
        correctDelay.setOnFinished(e -> {

            AudioManager.getInstance().stopClip("Correct");
            if (forfeited) {
                AudioManager.getInstance().playBackground("/Audio/Winner.mp3", 0.5);
                endGame(true);
            } else if (verified[1]) {
                AudioManager.getInstance().playBackground("/Audio/Winner.mp3", 0.5);
                endGame(true);
            } else {
                loadNextQuestion();
                // FIX: Re-enable clicks for next round
                for (Button btn : answerButtons) btn.setMouseTransparent(false);
            }
        });
        return correctDelay;
    }


    //sets background music volume to 0 for friend call
    @FXML
    private void lifeline1() {
        AudioManager.getInstance().setBackgroundVolume(0.0);
        lifeline1.setVisible(false); // effectively disable lifeline
        System.out.println("TRIGGERED LIFELINE!!");
        newGame.callAFriend();
    }

    // Displays graph for the audience vote
    @FXML private void lifeline2() {
        lifeline2.setVisible(false); // effectively disable lifeline
        System.out.println("TRIGGERED LIFELINE!!");
        int[] results = newGame.audienceVote();
        for (int result : results) System.out.println(result);
        AudienceChart.showGraph(results);
    }

    // sets the two wrong options from newGame to be invisible
    @FXML private void lifeline3() {
        System.out.println("TRIGGERED LIFELINE!!");
        lifeline3.setVisible(false);
        int[] results = newGame.fiftyFifty();
        for (int result : results) answerButtons[result].setVisible(false);
    }

    // prepares to hijack handleAnswer for Landmine lifeline
    @FXML private void lifeline4() {
        lifeline4Status = true;
        lifeline4.setVisible(false);
    }

    // If answer is correct from newGame.landMine(), set to green. otherwise, red
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

    // triggers endgame sequence. If won, sets the endgame screen to show winning. losing game otherwise
    // also checks if have fortfieted. if yes and got answer correct, gets total money gathered. Otherwise, only get the money from safe haven
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

    // updates the ladder (very astetik)
    public void updateLadder(int roundsWon) { int currentIndex = roundsWon - 1;
        for (int i = 0; i < ladderBoxes.size(); i++) {
            javafx.scene.shape.Rectangle box = ladderBoxes.get(i);
            if (i < currentIndex) {
                box.setVisible(true);
                box.setFill(Color.rgb(50, 205, 50, 0.5));
            } else if (i == currentIndex) {
                box.setVisible(true);
                box.setFill(Color.ORANGE);
                box.setOpacity(1.0);
            } else {
                box.setVisible(false);
            }
        }
    }
}