package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import model.Player;

public class KeyHandler implements KeyListener
{	
	private static KeyHandler keyHandlerInstance;
	private boolean up,right,left,down; //stato dei 4 tasti di direzione
	private boolean space,enter; //tasti per invio nel menu 
	private boolean shot;
	private boolean isShooting = false;
	private boolean isJumping = false;
	
	private KeyHandler() {} ;
	public static KeyHandler getInstance() {
		if(keyHandlerInstance == null) { keyHandlerInstance = new KeyHandler(); }
		return keyHandlerInstance;
	}
	
	public void resetKeys() {
	        up = false;
	        down = false;
	        left = false;
	        right = false;
	    }
	
	@Override
	public void keyTyped(KeyEvent e) { } //è più specifico e si occupa del caso in cui il tasto premuto rappresenti un carattere visibile

	@Override
	public void keyPressed(KeyEvent e) //comportamento per ogni tasto premuto
	{
		int code = e.getKeyCode(); //contiene il codice del tasto premuto e gli if attivano lo stato di esso
		
		if(GameEngine.getInstance().getGameState() == 2 && code == KeyEvent.VK_W && !Player.getInstance().getFallOn())
		{
			up = true;
			isJumping = true;
		}
		
		if(GameEngine.getInstance().getGameState() == 0) {
			if(code == KeyEvent.VK_S) down = true ;
			if(code == KeyEvent.VK_W) up = true;
		}
		
		if(code == KeyEvent.VK_A) left = true;
		if(code == KeyEvent.VK_D) right = true;
		if(code == KeyEvent.VK_SPACE && isShooting == false)
		{
			space = true;
			shot = true;
			isShooting = true;
		}
		if(code == KeyEvent.VK_ENTER) {
			enter = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) //comportamento per ogni tasto rilasciato
	{
		int code = e.getKeyCode(); //contiene il codice del tasto rilasciato e gli if disattivano lo stato di esso
		if( code == KeyEvent.VK_W)
		{
			up = false;
			isJumping = false;
		}
		if( code == KeyEvent.VK_A) left = false;
		if( code == KeyEvent.VK_D) right = false;
		if(code == KeyEvent.VK_SPACE)
		{
			space = false;
			shot = false;
			isShooting = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enter = false;
		}
	}

	//Getter
	public boolean isUp() { return up; }
	public boolean isRight() { return right; }
	public boolean isLeft() { return left; }
	public boolean isDown() { return down; }
	public boolean getEnter() { return enter | space; }
	
	public boolean isJumping()
	{
		boolean wasJumping = up;
		up = false;
		return wasJumping; 
	}
	public boolean isShooting()
	{
		boolean wasShooting = shot;
		shot = false;
		return wasShooting; 
	}
	
	//Setter
	public void setUp(boolean x) { up = x; }
	public void setIsJumping(boolean x) { isJumping = x; }
	public void setRight(boolean x) { right = x; }
	public void setLeft(boolean x) { left = x; }
	public void setDown(boolean x) { down = x; }
}
