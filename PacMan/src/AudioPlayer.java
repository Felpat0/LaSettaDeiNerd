import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {
	Clip clip;
	public AudioPlayer(){
		
	}
	
	public void playAudio(String path, Boolean loop){
			
				try {
					BufferedInputStream audio = new BufferedInputStream(this.getClass().getResourceAsStream(path));
					 AudioInputStream audioInput = AudioSystem.getAudioInputStream(audio);
					try {
						clip = AudioSystem.getClip();
						clip.open(audioInput);
						clip.start();
						if(loop)
							clip.loop(Clip.LOOP_CONTINUOUSLY);
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

}
