package machineproblem.whowantstobeamillionaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionSetup {

    private static final List<String[]> questions = new ArrayList<>();
    private static final Random rand = new Random();

    static {
        loadCSV();
    }

    private static void loadCSV() {
        try {
            InputStream in = QuestionSetup.class.getResourceAsStream("questions.csv");
            if (in == null) {
                System.err.println("CSV FILE NOT FOUND YOU DUM DUM");
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; } // skip header
                String[] row = line.split(",");
                questions.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] generateQuestion() {
        if (questions.isEmpty()) {
            System.err.println("NO QUESTIONS LOADED YOU ABSOLUTE MUFFIN");
            return new String[]{"ERROR", "ERR", "ERR", "ERR", "ERR", "1"};
        }

        // Return exactly like your previous format:
        // [question, A, B, C, D, correct_index]
        return questions.get(rand.nextInt(questions.size()));
    }
}
