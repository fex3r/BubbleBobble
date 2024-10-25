package control;

import java.util.Observable;

import view.GamePanel;

@SuppressWarnings("deprecation")
public class GameEngine extends Observable implements Runnable {
	
	private GamePanel gamePanel;
	Thread gameThread;
	int FPS = 60;
	
	
	public GameEngine(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	
	@Override
	public void run() {
		
		double drawStep = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawStep;
		
		
		while(gameThread != null) {
			
			long currentTime = System.nanoTime();
			
			setChanged();
			notifyObservers();
			
			gamePanel.repaint();
			
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				// conversione da nano a millisecondi per il metodo sleep
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawStep;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
}
