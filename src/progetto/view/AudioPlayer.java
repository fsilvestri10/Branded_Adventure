package progetto.view;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/** VIEW: riproduzione della musica (è output verso l'utente, come la grafica). */
public class AudioPlayer {

    private Clip clip;

    /** Fa partire la traccia /nome.wav presa dalla cartella resources. */
    public void play(String track) {
        this.stop();
        try {
            URL url = AudioPlayer.class.getResource("/" + track + ".wav");
            if (url == null) {
                System.err.println("Traccia audio non trovata: " + track);
                return;
            }
            try (AudioInputStream stream = AudioSystem.getAudioInputStream(url)) {
                this.clip = AudioSystem.getClip();
                this.clip.open(stream);
            }
            // per abbassare un po' il volume
            FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15);
            this.clip.start();
        } catch (Exception e) {
            // senza audio il gioco deve funzionare lo stesso
            e.printStackTrace();
            this.clip = null;
        }
    }

    public void stop() {
        if (this.clip != null) {
            this.clip.stop();
            this.clip.close();
            this.clip = null;
        }
    }
}
