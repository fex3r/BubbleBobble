package control;

import java.util.ArrayList;

import model.Directions;
import model.Entity;
import model.GameMap;
import model.Shot;
import model.WiewData;

/**
 * La classe CollisionChecker ha lo scopo di verificare le collisioni tra personaggio, nemici e bolle
 */
public class CollisionChecker 
{
	/**
	 * Costruttore di CollisionChecker
	 */
	public CollisionChecker() { }
	
	/**
	 * Verifica se l'entità passata come parametro, ha una collisione orizzontale con un blocco della mappa
	 * @param entity rappresenta l'entità a cui viene effettuato il controllo
	 */
	public static void checkCollision(Entity entity) 
	{
		int leftTopX = entity.getX() + entity.getHitBox().x;
		int leftTopY = entity.getY() + entity.getHitBox().y;
		
		int rightTopX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int rightTopY = entity.getY() + entity.getHitBox().y;
		
		int leftBottomX = entity.getX() + entity.getHitBox().x;
		int leftBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		int rightBottomX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int rightBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		
		switch(entity.getDirection())
		{
			case LEFT:
				int leftTopXScaled = (leftTopX-entity.getSpeed())/WiewData.TILE_SIZE.getValue();
				int leftBottomXScaled = (leftBottomX-entity.getSpeed())/WiewData.TILE_SIZE.getValue();
				int leftTopYScaled = leftTopY/WiewData.TILE_SIZE.getValue();
				int leftBottomYScaled = leftBottomY/WiewData.TILE_SIZE.getValue();
				if(GameMap.solidBlocks.contains(GameMap.getInstance().getValue(leftTopYScaled, leftTopXScaled)) || GameMap.solidBlocks.contains(GameMap.getInstance().getValue(leftBottomYScaled, leftBottomXScaled)))
				{
					entity.setHitBox(true);
				}
				break;
			case RIGHT:
				int rightTopXScaled = (rightTopX+entity.getSpeed())/WiewData.TILE_SIZE.getValue();
				int rightBottomXScaled = (rightBottomX+entity.getSpeed())/WiewData.TILE_SIZE.getValue();
				int rightTopYScaled = rightTopY/WiewData.TILE_SIZE.getValue();
				int rightBottomYScaled = rightBottomY/WiewData.TILE_SIZE.getValue();
				if(GameMap.solidBlocks.contains(GameMap.getInstance().getValue(rightTopYScaled, rightTopXScaled)) || GameMap.solidBlocks.contains(GameMap.getInstance().getValue(rightBottomYScaled, rightBottomXScaled)))
				{
					entity.setHitBox(true);
				}
				break;		
		}
	}
	
	/**
	 * Verifica se l'entità passata come parametro, ha una collisione con un blocco sopra di lei
	 * @param entity rappresenta l'entità a cui viene effettuato il controllo
	 */
	public static void checkUp(Entity entity) {
		int leftTopX = entity.getX() + entity.getHitBox().x;
		int leftTopY = entity.getY() + entity.getHitBox().y;
		
		int rightTopX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int rightTopY = entity.getY() + entity.getHitBox().y;
		
		int leftTopXScaled = leftTopX/WiewData.TILE_SIZE.getValue();
		int leftTopYScaled = (leftTopY-entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		int rightTopXScaled = rightTopX/WiewData.TILE_SIZE.getValue();
		int rightTopYScaled = (rightTopY-entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		
		if(GameMap.solidBlocks.contains((GameMap.getInstance().getValue(rightTopYScaled, rightTopXScaled))) || GameMap.solidBlocks.contains(GameMap.getInstance().getValue(leftTopYScaled, leftTopXScaled)))
		{
			entity.setHitUp(true);
		}
	}
	
	/**
	 * Verifica se l'entità passata come parametro ha dei blocchi sottostanti che hanno la funzione di pavimento
	 * @param entity rappresenta l'entità a cui viene effettuato il controllo
	 */
	public static void checkFall(Entity entity)
	{
		int leftBottomX = entity.getX() + entity.getHitBox().x;
		int leftBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		int rightBottomX = entity.getX() + entity.getHitBox().x + entity.getHitBox().width;
		int rightBottomY = entity.getY() + entity.getHitBox().y + entity.getHitBox().width;
		
		int leftBottomXScaled = leftBottomX /WiewData.TILE_SIZE.getValue();
		int rightBottomXScaled = rightBottomX /WiewData.TILE_SIZE.getValue();
		int leftBottomYScaled = (leftBottomY + entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		int rightBottomYScaled = (rightBottomY + entity.getSpeed())/WiewData.TILE_SIZE.getValue();
		
		if(GameMap.solidBlocks.contains((GameMap.getInstance().getValue(leftBottomYScaled, leftBottomXScaled))) || GameMap.solidBlocks.contains(GameMap.getInstance().getValue(rightBottomYScaled, rightBottomXScaled))) 
		{
			if(GameMap.endBlock.contains(GameMap.getInstance().getValue(leftBottomYScaled, leftBottomXScaled)) || GameMap.endBlock.contains(GameMap.getInstance().getValue(rightBottomYScaled, rightBottomXScaled))) 
			{
				//questo andrà cambiato con un setY(getY della current map)
				entity.setY(0);
			}
			else
			{
				entity.setFall(false);
			}
		}	
	}
	
	/**
	 * 
	 * @param entity
	 * @param target
	 * @return
	 */
	public static boolean checkHit(Entity entity,Entity target) {
		
		int aux;
		boolean result = false;

			if(target != null) 
			{
				
				aux = entity.getX() + entity.getHitBox().x;
				entity.setHitboxX(aux);
				aux = entity.getY() + entity.getHitBox().y;
				entity.setHitboxY(aux);
				
				aux = target.getX() + target.getHitBox().x;
				target.setHitboxX(aux);
				aux = target.getY() + target.getHitBox().y;
				target.setHitboxY(aux);
				
				if(entity.getHitBox().intersects(target.getHitBox())) 
				{
						result =  true;
				}
				
				entity.setHitboxX(entity.getHitboxDefaultX());
				entity.setHitboxY(entity.getHitboxDefaultY());
				
				target.setHitboxX(target.getHitboxDefaultX());
				target.setHitboxY(target.getHitboxDefaultY());

			}
			return result;
		}
		

}
