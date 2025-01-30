package control;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.GameMap;
import model.Player;
import model.Shot;
import model.TornadoDemon;
import view.GameMenu;
import view.GamePanel;
import view.LayoutContainer;

public class Main {

	public static void main(String[] args) 
	{	
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BUBBLE BOBBLE");
		
		//gamePanel andrà fatto sparire da qua
		GamePanel gamePanel = GamePanel.getInstance();
		GameEngine gameEngine = GameEngine.getInstance();
		
		
		gameEngine.addObserver(Player.getInstance());
		
		//da togliere
		TornadoDemon demon = new TornadoDemon();
		gamePanel.setDemon(demon);
		

		
		window.add(LayoutContainer.getInstance());
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		
		
		window.setVisible(true);
		
		gameEngine.startGameThread();
		
	}
}
