package machineproblem.whowantstobeamillionaire;
import java.util.Random;

public class MainGame {
    // Standard 15-round prize money
    private final int[] PRIZE_VALUES = {
            100, 200, 300, 500, 1000,       // Q1-Q5
            2000, 4000, 8000, 16000, 32000, // Q6-Q10
            64000, 125000, 250000, 500000, 1000000 // Q11-Q15
    };
    final int MAX_ROUND = 15;        //set to 1 for easier testing of game end and game complete
    private int correct_answer;
    private final int trials = 100; //for lifeline1
    public int score;
    public int round;
    private final Random rand = new Random();


    public MainGame() {
        this.round = 1;
        this.score = 0;
        this.correct_answer = -1; // invalid until a question is set
    }


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
            updateScore();
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

    public int lifeline1() {
        //TODO: Call a friend logic. We know the correct answer in this class. Get that and then have like a 70% chance of the friend being right.
        //TODO: make sure also to keep track of when it has been used in the game or not
        //NOTE: better to not change the name so UI guy doesn't have to update what needs to be called

        // 70% chance to return the correct answer
        if (rand.nextDouble() < 0.7) {
            return correct_answer;
        }

        // 30% chance: pick one of the 3 wrong answers
        int pick = rand.nextInt(3); // 0, 1, or 2

        // Shift index to skip correct_answer
        if (pick >= correct_answer) {
            pick++;
        }
        return pick;
    }


    public int[] lifeline2() {
        int[] counts = new int[4];

        for (int i = 0; i < trials; i++) {
            double r = rand.nextDouble();

            if (r < 0.4) {
                counts[correct_answer]++;
            } else {
                int pick;
                do {
                    pick = rand.nextInt(4);
                } while (pick == correct_answer);

                counts[pick]++;
            }
        }
        return counts;
    }

    public int[] lifeline3() {
        // Create array of wrong options only
        int[] wrongOptions = new int[3];
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            if (i != correct_answer) {
                wrongOptions[idx++] = i;
            }
        }

        Random rand = new Random();

        // Pick first wrong option
        int firstIdx = rand.nextInt(wrongOptions.length);
        int first = wrongOptions[firstIdx];

        // Remove the first pick from the array
        int[] remaining = new int[wrongOptions.length - 1];
        int j = 0;
        for (int i = 0; i < wrongOptions.length; i++) {
            if (i != firstIdx) {
                remaining[j++] = wrongOptions[i];
            }
        }

        // Pick second wrong option from remaining
        int secondIdx = rand.nextInt(remaining.length);
        int second = remaining[secondIdx];

        return new int[]{first, second};
    }

    public boolean lifeline4(int answer) {
        return answer == correct_answer;
    }

    public void updateScore() {
        //  -2 is done because round is set to next question. (ex. if round1 win, 'round' = 2,
        // but we need index 0 for round1 prize money
        int completedRoundIndex = round - 2;

        if (completedRoundIndex >= 0 && completedRoundIndex < PRIZE_VALUES.length) {
            this.score = PRIZE_VALUES[completedRoundIndex];
        } else if (completedRoundIndex >= PRIZE_VALUES.length) {
            // case if round 15 has just been won
            this.score = PRIZE_VALUES[PRIZE_VALUES.length - 1];
        } else {
            // start of game
            this.score = 0;
        }
    }

    public int getGuaranteedPrize() {
        // Ensure the prize array exists
        if (PRIZE_VALUES == null || PRIZE_VALUES.length < 10) {
            return 0;
        }

        // Define Safe Haven amounts
        // Index 4 = Round 5 ($1,000)
        // Index 9 = Round 10 ($32,000)
        int safeHaven1 = PRIZE_VALUES[4];
        int safeHaven2 = PRIZE_VALUES[9];

        // Logic: check which safe haven the player has passed
        if (score >= safeHaven2) {
            return safeHaven2; // Guaranteed $32,000
        } else if (score >= safeHaven1) {
            return safeHaven1; // Guaranteed $1,000
        } else {
            return 0; // Haven't reached the first safe haven yet
        }
    }


}
