package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{	
	private boolean up,right,left,down; //stato dei 4 tasti di direzione
	
	@Override
	public void keyTyped(KeyEvent e) { } //è più specifico e si occupa del caso in cui il tasto premuto rappresenti un carattere visibile

	@Override
	public void keyPressed(KeyEvent e) //comportamento per ogni tasto premuto
	{
		int code = e.getKeyCode(); //contiene il codice del tasto premuto e gli if attivano lo stato di esso
		if( code == KeyEvent.VK_W) up = true;
		if( code == KeyEvent.VK_S) down = true;
		if( code == KeyEvent.VK_A) left = true;
		if( code == KeyEvent.VK_D) right = true;	
	}

	@Override
	public void keyReleased(KeyEvent e) //comportamento per ogni tasto rilasciato
	{
		int code = e.getKeyCode(); //contiene il codice del tasto rilasciato e gli if disattivano lo stato di esso
		if( code == KeyEvent.VK_W) up = false;
		if( code == KeyEvent.VK_S) down = false;
		if( code == KeyEvent.VK_A) left = false;
		if( code == KeyEvent.VK_D) right = false;
	}

	//Getter
	public boolean isUp() { return up; }
	public boolean isRight() { return right; }
	public boolean isLeft() { return left; }
	public boolean isDown() { return down; }
}
