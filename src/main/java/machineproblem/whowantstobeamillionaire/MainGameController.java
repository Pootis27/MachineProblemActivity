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
    @FXML protected Button lifeline1;
    @FXML protected Button lifeline2;
    @FXML protected Button lifeline3;
    @FXML protected Button lifeline4;

    private Button[] buttons;

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
            buttons[result].setVisible(false);
        }
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
        buttons = new Button[]{buttonA, buttonB, buttonC, buttonD};
    }

    public void questionSetup() {
        String[] question_setup = newGame.setupQuestion();
        roundCounter.setText(question_setup[5]);
        questionLabel.setText(question_setup[0]);
        buttonA.setText(question_setup[1]);
        buttonB.setText(question_setup[2]);
        buttonC.setText(question_setup[3]);
        buttonD.setText(question_setup[4]);
        for (Button btn : buttons) {
            btn.setVisible(true);
        }
    }

    private void handleAnswer(int answerNumber) {
        boolean[] verified = newGame.answerChecker(answerNumber);

        if (verified[0]) {
            System.out.println("Correct! Current Prize: $" + newGame.score);
            if (verified[1]) {
                newGame.gameCompleted();
            } else {
                questionSetup();
            }
        } else { //calls guaranteed prize money for gameover
            newGame.gameOver();
            int checkpointMoney = newGame.getGuaranteedPrize();
            System.out.println("WRONG! You go home with: $" + checkpointMoney);
            newGame.gameOver();
        }
    }

}
