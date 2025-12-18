package machineproblem.whowantstobeamillionaire;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CallAFriendAudio {
    static String[] voice_list = {"Vedal", "Gman", "Woman", "Miku"};
    static Map<String, String[]> voice_dict = new HashMap<>();

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
    public static void playAudio(int correct_answer) {
        Random rand = new Random();
        String chosen_character = voice_list[rand.nextInt(voice_list.length)];
        String questionPath = "/voice/" + chosen_character + "/" + voice_dict.get(chosen_character)[4];
        String answerPath   = "/voice/" + chosen_character + "/" + voice_dict.get(chosen_character)[correct_answer];
        playSound(questionPath);
        playSound(answerPath);
    }

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
