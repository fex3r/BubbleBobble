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
	protected Entity owner;		//Chi ha sparato il colpo
	private boolean hitBlock = false;
	protected static ArrayList<Shot> shots = new ArrayList<>();	//Array contenente i colpi presenti in mappa
	
	/**
	 * Costruttore della classe Shot
	 * @param x posizione nell'asse x di Shot
	 * @param y posizione nell'asse y di Shot
	 * @param direction direzione che ha lo sparo
	 */
	public Shot(int x, int y, Directions direction, Entity entity)
	{
		owner = entity;
		shotImage = entity.getShotImage();
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
	 * @return la lista dei colpi sparati, che sono ancora attivi
	 */
	public static ArrayList<Shot> getShots() { return shots; }
	
	/**
	 * @return hitBlock
	 */
	public boolean getHitBlock() { return hitBlock; }
	
	
	/**
	 * Restituisce l'istanza di chi ha sparato il colpo
	 * @return l'istanza di chi ha sparato il colpo
	 */
	public Entity getOwner() { return owner; }
	
	@Override
	/**
	 * @return la direzione dello sparo	 
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
	
	@Override
	public void draw(Graphics2D g2) 
	{
		g2.drawImage(shotImage,x, y, 48, 48 ,null);	
	}

	
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