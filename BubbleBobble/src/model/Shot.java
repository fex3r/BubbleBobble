package model;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import control.KeyHandler;

public class Shot
{
	private int x;
	private int y;
	private int speed = 8;
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
	public Shot() { this(10,10); }
	public Shot(int x, int y) { shots.add(new Shot(0,0)); }
	
	//Getters
	public static ArrayList<Shot> getShots() { return shots; }
	
	//Setters
	public void setX (int x) { this.x = x; }
	public void setY (int y) { this.y = y; }

	/*
	public void update()
	{
		if(kh.isShooting())
		{
			new Shot();
		}
	}
	*/
	public void draw(Graphics2D g2) 
	{
		g2.drawImage(shotImage,x, y, 48, 48 ,null);
	}
}
