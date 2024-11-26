package model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class GameMap
{ 							//0 - Vuoto     1 - Muro mappa uno		2 - Muro mappa due ect. ect.
	
	public static BufferedImage[] blocks = new BufferedImage[2];
	public static int[][] map;
	
	public GameMap()
	{
		this.initializeBlocks();
		this.initializeGameMap();
		for(int i = 0; i<map.length; i++)
		{
			for(int j = 0; j<map[0].length; j++)
			{
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	
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
		map = new int[rows][cols];
		
		
		try
		{
			inputStream = getClass().getResourceAsStream("/GameMap.txt");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream));
			for(int i = 0; i<rows; i++)
			{
				String row = br2.readLine();
				for(int j = 0; j<cols; j++)
				{
					map[i][j] = Character.getNumericValue(row.charAt(j));
				}
			}
			/*
			for(int i = 0; i<rows; i++)
			{
				for(int j = 0; j<cols; j++)
				{
					map[i][j] = Character.getNumericValue(br2.read());
				}
				br2.readLine();
			}
			*/
		}
		catch (IOException e)
		{
			e.getMessage();
		}
	}
	
		
		
	public void initializeBlocks() //Istanzia la matrice di blocchi
	{
		try
		{
			blocks[1] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_1.png"));
		}
		catch(IOException e)
		{
			e.getMessage();
		}
	}
	
	public static BufferedImage getBlock(int x) { return blocks[x]; } //Restituisce un blocco
	
	public static int getValue(int x, int y) { return map[x][y]; }

}
