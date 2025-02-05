package model;

import java.util.ArrayList;

/**
 * La classe Level gestisce i singoli livelli, permettendo la creazione di nemici in un determinato livello
 */
public class Level 
{	
	protected int levelNum;
	public int levelMapX;
	protected ArrayList<EnemySpawn> enemySpawn;
	
	/**
	 * Costruttore di Level
	 * @param levelNum numero del livello
	 * @param levelMapX altezza del livello rispetto la mappa intera
	 */
	public Level(int levelNum,int levelMapX) 
	{
		this.levelNum = levelNum;
		this.levelMapX = levelMapX;
		this.enemySpawn = new ArrayList();
	}
	
	/**
	 * Permette di avere le informazioni su chi è il nemico da creare e dove posizionarlo
	 */
	private static class EnemySpawn
	{
		private Enemy enemy;
		
		/**
		 * Crea un nuovo nemico nel livello
		 * @param enemy nemico da creare
		 * @param xSpawn indice x
		 * @param ySpawn indice y
		 */
		protected EnemySpawn(Enemy enemy,int xSpawn,int ySpawn) 
		{
			this.enemy = enemy;
			enemy.setX(xSpawn);
			enemy.setY(ySpawn);
		}
		/**
		 * @return nemico
		 */
		public Enemy getEnemyType() {return enemy;}
	} 
	
	/**
	 * Aggiunge lo spawn di nemici nella lista
	 */
	public void setEnemySpawn(Enemy enemy,int xSpawn,int ySpawn) 
	{
		enemySpawn.add(new EnemySpawn(enemy,xSpawn,ySpawn));
	}
}
