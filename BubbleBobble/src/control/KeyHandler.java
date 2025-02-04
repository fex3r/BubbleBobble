package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import model.Player;

/**
 * Gestisce gli input di tastiera
 */
public class KeyHandler implements KeyListener
{	
	private static KeyHandler keyHandlerInstance;
	private boolean up,right,left,down; //stato dei 4 tasti di direzione
	private boolean space,enter; //tasti per invio nel menu 
	private boolean shot;
	private boolean pause;
	private boolean isShooting = false;
	private boolean isJumping = false;
	
	
	private KeyHandler() { } //Costruttore private per singleton pattern
	
	/**
	 * @return l'istanza di key handler, altrimenti ne crea una
	 */
	public static KeyHandler getInstance() 
	{
		if(keyHandlerInstance == null) { keyHandlerInstance = new KeyHandler(); }
		return keyHandlerInstance;
	}
	
	/**
	 * Imposta lo stato dei 4 tasti di direzione a false
	 */
	public void resetKeys() 
	{
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
		
		if(GameEngine.getInstance().getGameState() == 0 || GameEngine.getInstance().getGameState() == 3) {
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
		if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P) {pause = true;}
		
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
		
		if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P) { pause = false; }
	}

	//Getter
	/**
	 * @return true se è premuto il tasto Up sulla tastiera
	 */
	public boolean isUp() { return up; }
	/**
	 * @return true se è premuto il tasto Right sulla tastiera
	 */
	public boolean isRight() { return right; }
	/**
	 * @return true se è premuto il tasto Left sulla tastiera
	 */
	public boolean isLeft() { return left; }
	/**
	 * @return true se è premuto il tasto Down sulla tastiera
	 */
	public boolean isDown() { return down; }
	/**
	 * @return true se è premuto il tasto spazio sulla tastiera
	 */
	public boolean getEnter() { return enter | space; }
	/**
	 * @return true se è stato premuto il tasto esc sulla tastiera 
	 */
	public boolean getpause() {return pause;}
	
	/**
	 * @return true se Player sta eseguendo un salto
	 */
	public boolean isJumping()
	{
		boolean wasJumping = up;
		up = false;
		return wasJumping; 
	}
	
	/**
	 * @return true se Player sta eseguendo uno sparo
	 */
	public boolean isShooting()
	{
		boolean wasShooting = shot;
		shot = false;
		return wasShooting; 
	}
	
	/**
	 * Assegna x all'attributo up
	 * @param x valore da assegnare a up
	 */
	public void setUp(boolean x) { up = x; }
	/**
	 * Assegna x all'attributo isJumping, 
	 * @param x valore da assegnare a isJumping
	 */
	public void setIsJumping(boolean x) { isJumping = x; }
	/**
	 * Assegna x all'attributo right
	 * @param x valore da assegnare a right
	 */
	public void setRight(boolean x) { right = x; }
	/**
	 * Assegna x all'attributo left
	 * @param x valore da assegnare a left
	 */
	public void setLeft(boolean x) { left = x; }
	/**
	 * Assegna x all'attributo down
	 * @param x valore da assegnare a down
	 */
	public void setDown(boolean x) { down = x; }
	/**
	 * Assegna x all'attributo space e enter
	 * @param x valore da assegnare a space e enter
	 */
	public void setEnter(boolean x) 
	{
		enter = x;
		space = x;
	}
}
