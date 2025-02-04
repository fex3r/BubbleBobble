package model;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;
import control.KeyHandler; 
import view.GamePanel;

/**
 * Questa classe rappresenta il giocatore, comandato dall'utente.
 */

@SuppressWarnings("deprecation")
public final class Player extends Entity
{
	private static Player playerInstance;
	private boolean jump;
	private boolean spriteSparo = false;
	private int jumpValue = 0;
	protected BufferedImage standR1,standR2,standL1,standL2,moveR1,moveR2,moveR3,moveL1,moveL2,moveL3,sparoS,sparoD;
	
	// costruttore privato Pattern Singleton
	private Player() 
	{
		setDefaultValues();
		getPlayerImage();
	}
	
	/**
	 * Restituisce l'istanza di Player se già creata, altrimenti la crea.
	 * @return istanza di Player
	 */
	public static Player getInstance() 
	{
		if(playerInstance == null) playerInstance = new Player();
		return playerInstance;
	}
	
	/**
	 * Definisce un nuovo valore al parametro jump
	 * @param x valora da settare
	 */
	public void setJump(boolean x) { jump = x; }
	
	/**
	 * Restituisce il valore di jump
	 * @return valore di jump
	 */
	public boolean getJump() { return jump; }
	
	/**
	 * Assegna dei valori all'istanza di Player nel momento in cui viene creato
	 */
	public void setDefaultValues()
	{
		x = 100;
		y = 100;
		speed = 4;
		direction = Directions.LEFT;
		oldDirection = Directions.LEFT;
		hitBoxDefaultX = 11;
		hitBoxDefaultY = 5;
		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
		
	}
	/**
	 * Definisce le immagini che verranno utilizzate per rappresentare il Player
	 */
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
			sparoS = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/sparoS.png"));
			sparoD = ImageIO.read(getClass().getResourceAsStream("/sprites/bubblun/protagonista/sparoD.png"));
			shotImage = ImageIO.read(getClass().getResourceAsStream("/sprites/misc/image_271.png"));
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}	

	@Override
	public void update(Observable o, Object arg) 
	{
		if(GameEngine.getInstance().getGameState() == 2) //Esegue solamente se siamo in game state
		{
			hitBoxOn = false;
			fallOn = true;
			
			//Controllo delle collisioni
			if(!this.jump)
			{
				CollisionChecker.checkCollision(Player.getInstance());
				CollisionChecker.checkFall(Player.getInstance());
			}
			
			//Salto
			if(KeyHandler.getInstance().isUp())
			{
				
				Player.getInstance().setJump(true);
			}
			
			//Spostamento a sinistra
			else if(KeyHandler.getInstance().isLeft()) 
			{
				direction = Directions.LEFT;
				CollisionChecker.checkCollision(Player.getInstance());
				if( Player.getInstance().hitBoxOn == false) x = x-speed;	
			}
			
			//Spostamento a destra
			else if(KeyHandler.getInstance().isRight()) 
			{	
				direction = Directions.RIGHT;
				CollisionChecker.checkCollision(Player.getInstance());
				if( Player.getInstance().hitBoxOn == false) x = x+speed;
			}
			
			//Posizione normale
			else
			{
				direction = Directions.STAND;	
			}
			
			
			//Caduta libera
			if(Player.getInstance().fallOn == true && Player.getInstance().jump == false)
			{
				y = y+speed;
			}
			
			//Aggiornamento delle sprite
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
				spriteSparo = false;
			}
			
			//Sparo del personaggio
			if(KeyHandler.getInstance().isShooting())
			{
				spriteSparo = true;
				spriteCounter = 10;
				this.shot();
			}
			
			//Salto del personaggio
			if(jump && !KeyHandler.getInstance().isJumping())
			{
				KeyHandler.getInstance().setIsJumping(true);
				if(jumpValue < ((WiewData.TILE_SIZE.getValue()+2)*3)/9)
				{
					y-=7;
					jumpValue++;
				}
				else
				{
					jumpValue = 0;
					Player.getInstance().setJump(false);
				}
			}
		}
	}
	
	
	
	@Override
	public void draw(Graphics2D g2) 
	{	
		BufferedImage image = null;
		
		//Assegnazione delle sprite
		switch(direction) {
		case LEFT:
			if(spriteSparo == true) {image = sparoS;}
			else if(spriteNum == 1) image = moveL1;
			else if(spriteNum == 2) image = moveL2;
			else if(spriteNum == 3) image = moveL3;
			oldDirection = Directions.LEFT;
			break;
			
		case RIGHT:
			if(spriteSparo == true) {image = sparoD;}
			else if(spriteNum == 1) image = moveR1;
			else if(spriteNum == 2) image = moveR2;
			else if(spriteNum == 3) image = moveR3;
			oldDirection = Directions.RIGHT;
			break;
			
		case STAND:
			if(oldDirection == Directions.RIGHT) 
			{
				if(spriteSparo == true) {image = sparoD;}
				else if(spriteNum == 1) image = standR1;
				else image = standR2;
			}
			else if(oldDirection == Directions.LEFT) 
			{
				if(spriteSparo == true) {image = sparoS;}
				else if(spriteNum == 1) image = standL1;
				else image = standL2;
			}
		}
		
		//Stampa
		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
		
    
	}

	@Override
	public void die() 
	{
		// TODO Auto-generated method stub
		
	}
}
