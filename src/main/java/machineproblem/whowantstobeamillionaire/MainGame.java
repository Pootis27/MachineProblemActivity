package machineproblem.whowantstobeamillionaire;

public class MainGame {
    final int MAX_ROUND = 5;        //set to 1 for easier testing of game end and game complete
    private int correct_answer = 4;
    public int score = 0;
    public int round = 1;

    public String[] setupQuestion() {
        String[] current_setup = QuestionSetup.generateQuestion();
        correct_answer = Integer.parseInt(current_setup[5]);

        // The returned array now has 6 elements: Q, A, B, C, D, ROUND
        String[] trimmed = new String[6];
        System.arraycopy(current_setup, 0, trimmed, 0, 5);

        trimmed[5] = "Round " + round + " / " + MAX_ROUND;

        return trimmed;
    }



    public boolean[] answerChecker(int choice) {
        boolean[] verified = {false,false};
        if (choice == correct_answer) {
            verified[0] = true;
            round++;
        }
        if(round > MAX_ROUND) {
            verified[1] = true;
        }
        return verified;
    }

    public void gameCompleted() {
        //TODO: whatever the fuck to do with game complete.
        System.out.println("congrats i guess");
    }

    public void gameOver() {
        //TODO: whatever the fuck to do with game over.
        System.out.println("ya lose lmao");
    }

    public void addScore() {
        //TODO: score logic
    }


}
