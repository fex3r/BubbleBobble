package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import model.GameMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import control.KeyHandler;
import model.Player;

public class GamePanel extends JPanel
{	
	final int startingTileSize = 16; 
	final int scale = 3;
	
	final int tileSize = startingTileSize*scale;
	final int maxScreenCol = 20;
	final int maxScreenRow = 16;
	final int gameScreenWidth = maxScreenCol*tileSize;
	final int gameScreenHeight = maxScreenRow*tileSize;
	GameMap map = new GameMap();
	
	
	KeyHandler kh = new KeyHandler(); //Gestione degli input da tastiera

	public GamePanel() 
	{
		this.setPreferredSize(new Dimension(gameScreenWidth,gameScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		
		this.addKeyListener(kh);
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
		
		Player p = new Player.Builder().setGamePanel(this).setKeyHandler(kh).setName("prova").build();
		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// da cambiare con gli sprite
		//g2.setColor(Color.WHITE);
		
		//Stampa mappa
		for (int i = 0; i<maxScreenRow; i++) //Asse X
		{
			for(int j = 0; j<maxScreenCol; j++) //Asse Y
			{
				int x = map.getValue(i,j);
				switch(x)
				{
					case 0:
						g2.setColor(Color.WHITE);
						break;
					case 1:
						g2.drawImage(map.getBlock(x), j*tileSize, i*tileSize, tileSize, tileSize, null);
						break;
					default:
						g2.setColor(Color.BLACK);
				}
				//g2.fillRect(j*tileSize, i*tileSize, tileSize, tileSize);
			}
		}
		
		
		// da cambiare con gli sprite (Personaggio)
		g2.setColor(Color.BLUE);
		g2.fillRect(Player.getInstance().getX(), Player.getInstance().getY(), tileSize, tileSize);
		
		
		g2.dispose();
	}
}
