package control;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * La classe {@code AudioManager} si occupa della gestione dell'audio nel gioco.
 * Implementa il pattern Singleton per garantire che esista un'unica istanza di {@code AudioManager}.
 *
 */
public class AudioManager {

	private static AudioManager instance;
	private Clip clip;

	/**
     * Restituisce l'istanza statica di AudioManager.
     * 
     * 
     * @return l'istanza unica del AudioManager.
     */
	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}
	
	private AudioManager() {

	}

	/**
	 * Riproduce il file audio assegnato.
	 * Il metodo carica e riproduce il file audio passato, eseguendolo in base allo scopo richiesto:
	 * 
	 * @param filename Il percorso del file audio da riprodurre.
	 * @param loop Se {@code true}, il file audio viene riprodotto in loop; altrimenti viene riprodotto una sola volta.
	 */
	public void play(String filename, boolean loop) {

		try {
			
			InputStream in = getClass().getResourceAsStream(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			if (loop) {
				instance.stop();
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				clip.start();
			} else {
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Interrompe la riproduzione dell'audio corrente
	 */
	public void stop() {
		if (clip != null) {
			clip.stop();
			clip.close();
			clip = null;
		}
	}
}