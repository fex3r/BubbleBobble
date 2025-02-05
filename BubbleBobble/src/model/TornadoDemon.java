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

public class TornadoDemon extends Enemy
{	
	protected Directions directionForImage = Directions.LEFT;
	protected int movement = 1;
	protected int frame = 0;
	protected int shotCooldown = 0;
	protected BufferedImage aliSuD,aliDietroD,aliMezzoD,aliBasse1D,aliBasse2D,aliSuS,aliDietroS,aliMezzoS,aliBasse1S,aliBasse2S,bolla1,bolla2;
	protected BufferedImage sparo1,sparo2,sparo3,sparo4,sparo1S,sparo2S,sparo3S,sparo4S;
	private int shootAnimation = 5;
	private int shootAnimationSpriteCounter = 0;
	
	/**
	 * Costruttore di TornadoDemon
	 */
	public TornadoDemon() {
		
		setDefaultValues();
		getDemonImage();
		GameEngine.getInstance().addObserver(this);
		enemies.add(this);
	}
	
	/**
	 * Assegna i valori iniziali di TornadoDemon
	 */
	public void setDefaultValues() 
	{
		x = 200;
		y = 200;
		speed = 2;
		direction = Directions.UP;
		oldDirection = Directions.LEFT;
		hitBoxDefaultX = 16;
		hitBoxDefaultY = 11;

		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
	}
	
	/**
	 * Carica le giuste sprite per rappresentare TornadoDemon
	 */
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
			
			sparo1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo1.png"));
			sparo2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo2.png"));
			sparo3 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo3.png"));
			sparo4 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo4.png"));
			sparo1S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo1S.png"));
			sparo2S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo2S.png"));
			sparo3S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo3S.png"));
			sparo4S = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/sparo4S.png"));
			
			bolla1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/demonBolla1.png"));
			bolla2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/demonBolla2.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Verifica se Player è nel raggio di tiro di TornadoPlayer
	 */
	private boolean isPlayerInRange() {
		int xDistance = Math.abs(Player.getInstance().x-x);
		int yDistance = Math.abs(Player.getInstance().y-y);
		
		if(Player.getInstance().x > x && directionForImage == Directions.RIGHT) {
			if(xDistance < 900 && yDistance <900) {
				return true;
			}
		}else if(Player.getInstance().x < x && directionForImage == Directions.LEFT) {
			if(xDistance < 900 && yDistance <900) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Aggiornamento di TornadoPlayer per posizione, sparo, aggiornamento sprite per rappresentazione
	 */
	@Override
	public void update(Observable o, Object arg) {

		if(GameEngine.getInstance().getGameState() == 2) {
			if(shootAnimation > 4) {
				hitBoxOn = false;
				fallOn = true;
				hitUp = false;
				changeDirection = false;
				CollisionChecker.checkCollision(this);
				CollisionChecker.checkUp(this);
				CollisionChecker.checkFall(this);
				
				if(shotCooldown > 0) {
					shotCooldown--;
				}
				
				
				if(isPlayerInRange() && shotCooldown <= 0 && bubbleStatus == false) {
					shootTornado(Player.getInstance().x,Player.getInstance().y);
					shotCooldown = 300;
					shootAnimation = 0;
				}
				
				Random rand = new Random();
				int aux = rand.nextInt(100)+1;
				
				frame++;
				if(frame > 30) {
					if(aux <= 25) {
						//changeDirection = true;
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
			}else {
				
				shootAnimationSpriteCounter++;
				if(shootAnimationSpriteCounter > 10) {
					if(shootAnimation == 0) shootAnimation = 1;
					else if(shootAnimation == 1) shootAnimation = 2;
					else if(shootAnimation == 2) shootAnimation = 3;
					else if(shootAnimation == 3) shootAnimation = 4;
					else if(shootAnimation == 4) shootAnimation = 5;
					shootAnimationSpriteCounter = 0;
				}
				
			}
		
		}
	}

	/**
	 * Sparo del tornado
	 */
	private void shootTornado(int targetX,int targetY) {
		if(directionForImage == Directions.LEFT) {
			new TornadoShot(x,y,targetX,targetY,directionForImage);
		}
		
	}

	/**
	 * Gestione della morte di TornadoPlayer
	 */
	@Override
	public void die() {
		
		if(bubbleStatus == true) {
			
			
			enemies.remove(this);
			GameEngine.getInstance().deleteObserver(this);
			
		}else {
			new PowerUp(x,y);
			bubbleStatus = true;
		}
		
	}

	/**
	 * Disegno di TornadoPlayer
	 */
	@Override
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		if(!bubbleStatus) {
			if(shootAnimation > 3) {
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
					switch(shootAnimation) {
					case 0:
						if(directionForImage == Directions.LEFT) image = sparo1S;
						else image = sparo1;
						break;
					case 1:
						if(directionForImage == Directions.LEFT) image = sparo2S;
						else image = sparo2;
						break;
					case 2:
						if(directionForImage == Directions.LEFT) image = sparo3S;
						else image = sparo3;
						break;
					case 3:
						if(directionForImage == Directions.LEFT) image = sparo4S;
						else image = sparo4;
						break;
					}
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
