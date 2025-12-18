package machineproblem.whowantstobeamillionaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainGame {
    private static final double CALL_A_FRIEND_CORRECT_CHANCE = 0.7;
    private static final int[] SAFE_HAVENS = {4,9};
    private static final int AUDIENCE_SIZE = 100;
    private static final double AUDIENCE_CORRECT_CHANCE = 0.4;
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

    public int getCorrectAnswer() {
        return correct_answer;
    }


    public String[] setupQuestion() {
        String[] current_setup = QuestionSetup.generateQuestion();
        correct_answer = Integer.parseInt(current_setup[5]);
        return new String[]{
                current_setup[0],
                current_setup[1],
                current_setup[2],
                current_setup[3],
                current_setup[4],
                "Round " + round + " / " + MAX_ROUND
        };
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

    public void callAFriend() {
        // 70% chance to return the correct answer
        if (rand.nextDouble() < CALL_A_FRIEND_CORRECT_CHANCE) {
            CallAFriendAudio.playAudio(correct_answer);
        }

        // 30% chance: pick one of the 3 wrong answers
        int pick = rand.nextInt(3); // 0, 1, or 2

        // Shift index to skip correct_answer
        if (pick >= correct_answer) {
            pick++;
        }
        CallAFriendAudio.playAudio(pick);
    }


    public int[] audienceVote() {
        int[] counts = new int[4]; // 4 answer options

        for (int i = 0; i < AUDIENCE_SIZE; i++) {
            if (rand.nextDouble() < AUDIENCE_CORRECT_CHANCE) {
                counts[correct_answer]++;
            } else {
                int pick;
                do {
                    pick = rand.nextInt(4); // pick a random wrong answer
                } while (pick == correct_answer);
                counts[pick]++;
            }
        }

        return counts;
    }

    public int[] fiftyFifty() {
        List<Integer> wrongOptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != correct_answer) wrongOptions.add(i);
        }
        Collections.shuffle(wrongOptions, rand);
        return new int[]{wrongOptions.get(0), wrongOptions.get(1)};
    }


    public boolean landMine(int answer) {
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
        for (int i = SAFE_HAVENS.length - 1; i >= 0; i--) {
            int roundIndex = SAFE_HAVENS[i];
            if (score >= PRIZE_VALUES[roundIndex]) return PRIZE_VALUES[roundIndex];
        }
        return 0;
    }



}
