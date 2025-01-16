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
	
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return speed; }
	public Rectangle getHitBox() { return hitBox; }
	public Directions getDirection() { return direction; }
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }
	public void setHitBox(Boolean bool) { hitBoxOn = bool; }
	
	
	public void jump()
	{
		for(int i = 0; i<16; i++) y--;
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				for(int i = 0; i<16; i++) y++;
				
				timer.cancel();
			}
		};
		timer.schedule(task, 225);

	}
}
