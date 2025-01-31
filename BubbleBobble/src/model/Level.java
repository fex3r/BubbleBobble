package model;

import java.util.ArrayList;

public class Level {
	
	protected int levelNum;
	protected int levelMapX;
	protected ArrayList<EnemySpawn> enemySpawn;
	
	
	public Level(int levelNum,int levelMapX) {
		this.levelNum = levelNum;
		this.levelMapX = levelMapX;
		this.enemySpawn = new ArrayList();
	}
	
	private static class EnemySpawn{
		private Enemy enemy;
		
		
		protected EnemySpawn(Enemy enemy,int xSpawn,int ySpawn) {
			this.enemy = enemy;
			enemy.setX(xSpawn);
			enemy.setY(ySpawn);
		}
		public Enemy getEnemyType() {return enemy;}
	} 
	
	public void setEnemySpawn(Enemy enemy,int xSpawn,int ySpawn) {
		enemySpawn.add(new EnemySpawn(enemy,xSpawn,ySpawn));
	}
}
