package main.entities;

import java.awt.Canvas;
import java.awt.event.MouseEvent;

/** This is the object that represents the player's ship.
 *  The player will interact with the game world through this object. 
 *  
 *  @author Kevin Glass, at http://www.cokeandcode.com/node/6,
 *  and heavily modified by Yunsik Choi and Daryl Hennegan, group G3
 */
public class Player extends Entity {
	
	/** Constructor for setting the starting values for the player
	 *
	 */
	public Player(String refer, double x, double y, Canvas canvas){
		look = new LookManager().getLook(refer, canvas);
		this.x = x;
		this.y = y;
	}
	
	/**Causes the missile to move by an amount proportional to n.
	 * @param n the game's frame rate
	 */
	@Override
	public void move(long n){
			x += n*vx/1000;
			y += n*vy/1000;
	}
}
