package edu.ucsb.umail.sbluen.geowars.entities;

import java.awt.Canvas;

/**This class represents the missiles fired by the player.
 * 
 * @author Yunsik Choi and Daryl Hennegan, group G3
 *
 */
public class Missile extends Entity{
	
	/**
	 * Keeps track of the Entity that fired this missile
	 */
	Entity Source; //keeps track of what fired this missile
		
	/**Constructor for setting the starting values for the missiles.
	 * 
	 * 
	 * @param Source The Entity object that fires this missile
	 * @param x the x-coordinate of this missile
	 * @param y the y-coordinate of this missile
	 * @param c the canvas on which this missile is to be drawn on
	 * @param vy the starting velocity of the missile in the y direction
	 * @param vx the starting velocity of the missile in the x direction
	 * @param theta the angle that the missile starts out being rotated
	 */
	public Missile(Entity Source, double x, double y, Canvas c, double vy, double vx, double theta){
		String refer;
		this.Source = Source;
				
		if(Source instanceof Player)
		{
			refer = "resources/missile.png";
		}
		else
		
		if(Source instanceof ShooterEnemy)
		{
			refer = "resources/EnemyMissile.png";
		}
		else
		{
			refer = "";
		}
		
		view = new ViewManager().getLook(refer, c);
		this.x = x;
		this.y = y;
		this.vy = vy;
		this.vx = vx;
		this.theta = theta;
	}
		
	/** Causes the missile to move by an amount proportional to n.
	 * @param n the game's frame rate
	 */
	@Override
	public void move(long n){
		x += n*vx / 1000;
		y += n*vy / 1000;
	}
	
	/** Returns the Entity that fired this missile object
	 * 
	 * @return The entity that fired this missile object
	 */
	public Entity getSource()
	{
		return Source;
	}
}
