package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity implements Observer
{
	protected int x; //Posizione dell' entità nella coordinata x
	protected int y; //Posizione dell' entità nella coordinata y
	protected int speed ; //Velocità dell'entità
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
	
	
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return speed; }
	public Directions getDirection() { return direction; }
	public Directions getOldDirection() { return oldDirection; }
	public Rectangle getHitBox() { return hitBox; }
	public boolean getFallOn() { return fallOn; }
	public int getHitboxDefaultX() { return hitBoxDefaultX;} 
	public int getHitboxDefaultY() { return hitBoxDefaultY;} 
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }
	public void setHitBox(Boolean bool) { hitBoxOn = bool; }
	public void setHitboxX(int x) { hitBox.x = x; }
	public void setHitboxY(int y) { hitBox.y = y; }
	public void setFall(Boolean bool) { fallOn = bool; }
	public void setHitUp(Boolean bool) { hitUp = bool;}
	
	public abstract void die();
	
	public void jump()
	{
		if(fallOn == false)
		{
			for(int i = 0; i<WiewData.TILE_SIZE.getValue()*3; i++)
			{
				y--;
			}
		}
		
	}
	public abstract void draw(Graphics2D g2);
	
}
