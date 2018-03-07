import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	private Clip clip;
	private File sound;
	
	public Audio(String file) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		sound = new File(file);
		clip = AudioSystem.getClip();
		AudioInputStream inpStream = AudioSystem.getAudioInputStream(sound);
		clip.open(inpStream);
	}
	
	public void play(){
		try{
			clip.start();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}

		try {
			clip = AudioSystem.getClip();
			AudioInputStream inpStream = AudioSystem.getAudioInputStream(sound);
			clip.open(inpStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
