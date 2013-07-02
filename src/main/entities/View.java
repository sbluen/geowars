package main.entities;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**This class is created by the LookManage class 
 * to pass an image into an entity object so it can be displayed on the game screen.
 *  This class is responsible for drawing the object .
 *  
 * @author Kevin Glass, at http://www.cokeandcode.com/node/6, 
 * with additions by Steven Bluen, group G3
 */
public class View {
	private Image image;
	private Canvas canvas; 
	
	/**Constructor that sets up the look with its image.
	 * 
	 * @param image the image to draw
	 * @param canvas the canvas on which the image is to be drawn
	 */
	public View(Image image, Canvas canvas){
		this.image = image;
		this.canvas = canvas;
	}
	
	/**Gets the width of the image that this Look is associated with.
	 * 
	 * @return the width of the image
	 */
	public int getWidth(){
		return image.getWidth(canvas);
	}
	
	/**Gets the height of the image that this Look is associated with.
	 * 
	 * @return the height of the image
	 */
	public int getHeight(){
		return image.getHeight(canvas);
	}
	
	/**Draws an image on the screen
	 * This function uses theta in radians 
	 * because that makes the other calculations simpler.
	 * @param g the graphics object that is used to draw the image
	 * @param x the x coordinate of the point where the image is to be drawn
	 * @param y the y coordinate of the point where the image is to be drawn
	 * @param theta the angle of rotation in which the image is to be drawn
	 */
	 /* NOTE: Rotating something around an arbitrary point could
	 /* potentially produce interesting effects. */
	public void draw(Graphics g, double x, double y, double theta){
		Graphics2D g2 = (Graphics2D)g;
		if(theta==0){
			g2.drawImage(image, (int)x, (int)y, canvas);
		}
		else{
			AffineTransform at = AffineTransform.getTranslateInstance(x, y);
	        at.rotate(theta, image.getWidth(canvas)/2.0, image.getHeight(canvas)/2.0);
			g2.drawImage(image, at, canvas);
		}
	}
}
