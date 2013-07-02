package edu.ucsb.umail.sbluen.gamedata;
import java.applet.Applet;
import java.applet.AudioClip;

/**Class used for playing sounds.
 * 
 * @author Steven Bluen and Yunsik Choi, group G3
 *
 */
public class Sound{
	
	private String refer;
	private AudioClip sound;
	
	/**Stores the sound.
	 * 
	 * @param s the relative location of the sound, from the compile location
	 */
	public Sound(String s){
		refer = "resources/"+s+".au";
		sound = Applet.newAudioClip(getClass().getClassLoader().getResource(refer));
	}
	
	/**Plays the stored sound.*/
	public void play(){
		sound.play();
	}
}
