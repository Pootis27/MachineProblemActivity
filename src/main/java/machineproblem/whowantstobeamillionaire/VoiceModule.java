package machineproblem.whowantstobeamillionaire;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VoiceModule {

    public static void generateVoiceAndPlay(String text) {
        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
        );
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        Voice[] voicelist = VoiceManager.getInstance().getVoices();

        if (voice != null) {
            voice.allocate();
            boolean status = voice.speak(text);
            voice.deallocate();
        } else {

        }
    }
}
