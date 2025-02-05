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

public class FlyBomb extends Enemy{
	
	private Directions directionForImage = Directions.LEFT;
	private BufferedImage bomb1,bomb2,bomb3,bomb4,bomb5,bomb6,boom1,boom2,boom3,boom4,boom5,boom6,boom7;
	private BufferedImage bomb1S,bomb2S,bomb3S,bomb4S,bomb5S,bomb6S;
	private int frame = 0;
	private int spriteNum = 1;
	private int spriteCounter = 0;
	private boolean esplosionAnimation = false;
	
	/**
	 * Costruttor di FlyBomb
	 */
	public FlyBomb() {

		setDefaultValues();
		getImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
	/**
	 * Assegnazione dei valori iniziali di FlyBomb
	 */
	private void setDefaultValues() {
		speed = 2;
		direction = Directions.LEFT;
		hitBoxDefaultX = 5;
		hitBoxDefaultY = 5;

		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
	}

	/**
	 * Inizializzazione delle immagini che rappresenteranno FlyBomb
	 */
	private void getImage() {
		try {
			bomb1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb1.png"));
			bomb2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb2.png"));
			bomb3 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb3.png"));
			bomb4 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb4.png"));
			bomb5 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb5.png"));
			bomb6 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb6.png"));
			
			bomb1S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb1S.png"));
			bomb2S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb2S.png"));
			bomb3S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb3S.png"));
			bomb4S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb4S.png"));
			bomb5S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb5S.png"));
			bomb6S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/bomb6S.png"));
			
			boom1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom1.png"));
			boom2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom2.png"));
			boom3 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom3.png"));
			boom4 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom4.png"));
			boom5 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom5.png"));
			boom6 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom6.png"));
			boom7 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/flyBomb/boom7.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Aggiornamento di FlyBomb, per spostamento, volo, sparo e aggiornamento immagini
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(GameEngine.getInstance().getGameState() == 2) {
				if(!esplosionAnimation) {
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
						if(spriteNum == 1) spriteNum = 2;
						else if(spriteNum == 2) spriteNum = 3;
						else if(spriteNum == 3) spriteNum = 4;
						else if(spriteNum == 4) spriteNum = 5;
						else if(spriteNum == 5) spriteNum = 6;
						else if(spriteNum == 6) spriteNum = 1;
						spriteCounter = 0;
					}
				}else {
					spriteCounter ++;
					if(spriteCounter > 15) 
						{
							if(spriteNum == 1) spriteNum = 2;
							else if(spriteNum == 2)  spriteNum = 3;
							else if(spriteNum == 3) spriteNum = 4;
							else if(spriteNum == 4) spriteNum = 5;
							else if(spriteNum == 5) spriteNum = 6;
							else if(spriteNum == 6) spriteNum = 7;
							else if(spriteNum == 7) die();
							spriteCounter = 0;
						}
					}
				}
		}
		
		
	/**
	 * Metodo per la morte di FlyBomb, quando viene colpito esplode e viene allargata la sia hitbox
	 */
	@Override
	public void die() {
		
		if(spriteNum > 6 ) {
			enemies.remove(this);
			GameEngine.getInstance().deleteObserver(this);
		}else {
			esplosionAnimation = true;
			this.x = this.x-30;
			this.y = this.y-30;
			
			hitBox.x = 0;
			hitBox.y = 0;
			hitBox.width = 110;
			hitBox.height = 110;
			spriteCounter = 1;
			spriteNum = 1;
		}
		
		
		
		
		
	}

	/**
	 * Disegno di FlyBomb
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		if(!esplosionAnimation) {
				//System.out.println("esplosion superato");
				switch(directionForImage) {
				case LEFT:
					switch(spriteNum) {
					case 1:
						image = bomb1S;
						break;
					case 2:
						image = bomb2S;
						break;
					case 3:
						image = bomb3S;
						break;
					case 4:
						image = bomb4S;
						break;
					case 5:
						image = bomb5S;
						break;
					case 6:
						image = bomb6S;
						break;
					}
				case RIGHT:
					switch(spriteNum) {
					case 1:
						image = bomb1;
						break;
					case 2:
						image = bomb2;
						break;
					case 3:
						image = bomb3;
						break;
					case 4:
						image = bomb4;
						break;
					case 5:
						image = bomb5;
						break;
					case 6:
						image = bomb6;
						break;
					}
				}
		}else {
			switch(spriteNum) {
			case 1:
				image = boom1;
				break;
			case 2:
				image = boom2;
				break;
			case 3:
				image = boom3;
				break;
			case 4:
				image = boom4;
				break;
			case 5:
				image = boom5;
				break;
			case 6:
				image = boom6;
				break;
			case 7:
				image = boom7;
				break;
			default:
				image = boom7;
				break;

			}
		}
	
		if(!esplosionAnimation) {
			g2.drawImage(image,x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
		}else g2.drawImage(image,x+5, y+5, WiewData.TILE_SIZE.getValue()+75,WiewData.TILE_SIZE.getValue()+75,null);
		
	}
	
}
