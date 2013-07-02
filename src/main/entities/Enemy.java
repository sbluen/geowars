package main.entities;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**This is the object that represents enemy ships.
 *  The player will be trying to eliminate these
 *   objects as the objective of the game.
 * @author Kevin Glass, at http://www.cokeandcode.com/node/6
 * and heavily modified by Yunsik Choi and Daryl Hennegan, group G3
 *
 */
public class Enemy extends Entity {
	/**The maximum speed of the enemy.*/
	protected double maxSpeed;
	/** The window size*/
	protected int windowSizeX, windowSizeY;
	private String refer = "resources/enemy1.png";
	private int value = 10;
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
	public Enemy(double x, double y, Canvas canvas,
			double vx, double vy, double maxSpeed, int sizeX, int sizeY){
			view = new ViewManager().getLook(refer, canvas);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.maxSpeed = maxSpeed;
		windowSizeX = sizeX;
		windowSizeY = sizeY;
	}
	
	/**This method returns how much this enemy is worth when killed
	 *  
	 * @return value The number of points this enemy object is worth
	 */	
	public int getValue(){	
		return value;
	}
	
	/**This is the move method, which makes this enemy go back if
	 *  it is outside the game area.
	 * @param n the game's frame rate
	 */
	@Override
	public void move(long n){
		//theta += n*(Math.random()*0.1+0.01)/20;
		x += vx;
		y += vy;
		//boundary checks designed by Yunsik
		if(x < 30){
			
			  vx = Math.random()*maxSpeed;
		}
		else if(y < 30){
		   	
		        vy = Math.random()*maxSpeed;
		}
		else if(y > windowSizeY-50){
			
				vy = Math.random()*maxSpeed;
		        vy = vy - vy*2;
			
		}
		else if(x > windowSizeX-50){
			
				vx = Math.random()*maxSpeed;
		        vx = vx - vx*2;
		}
	}
	
	/**Draws this enemy.
	 * @param g the graphics object that this enemy is to be drawn with.
	 */
	@Override
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		view.draw(g2, x, y, theta);
	}
}
