package main.gameData;

/** Stores static variables used for controlling the buttons in the sub-menu
 * and keeping track of player's record and game status and so on.
 * 
 * @author Yunsik Choi, group G3
 *
 */
public class Record{
	/** The player's name */
	public static String playername = null;
	
	/** The player's level.*/
	public static int level = 1;
	
	/** The player's score.*/
	public static int score = 0;
	
	/** The player's life remaining.*/
	public static int playerLife = 100;
	
	/** Indicates new game is to start */	
	public static boolean newGame = false;
	
	/** Indicates a next level */
	public static boolean nextLevel = false;
	
	/** Indicates current game is going on */
	public static boolean playing = true;
	
	/** Indicates game is to be loaded */
	public static boolean loadGame = false;
	
	/** Indicates game is paused */ 
	public static boolean paused = false;
	
	/** Default setting for loading options. Load a new game */
	public static boolean loadNewGame = true;
	
	/** Loading options. Resume the game */
	public static boolean loadResume = false;
	
	/** Indicates game is not loaded yet */
	public static boolean beforeLoading = true;
	
	/** Most recent player's name.
	 * Used to check if returning player's record was saved on the data file */
	public static String mostRecentPlayerName = null;  
	
	/* No methods should show up on the javadocs, so this constructor must be made private.
	 * No need to create an object for this class */
	private Record(){}
}
