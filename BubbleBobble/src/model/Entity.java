package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * La classe rappresenta le entità all'interno del gioco, ovvero personaggi e spari
 * La classe è resa astratta, infatti non possiamo creare istanze di Entity ma solamente delle sue sottoclassi
 */
public abstract class Entity implements Observer
{
	protected int x; 
	protected int y; 
	protected int speed ; 
	Directions direction;
	Directions oldDirection;
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	protected Rectangle hitBox;
	protected int hitBoxDefaultX;
	protected int hitBoxDefaultY;
	protected boolean hitBoxOn = false;
	protected boolean fallOn = false;
	protected boolean hitUp = false;
	protected BufferedImage shotImage;
	
	
	/**
	 * @return la posizione dell'entità sull'asse x
	 */
	public int getX() { return x; }
	/**
	 * @return la posizione dell'entità sull'asse y
	 */
	public int getY() { return y; }
	/**
	 * @return la velocità dell'entità
	 */
	public int getSpeed() { return speed; }
	/**
	 * @return la direzione dell'entità
	 */
	public Directions getDirection() { return direction; }
	/**
	 * @return la vecchia direzione dell'entità
	 */
	public Directions getOldDirection() { return oldDirection; }
	/**
	 * @return la hitBox dell'entità (ovvero il rettangolo che lo circonda)
	 */
	public Rectangle getHitBox() { return hitBox; }
	/**
	 * @return true se l'entità ha il vuoto sotto
	 */
	public boolean getFallOn() { return fallOn; }
	/**
	 * @return restituisce la distanza di x tra l'entità e la sua hitbox
	 */
	public int getHitboxDefaultX() { return hitBoxDefaultX;} 
	/**
	 * @return restituisce la distanza y tra l'entità e la sua hitbox
	 */
	public int getHitboxDefaultY() { return hitBoxDefaultY;} 
	/**
	 * @return restituisce l'immagine del colpo che spara questa entità
	 */
	public BufferedImage getShotImage() { return shotImage; }
		
	/**
	 * Assegna un nuovo valore a x
	 * @param newX nuova x
	 */
	public void setX(int newX) { x = newX; }
	/**
	 * Assegna un nuovo valore a y
	 * @param newY nuova y
	 */
	public void setY(int newY) { y = newY; }
	/**
	 * Assegna un nuovo valore a hitBox
	 * @param bool parametro che indica se colpisce 
	 */
	public void setHitBox(Boolean bool) { hitBoxOn = bool; }
	/**
	 * Assegna un nuovo valore alla x della hitbox
	 * @param x nuova x
	 */
	public void setHitboxX(int x) { hitBox.x = x; }
	/**
	 * Assegna un nuovo valore alla y della hitbox
	 * @param y nuova y
	 */
	public void setHitboxY(int y) { hitBox.y = y; }
	/**
	 * Assegna un nuovo valore a fallOn
	 * @param bool nuovo valore di fallOn
	 */
	public void setFall(Boolean bool) { fallOn = bool; }
	/*
	 * Assegna un nuovo valore a hitUp
	 */
	public void setHitUp(Boolean bool) { hitUp = bool;}
	/**
	 * Assegna una nuova direzione
	 * @param dir
	 */
	public void setDirection(Directions dir) { direction = dir; }
	
	/**
	 * Metodo astratto che per i comportamenti di Entity quando muore
	 */
	public abstract void die();
	
	/**
	 * Metodo per far sparare le entità
	 */
	public void shot()
	{
		new Shot(getShotPosition(), this.getY(), getShotDirection(), this);
	}
	
	/**
	 * @return la direzione del colpo sparato
	 */
	private Directions getShotDirection()
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
	
	/**
	 * @return la posizione da cui partirà il colpo
	 */
	private int getShotPosition()
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
	
	
	/**
	 * Disegno dell'entità
	 */
	public abstract void draw(Graphics2D g2);
	
}
