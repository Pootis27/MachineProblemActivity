package machineproblem.whowantstobeamillionaire;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static AudioManager instance;
    private final Map<String, AudioClip> clips = new HashMap<>();
    private MediaPlayer backgroundPlayer;


    private AudioManager() {}

    private void loadClip(String key, String path, double volume) {
        URL resource = getClass().getResource(path);
        if (resource == null) {
            System.err.println("AudioManager ERROR: Could not find audio file: " + path);
            return; // skip loading
        }
        AudioClip clip = new AudioClip(resource.toExternalForm());
        clip.setVolume(volume);
        clips.put(key, clip);
    }
    // Auto load all clips first so no more file searching for .wav (different for mp3)
    private void loadAllClips() {
        loadClip("Transition1", "/audio/Transition1.wav", 0.3);
        loadClip("DrumRoll", "/audio/DrumRoll.wav", 0.3);
        loadClip("Correct", "/audio/Correct.wav", 0.3);
        loadClip("Wrong", "/audio/Wrong.wav", 0.3);
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
            instance.loadAllClips();
        }
        return instance;
    }

    public void playClip(String key) {
        AudioClip clip = clips.get(key);
        if (clip != null) {
            clip.play();
        }  else {
            System.err.println("AudioManager WARNING: Clip not found for key: " + key);
        }
    }
    public void stopClip(String key) {
        AudioClip clip = clips.get(key);
        if (clip != null) {
            clip.stop();
        } else {
            System.err.println("AudioManager WARNING: Clip not found for key: " + key);
        }
    }

    public void playBackground(String path, double volume) {
        stopBackground();

        URL resource = getClass().getResource(path);
        if (resource == null) {
            System.err.println("AudioManager ERROR: Could not find background music: " + path);
            return;
        }

        Media media = new Media(resource.toExternalForm());
        backgroundPlayer = new MediaPlayer(media);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
        backgroundPlayer.setVolume(volume);
        backgroundPlayer.play();
    }

    public void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }

    public void pauseBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.pause();
        }
    }

    public void resumeBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.play();
        }
    }
}
