package model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Questa classe rappresenta e gestisce la mappa di gioco
 */
public class GameMap
{ 							//0 - Vuoto     1 - Muro mappa uno		2 - Muro mappa due ect. ect.da
	private static GameMap gameMapInstance;
	public static BufferedImage[] blocks = new BufferedImage[10];
	public static int[][] bigMap;
	public static int[][] map = new int[17][20];
	public int indexValidMap = 0;	//+19
	public static List<Integer> solidBlocks = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
	public static List<Integer> endBlock = new ArrayList<>(List.of(9));
	
	
	//Costruttore privato per pattern Singleton
	private GameMap()
	{
		this.initializeBlocks();
		this.initializeGameMap();
		
	}
	
	/**
	 * Restituisce l'istanza di GameMap, se non vi è viene creata
	 * @return istanza di GameMap
	 */
	public static GameMap getInstance() 
	{
		if(gameMapInstance == null) gameMapInstance = new GameMap();
		return gameMapInstance;
	}
	
	/**
	 * Inizializza la mappa da file, quando viene creata l'istanza di GameMap
	 */
	public void initializeGameMap()
	{
		int rows = 0;
		int cols = 0;
		InputStream inputStream = getClass().getResourceAsStream("/GameMap.txt");
		try
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			
			while((line = br1.readLine()) != null)
			{
				rows++; 
				cols = line.length();
			}
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		bigMap = new int[rows][cols];
		
		try
		{
			inputStream = getClass().getResourceAsStream("/GameMap.txt");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream));
			for(int i = 0; i<rows; i++)
			{
				String row = br2.readLine();
				for(int j = 0; j<cols; j++)
				{
					bigMap[i][j] = Character.getNumericValue(row.charAt(j));
				}
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		
		getValidMap();
	}
	
	/**
	 * In base al valore di indexValidMap, trascrive su map la mappa che verrà visualizzata dall'utente
	 */
	private void getValidMap()
	{
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j<map[0].length; j++)
			{
				map[i][j] = bigMap[i + indexValidMap][j];
			}
		}
	}
	
	/**
	 * Assegna le immagini alle correte variabili
	 */
	public void initializeBlocks() //Istanzia la matrice di blocchi
	{
		try
		{
			blocks[1] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_1.png"));
			blocks[2] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_10.png"));
			blocks[3] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_94.png"));
			blocks[4] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_70.png"));
			blocks[5] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_38.png"));
			blocks[6] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_100.png"));
			blocks[7] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_85.png"));
			blocks[8] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_55.png"));
			blocks[9] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_15.png"));
		}
		catch(IOException e)
		{
			e.getMessage();
		}
	}
	
	/**
	 * Restituisce un blocco dalla lista di blocchui
	 * @param x indice del blocco da restituire
	 * @return blocco
	 */
	public BufferedImage getBlock(int x) { return blocks[x]; } 
	
	/**
	 * Restituisce il valore della mappa
	 * @param x riferimento asse x del valore sulla mappa
	 * @param y riferimento asse y del valore sulla mappa
	 * @return il valore della mappa
	 */
	public int getValue(int x, int y) { return map[x][y]; }
	
	/**
	 * Restituisce l'indice da cui parte la validMap, ovvero la mappa che viene visualizzata dall'utente
	 * @return indice di validMap
	 */
	public int getIndexValidMap() { return indexValidMap; }
	
	/**
	 * Assegna un nuovo valore a indexValidMap
	 * ovvero l'indice da cui inizia la mappa che verrà visualizzata dall'utente
	 * @param x indice da settare 
	 */
	public void setIndexValidMap(int x) { indexValidMap = x;}
	
	/**
	 * Incrementa il valore di indexValidMap
	 * Ovvero l'indice da cui inizia la mappa che verrà visualizzata dall'utente
	 */
	public void increaseIndexValidMap() 
	{ 
		indexValidMap++; 
		initializeGameMap();
	}

}
