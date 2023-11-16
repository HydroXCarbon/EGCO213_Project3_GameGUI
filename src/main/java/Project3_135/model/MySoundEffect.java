package Project3_135.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MySoundEffect {
    private Clip clip;
    private FloatControl gainControl;

    public MySoundEffect(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void playLoop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void setVolume(float gain) {
        if (gain < 0.0f) gain = 0.0f;
        if (gain > 1.0f) gain = 1.0f;
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}
