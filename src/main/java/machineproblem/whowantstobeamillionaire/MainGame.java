package machineproblem.whowantstobeamillionaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainGame {
    // Adjustable Game Logic
    private static final double CALL_A_FRIEND_CORRECT_CHANCE = 0.7;  // Chance that friend is right
    private static final int[] SAFE_HAVENS = {4,9};                 // safe havens of the game (still need to update ui for this one if planning to change)
    private static final int AUDIENCE_SIZE = 100;                   // Amount of audience voting
    private static final double AUDIENCE_CORRECT_CHANCE = 0.4;      // chance that an audience member picks the right choice
    final int MAX_ROUND = 15;        // Max round. still have to update a couple of things since both ladder, score, and safe haven depend on this

    // Standard 15-round prize money
    private final int[] PRIZE_VALUES = {
            100, 200, 300, 500, 1000,       // Q1-Q5
            2000, 4000, 8000, 16000, 32000, // Q6-Q10
            64000, 125000, 250000, 500000, 1000000 // Q11-Q15
    };

    // Helpful values to know
    private int correct_answer;  // 0 -> A, 1 -> B, 2 -> C, 3 -> D
    public int score;
    public int round;
    private final Random rand = new Random();
    private final QuestionSetup questionSetup = new QuestionSetup();

    // Constructor for Main Game
    public MainGame() {
        this.round = 1;
        this.score = 0;
        this.correct_answer = -1; // invalid until a question is set
    }

    // UI shouldn't have access to this but out of time
    public int getCorrectAnswer() {
        return correct_answer;
    }

    /**
     * Calls class that handles generating questions to generate question for the UI to use
     * Also sets up the correct answer for the current round
     * @return a list of strings in the form ["question", "option1", "option2", "option3", "option4"]
     */
    public String[] setupQuestion() {
        String[] current_setup = questionSetup.generateQuestion();
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

    /**
     * Checks if the choice made is correct and also checks if the game has reached maximum round
     * @param choice from the UI which will be checked.
     * @return list of boolean in the form [isCorrect?, hasGameEnded?]
     */
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

    /**
     * A friend answers which has a CALL_A_FRIEND_CORRECT_CHANCE% chance of being correct
     * Also calls upon the CallAFriendAudio class to play audio simulating calling a friend based on the answer friend chooses
     */
    public void callAFriend() {
        int answerToPlay;
        if (rand.nextDouble() < CALL_A_FRIEND_CORRECT_CHANCE) {
            answerToPlay = correct_answer;
        } else {
            int pick = rand.nextInt(3); // 0,1,2
            if (pick >= correct_answer) pick++;
            answerToPlay = pick;
        }

        // play audio and resume background when done
        CallAFriendAudio.playAudio(answerToPlay, () -> {
            AudioManager.getInstance().setBackgroundVolume(0.5);
        });
    }

    /**
     * simulates 100 audience answering each having AUDIENCE_CORRECT_CHANCE% chance of being correct
     * @return list of int that tells the UI how many audience member voted for the option
     */
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

    /**
     * Handles the logic for hiding two wrong options. really just here to avoid UI hiding the correct optoin
     * @return the options that we want to hide. To be handled by UI
     */
    public int[] fiftyFifty() {
        List<Integer> wrongOptions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != correct_answer) wrongOptions.add(i);
        }
        Collections.shuffle(wrongOptions, rand);
        return new int[]{wrongOptions.get(0), wrongOptions.get(1)};
    }


    /**
     * Boolean that just tells us if the choice is wrong or right without triggering score and ending game
     * @param choice from Ui, to be checked here
     * @return boolean if choice is correct or wrong
     */
    public boolean landMine(int choice) {
        return choice == correct_answer;
    }

    /**
     * handles keeping track of the score of the player
     */
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
