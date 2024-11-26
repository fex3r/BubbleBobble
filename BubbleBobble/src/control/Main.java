package control;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.GameMap;
import model.Player;
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
		
		/*
		
		// Crea uno JScrollPane con il pannello
        JScrollPane scrollPane = new JScrollPane(gamePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Aggiungi lo JScrollPane al frame
        window.add(scrollPane);
        
        */
		
		window.setVisible(true);
		
		gameEngine.startGameThread();
		
	}
}
