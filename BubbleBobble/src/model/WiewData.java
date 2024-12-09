package model;

public enum WiewData {
	
	STARTING_TILE_SIZE(16),
	SCALE(3),
	TILE_SIZE(STARTING_TILE_SIZE.value*SCALE.value),
	MAX_SCREEN_COL(20),
	MAX_SCREEN_ROW(16),
	GAME_SCREEN_WIDTH(MAX_SCREEN_COL.value*TILE_SIZE.value),
	GAME_SCREEM_HEIGHT(MAX_SCREEN_ROW.value*TILE_SIZE.value);
	
	private final int value;

	WiewData(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
