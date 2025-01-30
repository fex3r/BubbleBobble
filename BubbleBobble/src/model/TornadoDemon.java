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

public class TornadoDemon extends Enemy{
	
	protected Directions directionForImage = Directions.LEFT;
	protected int movement = 1;
	protected int frame = 0;
	protected BufferedImage aliSuD,aliDietroD,aliMezzoD,aliBasse1D,aliBasse2D,aliSuS,aliDietroS,aliMezzoS,aliBasse1S,aliBasse2S,bolla1,bolla2;
	
	public TornadoDemon() {
		setDefaultValues();
		getDemonImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
	
	public void setDefaultValues() {
		x = 200;
		y = 200;
		speed = 2;
		direction = Directions.LEFT;
		oldDirection = Directions.LEFT;
		hitBoxDefaultX = 16;
		hitBoxDefaultY = 11;

		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
	}
	
	private void getDemonImage() {
		try {
			
			aliSuD = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliSu.png"));
			aliDietroD = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliDietro.png"));
			aliMezzoD = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliMezzo.png"));
			aliBasse1D = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliBasse1.png"));
			aliBasse2D = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliBasse2.png"));
			
			aliSuS = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliSuS.png"));
			aliDietroS = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliDietroS.png"));
			aliMezzoS = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliMezzoS.png"));
			aliBasse1S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliBasse1S.png"));
			aliBasse2S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/aliBasse2S.png"));
			
			bolla1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/demonBolla1.png"));
			bolla2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/demonBolla2.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(GameEngine.getInstance().getGameState() == 1) {
			hitBoxOn = false;
			fallOn = true;
			hitUp = false;
			changeDirection = false;
			CollisionChecker.checkCollision(this);
			CollisionChecker.checkUp(this);
			CollisionChecker.checkFall(this);
			
			
			Random rand = new Random();
			int aux = rand.nextInt(100)+1;
			
			frame++;
			if(frame > 30) {
				if(aux <= 25) {
					changeDirection = true;
				}
				frame = 0;
			}
			
						

			if(hitBoxOn == true) {
				changeDirection = false;
				if(direction == Directions.LEFT) {
					direction = Directions.RIGHT;
					directionForImage = Directions.RIGHT;
				}else {
					direction = Directions.LEFT;
					directionForImage = Directions.LEFT;
				}
			}else if(hitUp == true) {
				changeDirection = false;
				direction = Directions.DOWN;
			}else if(fallOn == false) {
				changeDirection = false;
				direction = Directions.UP;
			}
		
			//cambio di direzione casuale 
			if(changeDirection == true) {
				Directions newDirection = direction;
				while(direction == newDirection) {
					
					aux = rand.nextInt(4);
					switch(aux) {
					case 0:
						direction = Directions.UP;
						break;
					case 1:
						direction = Directions.DOWN;
						break;
					case 2: 
						direction = Directions.LEFT;
						directionForImage = Directions.LEFT;
						break;
					case 3: 
						direction = Directions.RIGHT;
						directionForImage = Directions.RIGHT;
						break;
					}
				}
				
			}
			
			//movimento effettivo 
			switch(direction) {
			case RIGHT:
			    x = x+speed;
				break;
			case LEFT:
				x = x-speed;
				break;
			case DOWN:
				if(fallOn == true)y = y+speed;
				break;
			case UP:
				y = y-speed;
				break;
			}
	
			
			spriteCounter ++;
			if(spriteCounter > 10) 
			{
				if(movement == 1) {
					if(spriteNum == 1) spriteNum = 2;
					else if(spriteNum == 2) spriteNum = 3;
					else if(spriteNum == 3) spriteNum = 4;
					else if(spriteNum == 4) {
						spriteNum = 5 ;
						movement = 2;
					}
				}
				else {
					if(spriteNum == 5) spriteNum = 4;
					else if(spriteNum == 4) spriteNum = 3;
					else if(spriteNum == 3) spriteNum = 2;
					else if(spriteNum == 2) {
						spriteNum = 1;
						movement = 1;
					}
				}
				
				spriteCounter = 0;
			}
		
		}
	}

	@Override
	public void die() {
		
		if(bubbleStatus == true) {
			
			enemies.remove(this);
			GameEngine.getInstance().deleteObserver(this);
			
		}else bubbleStatus = true;
		
	}


	@Override
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		if(bubbleStatus == false) {
			switch(directionForImage) {
			case LEFT:
				switch(spriteNum) {
				case 1:
					image = aliDietroS;
					break;
				case 2:
					image = aliSuS;
					break;
				case 3:
					image = aliMezzoS;
					break;
				case 4:
					image = aliBasse1S;
					break;
				case 5:
					image = aliBasse2S;
					break;
				}
				break;
			case RIGHT:
				switch(spriteNum) {
				case 1:
					image = aliDietroD;
					break;
				case 2:
					image = aliSuD;
					break;
				case 3:
					image = aliMezzoD;
					break;
				case 4:
					image = aliBasse1D;
					break;
				case 5:
					image = aliBasse2D;
					break;
				}
				break;
			}
		}else {
			switch(spriteNum) {
			case 1:
			case 2:
				image = bolla1;
				break;
			case 3:
			case 4:
			case 5:
				image = bolla2;
				break;
			}
		}
		
		
		g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue()+17,WiewData.TILE_SIZE.getValue()+17,null);
		
//		stampa dell'hitbox
//		g2.setColor(Color.RED);
//		g2.drawRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height);
	}
	
}
