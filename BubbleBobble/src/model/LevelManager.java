package model;

import java.util.Map;

import control.GameEngine;

/**
 * Questa classe gestisce i diversi livelli, e lo scrollamento della mappa al termine di un livello
 */
public class LevelManager
{	
	private static LevelManager levelManagerInstance;
	protected int currentLevel;
	protected int endedLevel;
	protected int currentPoints = 0;
	protected boolean gameEnded = false;
	protected boolean animationGoing = false;
	private int lastLevel = 1;
	protected Level level;
	
	/**
	 * Costruttore private
	 */
	private LevelManager() {}
	
	/**
	 * @return un istanza di LevelManager, altrimenti ne crea una
	 */
	public static LevelManager getInstance() 
	{
		if(levelManagerInstance == null) levelManagerInstance = new LevelManager();
		return levelManagerInstance;
	}
	
	/**
	 * @return true se il gioco è finito
	 */
	public boolean getGameEnded() {return gameEnded;}
	/**
	 * @return il livello corrente
	 */
	public int getCurrentLevel() {return currentLevel;}
	/**
	 * Modifica il livello corrente
	 * @param x nuovo livello
	 */
	public void setCurrentLevel(int x) { currentLevel = x;}
	
	/**
	 * Inizializza il livello in cui ci troviamo
	 */
	public void initLevel() {
		
		if(GameEngine.getInstance().getNewGameOn()) 
		{
			currentLevel = 0;
			GameEngine.getInstance().setNewGameOn(false);
		}

		
		switch(currentLevel) 
		{
			case 0:
				GameMap.getInstance().setIndexValidMap(2);
				level = new Level(currentLevel,0);
				level.setEnemySpawn(new TornadoDemon(),180,200);
				level.setEnemySpawn(new Mighta(), 300, 300);
				level.setEnemySpawn(new Invader(),500,480);
				//level.setEnemySpawn(new Hidegons(), 590, 470);
				break;
			case 1:
				level = new Level(currentLevel,20);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+600,GameMap.getInstance().indexValidMap+100);
				}
				
				break;
			}	
		}
	
	/**
	 * Incrementa il livello corrente, per passare al successivo
	 */
	public void setNextLevel() {currentLevel++;}
	
	/**
	 * Permette lo scrollamento al livello successivo
	 */
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
	
	/**
	 * Verifica la conclusione del livello
	 * @return true se non vi sono più nemici nella mappa
	 */
	public boolean levelEnded() {
		if(Enemy.getEnemies().isEmpty()) {
			if(!animationGoing) endedLevel = currentLevel;
			return true;
		}
		return false;
	}

}
