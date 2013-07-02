package main.entities;

import java.awt.Canvas;

/**PowerUp is a class to represent all of the objects collected by the player to give a certain kind of bonus.
 * Power ups should be dropped by enemies when they are destroyed.
 * @author Daryl Hennegan, Group G3
 * 
 *
 */
public class PowerUp extends Entity {

	private String type;

	private int amount = 5; //The amount of benefit this powerup gives the player when picked up
	
	/** Constructor for setting the starting values for this power up
	 * @param x the x-coordinate of this power up
	 * @param y the y-coordinate of this power up
	 * @param canvas the canvas on which this power up is to be drawn on
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
public PowerUp(String type,Canvas canvas,double x, double y)
{
	this.type = type;
	this.x = x;
	this.y = y;
	
	
	
	//Also intialize the look objects here depending on the type
	if (type.equals("Life")){
		amount = 20;
		view = new ViewManager().getLook("resources/LifePowerUp.png", canvas);
	}

	if (type.equals("Score")){
		amount = 50;
		view = new ViewManager().getLook("resources/ScorePowerUp.png", canvas);
		
	}
	
	
	vtheta = Math.toRadians(10);
	
	
	
}
	

/**rotates the object
 * @param n the game's frame rate
 */
public void move(long n){

	theta += vtheta;
	
}
	

/**
 * @return the type of this power up
 */	
public String getType(){
	
	return type;
}

/**This method returns how much this powerup is worth
 *  
 * @return value The number of points this powerup object is worth
 */	
public int getAmount(){
	
	return amount;
	
}
	
	
	
}

