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
{ 
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
	 * @return l'istanza di GameMap, altrimenti ne viene creata una
	 */
	public static GameMap getInstance() 
	{
		if(gameMapInstance == null) gameMapInstance = new GameMap();
		return gameMapInstance;
	}
	
	/**
	 * Trascrive la mappa dal file, a una matrice
	 */
	public void initializeGameMap()
	{
		//Creazione matrice
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
		
		//Scrittura matrice
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
	 * Definisce la mappa che verrà mostrata all'utente
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
	 * Inizializza le immagini dei blocchi
	 */
	public void initializeBlocks()
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
	 * @param x indice del blocco da restituire
	 * @return Il blocco all'indice x nella lista dei blocchi
	 */
	public BufferedImage getBlock(int x) { return blocks[x]; } 
	
	/**
	 * @param x riferimento asse x del valore sulla mappa
	 * @param y riferimento asse y del valore sulla mappa
	 * @return il valore della mappa nelle coordinate (x,y)
	 */
	public int getValue(int x, int y) { return map[x][y]; }
	
	/**
	 * @return l'indice utilizzato per calcolare la mappa ch verrà mostrata all'utente
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
