package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import control.CollisionChecker;
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
	
	public void setDefaultValues()
	{
		x = 100;
		y = 100;
		speed = 4;
		direction = Directions.LEFT;
		oldDirection = Directions.LEFT;
		hitBox = new Rectangle();
		hitBox.x = 11;
		hitBox.y = 5;
		hitBox.width = 32;
		hitBox.height = 38;
		
	}
	public void getPlayerImage() 
	{
		try 
		{	
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
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static class Builder //Builder pattern
	{
		//Duplicazione attributi
		private String name;
		private KeyHandler kh;
		
		
		public Builder() { }
		
		//Setter
		public Builder setKeyHandler(KeyHandler kh) { this.kh = kh; return this; }
		public Builder setName(String name) { this.name = name; return this; }
		
		//Metodo build finale
		public Player build() 
		{ 
			Player p = Player.getInstance();
			p.kh = this.kh;
			p.name = this.name;
			return p;
		}
	}
	
	

	@Override
	public void update(Observable o, Object arg) 
	{
		hitBoxOn = false;
		fallOn = true;
		CollisionChecker.checkCollision(Player.getInstance());
		if(kh.isUp())
		{
			jump();
		}
		else if(kh.isDown()) y = y + speed;
		
		else if(kh.isLeft()) 
		{
			direction = Directions.LEFT;
			CollisionChecker.checkCollision(Player.getInstance());
			if( Player.getInstance().hitBoxOn == false) x = x-speed;	
		}
		else if(kh.isRight()) 
		{	
			direction = Directions.RIGHT;
			CollisionChecker.checkCollision(Player.getInstance());
			if( Player.getInstance().hitBoxOn == false) x = x+speed;
		}
		else
		{
			direction = Directions.STAND;	
		}
		
		
		if(Player.getInstance().fallOn == true) {
			
			y = y+speed;
		}
		
		/*
		if(hitBoxOn == false) {
			if(kh.isLeft()) {  x = x - speed;}
			else if(kh.isRight()) { x = x + speed;}
			else if(kh.isUp()) y = y - speed;
			else if(kh.isDown()) y = y + speed;
		}
		*/
		spriteCounter ++;
		if(spriteCounter > 20) 
		{
			if(spriteNum == 1) spriteNum = 2;
			else if(spriteNum == 2) 
			{
				if(direction == Directions.STAND) spriteNum = 1;
				else spriteNum = 3;
			}
			else if(spriteNum == 3) 
			{
				spriteNum = 1;	
			}
			spriteCounter = 0;
		}
		
		if(kh.isShooting())
		{
			this.shot();
		}
		
	}
	
	public void shot()
	{
		new Shot(setShotPosition(), this.getY(), setShotDirection());	//Creando l'oggetto, viene ricreato di nuovo nel costruttore e va in loop
	}
	
	private Directions setShotDirection()
	{
		Directions direction;
		switch(this.direction)
		{
		case LEFT:
			return Directions.LEFT;
		case RIGHT:
			return Directions.RIGHT;
		case STAND:
			if(this.oldDirection.equals(Directions.LEFT)) return Directions.LEFT;
		}
		return Directions.RIGHT;
	}
	
	private int setShotPosition()
	{
		int position = 0;
		switch(this.direction)
		{
		case LEFT:
			position = this.getX() - WiewData.TILE_SIZE.getValue(); 
			break;
		case RIGHT:
			position = this.getX() + WiewData.TILE_SIZE.getValue();
			break;
		case STAND:
			if(this.oldDirection.equals(Directions.LEFT))
			{
				position = this.getX() - WiewData.TILE_SIZE.getValue();
			}
			else
			{
				position = this.getX() + WiewData.TILE_SIZE.getValue();
			}
		}
		return position;
	}
	
	public void draw(Graphics2D g2) 
	{	
		BufferedImage image = null;
		
		switch(direction) {
		case LEFT:
			if(spriteNum == 1) image = moveL1;
			else if(spriteNum == 2) image = moveL2;
			else if(spriteNum == 3) image = moveL3;
			oldDirection = Directions.LEFT;
			break;
			
		case RIGHT:
			if(spriteNum == 1) image = moveR1;
			else if(spriteNum == 2) image = moveR2;
			else if(spriteNum == 3) image = moveR3;
			oldDirection = Directions.RIGHT;
			break;
			
		case STAND:
			if(oldDirection == Directions.RIGHT) 
			{
				if(spriteNum == 1) image = standR1;
				else image = standR2;
			}
			else if(oldDirection == Directions.LEFT) 
			{
				if(spriteNum == 1) image = standL1;
				else image = standL2;
			}
		}
		

		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
	}
}
