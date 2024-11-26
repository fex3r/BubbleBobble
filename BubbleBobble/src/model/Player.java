package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import control.KeyHandler; 
import view.GamePanel;

@SuppressWarnings("deprecation")
public final class Player extends Entity implements Observer
{
	private String name;
	private KeyHandler kh;
	private GamePanel gp;
	private static Player playerInstance;
	
	// costruttore privato Pattern Singletone
	private Player() {
		setDefaultValues();
		getPlayerImage();
	}
	
	// Singleton pattern
	public static Player getInstance() 
	{
		if(playerInstance == null) playerInstance = new Player();
		return playerInstance;
	}
	
	public void setDefaultValues(){
		x = 100;
		y = 100;
		speed = 4;
		direction = Directions.LEFT;
		oldDirection = Directions.LEFT;
	}
	public void getPlayerImage() {
		try {
			
			standR1 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/fermo_d_1.png"));
			standR2 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/fermo_d_2.png"));
			standL1 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/fermo_s_1.png"));
			standL2 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/fermo_s_2.png"));
			moveR1 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_d_1.png"));
			moveR2 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_d_2.png"));
			moveR3 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_d_3.png"));
			moveL1 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_s_1.png"));
			moveL2 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_s_2.png"));
			moveL3 = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/muovi_s_3.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static class Builder //Builder pattern
	{
		//Duplicazione attributi
		private String name;
		private KeyHandler kh;
		private GamePanel gp;
		
		
		public Builder() { }
		
		//Setter
		public Builder setKeyHandler(KeyHandler kh) { this.kh = kh; return this; }
		public Builder setGamePanel(GamePanel gp) { this.gp = gp; return this; }
		public Builder setName(String name) { this.name = name; return this; }
		
		//Metodo build finale
		public Player build() 
		{ 
			Player p = Player.getInstance();
			p.kh = this.kh;
			p.gp = this.gp;
			p.name = this.name;
			return p; 
		}
	}
	
	

	@Override
	public void update(Observable o, Object arg) 
	{
		if(kh.isUp()) y = y - speed;
		else if(kh.isDown()) y = y + speed;
		
			
		if(kh.isLeft()) {
			
			x = x - speed;
			direction = Directions.LEFT;
			
		}else if(kh.isRight()) {
			
			x = x + speed;
			direction = Directions.RIGHT;
			
		}else{
			
			direction = Directions.STAND;
			
			}
			
		spriteCounter ++;
		if(spriteCounter > 20) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
				
			else if(spriteNum == 2) {
				if(direction == Directions.STAND) {
					spriteNum = 1;
				}else{
					spriteNum = 3;
					}
			}
			else if(spriteNum == 3) {
				
				spriteNum = 1;
				
			}
				spriteCounter = 0;
			}
		
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case LEFT:
			if(spriteNum == 1) {
				image = moveL1;
			}
			else if(spriteNum == 2) {
				image = moveL2;
			}
			else if(spriteNum == 3) {
				image = moveL3;
			}
			oldDirection = Directions.LEFT;
			break;
			
		case RIGHT:
			if(spriteNum == 1) {
				image = moveR1;
			}
			else if(spriteNum == 2) {
				image = moveR2;
			}
			else if(spriteNum == 3) {
				image = moveR3;
			}
			oldDirection = Directions.RIGHT;
			break;
			
		case STAND:
			if(oldDirection == Directions.RIGHT) {
				if(spriteNum == 1) {
					image = standR1;
				}else {
					image = standR2;
				}
			}else if(oldDirection == Directions.LEFT) {
				if(spriteNum == 1) {
					image = standL1;
				}else {
					image = standL2;
				}
			}
		}
		

		
		g2.drawImage(image,x, y, gp.tileSize, gp.tileSize,null);
	}
}
