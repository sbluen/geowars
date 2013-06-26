package main.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** This is type of enemy that orbits another enemy,
 * which is to be designated with the axis parameter.
 * The game makes this Enemy do twice the damage of normal enemies for now.
 * @author Steven Bluen, Group G3
 *
 */
public class SatelliteEnemy extends Enemy {
	private String refer="resources/enemy3.png";
	private Entity axis;
	private double r;  //as described in javadocs
	private int value=25;

	/**Constructor for setting the default values.
	 * 
	 * @param theta the angle that this enemy starts out being rotated in
	 * @param vtheta the speed of rotation that this enemy has
	 * @param r the distance this enemy stays from the entity that it rotates around
	 * @param canvas the canvas that this enemy is to be drawn on
	 * @param sizeX the width of the canvas
	 * @param sizeY the height of the canvas
	 * @param axis the entity this emeny is to rotate around like a satellite
	 */
	public SatelliteEnemy(double theta, double vtheta, double r,
			Canvas canvas, int sizeX, int sizeY, Entity axis) {
		super(axis.getX()+r*Math.cos(theta),
				axis.getY()+r*Math.sin(theta),
				canvas, 0, 0, 0, sizeX, sizeY);
		look = new LookManager().getLook(refer, canvas);
		this.theta=theta;
		this.vtheta=vtheta;
		this.r=r;
		this.axis=axis;
	}
	
	/** As this entity moves, it rotates around the axis
	 *  with an angular velocity of vtheta.
	 *  This should be put at the limit if it would otherwise go offscreen.
	 *  @param n the game's frame rate
	 */
	public void move(long n){
		x=axis.getX()+r*Math.cos(theta);
		if (x<=0) x=0;
		//I know that this doesn't work right but I don't know how to fix this.
		if (x>=windowSizeX-look.getWidth()) x=windowSizeX-look.getWidth();
		y=axis.getY()+r*Math.sin(theta);
		if (y<=0) y=0;
		//I know that this doesn't work right but I don't know how to fix this.
		if (y>=windowSizeX-look.getHeight()) x=windowSizeY-look.getHeight();
		theta+=n*vtheta;
	}
	
	/**Draws this enemy.
	 * @param g the graphics object that this enemy is to be drawn with.
	 */	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		look.draw(g2, x, y, theta);
	}	
	
	/**This method returns how much this enemy is worth when killed
	 *  
	 * @return value The number of points this enemy object is worth
	 */
	public int getValue(){
		return value;
	}
}
