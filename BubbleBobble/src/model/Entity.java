package model;

public abstract class Entity 
{
	private String name;
	private int x; //Posizione dell' entità nella coordinata x
	private int y; //Posizione dell' entità nella coordinata y
	private static final int entitySpeed = 4; //Velocità dell'entità (è uguale per player e nemici)
		
	public Entity(String name) { this.name = name; } //Costruttore (per ora vuoto)
		
	//Getter 
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSpeed() { return entitySpeed; }
		
	//Setter
	public void setX(int newX) { x = newX; }
	public void setY(int newY) { y = newY; }	
}
