package model;

import java.util.Map;

import control.GameEngine;

public class LevelManager {
	
	private static LevelManager levelManagerInstance;
	protected int currentLevel;
	protected int currentPoints = 0;
	protected boolean gameEnded = false;
	private int lastLevel = 1;
	protected Level level;
	
	private LevelManager() {}
	
	public static LevelManager getInstance() {
		if(levelManagerInstance == null) levelManagerInstance = new LevelManager();
		return levelManagerInstance;
		
	}
	
	public boolean getGameEnded() {return gameEnded;}
	public int getCurrentLevel() {return currentLevel;}
	public void setCurrentLevel(int x) { currentLevel = x;}
	
	public void initLevel() {
		
		if(GameEngine.getInstance().getNewGameOn()) {
			currentLevel = 0;
			GameEngine.getInstance().setNewGameOn(false);
		}

		
			switch(currentLevel) {
			case 0:
				level = new Level(currentLevel,0);
				level.setEnemySpawn(new TornadoDemon(), 700, 200);
				GameMap.getInstance().setIndexValidMap(0);
				break;
			}	
		}
	
	public void setNextLevel() {currentLevel++;}
	
	public void nextLevelAnimation() {
		GameEngine.getInstance().setGamePause(true);
		
		if(level.levelMapX > GameMap.getInstance().getIndexValidMap()) {
			
			GameMap.getInstance().increaseIndexValidMap();
			Player.getInstance().setX(Player.getInstance().x + GameMap.getInstance().getIndexValidMap());
			
		}else { GameEngine.getInstance().setGamePause(false);}
	}
	
	public boolean levelEnded() {
		if(Enemy.getEnemies().isEmpty()) {
			return true;
		}
		return false;
	}

}
