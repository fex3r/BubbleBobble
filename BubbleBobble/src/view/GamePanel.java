package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
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
import model.LevelManager;
import model.Player;
import model.ProfileManager;
import model.Shot;
import model.TornadoDemon;
import model.TornadoShot;
import model.WiewData;

public class GamePanel extends JPanel
{	
	private static GamePanel gamePanelInstance;
	private JPanel pointsPanel;
	private Font font;
	
	private GamePanel() 
	{
		this.setLayout(null);
		
		this.setPreferredSize(new Dimension(WiewData.GAME_SCREEN_WIDTH.getValue(),WiewData.GAME_SCREEM_HEIGHT.getValue()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		this.setIgnoreRepaint(true);
		getAssets();
		
		pointsPanel = new PointsPanel();
		pointsPanel.setBackground(Color.black);
		pointsPanel.setBounds(0,0,WiewData.GAME_SCREEN_WIDTH.getValue(),WiewData.TILE_SIZE.getValue());
		pointsPanel.setOpaque(true);
		pointsPanel.setIgnoreRepaint(true);
		
		this.add(pointsPanel);
		this.addKeyListener(KeyHandler.getInstance());
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
		
		
		
	}

	
	public static GamePanel getInstance() 
	{
		if(gamePanelInstance == null) gamePanelInstance = new GamePanel();
		return gamePanelInstance;
	}
	private void getAssets() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/BubblegumSans-Regular.ttf")).deriveFont(30f);
		} catch (IOException | FontFormatException e) {
			
			e.printStackTrace();
		}
	}
	
	private class PointsPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
  
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            
            
            g2.setFont(font);
    		g2.setColor(Color.MAGENTA);
    		g2.drawString("Profile name:",30,30);
    		g2.drawString("Points:", 450, 30);
    		g2.drawString("Current level:",700,30);
    		
    		g2.setColor(Color.white);
    		g2.drawString(ProfileManager.getInstance().getCurrentProfileName(), 200, 30);
    		g2.drawString(Integer.toString(ProfileManager.getInstance().getCurrentPoints()), 550, 30);
    		g2.drawString(Integer.toString(LevelManager.getInstance().getCurrentLevel()), 870, 30);
    		
    		
        }
    }
	
	@Override
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		//Stampa mappa
		for (int i = 1; i<WiewData.MAX_SCREEN_ROW.getValue(); i++) //Asse X
		{
			for(int j = 0; j<WiewData.MAX_SCREEN_COL.getValue(); j++) //Asse Y
			{
				int x = GameMap.getInstance().getValue(i,j);
				
				g2.drawImage(GameMap.getInstance().getBlock(x), j*WiewData.TILE_SIZE.getValue(), i*WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(), null);
			
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
			
			for(int t = 0; t<TornadoShot.getTornadoShots().size(); t++) {
				TornadoShot.getTornadoShots().get(t).draw(g2);
			}
		}
		
		if(KeyHandler.getInstance().getpause()) {
			GameEngine.getInstance().setGameState(3);
		}
		
		Player.getInstance().draw(g2);	//Stampa del personaggio

		pointsPanel.repaint();
		g2.dispose();
	}
}
