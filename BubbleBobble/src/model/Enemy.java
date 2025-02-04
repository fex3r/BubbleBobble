package model;

import java.util.ArrayList;

public abstract class Enemy extends Entity
{
	protected boolean changeDirection; 
	protected boolean bubbleStatus = false;
	protected static ArrayList<Enemy> enemies = new ArrayList<>();		//contiene i nemici ancora in vita
	
	/**
	 * @return la lista di nemici ancora in vita
	 */
	public static ArrayList<Enemy> getEnemies() { return enemies; }
	
	/**
	 * @return false se il nemico è ancora vivo, altrimento true se è in stato di bolla
	 */
	public boolean getBubbleStatus()  { return bubbleStatus; }
}
