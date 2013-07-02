package main.entities;
import java.awt.Graphics;
import java.awt.Rectangle;

/**Entity is a superclass for all of the objects displayed on the screen.
 *  Any object (players, enemies, projectiles) extends this class.
 * @author Kevin Glass, at http://www.cokeandcode.com/node/6 
 * and heavily modified by Yunsik Choi and Steven Bluen
 *
 */
public abstract class Entity {
	/**Variable to keep track of the entity's position.*/
	protected double x, y;
	/**The direction that this entity is pointing in.*/
	protected double theta;
	/**Velocity variable.*/
	protected double vx, vy, vtheta; //velocity
	/**The sprite that this entity is to be drawn with.*/
	protected View view;
	/**A rectangle around this object, used for collision checking.*/
	protected Rectangle thisRec = new Rectangle();
	
	/**This method defines how an entity moves.
	 * 
	 * @param n the amount of time that has passed
	 */
	public abstract void move(long n);
	
	/**Sets an entity's x coordinate.
	 * 
	 * @param n the number that this entity's x coordinate is
	 * to be set to
	 */
	public void setX(double n){
		x = n;
	}
	
	/**Sets an entity's y coordinate.
	 * 
	 * @param n the number that this entity's y coordinate is
	 * to be set to
	 */
	public void setY(double n){
		y = n;
	}
	
	/**Gets the entity's x coordinate.
	 * 
	 * @return the entity's x coordinate
	 */
	public double getX(){
		return x;
	}
	
	/**Gets the entity's y coordinate.
	 * 
	 * @return the entity's y coordinate
	 */
	public double getY(){
		return y;
	}
	
	/**sets the entity's velocity vx in the x direction
	 * 
	 * @param n the value for vx to be set to.
	 */
	public void setVX(double n){
		vx = n;
	}
	
	/**Sets the entity's velocity vy in the y direction
	 * 
	 * @param n the value for vy to be set to.
	 */
	public void setVY(double n){
		vy = n;
	}
	
	/**Gets the entity's velocity vx in the x direction
	 * 
	 * @return vx
	 */
	public double getVX(){
		return vx;
	}
	
	/**Gets the entity's velocity vy in the y direction
	 * 
	 * @return vy
	 */
	public double getVY(){
		return vy;
	}
	
	/**Gets the angular direction theta that the entity is pointing in.
	 * 
	 * @return theta in radians as used on the unit circle
	 */
	public double getTheta() {
		return theta;
	}
	
	/**Gets the angular velocity vtheta.
	 * 
	 * @return theta in radians as used on the unit circle
	 */
	public double getVTheta() {
		return vtheta;
	}
	
	/**Sets the angular direction theta that the entity is pointing in.
	 * 
	 * @param theta theta in radians as used on the unit circle
	 */
	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	/**Sets the angular velocity vtheta.
	 * 
	 * @param vtheta theta in radians as used on the unit circle
	 */
	public void setVTheta(double vtheta) {
		this.vtheta = vtheta;
	}
	
	/**Uses the Look class to draw a sprite of this object.*/
	public void draw(Graphics g){
		view.draw(g, x, y, theta);
	}
	
	/**Determines if an Entity is colliding with this one.*/
	public boolean collide(Entity other){
		if (other instanceof PlaceholderEnemy) return false;
		//implement pixel-based collision detection
		thisRec.setBounds((int)x, (int)y, view.getWidth(), view.getHeight());
		Rectangle otherRec = new Rectangle();
		otherRec.setBounds((int)other.x, (int)other.y, 
				other.view.getWidth(), other.view.getHeight());
		return thisRec.intersects(otherRec);
	}
	
	/**Sets the entity's velocity vx in the x direction.
	 * 
	 * @param n the value for vx to be set to
	 */
	public void setHorizontalMove(double n){
		vx = n;
	}
	
	/**Sets the entity's velocity vy in the y direction.
	 * 
	 * @param n the value for vy to be set to
	 */
	public void setVerticalMove(double n){
		vy = n;
	}
	
	/**gets the entity's velocity vx in the x direction.
	 * 
	 * @return vx
	 */
	public double getHorizontalMove(){
		return vx;
	}
	
	/**Gets the entity's velocity vy in the y direction.
	 * 
	 * @return vy
	 */
	public double getVerticalMove(){
		return vy;
	}
}
