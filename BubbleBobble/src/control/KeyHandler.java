package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import model.Player;

public class KeyHandler implements KeyListener
{	
	private boolean up,right,left,down; //stato dei 4 tasti di direzione
	private boolean shot;
	private boolean isShooting = false;
	
	private boolean isJumping = false;
	
	
	
	@Override
	public void keyTyped(KeyEvent e) { } //è più specifico e si occupa del caso in cui il tasto premuto rappresenti un carattere visibile

	@Override
	public void keyPressed(KeyEvent e) //comportamento per ogni tasto premuto
	{
		int code = e.getKeyCode(); //contiene il codice del tasto premuto e gli if attivano lo stato di esso
		
		if (code == KeyEvent.VK_W && isJumping == false) //***************Inserisci anche il controllo se sotto ha il pavimento*****************
		{
			up = true;
		}
		
		if(code == KeyEvent.VK_S) down = true;
		if(code == KeyEvent.VK_A) left = true;
		if(code == KeyEvent.VK_D) right = true;
		if(code == KeyEvent.VK_SPACE && isShooting == false)
		{
			shot = true;
			isShooting = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) //comportamento per ogni tasto rilasciato
	{
		int code = e.getKeyCode(); //contiene il codice del tasto rilasciato e gli if disattivano lo stato di esso
		if( code == KeyEvent.VK_W) up = false; 
		if( code == KeyEvent.VK_S) down = false;
		if( code == KeyEvent.VK_A) left = false;
		if( code == KeyEvent.VK_D) right = false;
		if(code == KeyEvent.VK_SPACE)
		{
			shot = false;
			isShooting = false;
		}
	}

	//Getter
	public boolean isUp() { return isJumping; }
	public boolean isRight() { return right; }
	public boolean isLeft() { return left; }
	public boolean isDown() { return down; }
	public boolean isShooting()
	{
		boolean wasShooting = shot;
		shot = false;
		return wasShooting; 
	}
	
	//Setter
	public void setUp(boolean x) { up = x; }
}
