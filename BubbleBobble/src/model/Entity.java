package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity 
{
	protected int x; //Posizione dell' entità nella coordinata x
	protected int y; //Posizione dell' entità nella coordinata y
	protected int speed ; //Velocità dell'entità
	Directions direction;
	Directions oldDirection;
	protected BufferedImage standR1,standR2,standL1,standL2,moveR1,moveR2,moveR3,moveL1,moveL2,moveL3;
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	protected Rectangle hitBox;
	protected boolean hitBoxOn = false;
	protected boolean fallOn = false;
	
	
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return speed; }
	public Directions getDirection() { return direction; }
	public Directions getOldDirection() { return oldDirection; }
	public Rectangle getHitBox() { return hitBox; }
	public boolean getFallOn() { return fallOn; }
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }
	public void setHitBox(Boolean bool) { hitBoxOn = bool; }
	public void setFall(Boolean bool) { fallOn = bool; }
	
	
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
}
