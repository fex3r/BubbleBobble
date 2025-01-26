package control;

import java.util.Observable;

import model.Player;
import view.GameMenu;
import view.GamePanel;
import view.LayoutContainer;

@SuppressWarnings("deprecation") //elimina gli avvertimenti relativi all'uso di classi deprecate

public class GameEngine extends Observable implements Runnable 
{	
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private static GameEngine gameEngineInstance;
	Thread gameThread; 
	private int FPS = 60;
	private static int gameState = 0;
	private static final int menuState = 0;
	private static final int playState = 1;
	
	private GameEngine(GamePanel gamePanel, GameMenu gameMenu) { 
		this.gamePanel = gamePanel;
		this.gameMenu = gameMenu;
	}
	
	// Singleton pattern
	public static GameEngine getInstance() 
	{
		if(gameEngineInstance == null) gameEngineInstance = new GameEngine(GamePanel.getInstance(),GameMenu.getInstance());
		return gameEngineInstance;
	}
		
	
	@Override
	public void run() 
	{	
		double drawStep = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawStep; //calcola il tempo del prossimo aggiornamento
		//gameMenu.repaint();
		while(gameThread != null) 
		{	
			long currentTime = System.nanoTime(); //tempo corrente
			
			setChanged();
			notifyObservers();
			
			if(gameState == menuState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.MENU_CARD)) {
				LayoutContainer.getInstance().showCard(LayoutContainer.MENU_CARD);;
				KeyHandler.getInstance().resetKeys();
			}
			else if(gameState == playState && !LayoutContainer.getInstance().getCardName().equals(LayoutContainer.GAME_CARD)) {
				LayoutContainer.getInstance().showCard(LayoutContainer.GAME_CARD);
				KeyHandler.getInstance().resetKeys();
			}
			
			
			LayoutContainer.getInstance().repaint();
			
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
	
	public void setGameState(int state) {
		gameState = state;
	}
	public int getGameState() {
		return gameState;
	}
	
}
