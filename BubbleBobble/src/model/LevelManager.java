package model;

import java.util.Map;

import control.GameEngine;

public class LevelManager {
	
	private static LevelManager levelManagerInstance;
	protected int currentLevel;
	protected int endedLevel;
	protected int currentPoints = 0;
	protected boolean gameEnded = false;
	protected boolean animationGoing = false;
	private int lastLevel = 1;
	protected Level level;
	
	private LevelManager() {}
	
	public static LevelManager getInstance() 
	{
		if(levelManagerInstance == null) levelManagerInstance = new LevelManager();
		return levelManagerInstance;
	}
	
	public boolean getGameEnded() {return gameEnded;}
	public int getCurrentLevel() {return currentLevel;}
	public void setCurrentLevel(int x) { currentLevel = x;}
	
	public void initLevel() {
		
		if(GameEngine.getInstance().getNewGameOn()) 
		{
			currentLevel = 0;
			GameEngine.getInstance().setNewGameOn(false);
		}

		
		switch(currentLevel) 
		{
			case 0:
				GameMap.getInstance().setIndexValidMap(0);
				level = new Level(currentLevel,0);
				level.setEnemySpawn(new TornadoDemon(),200,200);
				level.setEnemySpawn(new Invader(),500,480);
				break;
			case 1:
				level = new Level(currentLevel,19);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+600,GameMap.getInstance().indexValidMap+100);
				}
				
				break;
			}	
		}
	
	public void setNextLevel() {currentLevel++;}
	
	public void nextLevelAnimation() 
	{
		GameEngine.getInstance().setGamePause(true);
		animationGoing = true;
		if(endedLevel == currentLevel) {
			setNextLevel();
			initLevel();
			
		}
		
		if(level.levelMapX > GameMap.getInstance().getIndexValidMap()) {
			GameMap.getInstance().increaseIndexValidMap();
			Player.getInstance().setY(Player.getInstance().y++);
			
		}else { 
			GameEngine.getInstance().setGamePause(false);
			initLevel();
			animationGoing = false;
			}
	}
	
	public boolean levelEnded() {
		if(Enemy.getEnemies().isEmpty()) {
			if(!animationGoing) endedLevel = currentLevel;
			return true;
		}
		return false;
	}

}
