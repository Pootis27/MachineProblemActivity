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

    public String lifeline1() {
        //TODO: Call a friend logic. We know the correct answer in this class. Get that and then have like a 70% chance of the friend being right.
        //TODO: make sure also to keep track of when it has been used in the game or not
        //NOTE: better to not change the name so UI guy doesn't have to update what needs to be called
        return "";
    }

    public int[] lifeline2() {
        //TODO: Audience vote logic. We can do like 100 runs of random generation where the correct answer has like 40% of being picked while the others have lik 20% of being picked
        //TODO: make sure also to keep track of when it has been used in the game or not
        return new int[] {40,20,20,20};
    }

    public int[] lifeline3() {
        //TODO: 50/50. just return int to symbolize the options that we want to hide. make sure not to hide the answer (this class should know it)
        return new int[] {50,50};
    }

    public void lifeline4() {
        //TODO: good luck
    }

    public int ladder() {
        //TODO: score logic
        //Returns an int for both ladder and keeping track of score too!
        return 300;
    }



}
