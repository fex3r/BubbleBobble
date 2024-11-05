package control;

import java.util.Observable;

import view.GamePanel;

@SuppressWarnings("deprecation") //elimina gli avvertimenti relativi all'uso di classi deprecate

public class GameEngine extends Observable implements Runnable 
{	
	private GamePanel gamePanel;
	Thread gameThread; 
	int FPS = 60;
	
	public GameEngine(GamePanel gamePanel) { this.gamePanel = gamePanel; }
	
	@Override
	public void run() 
	{	
		double drawStep = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawStep; //calcola il tempo del prossimo aggiornamento
		
		while(gameThread != null) 
		{	
			long currentTime = System.nanoTime(); //tempo corrente
			
			setChanged();
			notifyObservers();
			
			gamePanel.repaint(); //modifica l'interfaccia
			
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
	
}
