package main.entities;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**This is a type of enemy that chases the player.
 *  The player will be trying to eliminate these
 *   objects as the objective of the game.
 * @author Steven Bluen, group G3
 *
 */
public class ChaserEnemy extends Enemy {
	private String refer = "resources/enemy2.png";
	private Entity player;  //holds the coordinates of the player that this
						    //enemy chases
	private double v;	  	//magnitude of velocity
	private int time;		//time since last change in direction
	private int value = 20;
	
	
	/** Constructor for setting the starting values for this enemy.
	 * @param x the x-coordinate of this enemy
	 * @param y the y-coordinate of this enemy
	 * @param canvas the canvas on which this enemy is to be drawn on
	 * @param vy the starting velocity of the enemy in the y direction
	 * @param vx the starting velocity of the enemy in the x direction
	 * @param maxSpeed the maximum speed that this enemy is to move
	 * @param sizeX the width of the game area
	 * @param sizeY the height of the game area
	 * @param player the player that this enemy chases
	 */
	public ChaserEnemy(double x, double y, Canvas canvas, double vx,
			double vy, double maxSpeed, int sizeX, int sizeY, Entity player){
		super(x, y, canvas, vx, vy, maxSpeed, sizeX, sizeY);
		view = new ViewManager().getLook(refer, canvas);
		this.player=player;
		v=Math.min(Math.sqrt(vx*vx+vy*vy), maxSpeed);
		time=0;
	}
	
	/**This is the move method, which moves this enemy towards the player.
	 * @param n the game's frame rate
	 */
	@Override
	public void move(long n){
		time+=n;
		if (time>=100){
			theta=Math.atan2((player.getY()-y), (player.getX()-x));
		}
		y+=v*Math.sin(theta);
		x+=v*Math.cos(theta);
	}
	
	/**This method returns how much this enemy is worth when killed
	 *  
	 * @return value The number of points this enemy object is worth
	 */	
	public int getValue(){
		
		return value;
		
	}
	
	
	
	/**Draws this enemy.
	 * @param g the graphics object that this enemy is to be drawn with.
	 */
	@Override
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		view.draw(g2, x, y, theta+Math.toRadians(90));
	}
}