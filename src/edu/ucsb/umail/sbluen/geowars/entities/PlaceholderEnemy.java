package edu.ucsb.umail.sbluen.geowars.entities;

import java.awt.Canvas;
import java.awt.Graphics;

/**This is not a real enemy, only an Enemy because that's the easiest
 * way to implement this.
 * This type of enemy is neither displayed nor collidable.
 * It is used to allow other enemies to move in a certain way.
 * @author Steven Bluen, Group G3
 *
 */
public class PlaceholderEnemy extends Enemy{
	
	/** Constructor for setting the starting values for this enemy.
	 * @param x the x-coordinate of this enemy
	 * @param y the y-coordinate of this enemy
	 * @param canvas the canvas on which this enemy is to be drawn on
	 * @param vy the starting velocity of the enemy in the y direction
	 * @param vx the starting velocity of the enemy in the x direction
	 * @param maxSpeed the maximum speed that this enemy is to move
	 * @param sizeX the width of the game area
	 * @param sizeY the height of the game area
	 */
	public PlaceholderEnemy(double x, double y, Canvas canvas, double vx,
			double vy, double maxSpeed, int sizeX, int sizeY) {
		super(x, y, canvas, vx, vy, maxSpeed, sizeX, sizeY);
		// TODO Auto-generated constructor stub
	}
	
	/**Draws this enemy.
	 * Does nothing for now
	 * @param g the graphics object that this enemy is to be drawn with.
	 */
	@Override
	public void draw(Graphics g){
		return;
	}
	
	/**This overrides the inherited method to make it always return false
	 * @return false
	 */
	public boolean collide(Entity other){
		return false;
	}
}
