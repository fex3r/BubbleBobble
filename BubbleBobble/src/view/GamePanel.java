package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import control.KeyHandler;
import model.Player;

public class GamePanel extends JPanel
{	
	final int startingTileSize = 16; 
	final int scale = 3;
	
	final int tileSize = startingTileSize*scale;
	final int maxScreenCol = 24;
	final int maxScreenRow = 20;
	final int gameScreenWidth = maxScreenCol*tileSize;
	final int gameScreenHeight = maxScreenRow*tileSize;
	
	
	KeyHandler kh = new KeyHandler();

	public GamePanel() 
	{
		this.setPreferredSize(new Dimension(gameScreenWidth,gameScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 
		
		this.addKeyListener(kh);
		this.setFocusable(true);
	}
	
	
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// da cambiare con gli sprite
		g2.setColor(Color.WHITE);
		
		if(kh.isUp()) Player.setY(Player.getY() - Player.getSpeed());
		
		else if(kh.isDown()) Player.setY(Player.getY() + Player.getSpeed());
		
		else if(kh.isLeft()) Player.setX(Player.getX() - Player.getSpeed()); 
		
		else if(kh.isRight()) Player.setX(Player.getX() + Player.getSpeed());
		
		g2.fillRect(Player.getX(), Player.getY(), tileSize, tileSize);
		g2.dispose();
	}	
}
