package model;

import java.util.ArrayList;

public abstract class Enemy extends Entity
{
	
	protected boolean changeDirection;
	protected boolean bubbleStatus = false;
	//contiene i nemici in vita
	protected static ArrayList<Enemy> enemies = new ArrayList<>();
	
	
	public static ArrayList<Enemy> getEnemies() { return enemies; }
	public boolean getBubbleStatus()  {return bubbleStatus; }
	
}
