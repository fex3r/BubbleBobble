package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import control.GameEngine;

public class ProfileManager {
	
	private  String currentProfileName;
	private int profilePoints;
	private int currentPoints;
	private int profileWins;
	private int	idProfile;
	private static ProfileManager profileManagerInstance;
	private Pattern regexProfileName = Pattern.compile("^[A-Za-z]+$");
	private Matcher matcher;
	
	/**
	 * Costruttore privato di ProfileManager
	 */
	private ProfileManager() {};
	
	/**
	 * @return l'istanza di ProfileManager, altrimenti ne crea una
	 */
	public static ProfileManager getInstance() {
		if(profileManagerInstance == null) profileManagerInstance = new ProfileManager();
		return profileManagerInstance;
	}
	
	/**
	 * @return il punteggio corrente del profilo
	 */
	public int getCurrentPoints() {return currentPoints;}
	/**
	 * @return il nome corrente del profilo
	 */
	public String getCurrentProfileName() {return currentProfileName;}
	
	/**
	 * Salva i dati all'interno del file contenente tutti i dati
	 * @return true se il salvataggio ha avuto successo
	 */
	public boolean save() {
		String lineToChange = String.format("%s %d %d %d %d%n", currentProfileName,profilePoints,profileWins,LevelManager.getInstance().getCurrentLevel(),idProfile);
		File data = new File("resources/saves/data.txt");
		List<String> lines = null;
		if(!data.exists()) {
			JOptionPane.showMessageDialog(null,"Mi dispiace non sono presenti salvataggi","Salvataggi non trovati",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(data))){
			
			lines = reader.lines().collect(Collectors.toList());
			
			for(String line : lines) {
				String existingProfileId = line.split(" ")[4];
				if(existingProfileId.equals(idProfile)) {
					 lines.set(Integer.parseInt(existingProfileId), lineToChange);
					 break;
				}
			}
		}catch (IOException e) {
	       e.printStackTrace();
	    }
		
		try {
			Files.write(Paths.get("resources/saves/data.txt"),lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null,"dati salvati correttamente","dati salvati",JOptionPane.PLAIN_MESSAGE);
		return true;
		
	
	}
	
	/**
	 * Carica il profilo in base al nome inserito nel box
	 * @return true se il caricamento è andato a buon fine
	 */
	public boolean loadProfile() {
		File data = new File("resources/saves/data.txt");
		if(!data.exists()) {
			JOptionPane.showMessageDialog(null,"Mi dispiace non sono presenti salvataggi","Salvataggi non trovati",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		currentProfileName = JOptionPane.showInputDialog(null, "Inserisci il nome del profilo:");
		
		if(currentProfileName == null) {
			JOptionPane.showMessageDialog(null,"Devi inserire un nome profilo per giocare!!","Nome vuoto",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		matcher = regexProfileName.matcher(currentProfileName);
		
		if(!matcher.matches()) {
			JOptionPane.showMessageDialog(null,"Mi dispiace il nome non è valido, prova ad inserire solo caratteri alfabetici senza spazi !!","Nome non valido",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		currentProfileName.toLowerCase();
		String auxProfilePoints = null;
		String auxProfileWins = null;
		String auxCurrentLevel = null;
		String auxIdProfile = null;
		boolean somethingFound = false;
		try(BufferedReader reader = new BufferedReader(new FileReader(data))){
			
			List<String> lines = reader.lines().collect(Collectors.toList());
			
			for(String line : lines) {
				String existingProfile = line.split(" ")[0];
				if(currentProfileName.equals(existingProfile)) {
					somethingFound = true;
					auxProfilePoints = line.split(" ")[1];
					auxProfileWins = line.split(" ")[2];
					auxCurrentLevel = line.split(" ")[3];
					auxIdProfile = line.split(" ")[4];
					return true;
					 
				}
			}
		}catch (IOException e) {
	       e.printStackTrace();
	    }
		
		if(!somethingFound) {
			JOptionPane.showMessageDialog(null,"Mi dispiace non sono presenti salvataggi con questo nome profilo","Salvataggi non trovati",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		profilePoints = Integer.parseInt(auxProfilePoints);
		profileWins = Integer.parseInt(auxProfileWins);
		LevelManager.getInstance().setCurrentLevel(Integer.parseInt(auxCurrentLevel));
		idProfile = Integer.parseInt(auxIdProfile);
		return true;
	}
	
	/**
	 * Permette la creazione di un nuovo profilo inserendo un nome non presente nel file con tutte le informazioni
	 * @return true se la creazione del profilo ha avuto successo
	 */
	public boolean newProfile() {
		
		boolean error = true;
		File data = new File("resources/saves/data.txt");
		
		if(!data.exists()) {
            try {
				data.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	
			currentProfileName = JOptionPane.showInputDialog(null, "Inserisci il nome del profilo:");
			
			if(currentProfileName == null) {
				JOptionPane.showMessageDialog(null,"Devi inserire un nome profilo per giocare!!","Nome vuoto",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			matcher = regexProfileName.matcher(currentProfileName);
			
			if(!matcher.matches()) {
				JOptionPane.showMessageDialog(null,"Mi dispiace il nome non è valido, prova ad inserire solo caratteri alfabetici senza spazi !!","Nome non valido",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			currentProfileName.toLowerCase();
			try(BufferedReader reader = new BufferedReader(new FileReader(data))){
				
				List<String> lines = reader.lines().collect(Collectors.toList());
				idProfile = lines.size()+1;
				
				for(String line : lines) {
					String existingProfile = line.split(" ")[0];
					if(currentProfileName.equals(existingProfile)) {
						 JOptionPane.showMessageDialog(null, "Il profile è già esistente, prova ad inserire un nome diverso", "Errore", JOptionPane.ERROR_MESSAGE);
						 return false;
					}
				}
			}catch (IOException e) {
		       e.printStackTrace();
		    }
			
			
		if(currentProfileName != null) {
			try (FileWriter writer = new FileWriter(data, true)) {
		        writer.write(currentProfileName +" "+"0"+" "+"0"+" "+"0"+" "+idProfile+"\n");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return true;
	}
	
}
