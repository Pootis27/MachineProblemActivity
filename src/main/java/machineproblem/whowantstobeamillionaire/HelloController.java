package machineproblem.whowantstobeamillionaire;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {

    private MainGame newGame;

    @FXML protected Label questionLabel;
    @FXML protected Label roundCounter;
    @FXML protected Button buttonA;
    @FXML protected Button buttonB;
    @FXML protected Button buttonC;
    @FXML protected Button buttonD;


    @FXML
    protected void answerA() {
        boolean[] verified = newGame.answerChecker(1);
        if(verified[0]) {
            if(verified[1]) {
                newGame.gameCompleted();
            }
            else {
                questionSetup();
            }
        }
        else {
            newGame.gameOver();
        }
    }

    @FXML
    protected void answerB() {
        boolean[] verified = newGame.answerChecker(2);
        if(verified[0]) {
            if(verified[1]) {
                newGame.gameCompleted();
            }
            else {
                questionSetup();
            }
        }
        else {
            newGame.gameOver();
        }
    }

    @FXML
    protected void answerC() {
        boolean[] verified = newGame.answerChecker(3);
        if(verified[0]) {
            if(verified[1]) {
                newGame.gameCompleted();
            }
            else {
                questionSetup();
            }
        }
        else {
            newGame.gameOver();
        }
    }

    @FXML
    protected void answerD() {
        boolean[] verified = newGame.answerChecker(4);
        if(verified[0]) {
            if(verified[1]) {
                newGame.gameCompleted();
            }
            else {
                questionSetup();
            }
        }
        else {
            newGame.gameOver();
        }
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
}
