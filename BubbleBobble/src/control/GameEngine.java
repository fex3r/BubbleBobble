package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.Enemy;
import model.Entity;
import model.Player;
import model.PowerUp;
import model.Shot;
import view.GameMenu;
import view.GamePanel;
import view.LayoutContainer;

@SuppressWarnings("deprecation") //elimina gli avvertimenti relativi all'uso di classi deprecate

public class GameEngine extends Observable implements Runnable 
{
	private static GameEngine gameEngineInstance;
	Thread gameThread; 
	private int FPS = 60;
	private int animationFrame = 0;
	private static int gameState = 0;
	private static final int menuState = 0;
	private static final int loadState = 1;
	private static final int playState = 2;
	private static final int pauseState = 3;
	private static boolean newGameOn;
	private static boolean workingOnProfiles = false;
	private static boolean gamePause = false;
	private List<Shot> diedShots;
	private List<Enemy> diedEnemies;
	private List<PowerUp> diedPower;
	private String musicMenu = "/music/cowboy.wav";
	
	private GameEngine() {
		diedShots = new ArrayList<>();
		diedEnemies = new ArrayList<>();
		diedPower = new ArrayList<>();
	}
	
	// Singleton pattern
	public static GameEngine getInstance() 
	{
		if(gameEngineInstance == null) gameEngineInstance = new GameEngine();
		return gameEngineInstance;
	}
		
	
	@Override
	public void run() 
	{	
		double drawStep = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawStep; //calcola il tempo del prossimo aggiornamento

		while(gameThread != null) 
		{	
			
			long currentTime = System.nanoTime(); //tempo corrente

			if(gamePause == false) {
				setChanged();
				notifyObservers();
			}
			
			
			if(gameState == menuState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.MENU_CARD)) {
				AudioManager.getInstance().play(musicMenu, true);
				LayoutContainer.getInstance().showCard(LayoutContainer.MENU_CARD);
				KeyHandler.getInstance().resetKeys();
			}
			else if(gameState == playState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.GAME_CARD)) {
				LayoutContainer.getInstance().showCard(LayoutContainer.GAME_CARD);
				KeyHandler.getInstance().resetKeys();
				
			}else if(gameState == pauseState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.PAUSE_CARD)) {
				LayoutContainer.getInstance().showCard(LayoutContainer.PAUSE_CARD);
				KeyHandler.getInstance().resetKeys();
				
			}else if(gameState == loadState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.LOAD_MENU_CARD)) {
				LayoutContainer.getInstance().showCard(LayoutContainer.LOAD_MENU_CARD);
				KeyHandler.getInstance().resetKeys();
			}
			
			if(gameState == playState && LevelManager.getInstance().levelEnded() && LayoutContainer.getInstance().getCardName().equals(LayoutContainer.GAME_CARD)) {
				animationFrame++;
				if(animationFrame >5) {
					LevelManager.getInstance().nextLevelAnimation();
					animationFrame = 0;
				}
				
				
			}
			
			if(!workingOnProfiles) {
				LayoutContainer.getInstance().repaint();
			}
			
			
			//controllo se i proiettili colpiscono qualcosa
			//gli shot fanno danno solo quando il bubble status è false
			Shot.getShots().stream()
			.forEach(shot -> Enemy.getEnemies()
			.stream()
			.forEach(enemy -> {
				if(CollisionChecker.checkHit(shot, enemy) && enemy.getBubbleStatus() == false && shot.getOwner().equals(Player.getInstance())) {
			
				diedShots.add(shot);
				diedEnemies.add(enemy);
			
				}
			}));
			
			//il personaggio fa danno quando il bubblestatus è true
			Enemy.getEnemies().forEach(enemy -> {
				if(CollisionChecker.checkHit(Player.getInstance(), enemy) && enemy.getBubbleStatus() == true) {
					diedEnemies.add(enemy);
				}else if(CollisionChecker.checkHit(Player.getInstance(), enemy)) {
					Player.getInstance().die();
				}
			});
			
			Shot.getShots().forEach(shot -> {
				if(CollisionChecker.checkHit(shot, Player.getInstance())) {
					Player.getInstance().die();
				}
			}
			);
			
			PowerUp.powerUp.forEach(power ->{
				if(CollisionChecker.checkHit(Player.getInstance(), power)) {
					diedPower.add(power);
				}
			}
			);
			
			
			
			
			diedShots.forEach(Shot::die);
			diedEnemies.forEach(Enemy::die);
			diedPower.forEach(PowerUp::die);
			
			diedShots.clear();
			diedEnemies.clear();
			diedPower.clear();
			
			
			try 
			{
				double remainingTime = nextDrawTime - System.nanoTime(); //tempo fino a prossimo aggiornamento
				
				remainingTime = remainingTime/1000000; //conversione da nano a millisecondi per il metodo sleep
				
				if(remainingTime < 0) remainingTime = 0;
				
				Thread.sleep((long) remainingTime); //mette in pausa il thread
				
				nextDrawTime += drawStep; //aggiorna il tempo del prossimo aggiornamento
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void startGameThread() 
	{	
		gameThread = new Thread(this);
		gameThread.start(); //Avvio del thread
	}
	
	public void setGameState(int state) {gameState = state;}
	public void setNewGameOn(boolean bool) {newGameOn = bool;}
	public void setGamePause(boolean bool) { gamePause = bool;}
	public void setWorkingOnProfiles(boolean bool) { workingOnProfiles = bool;}
	
	public int getGameState() {return gameState;}
	public boolean getNewGameOn() {return newGameOn;}
	public boolean getGamePause() {return gamePause;}
	
	
}
