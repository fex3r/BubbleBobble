package model;

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
public class Shot extends Entity implements Observer
{
	private int x;
	private int y;
	private int speed = 6;
	private Directions direction;
	private boolean hitBlock = false;
	
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
	private static ArrayList<Shot> shots = new ArrayList<>();
	
	//Costruttori
	public Shot(int x, int y, Directions direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		shots.add(this); 
		GameEngine.getInstance().addObserver(this);
		
		hitBox = new Rectangle();
		hitBox.x = 11;
		hitBox.y = 5;
		hitBox.width = 32;
		hitBox.height = 38;
	}
	
	//Getters
	public static ArrayList<Shot> getShots() { return shots; }
	public boolean getHitBlock() { return hitBlock; }
	
	@Override
	public Directions getDirection() { return this.direction; }
	
	//Setters
	public void setX (int x) { this.x = x; }
	public void setY (int y) { this.y = y; }
	public void setHitBlock(boolean x) { hitBlock = x; }

	@Override
	public void update(Observable o, Object arg) 
	{
		if(shots.contains(this))
		{
			hitBox.setLocation(x + 11, y + 5);
			
			if(this.direction.equals(Directions.LEFT))
			{
				x = x - speed;
				CollisionChecker.checkCollision(this);
				if( this.hitBoxOn == true) shots.remove(this);
			}
			else
			{
				x = x + speed;
				CollisionChecker.checkCollision(this);
				if( this.hitBoxOn == true) shots.remove(this);
			}
		}
	}
	
	public void draw(Graphics2D g2) 
	{
		g2.drawImage(shotImage,x, y, 48, 48 ,null);
	}
}
