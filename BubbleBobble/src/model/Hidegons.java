package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;

public class Hidegons extends Enemy
{
	
	protected BufferedImage bolla1, bolla2, bolla3,caduta,cadutaS,muovi1,muovi1S,muovi2,muovi2S,muovi3,muovi3S,sparo;
	
	private int frameDirezione = 0;
	private int frameSalto = 0;
	private int frameShot = 0;
	private int jumpValue = 0;
	private boolean jump = false;
	
	public Hidegons() {
		
		setDefaultValues();
		setImage();
		shotImage = sparo;
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
		
	}
	
	private void setImage() {
		
		try {
			bolla1 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/bolla1.png"));
			bolla2 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/bolla2.png"));
			bolla3 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/bolla3.png"));
			
			muovi1 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi1.png"));
			muovi1S = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi1S.png"));
			muovi2 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi2.png"));
			muovi2S = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi2S.png"));
			muovi3 = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi3.png"));
			muovi3S = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/muovi3S.png"));
			
			sparo = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/sparo.png"));
			
			caduta = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/caduta.png"));
			cadutaS = ImageIO.read(getClass().getResourceAsStream("/sprites/hidegons/hidegonsRed/cadutaS.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void setDefaultValues() {
		
		fallOn = true;
		shotImage = sparo;
		speed = 2;
		shotSpeed = 4;
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

	@Override
	public void update(Observable o, Object arg) {
		if(GameEngine.getInstance().getGameState() == 2) //Solamente se in game state
		{
			fallOn = true;
			changeDirection = false;
			hitBoxOn = false;
			CollisionChecker.checkCollision(this);
			CollisionChecker.checkFall(this);
			
			//Sceglie se cambiare la direzione 
			Random rand = new Random();
			frameDirezione++;
			if(frameDirezione > 30) 
			{
				if(rand.nextInt(5)>3) changeDirection = true;
				frameDirezione = 0;
			}
			
			//Sceglie se saltare
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
			
			//Sceglie se saltare
			if(frameShot > 90 && bubbleStatus == false)
			{
				this.shot();
				frameShot = 0;
			}
			else frameShot++;
			
			//Applica il salto
			if(jumpValue != 0)
			{
				y -= speed;
				jumpValue--;
				fallOn = false;
			}
			else jump = false;
			
			//Caduta libera
			if(fallOn)
			{
				y += speed;
			}
			
			//Se colpisce un muro, cambia direzione
			if(hitBoxOn == true)
			{
				changeDirection = true;
			}
			
			//Gestisce il cambio di direzione
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
			
			//Gestisce il movimento
			switch(this.getDirection())
			{
			case LEFT:
				x = x - speed;
				break;
			case RIGHT:
				x = x + speed;
				break;
			}
			
			//Decide le sprite che rappresenteranno il nemico
			if(bubbleStatus == false)
			{	
				
				spriteCounter ++;
				
				if(spriteCounter > 10) 
				{
					
					if(fallOn) {
						spriteNum = 1;
					}else {
						if(spriteNum == 1) spriteNum = 2;
						else if(spriteNum == 2) spriteNum = 3;
						else if(spriteNum == 3) spriteNum = 1;
					}
					
					spriteCounter = 0;
				}
			}
			else
			{
				spriteCounter ++;
				if(spriteCounter > 10) {
					if(spriteNum == 1) spriteNum = 2;
					else if(spriteNum == 2) spriteNum = 3;
					else if(spriteNum == 3) spriteNum = 1;
					
					spriteCounter = 0;
				}
				
			}
			
		}
		
	}

	@Override
	public void die() {
		if(bubbleStatus == true) 
		{
			enemies.remove(this);
			GameEngine.getInstance().deleteObserver(this);
			
		}
		else 
		{
			new PowerUp(x,y);
			bubbleStatus = true;
			
		}
		
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if(!bubbleStatus) {
			if(!fallOn) {
				switch(direction) {
				case LEFT:
					switch(spriteNum) {
					case 1:
						image = muovi1S;
						break;
					case 2:
						image = muovi2S;
						break;
					case 3:
						image = muovi3S;
						break;
					}
				case RIGHT:
					switch(spriteNum) {
					case 1:
						image = muovi1;
						break;
					case 2:
						image = muovi2;
						break;
					case 3:
						image = muovi3;
						break;
					}
				}
			}else {
				switch(direction) {
				case LEFT:
					image = cadutaS;
					break;
				case RIGHT:
					image = caduta;
				}
			}
			
			}else {
				switch(spriteNum) {
				case 1:
					image = bolla1;
					break;
				case 2:
					image = bolla2;
					break;
				case 3:
					image = bolla3;
					break;
			}
		}
		
		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
	}
}
