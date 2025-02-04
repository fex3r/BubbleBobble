package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;
import control.KeyHandler;

@SuppressWarnings("deprecation")
/**
 * La classe rappresenta i colpi sparati da Player
 */
public class Shot extends Entity
{
	private Directions direction;
	private boolean hitBlock = false;
	//adesso è singleton keyHandler quindi va cambiato tutto con KeyHandle.getInstance
	private KeyHandler kh;
	private static BufferedImage shotImage;
	static
	{	
		try 
		{
			shotImage = ImageIO.read(Shot.class.getResourceAsStream("/sprites/misc/image_271.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	};
	//Array contenente i shot in vita
	protected static ArrayList<Shot> shots = new ArrayList<>();
	
	/**
	 * Costruttore della classe Shot
	 * @param x posizione nell'asse x di Shot
	 * @param y posizione nell'asse y di Shot
	 * @param direction direzione che ha lo sparo
	 */
	public Shot(int x, int y, Directions direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		speed = 6;
		shots.add(this); 
		GameEngine.getInstance().addObserver(this);
		
		hitBox = new Rectangle();
		hitBoxDefaultX = 11;
		hitBoxDefaultY = 5;
				
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 23;
		hitBox.height = 23;
	}
	
	//Getters
	/**
	 * Restituisce la lista dei colpi sparati, ancora attivi
	 * @return la lista dei colpi sparati
	 */
	public static ArrayList<Shot> getShots() { return shots; }
	
	/**
	 * Restituisce la hit block
	 * @return hitBlock
	 */
	public boolean getHitBlock() { return hitBlock; }
	
	
	@Override
	/**
	 * Restituisce la direzione dello sparo
	 * @return direzione sparo	 
	 */
	public Directions getDirection() { return this.direction; }
	
	/**
	 * Assegna un nuovo valora alla x 
	 * @param x posizione sull'asse delle x di Shot
	 */
	public void setX (int x) { this.x = x; }
	
	/**
	 * Assegna un nuovo valore alla y
	 * @param y posizione sull'asse y di Shot
	 */
	public void setY (int y) { this.y = y; }

	/**
	 * Assegna un nuovo valore alla hitBlock di Shot
	 * @param x valore booleano che indica se Shot colpisce un blocco
	 */
	public void setHitBlock(boolean x) { hitBlock = x; }

	
	@Override
	public void update(Observable o, Object arg) 
	{
		//Se il colpo è ancora attivo, in base alla direzione si sposta
		if(shots.contains(this))
		{	
			if(this.direction.equals(Directions.LEFT))
			{
				x = x - speed;
				CollisionChecker.checkCollision(this);
				if( this.hitBoxOn == true) this.die();
			}
			else
			{
				x = x + speed;
				CollisionChecker.checkCollision(this);
				if( this.hitBoxOn == true) this.die();
			}
		}
	}
	
	/**
	 * Metodo di disegno del colpo
	 */
	public void draw(Graphics2D g2) 
	{
		g2.drawImage(shotImage,x, y, 48, 48 ,null);	
	}

	/**
	 * Gestisce la sparizione del colpo
	 */
	@Override
	public void die() 
	{
		if(shots.contains(this)) 
		{
			shots.remove(this);
			GameEngine.getInstance().deleteObserver(this);
		}
	}
}