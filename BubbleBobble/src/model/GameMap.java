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
		try
		{
			blocks[1] = ImageIO.read(getClass().getResourceAsStream("/blocks/normal blocks/block_1.png"));
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		
		
		
		
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
			BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream));
			for(int i = 0; i<rows; i++)
			{
				for(int j = 0; j<cols; j++)
				{
					map[i][j] = Character.getNumericValue(br2.read());
				}
				br2.readLine();
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}
	}
		
		

	
	public static BufferedImage getBlock(int x) { return blocks[x]; }
	//static File file = new File("/resources/GameMap.txt");
	
	//static InputStream file = GameMap.class.getResourceAsStream("/resources/GameMap.txt");
	
	
	
	
	
		
	
	
	
	
	
	
	
	//private static BufferedReader br = new BufferedReader(new FileReader("/resources/GameMap.txt"));
	
	
	/*
	private static int[][] map;
	{
		int rows = 0;
		int cols = 0;
		try
		{
			BufferedReader br = this.getBufferedReader();
			String line;
			while((line = br.readLine()) != null) 
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
			BufferedReader br2 = this.getBufferedReader(); //Ne creo uno nuovo perché non è possibile riazzerarlo
			for(int i = 0; i<rows; i++)
			{
				for(int j = 0; j<cols; j++)
				{
					map[i][j] = br2.read();
				}
				br2.readLine();
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		
	}
	*/
	
	
	
	
	static private BufferedReader getBufferedReader()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("/resources/GameMap.txt"));
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		return br;
	}
	/*
	private static BufferedReader br;
	{
		try
		{
			br = new BufferedReader(new FileReader("/resources/GameMap.txt"));
		}
		catch (IOException e)
		{
			e.getMessage();
		}
	}
	
	public static int getValue(int x, int y)
	{
		try
		{
			for(int i = 0; i<y; i++)
			{
				if(br.ready() == true)
				{
					br.readLine();
				}
			}
			return Integer.parseInt(String.valueOf(br.readLine().charAt(x)));
		}
		catch(IOException e)
		{
			return 0;
		}
	}
 	*/
	
	/*
	public static int getValue(int x, int y)
	{
		try
		{
			Scanner scanner = new Scanner(file);
			for(int i = 0; i<y; i++) scanner.next();
			return Integer.parseInt(scanner.next().split("")[x]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	*/
	
	public static int getValue(int x, int y)
	{
		return map[x][y];
	}
	
}
