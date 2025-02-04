package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;

/**
 * Classe che rappresenta il nemico Invader
 * Invader è un personaggio che si muove solo orizzontalmente 
 * Non spara ma se colpisce il personaggio, ti uccide
 */

public class Invader extends Enemy
{
	protected BufferedImage m1, m2, bolla1, bolla2, bolla3;
	private int countSprite = 0;
	private int frame = 0;
	
	/**
	 * Costruttore della classe Invader
	 */
	public Invader()
	{
		setDefaultValues();
		setImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
	/**
	 * Inizializza i valori di Invader
	 */
	public void setDefaultValues()
	{
		speed = 2;
		x = 300;
		y = 300;

		direction = Directions.LEFT;
		oldDirection = Directions.LEFT;
		
		hitBoxDefaultX = 6;
		hitBoxDefaultY = 6;
		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
	}
	
	/**
	 * Inizializza le immagini che rappresenteranno Invader
	 */
	public void setImage()
	{
		try 
		{
			m1 = ImageIO.read(getClass().getResourceAsStream("/sprites/invader/image_18.png"));
			m2 = ImageIO.read(getClass().getResourceAsStream("/sprites/invader/image_19.png"));
			
			bolla1 = ImageIO.read(getClass().getResourceAsStream("/sprites/invader/image_8.png"));
			bolla2 = ImageIO.read(getClass().getResourceAsStream("/sprites/invader/image_9.png"));
			bolla3 = ImageIO.read(getClass().getResourceAsStream("/sprites/invader/image_10.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	/**
	 * Metodo di aggiornamento di Invader
	 */
	public void update(Observable o, Object arg) 
	{
		if(GameEngine.getInstance().getGameState() == 2) //Solo se siamo in play state
		{
			changeDirection = false;
			hitBoxOn = false;
			fallOn = true;
			CollisionChecker.checkCollision(this);
			CollisionChecker.checkFall(this);
			
			//Ogni 30 frame, vi sono 2/5 di probabilità che Invader cambi direzione
			Random rand = new Random();
			frame++;
			if(frame > 30) 
			{
				if(rand.nextInt(5)>3) changeDirection = true;
				frame = 0;
			}
			
			//Se Invader colpisce un muro, cambia direzione
			if(hitBoxOn == true)
			{
				changeDirection = true;
			}
			
			//Gestisce il cambio di direzione di Invader
			if(changeDirection == true)
			{
				switch(this.getDirection())
				{
				case LEFT:
					direction = Directions.RIGHT;
					break;
				case RIGHT:
					direction = Directions.LEFT;
					break;
				}
			}
			
			//Gestisce la caduta libera di Invader
			if(this.fallOn == true)
			{
				y += speed;
			}
			
			
			//Gestisce il movimento di Invader
			switch(this.getDirection())
			{
			case LEFT:
				x = x - speed;
				break;
			case RIGHT:
				x = x + speed;
				break;
			}
			
			//Assegna le sprite corrette a Invader (Se vivo o sottoforma di bolla)
			if(bubbleStatus == false) //Se vivo
			{
				if(spriteNum == 1 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 2;
				}
				else if(spriteNum == 2 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 1;
				}
				else
				{
					countSprite++;
				}
			}
			else //Se sotto forma di bolla
			{
				if(spriteNum == 1 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 2;
				}
				else if(spriteNum == 2 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 3;
				}
				else if(spriteNum == 3 && countSprite > 20)
				{
					countSprite = 0;
					spriteNum = 1;
				}
				else
				{
					countSprite++;
				}
			}
			
		}
	}

	@Override
	/**
	 * Gestisce la morte di Invader
	 */
	public void die() 
	{
		if(bubbleStatus == true) 
		{
			enemies.remove(this);
			GameEngine.getInstance().deleteObserver(this);
			
		}
		else bubbleStatus = true;
		
	}

	@Override
	/**
	 * Metodo di disegno di Invader 
	 */
	public void draw(Graphics2D g2) 
	{
		BufferedImage image = null;
		
		if(bubbleStatus == false) 
		{
			if(spriteNum == 1) image = m1;
			else image = m2;
		}
		else
		{
			if(spriteNum == 1) image = bolla1;
			else if(spriteNum == 2) image = bolla2;
			else image = bolla3;
		}
		
		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
	}
}
