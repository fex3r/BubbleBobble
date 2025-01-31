package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import control.KeyHandler;
import model.WiewData;

public class PauseMenu extends JPanel{
	
	private static PauseMenu pauseMenuInstance;
	private String pause = "PAUSED";
	private String resume = "resume";
	private String save = "save";
	private String quit = "quit";
	private Font font;
	private BufferedImage bubble;
	private PauseMenu() {
		this.setPreferredSize(new Dimension(WiewData.GAME_SCREEN_WIDTH.getValue(),WiewData.GAME_SCREEM_HEIGHT.getValue()));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //Migliora la qualità video
		this.setIgnoreRepaint(true);
		
		this.addKeyListener(KeyHandler.getInstance());
		this.setFocusable(true); //Permette di ricevere eventi da tastiera
		
		getAssets();
	}
	
	public static PauseMenu getInstance() {
		if(pauseMenuInstance == null) pauseMenuInstance = new PauseMenu();
		return pauseMenuInstance;
	}
	
	private void getAssets() {
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/BubblegumSans-Regular.ttf")).deriveFont(100f);
			bubble = ImageIO.read(getClass().getResourceAsStream("/sprites/misc/image_263.png"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setFont(font);
		
		//disegno la scritta paused
		g2.setColor(Color.WHITE);
		g2.drawString(pause, 360, 100);
		
		font = font.deriveFont(50f);
		
		g2.setColor(Color.MAGENTA);
		g2.drawString(resume,375,360);
		g2.drawString(save, 375, 430);
		g2.drawString(quit, 375,500);
		
		
		
	}
}
