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
 * Banebou si comporta come un personaggio normale nello spostamento 
 * Non spara ma se ti colpisce muori 
 */
public class Banebou extends Enemy
{
	protected BufferedImage muovi_s_1, muovi_s_2, muovi_s_3, muovi_d_1, muovi_d_2, muovi_d_3, bolla1, bolla2, bolla3;
	
	private int countSprite = 0;
	private int frameDirezione = 0;
	private int frameSalto = 0;
	private int jumpValue = 0;
	private boolean jump = false;
	private boolean jumpAction = false;
	
	
	public Banebou()
	{
		setDefaultValues();
		setImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
	public void setDefaultValues()
	{
		fallOn = true;

		speed = 2;
		x = 250;
		y = 250;

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
			muovi_s_1 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_s_1.png"));
			muovi_s_2 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_s_2.png"));
			muovi_s_3 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_s_3.png"));
			muovi_d_1 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_d_1.png"));
			muovi_d_2 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_d_2.png"));
			muovi_d_3 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/muovi_d_3.png"));
			
			bolla1 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/bolla1.png"));
			bolla2 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/bolla2.png"));
			bolla3 = ImageIO.read(getClass().getResourceAsStream("/sprites/banebou/Banebou arancione/bolla3.png"));
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
			fallOn = true;
			changeDirection = false;
			hitBoxOn = false;
			CollisionChecker.checkCollision(this);
			CollisionChecker.checkFall(this);
			
			Random rand = new Random();
			frameDirezione++;
			if(frameDirezione > 30) 
			{
				if(rand.nextInt(5)>3) changeDirection = true;
				frameDirezione = 0;
			}
			
			frameSalto++;
			if(frameSalto > 30 && !fallOn)
			{
				if(rand.nextInt(3) < 1) 
				{
					jump = true;
					jumpValue = 48;
				}
				frameSalto = 0;
			}
			
			if(jumpValue != 0)
			{
				y -= speed;
				jumpValue--;
				fallOn = false;
			}
			else jump = false;
			
			if(fallOn)
			{
				y += speed;
			}
			
			
			System.out.println("FallOn: " + fallOn);
			System.out.println("Jump: " + jump);
			System.out.println("Jump value: " + jumpValue);
			System.out.println("---------------------");

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
					spriteNum = 4;
					break;
				case RIGHT:
					direction = Directions.LEFT;
					spriteNum = 1;
					break;
				}
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
					spriteNum = 3;
				}
				else if(spriteNum == 3 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 1;
				}
				else if(spriteNum == 4 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 5;
				}
				else if(spriteNum == 5 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 6;
				}
				else if(spriteNum == 6 && countSprite > 20) 
				{
					countSprite = 0;
					spriteNum = 4;
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
			switch(spriteNum)
			{
				case 1:
					image = muovi_s_1;
					break;
				case 2:
					image = muovi_s_2;
					break;
				case 3:
					image = muovi_s_3;
					break;
				case 4:
					image = muovi_d_1;
					break;
				case 5:
					image = muovi_d_2;
					break;
				case 6:
					image = muovi_d_3;
					break;
			}
		}
		else
		{
			if(spriteNum == 1) image = bolla1;
			else if(spriteNum == 2) image = bolla2;
			else image = bolla3;
		}
		
		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
		//stampa dell'hitbox
//		g2.setColor(Color.RED);
//		g2.drawRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height);
		
	}

}
