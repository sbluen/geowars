package edu.ucsb.umail.sbluen.geowars.gameui;

import edu.ucsb.umail.sbluen.geowars.gamedata.Record;
import edu.ucsb.umail.sbluen.geowars.gameui.Game;

/**
 * 
 * @author Steven Bluen
 *The main class that bootstraps everything
 */
public class Main {

	/**The main method.
	 * Put in this class because it makes things simpler
	 * @param args does nothing for now.
	 */
	public static void main(String[] args){
		Game game = new Game();
		
		//Wait until ready to load a game
		while(!Record.loadGame && !Record.newGame);
						
		/* Loads a new game for a new user and a returning user */
		if(Record.newGame)
			game.newGame();
				
		/* Reloads the game for a returning user */
		else if(Record.loadResume){
			game.submenu.updateLevelLabel();
			game.submenu.updateScoreLabel();
			game.initEntitiesResume();
		}
			
		game.submenu.setLoadBtnEnabled(false); /* Load button is no longer needed */
		Record.beforeLoading = false;
		game.requestFocus();
		while(true)
			game.gameLoop();
	} //end of main()

}
