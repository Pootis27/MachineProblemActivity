package machineproblem.whowantstobeamillionaire;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CallAFriendAudio {
    static String[] voice_list = {"Vedal", "Gman", "Woman", "Miku"};  // list of voices available
    static Map<String, String[]> voice_dict = new HashMap<>();

    // maps the entries from voice_list to their corresponding audio files
    static {
        voice_dict.put("Vedal", new String[]{
                "Vedal A.wav",
                "Vedal B.wav",
                "Vedal C.wav",
                "Vedal D.wav",
                "Vedal Question.wav"
        });

        voice_dict.put("Gman", new String[]{
                "Gman A.wav",
                "Gman B.wav",
                "Gman C.wav",
                "Gman D.wav",
                "Gman Question.wav"
        });

        voice_dict.put("Woman", new String[]{
                "Woman A.wav",
                "Woman B.wav",
                "Woman C.wav",
                "Woman D.wav",
                "Woman Question.wav"
        });

        voice_dict.put("Miku", new String[]{
                "Miku A.wav",
                "Miku B.wav",
                "Miku C.wav",
                "Miku D.wav",
                "Miku Question.wav"
        });

    }

    /**
     * Main thing called when callAFriend lifeline is triggered.
     * Also randomly chooses the character that will speak
     * On a new thread since playSound() is blocking
     * @param answerIndex the guess of our friend
     * @param onFinished just an object returned to indicate a method has finished
     */
    public static void playAudio(int answerIndex, Runnable onFinished) {
        new Thread(() -> {
            Random rand = new Random();
            String chosen_character = voice_list[rand.nextInt(voice_list.length)];
            String[] audios = voice_dict.get(chosen_character);

            String questionPath = "/voice/" + chosen_character + "/" + audios[4];
            String answerPath   = "/voice/" + chosen_character + "/" + audios[answerIndex];

            playSound(questionPath);
            playSound(answerPath);

            // callback on JavaFX thread
            if (onFinished != null) {
                javafx.application.Platform.runLater(onFinished);
            }
        }).start();
    }


    /**
     * Handles finding the sound files amd actually playing them
     * @param resourcePath the path of the wav file starting from src/main/resources/
     */
    public static void playSound(String resourcePath) {
        // resourcePath must start with '/', e.g. "/voice/Miku/Miku A.wav"
        URL url = CallAFriendAudio.class.getResource(resourcePath);
        if (url == null) {
            System.err.println("Resource not found: " + resourcePath);
            return;
        }

        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(url)) {
            Clip clip = AudioSystem.getClip();
            final Object lock = new Object();

            //ngl, thanks here chatgpt
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    synchronized (lock) { lock.notify(); }
                }
            });

            clip.open(audioIn);
            clip.start();

            // wait until playback finishes
            synchronized (lock) {
                try { lock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }

            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
