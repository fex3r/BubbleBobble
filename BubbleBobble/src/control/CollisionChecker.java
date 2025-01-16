package control;

import model.Directions;
import model.Entity;
import model.GameMap;
import model.WiewData;

public class CollisionChecker {
	
	
	public CollisionChecker() {
		
	}
	
	public static void checkCollision(Entity entity) {
		
		int leftHitX = entity.getX() + entity.getHitBox().x;
		int rightHitX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int topHitY = entity.getY() + entity.getHitBox().y;
		int bottomHitY = entity.getY() + entity.getHitBox().y + entity.getHitBox().height;
		
		int leftColHit = leftHitX/WiewData.TILE_SIZE.getValue();
		int rightColHit = rightHitX/WiewData.TILE_SIZE.getValue();
		int topRowHit = topHitY/WiewData.TILE_SIZE.getValue();
		int bottomRowHit = bottomHitY/WiewData.TILE_SIZE.getValue();
		
		int tile1,tile2;
		
		
		switch(entity.getDirection()) {
		
		case LEFT:
			leftColHit = (leftHitX - entity.getSpeed())/WiewData.TILE_SIZE.getValue();
			tile1 = GameMap.getValue(leftColHit,topRowHit);
			tile2 = GameMap.getValue(rightColHit,bottomRowHit);
			if(GameMap.solidBlocks.contains(tile1) || GameMap.solidBlocks.contains(tile2) ) {
				entity.setHitBox(true);
			}
		case RIGHT:
			rightColHit = (rightHitX + entity.getSpeed())/WiewData.TILE_SIZE.getValue();
			tile1 = GameMap.getValue(leftColHit,topRowHit);
			tile2 = GameMap.getValue(rightColHit,bottomRowHit);
			if(GameMap.solidBlocks.contains(tile1) || GameMap.solidBlocks.contains(tile2) ) {
				entity.setHitBox(true);
			}
		}
		
		
	}
	
}
