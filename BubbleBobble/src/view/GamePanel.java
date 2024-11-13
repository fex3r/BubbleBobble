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
	
	
	KeyHandler kh = new KeyHandler(); //Gestione degli input da tastiera

	public GamePanel() 
	{
		this.setPreferredSize(new Dimension(gameScreenWidth,gameScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		
		this.addKeyListener(kh);
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
		
		Player p = new Player.Builder().setGamePanel(this).setKeyHandler(kh).setName("Provetta").build();
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		BufferedImage block1 = null;
		
		try 
		{
            block1 = ImageIO.read(new File("C:/Users/matteo/Desktop/Progetto bubble bobble/BubbleBobble/resources/blocks/normal blocks/block_1.png"));
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
		
		
		//J'ho fatto sicuramente qualche impiccio co gli indici ma così funziona, pe sicurezza controlla
		//Stampa mappa
		
		for (int i = 0; i<GameMap.getMap()[0].length; i++) //Asse X
		{
			for(int j = 0; j<GameMap.getMap().length; j++) //Asse Y
			{
				int x = GameMap.getValue(i,j);
				switch(x)
				{
					case 0:
						g2.setColor(Color.WHITE);
						break;
					case 1:
						g2.drawImage(block1, i*tileSize, j*tileSize, tileSize, tileSize, null);
						break;
					default:
						g2.setColor(Color.BLACK);
				}
				g2.fillRect(j*tileSize, i*tileSize, tileSize, tileSize);
			}
		}
		
		
		// da cambiare con gli sprite (Personaggio)
		g2.setColor(Color.BLUE);
		g2.fillRect(Player.getInstance().getX(), Player.getInstance().getY(), tileSize, tileSize);
		
		
		g2.dispose();
	}
}
