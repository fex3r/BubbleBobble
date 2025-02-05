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
import model.PowerUp;
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
	/**
	 * Costruttore privato LevelManager
	 */
	private LevelManager() {}
	
	/**
	 * @return l'istanza di LevelManager, altrimenti ne crea una
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
	 * @return il livello corrente, quello che sta visualizzando l'utente
	 */
	public int getCurrentLevel() {return currentLevel;}
	/**
	 * Sposta un nuovo livello e lo pone come corrente
	 * @param x livello che vogliamo rendere corrente
	 */
	public void setCurrentLevel(int x) { currentLevel = x;}
	
	/**
	 * Inizializzazione dei Level, in base al valore di current level, visualizza il livello corretto
	 * con i giusti nemici
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
				level.setEnemySpawn(new Mighta(), 250, 250);

				level.setEnemySpawn(new Banebou(),150, 150);

				break;
			case 1:
				level = new Level(currentLevel,21);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),50, 160);
					level.setEnemySpawn(new Banebou(),150, 150);

					level.setEnemySpawn(new Mighta(), 220, 670);
					level.setEnemySpawn(new Mighta(), 100, 300);
					
				}
				break;
			case 2:
				level = new Level(currentLevel,40);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new Banebou(),150, 150);
					level.setEnemySpawn(new Banebou(),300, 300);
					level.setEnemySpawn(new Banebou(),150, 120);

					level.setEnemySpawn(new Mighta(), 700, 300);
					level.setEnemySpawn(new Mighta(), 456, 120);
					level.setEnemySpawn(new Mighta(), 300, 300);
				}
				break;
			case 3:
				level = new Level(currentLevel,59);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),250,150);
					level.setEnemySpawn(new FlyBomb(),600,300);

					level.setEnemySpawn(new Banebou(),150, 150);
					

					level.setEnemySpawn(new Mighta(), 300, 300);
				}
				break;
			case 4:
				level = new Level(currentLevel,78);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					
					level.setEnemySpawn(new Hidegons(),150, 150);
					level.setEnemySpawn(new Hidegons(), 300, 300);
					
					level.setEnemySpawn(new Mighta(), 400, 121);
				}
				break;
			case 5:
				level = new Level(currentLevel,97);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					
					level.setEnemySpawn(new Hidegons(),150, 150);
					level.setEnemySpawn(new Hidegons(), 300, 300);
					
					level.setEnemySpawn(new Invader(),560, 421);
					level.setEnemySpawn(new Invader(), 300, 500);
				}
				break;
			case 6:
				level = new Level(currentLevel,116);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new TornadoDemon(),700,100);
					
					level.setEnemySpawn(new Invader(),150, 150);
					level.setEnemySpawn(new Invader(), 170, 707);
					
					level.setEnemySpawn(new Invader(),150, 400);
					level.setEnemySpawn(new Invader(), 260, 150);
				}
				break;
			case 7:
				level = new Level(currentLevel,135);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					level.setEnemySpawn(new FlyBomb(),100,100);
					
					level.setEnemySpawn(new TornadoDemon(),150, 150);
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
					
					level.setEnemySpawn(new Invader(), 100, 600);
				}
				break;
			case 8:
				level = new Level(currentLevel,154);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					level.setEnemySpawn(new FlyBomb(),600,200);
					
					level.setEnemySpawn(new Mighta(),150, 130);
					level.setEnemySpawn(new Mighta(), 300, 300);
					level.setEnemySpawn(new Mighta(),750, 150);
					level.setEnemySpawn(new Mighta(), 300, 600);
				}
				break;
			case 9:
				level = new Level(currentLevel,173);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					level.setEnemySpawn(new FlyBomb(),700,100);
					
					level.setEnemySpawn(new TornadoDemon(),150, 150);
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
					
					level.setEnemySpawn(new Hidegons(),150, 150);
					level.setEnemySpawn(new Hidegons(), 300, 300);
				}
				break;
			case 10:
				level = new Level(currentLevel,192);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					level.setEnemySpawn(new Hidegons(),120,100);
					
					level.setEnemySpawn(new TornadoDemon(),150, 150);
					level.setEnemySpawn(new Mighta(), 300, 300);
					
					level.setEnemySpawn(new Banebou(),450, 150);
					level.setEnemySpawn(new Invader(), 300, 600);
				}
				break;
			case 11:
				level = new Level(currentLevel,211);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),100,100);
					level.setEnemySpawn(new FlyBomb(),200,100);
					level.setEnemySpawn(new FlyBomb(),700,300);
					level.setEnemySpawn(new FlyBomb(),500,300);
				}
				break;
			case 12:
				level = new Level(currentLevel,230);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new TornadoDemon(),140,100);
					level.setEnemySpawn(new TornadoDemon(),700,540);
					
					level.setEnemySpawn(new TornadoDemon(),150, 150);
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
					
					level.setEnemySpawn(new Invader(),85, 621);
					

					level.setEnemySpawn(new Mighta(), 200, 300);
				}
				break;
			case 13: 
				level = new Level(currentLevel,249);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),230,100);
					level.setEnemySpawn(new FlyBomb(),670,340);
					level.setEnemySpawn(new FlyBomb(),700,100);
					
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
					level.setEnemySpawn(new TornadoDemon(),400, 400);
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
				}
				break;
			case 14:
				level = new Level(currentLevel,268);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),130,800);
					level.setEnemySpawn(new FlyBomb(),280,100);
					level.setEnemySpawn(new FlyBomb(),560,460);
					level.setEnemySpawn(new FlyBomb(),700,100);
					
					level.setEnemySpawn(new Banebou(),150, 150);
					level.setEnemySpawn(new Banebou(), 300, 300);
				}
				break;
			case 15:
				level = new Level(currentLevel,287);
				if(!GameEngine.getInstance().getGamePause()) {
					level.setEnemySpawn(new FlyBomb(),700,100);
					level.setEnemySpawn(new FlyBomb(),340,100);
					level.setEnemySpawn(new TornadoDemon(),670, 150);
					level.setEnemySpawn(new TornadoDemon(), 300, 300);
					level.setEnemySpawn(new Hidegons(),150, 860);
					level.setEnemySpawn(new Invader(), 100, 100);
				}
				break;
			
			}	
		}
	
	/**
	 * Incrementa current level per passare al livello successivo
	 */
	public void setNextLevel() {currentLevel++;}
	
	/**
	 * Effettua la transazione per passare al livello successivo
	 */
	public void nextLevelAnimation() 
	{
		GameEngine.getInstance().setGamePause(true);
		animationGoing = true;
		if(endedLevel == currentLevel) {
			PowerUp.getPowerUpList().clear();
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
	
	/**
	 * @return true se il livello è terminato, ovvero se non vi sono più nemici nella mappa
	 */
	public boolean levelEnded() {
		if(Enemy.getEnemies().isEmpty() && !Player.getInstance().getJump() && !Player.getInstance().getFallOn()) 
		{
			if(!animationGoing) endedLevel = currentLevel;
			return true;
		}
		return false;
	}

}
