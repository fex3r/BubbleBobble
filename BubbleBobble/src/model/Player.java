package model;

import java.util.Observable;
import java.util.Observer;

import control.KeyHandler; 
import view.GamePanel;

@SuppressWarnings("deprecation")
public final class Player extends Entity implements Observer
{
	private String name;
	private KeyHandler kh;
	private GamePanel gp;
	private static Player playerInstance;
	
	// costruttore privato Pattern Singletone
	private Player() { }
	
	// Singleton pattern
	public static Player getInstance() 
	{
		if(playerInstance == null) playerInstance = new Player();
		return playerInstance;
	}
	
	public static class Builder //Builder pattern
	{
		//Duplicazione attributi
		private String name;
		private KeyHandler kh;
		private GamePanel gp;
		
		
		public Builder() { } //*******************controlla con name se ci va messo******************************
		
		//Setter
		public Builder setKeyHandler(KeyHandler kh) { this.kh = kh; return this; }
		public Builder setGamePanel(GamePanel gp) { this.gp = gp; return this; }
		public Builder setName(String name) { this.name = name; return this; }
		
		//Metodo build finale
		public Player build() 
		{ 
			Player p = Player.getInstance();
			p.kh = this.kh;
			p.gp = this.gp;
			p.name = this.name;
			return p; 
		}
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if(kh.isUp()) y = y - entitySpeed;
		else if(kh.isDown()) y = y - entitySpeed;
		else if(kh.isLeft()) x = x - entitySpeed; 
		else if(kh.isRight()) x = x - entitySpeed;
		
	}
}
