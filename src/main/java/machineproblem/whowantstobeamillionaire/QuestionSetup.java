package machineproblem.whowantstobeamillionaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class QuestionSetup {

    private static final List<String[]> questions = new ArrayList<>();
    private static final List<String[]> unusedQuestions = new ArrayList<>(); // for tracking unused
    private static final List<String> names = new ArrayList<>();
    private static final List<String> dates = new ArrayList<>();
    private static final List<String> objects = new ArrayList<>();
    private static final List<String> places= new ArrayList<>();
    private static final Random rand = new Random();

    static {
        loadCSVQuestions();
        loadCategoryCSV("names.csv", names);
        loadCategoryCSV("dates.csv", dates);
        loadCategoryCSV("objects.csv", objects);
        loadCategoryCSV("places.csv", places);
        resetQuestions(); // initially populate unusedQuestions
    }

    private static void loadCSVQuestions() {
        try (InputStream in = QuestionSetup.class.getResourceAsStream("questions.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            if (in == null) {
                System.err.println("CSV FILE NOT FOUND YOU DUM DUM");
                return;
            }

            String line;
            boolean skipHeader = true;
            while ((line = br.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }
                String[] row = line.split(",");
                questions.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCategoryCSV(String fileName, List<String> targetList) {
        try (InputStream in = QuestionSetup.class.getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            if (in == null) {
                System.err.println(fileName + " NOT FOUND!");
                return;
            }

            String line;
            boolean skipHeader = true;
            while ((line = br.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }
                targetList.add(line.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reset unused questions back to full list
    public static void resetQuestions() {
        unusedQuestions.clear();
        unusedQuestions.addAll(questions);
    }

    public static String[] generateQuestion() {
        if (unusedQuestions.isEmpty()) {
            System.err.println("ALL QUESTIONS USED! RESETTING...");
            resetQuestions();
        }

        // Pick a random question from unused questions
        int index = rand.nextInt(unusedQuestions.size());
        String[] picked = unusedQuestions.remove(index); // remove to avoid repeat

        String questionText = picked[0];
        String category = picked[1];
        String correctAnswer = picked[2];

        // Get the appropriate category list
        List<String> categoryList;
        switch (category.toLowerCase()) {
            case "name" -> categoryList = names;
            case "date" -> categoryList = dates;
            case "object" -> categoryList = objects;
            case "place" -> categoryList = places;
            default -> {
                System.err.println("Unknown category: " + category);
                categoryList = new ArrayList<>();
            }
        }

        // Prepare 4 options
        String[] options = new String[4];
        int correctIndex = rand.nextInt(4);
        options[correctIndex] = correctAnswer;

        // Pick 3 wrong options
        int added = 0;
        while (added < 3 && !categoryList.isEmpty()) {
            String candidate = categoryList.get(rand.nextInt(categoryList.size()));
            boolean duplicate = false;
            for (String opt : options) {
                if (candidate.equals(opt)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                for (int i = 0; i < 4; i++) {
                    if (options[i] == null) {
                        options[i] = candidate;
                        break;
                    }
                }
                added++;
            }
        }

        return new String[]{
                questionText,
                options[0],
                options[1],
                options[2],
                options[3],
                String.valueOf(correctIndex)
        };
    }
}
