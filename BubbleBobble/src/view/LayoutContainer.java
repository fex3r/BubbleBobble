package view;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class LayoutContainer extends JPanel {
	
	private static LayoutContainer layoutContainerInstance;
	private CardLayout cardLayout;
	public static final String MENU_CARD = "Menu";
	public static final String LOAD_MENU_CARD = "Load";
    public static final String GAME_CARD = "Game";
    private String cardName  = "";
    
	private LayoutContainer() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		this.setDoubleBuffered(true); //Migliora la qualità video
		this.setIgnoreRepaint(true); //diciamo alle swing di non preoccuparsi del repaint che potrebbe entrare in conflitto con i nostri repaint gestiti dal GameLoop
		
		add(GameMenu.getInstance(), MENU_CARD);
        add(GamePanel.getInstance(), GAME_CARD);
	}
	
	public static LayoutContainer getInstance() {
		if(layoutContainerInstance == null) layoutContainerInstance = new LayoutContainer();
		return layoutContainerInstance;
	}
	
	public void showCard(String cardName) {
		
        cardLayout.show(this, cardName);
        
        if(cardName.equals(MENU_CARD)){
        	GameMenu.getInstance().requestFocusInWindow();
        }else if(cardName.equals(GAME_CARD)) {
        	GamePanel.getInstance().requestFocusInWindow();
        }
        
        this.cardName = cardName;
    }
	
	public String getCardName() {
		return cardName;
	}
	
}
