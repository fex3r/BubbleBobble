package model;

import java.util.Observable;
import java.util.Observer;

import control.KeyHandler; 

@SuppressWarnings("deprecation")
public final class Player
{
	// posizione iniziale del giocatore da cambiare.
	// probabilmente è anche sbagliato dichiarare la posizione static in quanto si dovrebbe riferire
	// all'istanza del player
	// nel caso in cui verrà cambiata dovranno essere modificati tutti gli utilizzi all'interno del metodo
	//paintComponent dentro GamePanel
	
	private static int x = 0;
	private static int y = 0;
	private static final int playerSpeed = 4;
	private String name;
	private static Player playerInstance;
	
	// costruttore privato Pattern Singletone
	private Player(String name) { this.name = name; }
	
	// metodo di ritorno istanza singletone del giocatore
	public static Player getInstance(String name) 
	{
		if(playerInstance == null) playerInstance = new Player(name);
		return playerInstance;
	}

	public static int getSpeed() { return playerSpeed; }
	
	public static int getX() { return x; }
	public static int getY() { return y; }
	
	public static void setX(int x) { Player.x = x; }
	public static void setY(int y) { Player.y = y; }
}
