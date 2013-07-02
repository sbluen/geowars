package edu.ucsb.umail.sbluen.gamedata;
import java.awt.event.MouseEvent;

import edu.ucsb.umail.sbluen.entities.Entity;

/**Calculates the angle for the an entity to face.
 * Uses the {@link Entity} class.
 * @author Daryl Hennegan, group G3
 *
 */
public class AngleCalculator{

	private AngleCalculator(){}

	/**gets the angle directly from the mouse, for mouse events.
	 * 
	 * @param entity The entity whose angle is to be calculates.
	 * @param e The MouseEvent which the player's angle is calculated from.
	 * @return the angle in radians
	 */
	public static double calculateAngle(Entity entity, MouseEvent e)
	{
		double dx;
		double dy;
		int mouseX;	//x coordinate of the mouse
		int mouseY; //y coordinate of the mouse
				
		mouseX = e.getX();
		mouseY = e.getY();
		
		dx = mouseX-entity.getX();
		dy = mouseY-entity.getY();
		
		/*
		
		radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); 
				
		//sin(theta) = dy/radius
		dtheta = Math.asin(dy/radius);
				
		if(mouseX < entity.getX() && mouseY < entity.getY())
		{
			dtheta = (Math.toRadians(180)) - dtheta;
		} 
		else
		if(mouseX < entity.getX() && mouseY > entity.getY())
		{
			dtheta = -(Math.toRadians(180)) - dtheta;	
		} 
		
		*/
				
		return Math.atan2(dy, dx);	
	}
	
	
	/**Uses the coordinates stored inside the game instead of a MouseEvent.
	 * 
	 * @param entity The entity whose angle is to be calculates.
	 * @param mouseX x coordinate passed from the game
	 * @param mouseY y coordinate passed from the game
	 * @return the angle in radians
	 */
	public static double calculateAngle(Entity entity, int mouseX, int mouseY)
	{
		double dx;
		double dy;
	
		dx = mouseX-entity.getX();
		dy = mouseY-entity.getY();
		
		/*
		radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); 
		
		//sin(theta) = dy/radius
		dtheta = Math.asin(dy/radius);
		
		if(mouseX < entity.getX() && mouseY < entity.getY())
		{
			dtheta = (Math.toRadians(180)) - dtheta;
		} 
		else
		if(mouseX < entity.getX() && mouseY > entity.getY())
		{
			dtheta = -(Math.toRadians(180)) - dtheta;	
		} 
		*/
		return Math.atan2(dy, dx);
	}
	
	
	/**Uses the coordinates of 2 Entity objects
	 * 
	 * @param entity1 The entity you are trying to calculate the angle for.
	 * @param entity2 The entity whose position you want the angle to be with respect to. (i.e. entity1 faces entity2)
	 * 
	 * @return the angle in radians between these two entities
	 */
	public static double calculateAngle(Entity entity1, Entity entity2)
	{
			
		double dx;
		double dy;
		double dtheta;
	
		dx = entity2.getX() - entity1.getX();  //X increases left to right so we want this to be negative if entity 2 is left of entity 1
		
		dy = entity2.getY() - entity1.getY();  //Y increases from top to bottom so we want this to be negative if entity 2 is below entity 1
	
		dtheta = Math.atan2(dy, dx); 

		return dtheta;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}