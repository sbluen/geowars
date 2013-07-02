package main.entities;
import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**This class is responsible loading the images
 *  from a url and passing them into the look objects
 *  and keeping track of the look objects.
 *  
 *  @author Kevin Glass, at http://www.cokeandcode.com/node/6
 */
public class ViewManager {
	private HashMap<String, View> views = new HashMap<String, View>();
	
	/**Builds a sprite (Look class) from the image.
	 * 
	 * @param refer the location of the image
	 * @param canvas the canvas on which the image is to be drawn
	 * @return the sprite that the image is to be stored with
	 */
	public View getLook(String refer, Canvas canvas){
		if(views.get(refer)!=null)
			return views.get(refer);
		BufferedImage srcImage = null;
		URL url = null;
		
		try{
			url = getClass().getClassLoader().getResource(refer);
			if(url==null){
				File f=new File(refer);
				System.err.println("Cannot find the image, "+f.getAbsolutePath());
				System.exit(1);
			}
			srcImage = ImageIO.read(url);
		}
		catch(IOException e){
			System.err.println("Failed to load the image, "+refer);
			System.exit(1);
		}
		
		/** create an accelerated image of the same size from the source image,
		 * and store the accelerated image. */
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(srcImage.getWidth(), srcImage.getHeight(),
				Transparency.BITMASK);
		
		/** first create a graphic for drawing the source image to an off-screen image.
		 * draw the source image to the off-screen image. */
		image.getGraphics().drawImage(srcImage, 0, 0, null);
		View view = new View(image, canvas);
		views.put(refer, view);
		return view;
	}
}
