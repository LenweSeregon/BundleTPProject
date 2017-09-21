package view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread {
	private Clip audioClip;
	private AudioInputStream audioStream;

	/***
	 * Constructeur de Sound
	 * 
	 * @param name
	 *            nom du fichier du son
	 */
	public Audio(String name) {
		init(name);
	}

	/***
	 * méthode principale de la classe, une fois lancé, execute le son
	 * enregistré, une fois !
	 */
	public void run() {
		try {
			audioClip.open(audioStream);
			audioClip.start();
			audioClip.close();

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/***
	 * initialise la classe en chargant le son
	 * 
	 * @param fileName
	 *            nom du fichier du son
	 */
	public void init(String fileName) {
		InputStream ips = getClass().getResourceAsStream(fileName);

		try {
			URL url = Audio.class.getResource(fileName);
			audioStream = AudioSystem.getAudioInputStream(url);
			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
