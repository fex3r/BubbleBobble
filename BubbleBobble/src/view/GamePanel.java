package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import control.GameEngine;
import control.KeyHandler;
import model.Enemy;
import model.GameMap;
import model.Player;
import model.Shot;
import model.TornadoDemon;
import model.WiewData;

public class GamePanel extends JPanel
{	
	private static GamePanel gamePanelInstance;
	
	private GamePanel() 
	{
		this.setPreferredSize(new Dimension(WiewData.GAME_SCREEN_WIDTH.getValue(),WiewData.GAME_SCREEM_HEIGHT.getValue()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		this.setIgnoreRepaint(true);
		
		
		this.addKeyListener(KeyHandler.getInstance());
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
	}

	
	public static GamePanel getInstance() 
	{
		if(gamePanelInstance == null) gamePanelInstance = new GamePanel();
		return gamePanelInstance;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		//Stampa mappa
		for (int i = 0; i<WiewData.MAX_SCREEN_ROW.getValue(); i++) //Asse X
		{
			for(int j = 0; j<WiewData.MAX_SCREEN_COL.getValue(); j++) //Asse Y
			{
				int x = GameMap.getInstance().getValue(i,j);
				switch(x)
				{
					case 0:
						g2.setColor(Color.WHITE);
						break;
					case 1:
						g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
						break;
					case 2:
						g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
						break;
					case 3:
						g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
						break;
					case 4:
						g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
						break;
					case 9:
						g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
						break;
					default:
						g2.setColor(Color.BLACK);
				}
			}
		}
		
		if(!GameEngine.getInstance().getGamePause()) {
			//stampa dei proiettili
			for(int i = 0; i<Shot.getShots().size(); i++)
			{
				Shot.getShots().get(i).draw(g2);
			}
			
			//stampa dei nemici
			for(int j = 0; j<Enemy.getEnemies().size(); j++) {
				Enemy.getEnemies().get(j).draw(g2);
			}
		}
		
		
		Player.getInstance().draw(g2);	//Stampa del personaggio

		
		g2.dispose();
	}
}
