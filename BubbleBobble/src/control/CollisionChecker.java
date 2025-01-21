package control;

import model.Directions;
import model.Entity;
import model.GameMap;
import model.WiewData;

public class CollisionChecker {
	
	
	public CollisionChecker() {
		
	}
	
	public static void checkCollision(Entity entity) 
	{
		
		int leftTopX = entity.getX() + entity.getHitBox().x;
		int leftTopY = entity.getY() + entity.getHitBox().y;
		
		int rightTopX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int rightTopY = entity.getY() + entity.getHitBox().y;
		
		int leftBottomX = entity.getX() + entity.getHitBox().x;
		int leftBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		int rightBottomX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;;
		int rightBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		
		switch(entity.getDirection())
		{
			case LEFT:
				int leftTopXScaled = leftTopX/WiewData.TILE_SIZE.getValue();
				int leftBottomXScaled = leftBottomX/WiewData.TILE_SIZE.getValue();
				int leftTopYScaled = leftTopY/WiewData.TILE_SIZE.getValue();
				int leftBottomYScaled = leftBottomY/WiewData.TILE_SIZE.getValue();
				if(GameMap.solidBlocks.contains(GameMap.getValue(leftTopYScaled, leftTopXScaled)) || GameMap.solidBlocks.contains(GameMap.getValue(leftBottomYScaled, leftBottomXScaled)))
				{
					entity.setHitBox(true);
				}
				break;
			case RIGHT:
				int rightTopXScaled = rightTopX/WiewData.TILE_SIZE.getValue();
				int rightBottomXScaled = rightBottomX/WiewData.TILE_SIZE.getValue();
				int rightTopYScaled = rightTopY/WiewData.TILE_SIZE.getValue();
				int rightBottomYScaled = rightBottomY/WiewData.TILE_SIZE.getValue();
				if(GameMap.solidBlocks.contains(GameMap.getValue(rightTopYScaled, rightTopXScaled)) || GameMap.solidBlocks.contains(GameMap.getValue(rightBottomYScaled, rightBottomXScaled)))
				{
					entity.setHitBox(true);
				}
				break;		
		}
		
		int leftBottomXScaled = (leftBottomX + entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		int rightBottomXScaled = (rightBottomX+ entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		int leftBottomYScaled = (leftBottomY+ entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		int rightBottomYScaled = (rightBottomY+ entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		if(GameMap.solidBlocks.contains(GameMap.getValue(leftBottomYScaled, leftBottomXScaled)) || GameMap.solidBlocks.contains(GameMap.getValue(rightBottomYScaled, rightBottomXScaled)))
		{
			entity.setFall(false);
		}
		
	}
}
