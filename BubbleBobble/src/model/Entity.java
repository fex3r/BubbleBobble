package model;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity 
{
	protected int x; //Posizione dell' entità nella coordinata x
	protected int y; //Posizione dell' entità nella coordinata y
	protected static final int entitySpeed = 4; //Velocità dell'entità (è uguale per player e nemici)
		
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return entitySpeed; }
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }	
	
	
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
				//while(freeFall()) y++;
				timer.cancel();
			}
		};
		timer.schedule(task, 225);

	}
	
	/*
	private boolean freeFall()
	{
		
	}
	*/
}
