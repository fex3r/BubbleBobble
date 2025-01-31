package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import control.GameEngine;
import control.KeyHandler;
import model.WiewData;

public class GameMenu extends JPanel {
	
	private static GameMenu gameMenuInstance;
	private String newGame = "New Game";
	private String loadGame = "Load Game";
	private String quit = "Quit";
	private BufferedImage title,bubble;
	private int frames = 0;
	private Font font;
	private int commandId = 0;
	private Color currentColor = new Color(255, 255, 0);
	private Color[] colors = {
		        new Color(255, 255, 0),     
		        new Color(255, 215, 0),      
		        new Color(255, 255, 102),
		        new Color(255, 255, 51),
		        new Color(204, 204, 0),
		        new Color(244, 164, 96)
		    };
	 
	private GameMenu() {
		this.setPreferredSize(new Dimension(WiewData.GAME_SCREEN_WIDTH.getValue(),WiewData.GAME_SCREEM_HEIGHT.getValue()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		this.setIgnoreRepaint(true);
		
		this.addKeyListener(KeyHandler.getInstance());
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
		
		getAssets();
	}
	
	public static GameMenu getInstance() 
	{
		if(gameMenuInstance == null) gameMenuInstance = new GameMenu();
		return gameMenuInstance;
	}
	
	private void getAssets() {
		try {
			title = ImageIO.read(getClass().getResourceAsStream("/sprites/misc/image_21.png"));
			bubble = ImageIO.read(getClass().getResourceAsStream("/sprites/misc/image_263.png"));
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/BubblegumSans-Regular.ttf")).deriveFont(50f);
		} catch (IOException | FontFormatException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		

		frames++;
		if(frames > 5) {
			  currentColor = Arrays.stream(colors)
					.skip(new Random().nextInt(colors.length))
					.findFirst()
					.orElse(colors[0]);
			frames = 0;
		}
		
		for (int x = 0; x < title.getWidth(); x++) {
            for (int y = 0; y < title.getHeight(); y++) {
                int pixel = title.getRGB(x, y);
                
                Color color = new Color(pixel);
                
                if (color.getRed() > 200 && color.getGreen() > 200 && color.getBlue() < 100) {
                	title.setRGB(x, y, currentColor.getRGB());
                	
                }else { title.setRGB(x, y, pixel); }
            }
		}
		
		g2.drawImage(title, 270,50,400,400,null);
		
		
		g2.setFont(font);
		g2.setColor(Color.MAGENTA);
		g2.drawString(newGame, 350, 540);
		g2.drawString(loadGame, 350, 590);
		g2.drawString(quit, 350, 640);
		
		if(KeyHandler.getInstance().isDown()) {
			if(commandId == 2)commandId = 0;
			else commandId++;
			KeyHandler.getInstance().setDown(false);
		}
		
		if(KeyHandler.getInstance().isUp()) {
			if(commandId == 0) commandId = 2;
			else commandId --;
			KeyHandler.getInstance().setUp(false);
		}
		
		if(commandId == 0) {
			g2.drawImage(bubble, 310,510,35,35,null);
		}else if(commandId == 1) {
			g2.drawImage(bubble, 310,560,35,35,null);
		}else if(commandId == 2) {
			g2.drawImage(bubble, 310,610,35,35,null);
		}
		
		if(KeyHandler.getInstance().getEnter()) {
			if(commandId == 0) {
				GameEngine.getInstance().setNewGameOn(true);
				GameEngine.getInstance().setGameState(2);
			}else if(commandId == 1){
				GameEngine.getInstance().setNewGameOn(false);
				GameEngine.getInstance().setGameState(1);
			}else if(commandId == 2) {
				System.exit(0);
			}
		}
		g2.dispose();
	}
}
