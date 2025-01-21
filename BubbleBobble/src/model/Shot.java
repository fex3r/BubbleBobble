package model;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import control.GameEngine;
import control.KeyHandler;

@SuppressWarnings("deprecation")
public class Shot implements Observer
{
	private int x;
	private int y;
	private int speed = 8;
	private Directions direction;
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
		
		
		
	}
	
	//Getters
	public static ArrayList<Shot> getShots() { return shots; }
	
	//Setters
	public void setX (int x) { this.x = x; }
	public void setY (int y) { this.y = y; }

	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("ok");
		if(this.direction.equals(Directions.LEFT)) x = x - speed;
		else x = x + speed;
	}
	
	public void draw(Graphics2D g2) 
	{
		g2.drawImage(shotImage,x, y, 48, 48 ,null);
	}
}
