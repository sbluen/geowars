package main.entities;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import main.gameData.AngleCalculator;





//Daryl Hennegan
//5-17-09



/**This is a type of enemy that shoots at the player.
 *  The player will be trying to eliminate these
 *  objects as the objective of the game.
 * @author Daryl Hennegan, group G3
 *
 */
public class ShooterEnemy extends Enemy{
	
	/**The image this ShooterEnemy is represented by. */
	private String refer = "resources/ShooterEnemySprite.png";
	private long AI = 2;	//A number to determine how aggressive this enemy behaves with regard to shooting at the player
	private Canvas missile_canvas;
	private Entity target;
	private int speed = 500; //speed of the missile
	private long lastShooting;
	private boolean isShooting = false;
	private int value = 30;
	
	/** Constructor for setting the starting values for this enemy.
	 * @param x the x-coordinate of this enemy
	 * @param y the y-coordinate of this enemy
	 * @param canvas the canvas on which this enemy is to be drawn on
	 * @param vy the starting velocity of the enemy in the y direction
	 * @param vx the starting velocity of the enemy in the x direction
	 * @param maxSpeed the maximum speed that this enemy is to move
	 * @param sizeX the width of the game area
	 * @param sizeY the height of the game area
	 * @param target the target that this enemy shoots at
	 */
	public ShooterEnemy(double x, double y, Canvas canvas, double vx,
			double vy, double maxSpeed, int sizeX, int sizeY, Entity target){
		
		super(x, y, canvas, vx, vy, maxSpeed, sizeX, sizeY);
		
		
		
		
		vx = Math.random()*maxSpeed;
		vy = Math.random()*maxSpeed;
		
		
		
		missile_canvas = canvas;
		
		look = new LookManager().getLook(refer, canvas);
		this.target = target;
		
		
		lastShooting = System.currentTimeMillis();
		
		AI = (long) (Math.random() * 4) + 1;
		
	}
	
	
	/**This method returns how much this enemy is worth when killed
	 *  
	 * @return value The number of points this enemy object is worth
	 */	
	public int getValue(){
		
		return value;
		
	}

	
	/**Causes this ShooterEnemy to fire a missile, the game class is responsible for dealing with the created missile object.
	 * 
	 * @return The EnemyMissile object this ShooterEnemy is firing
	 * 
	 */
	public Missile Shoot(){
		
		double missile_angle = AngleCalculator.calculateAngle(this,target);
		
		double missile_vy = Math.sin(missile_angle) * speed;
		double missile_vx = Math.cos(missile_angle) * speed;
		
		//public Missile(Entity Source, double x, double y, Canvas c, double vy, double vx, double theta)

		Missile enemy_missile = new Missile(this, this.getX(), this.getY(), missile_canvas, missile_vy, missile_vx, missile_angle);
		
		return enemy_missile;
	}
	
	/**Causes this ShooterEnemy to adopt one of several AI patterns which determine shooting frequency
	 * 
	 * @param AI An integer specifying how aggressive you want the AI with 1 being the least aggressive and 5 being the most aggressive
	 * 
	 * 
	 */
	public void setAI(long AI){
		
		this.AI = AI;
		
		
	}

	


	
	
	
	/**Returns the AI pattern for this ShooterEnemy
	 * 
	 * @return An integer specifying how aggressive the AI for this ShooterEnemy is.
	 * 
	 * 
	 */	
	public long getAI(){
		
		return AI;
		
		
	}	
	
	
	/**Returns a boolean associated with this objects firing behavior.
	 * 
	 * @return A boolean specifying whether this enemy should fire a missile or not.
	 * 
	 * 
	 */		
	public boolean getShot(){
		
		return isShooting;
		
		
	}
	
	
	
	
	
	
	/**This is the move method, for this object it only determines when it should shoot.
	 * @param n the game's frame rate
	 */
	@Override
	public void move(long n){
		
		
		super.move(n);
		
		
		long shootingInterval = 2000/AI;
		
		if((System.currentTimeMillis() - lastShooting) < shootingInterval)
		{
			isShooting = false;
			return;
			
		}
		lastShooting = System.currentTimeMillis();
		isShooting = true;
		


	}
	
	
	/**Draws this enemy.
	 * @param g the graphics object that this enemy is to be drawn with.
	 */	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		look.draw(g2, x, y, 0);
	}	
	
}