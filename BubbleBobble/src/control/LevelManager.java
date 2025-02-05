package control;

import java.util.Map;

import model.Banebou;
import model.Enemy;
import model.FlyBomb;
import model.GameMap;
import model.Hidegons;
import model.Invader;
import model.Level;
import model.Mighta;
import model.Player;
import model.TornadoDemon;

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
				GameMap.getInstance().setIndexValidMap(2);
				level = new Level(currentLevel,0);
				level.setEnemySpawn(new TornadoDemon(),180,200);
				level.setEnemySpawn(new Mighta(), 300, 300);
				level.setEnemySpawn(new Invader(),500,480);
				level.setEnemySpawn(new Hidegons(), 590, 470);
				break;
			case 1:
				level = new Level(currentLevel,21);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),150, 150);
					level.setEnemySpawn(new FlyBomb(),600, 100);
					level.setEnemySpawn(new TornadoDemon(),180,200);
				}
				break;
			case 2:
				level = new Level(currentLevel,40);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
					level.setEnemySpawn(new TornadoDemon(),GameMap.getInstance().indexValidMap+180,GameMap.getInstance().indexValidMap+200);
				}
				break;
			case 3:
				level = new Level(currentLevel,59);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
				}
				break;
			case 4:
				level = new Level(currentLevel,78);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
				}
				break;
			case 5:
				level = new Level(currentLevel,97);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
				}
				break;
			case 6:
				level = new Level(currentLevel,116);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
				}
				break;
			case 7:
				level = new Level(currentLevel,135);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),GameMap.getInstance().indexValidMap+ 150, GameMap.getInstance().indexValidMap+150);
					level.setEnemySpawn(new FlyBomb(),GameMap.getInstance().indexValidMap+ 700,GameMap.getInstance().indexValidMap+100);
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
			if(level.levelMapX > GameMap.getInstance().getIndexValidMap()) GameMap.getInstance().increaseIndexValidMap();
			
			
			if(Player.getInstance().getX() > 400) {
				System.out.println("entro qua");
				Player.getInstance().setX(Player.getInstance().getX() - 48);
			}else if(Player.getInstance().getX() < 400) {
				Player.getInstance().setX(Player.getInstance().getX() + 48);
			}
		
			if(Player.getInstance().getY() > 64) {
				
				Player.getInstance().setY(Player.getInstance().getY() - 48);
				
			}else if(Player.getInstance().getY() < 64) {
				
				Player.getInstance().setY(Player.getInstance().getY() + 48);
			}
			
			
			
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
