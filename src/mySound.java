import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class mySound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public mySound() {

        soundURL[0] = getClass().getResource("/sound/card.wav");
        soundURL[1] = getClass().getResource("/sound/rip.wav");
        soundURL[2] = getClass().getResource("/sound/shuffling.wav");

    }

    public void setFile (int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void play() {
        clip.start();
    }

}
