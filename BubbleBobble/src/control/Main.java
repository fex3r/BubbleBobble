package control;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.GameMap;
import model.Player;
import model.Shot;
import view.GamePanel;

public class Main {

	public static void main(String[] args) 
	{	
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BUBBLE BOBBLE");
		
		GamePanel gamePanel = new GamePanel();
		GameEngine gameEngine = new GameEngine(gamePanel);
		
		
		gameEngine.addObserver(Player.getInstance());
		
		
		window.add(gamePanel);
		window.pack();
		
		window.setLocationRelativeTo(null);
		
		
		window.setVisible(true);
		
		gameEngine.startGameThread();
		
	}
}
