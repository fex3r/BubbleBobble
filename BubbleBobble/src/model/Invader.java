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

/*
 * Invader è il personaggio che si muove solamente per orizzontale ma con velocità maggiore. 
 * Non spara ma se ti colpisce muori 
 */

public class Invader extends Enemy
{
	protected BufferedImage m1, m2, bolla1, bolla2, bolla3;
	private int countSprite = 0;
	private int frame = 0;
	
	public Invader()
	{
		setDefaultValues();
		setImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
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
	public void update(Observable o, Object arg) 
	{
		if(GameEngine.getInstance().getGameState() == 2) 
		{
			changeDirection = false;
			hitBoxOn = false;
			fallOn = true;
			CollisionChecker.checkCollision(this);
			CollisionChecker.checkFall(this);
			
			Random rand = new Random();
			frame++;
			if(frame > 30) 
			{
				if(rand.nextInt(5)>3) changeDirection = true;
				frame = 0;
			}
			
			if(hitBoxOn == true)
			{
				changeDirection = true;
			}
			
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
			
			if(this.fallOn == true)
			{
				y += speed;
			}
			
			
		
			switch(this.getDirection())
			{
			case LEFT:
				x = x - speed;
				break;
			case RIGHT:
				x = x + speed;
				break;
			}
			
			if(bubbleStatus == false)
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
			else
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
