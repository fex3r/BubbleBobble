package model;

import java.awt.image.BufferedImage;

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
	
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return speed; }
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }	
}
