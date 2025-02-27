package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;

/**
 * La classe TornadoShot rappresenta i colpi sparati dal nemico TornadoDemon
 */
public class TornadoShot extends Entity{
	
	private int targetX;
	private int targetY;
	protected static ArrayList<TornadoShot> tornadoShots = new ArrayList<>();
	private BufferedImage tornado1,tornado2,tornado3,tornado4,tornado5;
	
	/**
	 * Costruttore di TornadoShot
	 * @param x posizione sull'asse x
	 * @param y posizione sull'asse y
	 * @param targetX posizione del target da raggiungere sull'asse delle x
	 * @param targetY posizione del target da raggiungere sull'asse delle y
	 * @param direction direzione del colpo
	 */
	public TornadoShot(int x, int y,int targetX,int targetY,Directions direction) {
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.direction = direction;
		tornadoShots.add(this); 
		GameEngine.getInstance().addObserver(this);
		getDefaultValues();
		getTornadoImage();
	}
	
	/**
	 * Inizializza i valori iniziali di TornadoShot
	 */
	private void getDefaultValues() {
		fallOn = true;
		speed = 4;
		spriteNum = 1;
		hitBox = new Rectangle();
		hitBoxDefaultX = 11;
		hitBoxDefaultY = 5;
				
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 23;
		hitBox.height = 23;
	}
	
	/**
	 * @return ritorna la lista di tutti i tornado shot ancora attivi
	 */
	public static ArrayList<TornadoShot> getTornadoShots(){ return tornadoShots;}
	
	/**
	 * Inizializza le immagini che rappresentano i tornado shot
	 */
	private void getTornadoImage() {
		try {
			
			tornado1 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/tornado/tornado1.png"));
			tornado2 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/tornado/tornado2.png"));
			tornado3 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/tornado/tornado3.png"));
			tornado4 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/tornado/tornado4.png"));
			tornado5 = ImageIO.read(getClass().getResourceAsStream("/sprites/towerfall/tornado/tornado5.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * @return true se il target da colpire è nel raggio del colpo
	 */
	private boolean isOnTarget() {
		if(x <= targetX+speed && x >= targetX-speed && y <= targetY+speed && y >= targetY-speed) {
			return true;
		}
		return false;
	}
	
	/**
	 * Aggiornamento del posizionamento del colpo e delle sprite utilizzate per rappresentarlo
	 */
	@Override
	public void update(Observable o, Object arg) {
	
		if(isOnTarget()) this.die();
		
		CollisionChecker.checkCollision(this);
		CollisionChecker.checkUp(this);
		CollisionChecker.checkFall(this);
		
		if(hitBoxOn || hitUp || fallOn == false) this.die();
		if(targetX > x) { x = x+speed; }
		else x = x-speed;
		
		if(targetY > y) { y = y+speed; }
		else y = y-speed;
		
		spriteCounter++;
		if(spriteCounter > 30) {
			if(spriteNum == 1) spriteNum = 2;
			else if(spriteNum == 2) spriteNum = 3;
			else if(spriteNum == 3) spriteNum = 4;
			else if(spriteNum == 4) spriteNum = 5;
			else spriteNum = 1;
			spriteCounter = 0;
		}
	}

	
	/**
	 * Gestione della fine di un tornado shot
	 */
	@Override
	public void die() {	

		if(tornadoShots.contains(this)) {
			tornadoShots.remove(this);
		}
		GameEngine.getInstance().deleteObserver(this);
	
	}

	
	/**
	 * Disegno di un tornado shot
	 */
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(spriteNum) {
		case 1:
			image = tornado1;
			break;
		case 2:
			image = tornado2;
			break;
		case 3:
			image = tornado3;
			break;
		case 4:
			image = tornado4;
			break;
		case 5:
			image = tornado5;
			break;
		}
		
		g2.drawImage(image,x,y,WiewData.TILE_SIZE.getValue(), WiewData.TILE_SIZE.getValue(),null);
		
	}
	
}
