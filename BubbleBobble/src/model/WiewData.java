package model;
/**
 * Questa enumerazione contiene tutti i dati riguardante lo schermo di gioco
 */
public enum WiewData 
{
	/**
	 * Grandezza iniziale di un quadrato di gioco
	 */
	STARTING_TILE_SIZE(16),
	/**
	 * Scalatura
	 */
	SCALE(3),
	/**
	 * Grandezza di un quadrato di gioco
	 */
	TILE_SIZE(STARTING_TILE_SIZE.value*SCALE.value),
	/**
	 * Larghezza della schermata di gioco (in quadrati di gioco)
	 */
	MAX_SCREEN_COL(20),
	/**
	 * Altezza della schermata di gioco (in quadrati di gioco)
	 */
	MAX_SCREEN_ROW(16),
	/**
	 * Larghezza della schermata di gioco (in pixel) 
	 */
	GAME_SCREEN_WIDTH(MAX_SCREEN_COL.value*TILE_SIZE.value),
	/**
	 * Altezza della schermata di gioco (in pizel)
	 */
	GAME_SCREEM_HEIGHT(MAX_SCREEN_ROW.value*TILE_SIZE.value);
	
	/**
	 * Rappresenta il valore di un determinato dato
	 */
	private final int value;

	/**
	 * Costruttore di WiewData
	 * @param value
	 */
	WiewData(int value) {
		this.value = value;
	}
	
	/**
	 * @return il valore di quel determinato dato
	 */
	public int getValue() {
		return value;
	}
}
