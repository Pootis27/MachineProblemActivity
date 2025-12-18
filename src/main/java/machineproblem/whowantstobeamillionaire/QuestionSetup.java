package machineproblem.whowantstobeamillionaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionSetup {

    public QuestionSetup() {
        loadQuestions();
        loadCategories();
        resetQuestions();
    }


    private final List<String[]> questions = new ArrayList<>();
    private final List<String[]> unusedQuestions = new ArrayList<>();

    private static final List<String> names = new ArrayList<>();
    private static final List<String> dates = new ArrayList<>();
    private static final List<String> objects = new ArrayList<>();
    private static final List<String> places = new ArrayList<>();

    private static final Random rand = new Random();

    /* -------------------- DATA SETUP -------------------- */

    private void loadQuestions() {
        questions.add(new String[]{
                "Who discovered America?", "Name", "Christopher Columbus"
        });
        questions.add(new String[]{
                "When did World War II end?", "Date", "1945"
        });
        questions.add(new String[]{
                "Where is the Eiffel Tower?", "Place", "Paris"
        });
        questions.add(new String[]{
                "Who wrote Hamlet?", "Name", "William Shakespeare"
        });
        questions.add(new String[]{
                "When was the Declaration of Independence signed?", "Date", "1776"
        });
        questions.add(new String[]{
                "Where is the Great Wall of China?", "Place", "China"
        });
        questions.add(new String[]{
                "Who painted the Mona Lisa?", "Name", "Leonardo da Vinci"
        });
        questions.add(new String[]{
                "When was the first moon landing?", "Date", "1969"
        });
        questions.add(new String[]{
                "Where is the Colosseum?", "Place", "Rome"
        });
        questions.add(new String[]{
                "Who invented the telephone?", "Name", "Alexander Graham Bell"
        });
    }

    private static void loadCategories() {
        names.addAll(List.of(
                "Christopher Columbus",
                "William Shakespeare",
                "Napoleon Bonaparte",
                "Albert Einstein",
                "Cleopatra",
                "Wolfgang Amadeus Mozart",
                "Nikola Tesla",
                "Marie Curie",
                "Abraham Lincoln",
                "Galileo Galilei",
                "Leonardo da Vinci",
                "Isaac Newton",
                "Genghis Khan",
                "Ludwig van Beethoven",
                "Ernest Hemingway",
                "J.R.R. Tolkien",
                "Winston Churchill",
                "Nelson Mandela",
                "Socrates"
        ));

        dates.addAll(List.of(
                "1776", "1912", "1945", "1969", "1983", "1989", "2007"
        ));

        objects.addAll(List.of(
                "Telephone", "Lightbulb", "Printing Press",
                "Steam Engine", "Computer", "Television",
                "Radio", "Airplane", "Wheel",
                "Microscope", "Telescope", "Camera",
                "Compass", "Vaccine", "Internet",
                "Clock", "Electric Motor", "Refrigerator",
                "Bicycle", "Typewriter"
        ));

        places.addAll(List.of(
                "Paris", "China", "Rome", "India",
                "New York", "Pisa", "Greece",
                "England", "Japan", "Brazil",
                "Australia", "Mexico"
        ));
    }

    /* -------------------- GAME LOGIC -------------------- */

    public void resetQuestions() {
        unusedQuestions.clear();
        unusedQuestions.addAll(questions);
    }

    public String[] generateQuestion() {
        if (unusedQuestions.isEmpty()) {
            resetQuestions();
        }

        int index = rand.nextInt(unusedQuestions.size());
        String[] picked = unusedQuestions.remove(index);

        String questionText = picked[0];
        String category = picked[1];
        String correctAnswer = picked[2];

        List<String> categoryList = switch (category.toLowerCase()) {
            case "name" -> names;
            case "date" -> dates;
            case "object" -> objects;
            case "place" -> places;
            default -> new ArrayList<>();
        };

        String[] options = new String[4];
        int correctIndex = rand.nextInt(4);
        options[correctIndex] = correctAnswer;

        int added = 0;
        while (added < 3) {
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
