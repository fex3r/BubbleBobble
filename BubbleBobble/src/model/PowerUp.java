package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import control.CollisionChecker;
import control.GameEngine;

public class PowerUp extends Entity
{
	private Random rand = new Random();
	protected int value;
	public static List<Integer> powerUpList = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,11,12));
	public static ArrayList<PowerUp> powerUp = new ArrayList<PowerUp>();
	private BufferedImage i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12;
	
	public PowerUp(int x, int y)
	{
		this.x = x;
		this.y = y;
		value = rand.nextInt(12) + 1;
		setImage();
		setDefaultValues();
		GameEngine.getInstance().addObserver(this);
		powerUp.add(this);
	}
	
	private void setDefaultValues()
	{
		fallOn = true;
		
		hitBoxDefaultX = 6;
		hitBoxDefaultY = 6;
		hitBox = new Rectangle();
		hitBox.x = hitBoxDefaultX;
		hitBox.y = hitBoxDefaultY;
		hitBox.width = 32;
		hitBox.height = 38;
	}
	
	private void setImage()
	{
		try
		{
			i1 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_68.png"));	//Banana -->
			i2 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_61.png"));	//Pannocchia -->
			i3 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_65.png"));	//Carota -->
			i4 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_70.png"));	//Anguria -->
			i5 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_66.png"));	//Peperone -->
			i6 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_71.png"));	//Arancia -->
			i7 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_63.png"));	//Cipolla -->
			i8 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_64.png"));	//Melanzana -->
			i9 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_67.png"));	//Patatine -->
			i10 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_29.png"));	//Birra -->
			i11 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_41.png"));	//Pozione viola -->
			i12 = ImageIO.read(getClass().getResourceAsStream("/sprites/item/image_42.png"));	//Pozione gialla -->
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static ArrayList<PowerUp> getPowerUpList() { return powerUp; }
	
	public void effect()
	{
		
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		if(GameEngine.getInstance().getGameState() == 2)
		{
			CollisionChecker.checkFall(this);
			
			if(fallOn)
			{
				y += speed;
			}
		}
	}

	@Override
	public void die() 
	{
		powerUp.remove(this);
		GameEngine.getInstance().deleteObserver(this);	
	}

	@Override
	public void draw(Graphics2D g2) 
	{
		BufferedImage image = null;
		
		switch(value)
		{
			case 1:
				image = i1;
				break;
			case 2: 
				image = i2;
				break;
			case 3:
				image = i3;
				break;
			case 4: 
				image = i4;
				break;
			case 5:
				image = i5;
				break;
			case 6: 
				image = i6;
				break;
			case 7:
				image = i7;
				break;
			case 8: 
				image = i8;
				break;
			case 9:
				image = i9;
				break;
			case 10: 
				image = i10;
				break;
			case 11:
				image = i11;
				break;
			case 12: 
				image = i12;
				break;
		}
		g2.drawImage(image, x, y, WiewData.TILE_SIZE.getValue(),WiewData.TILE_SIZE.getValue(),null);
		
//		stampa dell'hitbox
		g2.setColor(Color.RED);
		g2.drawRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height);
	}
}
