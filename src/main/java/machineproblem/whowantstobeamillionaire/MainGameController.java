package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainGameController {

    private MainGame newGame;

    @FXML protected Label questionLabel;
    @FXML protected Label roundCounter;
    @FXML protected Button buttonA;
    @FXML protected Button buttonB;
    @FXML protected Button buttonC;
    @FXML protected Button buttonD;

    @FXML
    protected void answerA() {
        handleAnswer(1);
    }

    @FXML
    protected void answerB() {
        handleAnswer(2);
    }

    @FXML
    protected void answerC() {
        handleAnswer(3);
    }

    @FXML
    protected void answerD() {
        handleAnswer(4);
    }

    @FXML
    private void lifeline1() {
        System.out.println("TRIGGERED LIFELINE!!");
        //TODO: Call a friend. You can use TTS or just a pop up box. Call newGame.lifeline1(). Implement the method inside the MainGame class. Then handle the ui stuff here
    }

    @FXML
    private void lifeline2() {
        System.out.println("TRIGGERED LIFELINE!!");
        //TODO: Audience vote. Show a graph or whatever representation you deem good enough. Call newGame.lifeline2(). Implement the method inside MainGame class then handle ui stuff here
    }

    @FXML
    private void lifeline3() {
        System.out.println("TRIGGERED LIFELINE!!");
        //TODO: 50/50. hide or color two wrong option. Call newGame.lifeline3(). Implement the method inside MainGame class then handle ui stuff here.
    }

    @FXML
    private void lifeline4() {
        System.out.println("TRIGGERED LIFELINE!!");
        //TODO: idk think of another lifeline lmao
    }

    @FXML
    public void initialize(){
        newGame = new MainGame();
        String[] question_setup = newGame.setupQuestion();
        roundCounter.setText(question_setup[5]);
        questionLabel.setText(question_setup[0]);
        buttonA.setText(question_setup[1]);
        buttonB.setText(question_setup[2]);
        buttonC.setText(question_setup[3]);
        buttonD.setText(question_setup[4]);
    }

    public void questionSetup() {
        String[] question_setup = newGame.setupQuestion();
        roundCounter.setText(question_setup[5]);
        questionLabel.setText(question_setup[0]);
        buttonA.setText(question_setup[1]);
        buttonB.setText(question_setup[2]);
        buttonC.setText(question_setup[3]);
        buttonD.setText(question_setup[4]);
    }

    private void handleAnswer(int answerNumber) {
        boolean[] verified = newGame.answerChecker(answerNumber);

        if (verified[0]) {
            if (verified[1]) {
                newGame.gameCompleted();
            } else {

                questionSetup();
            }
        } else {
            newGame.gameOver();
        }
    }

}
