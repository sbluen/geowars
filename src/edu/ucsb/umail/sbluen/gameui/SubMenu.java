package edu.ucsb.umail.sbluen.gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.ucsb.umail.sbluen.gamedata.Database;
import edu.ucsb.umail.sbluen.gamedata.PlayerInfo;
import edu.ucsb.umail.sbluen.gamedata.Record;


/**This is the sub-menu that goes below the game field.
 * 
 * Depends on {@link Record}.
 * @author Yunsik Choi and Steven Bluen, group G3
 *
 */
@SuppressWarnings("serial")
public class SubMenu extends JComponent implements ActionListener, ItemListener{ 	
	/* Dimension */
	private final int WIDTH = 800, HEIGHT = 100;
	
	private JLabel levelLbl, levelVal, scoreLbl, scoreVal, 
		playerLifeLbl, playerLifeVal, gameLbl;//, spacerLabel;
	
	private JButton newGameBtn, nextLvlBtn, loadBtn, quitBtn, helpBtn;
		
	/* Radio buttons for load options */
	private Checkbox loadNewGameRdo, loadResumeRdo;
	private CheckboxGroup gr;
	
	/**Constructor that creates this menu*/
	public SubMenu(){
			
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout());
		
		/* Add level label */
		levelLbl = new JLabel("Level: ");
		levelVal = new JLabel("1");
		levelLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		levelLbl.setForeground(Color.darkGray);
		levelVal.setFont(new Font("Arial", Font.BOLD, 18));
		levelVal.setForeground(new Color(0, 139, 69));
		add(levelLbl, "middle");
		add(levelVal);
		
		/* Add score label */
		scoreLbl = new JLabel("  Score: ");
		scoreVal = new JLabel("0");
		scoreLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		scoreLbl.setForeground(Color.darkGray);
		scoreVal.setFont(new Font("Arial", Font.BOLD, 18));
		scoreVal.setForeground(Color.blue);
		add(scoreLbl);
		add(scoreVal);
		
		/* Add player's remaining life label */
		playerLifeLbl = new JLabel("  Life: ");
		playerLifeVal = new JLabel("100%        ");
		playerLifeLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		playerLifeLbl.setForeground(Color.darkGray);
		playerLifeVal.setFont(new Font("Arial", Font.BOLD, 18));
		playerLifeVal.setForeground(Color.red);
		add(playerLifeLbl);
		add(playerLifeVal);
		
		/* Add radio buttons for loading options */
		gr = new CheckboxGroup();
		loadNewGameRdo = new Checkbox("Load New Game", true, gr); //default
		loadNewGameRdo.setFont(new Font("SansSerif", Font.BOLD, 15));
		loadResumeRdo = new Checkbox("Resume Game", false, gr);
		loadResumeRdo.setFont(new Font("SansSerif", Font.BOLD, 15));
		add(loadNewGameRdo);
		add(loadResumeRdo);
		loadNewGameRdo.addItemListener(this);
		loadResumeRdo.addItemListener(this);
		
		/* Nothing else on this line */
		add(Box.createRigidArea(new Dimension(100, 0)));
		
		/* Add next level button */
		nextLvlBtn = new JButton("Next Level");
		nextLvlBtn.setBackground(new Color(139, 0, 0)); //red
		nextLvlBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		nextLvlBtn.setForeground(new Color(237, 237, 237));
		nextLvlBtn.setEnabled(false);
		add(nextLvlBtn);
		nextLvlBtn.addActionListener(this);
		
		/* Add space in between */
		add(Box.createRigidArea(new Dimension(25, 0)));
		
		/* Add new game button */
		newGameBtn = new JButton("New Game");
		newGameBtn.setBackground(new Color(139, 0, 0)); //red
		newGameBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		newGameBtn.setForeground(new Color(237, 237, 237));
		newGameBtn.setEnabled(false);
		add(newGameBtn);
		newGameBtn.addActionListener(this);
				
		/* Add load button */
		loadBtn = new JButton("Load");
		loadBtn.setBackground(new Color(0, 100, 0)); //green
		loadBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		loadBtn.setForeground(new Color(237, 237, 237));
		loadBtn.setEnabled(true);
		add(loadBtn);
		loadBtn.addActionListener(this);
		
		/* Add quit button */
		quitBtn = new JButton("Quit");
		quitBtn.setBackground(new Color(0, 0, 139)); //blue
		quitBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		quitBtn.setForeground(new Color(237, 237, 237));
		quitBtn.setEnabled(true);
		add(quitBtn);
		quitBtn.addActionListener(this);
		
		/* Add help button */
		helpBtn = new JButton("Help");
		helpBtn.setBackground(new Color(0, 0, 139)); //blue
		helpBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		helpBtn.setForeground(new Color(237, 237, 237));
		helpBtn.setEnabled(true);
		add(helpBtn);
		helpBtn.addActionListener(this);
					
		/* Puts the label on a line of its own.
		 * This Rigid Area still does take up a pixel
		 * in height, so that must be taken into consideration. */
		add(Box.createRigidArea(new Dimension(2000, 0)));
		
		/* Add game label */
		gameLbl = new JLabel("");
		gameLbl.setFont(new Font("Verdana", Font.BOLD, 25));
		gameLbl.setForeground(Color.magenta);
		add(gameLbl);
		
	}
	
	/**Updates the score displayed.*/
	public void updateScoreLabel(){
		scoreVal.setText(""+Record.score);
	}
	
	/**Updates the level displayed.*/
	public void updateLevelLabel(){
		levelVal.setText(""+Record.level);
	}

	/**updates the display of the player's remaining life.*/
	public void updatePlayerLifeLabel(){
		playerLifeVal.setText(""+Record.playerLife+"%        ");
	}
	
	/**Shows the player that the game is over.*/
	public void gameOver(){
		gameLbl.setText("Game Over. New Game?");
	}
	
	/** Shows the player that he/she can move onto next level */
	public void nextLevel(){
		gameLbl.setText("Next Level available");
	}
	
	/** Shows the player that the game is paused */
	public void paused(){
		gameLbl.setText("Paused. Press \'P\' to Resume.");
	}
	
	/** Shows the player that the game is going on */
	public void playing(){
		gameLbl.setText("");
	}
	
	/** Shows the player's life when the new game starts */ 
	public void updateNewPlayerLifeLabel(){
		playerLifeVal.setText("100%   ");
	}
	
	/** Sets the new game button enabled */
	public void setNewGameBtnEnabled(boolean flag){
		newGameBtn.setEnabled(flag);
	}
	
	/** Set the next level button enabled */
	public void setNextLevelBtnEnabled(boolean flag){
		nextLvlBtn.setEnabled(flag);
	}
		
	/** Check if the next level button is enabled.
	 * 
	 * @return value true if enabled otherwise returns false.
	 */
	public boolean isNextLevelBtnEnabled(){
		return nextLvlBtn.isEnabled();
	}
	
	/** Check if the load button is enabled.
	 * 
	 * @return value true if enabled otherwise returns false.
	 */
	public boolean isLoadBtnEnabled(){
		return loadBtn.isEnabled();
	}
	
	/** Set the load button enabled */
	public void setLoadBtnEnabled(boolean flag){
		loadBtn.setEnabled(flag);
	}
	
	/** Set the radio buttons enabled */
	public void setRadioEnabled(boolean flag){
		loadNewGameRdo.setEnabled(flag);
		loadResumeRdo.setEnabled(flag);
	}
	
	/** Invoked when the button get clicked
	 * Overloaded from Interface ActionListener
	 */
	public void actionPerformed(ActionEvent e){
		/* next level button clicked */
		if(e.getSource() == nextLvlBtn){
			if(Record.paused)
				Record.paused = false;
			Record.nextLevel = true;
		}
		/* new game button clicked */
		if(e.getSource() == newGameBtn){
			if(Record.paused)
				Record.paused = false;
			Record.newGame = true;
		}
		/* load button clicked */
		if(e.getSource() == loadBtn){
			if(Record.loadNewGame){
				namePopup('N'); //no need to get player's record
				if(Record.playername != null){
					Database.emptyVectors();
					Record.level = 1;
					Record.score = 0;
					Record.playerLife = 100;
					Record.loadGame = true;
				}
			}
			else{ //Record.loadResume
				namePopup('R');
				if(Record.mostRecentPlayerName != null){
					if(!Database.isDataFile()){
						Database.emptyVectors();
						Record.mostRecentPlayerName = "";
						JOptionPane.showMessageDialog(null, "No record exists.\n"
								+"Load a new game");
					}
					else{ //data exists
						Database.readData();
						if(Database.getMostRecentPlayerInfo()){
							if(Record.playerLife == 0){
								Database.emptyVectors();
								Record.mostRecentPlayerName = "";
								JOptionPane.showMessageDialog(null, "That player is destroyed.\n"
											+"Load a new game");		
							}
							else{
								Database.emptyVectors();
								Record.playername = Record.mostRecentPlayerName;
								Record.loadGame = true;
							}
						}//end if
					}//end else
				}//end if
			}//end else
		}//end loadBtn
		/* quit button clicked */
		if(e.getSource() == quitBtn){
			Record.paused=true;
			if(Record.beforeLoading)
				quitPopupBeforeLoading();
			else if (Record.playerLife==0)
				quitPopupAfterDying();
			else
				quitPopupAfterLoading();
		}
		/* help button clicked */
		if(e.getSource() == helpBtn){
			Record.paused=true;
			new Help();
		}
	}
	
	/** Invoked when a radio button gets selected.
	 * Overloaded from Interface ItemListener.
	 */
	public void itemStateChanged(ItemEvent e){
		/* new game button selected */
		if(e.getItemSelectable() == loadNewGameRdo)
			if(Record.beforeLoading){
				Record.loadNewGame = true;
				Record.loadResume = false;
			}
		/* resume button selected */
		if(e.getItemSelectable() == loadResumeRdo)
			if(Record.beforeLoading){
				Record.loadNewGame = false;
				Record.loadResume = true;
			}
	}
	
	/* helper method displaying quit options before a game is loaded */
	private void quitPopupBeforeLoading(){
		String[] choices = {"Yes", "No"};
		int choice = JOptionPane.showOptionDialog(null, //center in window
				"Do you want to quit before loading the game?", //message
				"Quit Options",  //title
				JOptionPane.YES_NO_OPTION, //option type
				JOptionPane.PLAIN_MESSAGE, //message type
				null, //icon
				choices, //button text
				"Yes"); //default button's label
		switch(choice){
			case 0: //quit
				System.exit(0);
				break;
			default: //not quitting
		}
	}
		
	/* helper method displaying quit options after a player is dead */
	private void quitPopupAfterDying(){
		String[] choices = {"Yes", "No"};
		int choice = JOptionPane.showOptionDialog(null, //center in window
				"Do you want to quit without starting a new game?", //message
				"Quit Options",  //title
				JOptionPane.YES_NO_OPTION, //option type
				JOptionPane.PLAIN_MESSAGE, //message type
				null, //icon
				choices, //button text
				"Yes"); //default button's label
		switch(choice){
			case 0: //quit
				/* save highest score if a user sets a new record */
				PlayerInfo p = new PlayerInfo(Record.playername, Record.score, 
						Record.level, Record.playerLife);
				Database.emptyVectors();
				if(Database.isHighestScoreFile()) 
					Database.readHighestScore();
				Database.addPlayerInfoHighestScore(p);
				Database.createHighestScore();
				System.exit(0);
				break;
			default: //not quitting
		}
	}
	
	/* helper method displaying quit options after a game is loaded */
	private void quitPopupAfterLoading(){
		String[] choices = {"Save", "Don't save", "Cancel"};
		int choice = JOptionPane.showOptionDialog(null, //center in window
				"Do you want to quit with saving the game?", //message
				"Quit Options",  //title
				JOptionPane.YES_NO_OPTION, //option type
				JOptionPane.PLAIN_MESSAGE, //message type
				null, //icon
				choices, //button text
				"Save"); //default button's label
		switch(choice){
			case 0: //quit with saving the game
				PlayerInfo p = new PlayerInfo(Record.playername, Record.score, 
								Record.level, Record.playerLife);
				Database.emptyVectors();
				Database.addPlayerInfoData(p);
				if(Database.isDataFile())
					Database.readData();
				if(Database.isHighestScoreFile())
					Database.readHighestScore();
				Database.addPlayerInfoHighestScore(p);
				Database.createData();
				Database.createHighestScore();
				JOptionPane.showMessageDialog(null, "Data saved over \'data.txt\'");
				System.exit(0);
				break;
			case 1: //quit without saving the game
				JOptionPane.showMessageDialog(null, "Quit the game without saving data.");
				System.exit(0);
				break;
			default: //cancel
		}
	}
	
	/* helper method providing pop-up window for getting a user's name as input */
	private void namePopup(char c){
		String name = JOptionPane.showInputDialog(null, "Please enter your name!");
		name = removeFrontWhiteSpace(name);
		if(name == null || name.equals(""))
			JOptionPane.showMessageDialog(null, 
					"Please click the button \'Load\' again!");
		else{
			name = name.toUpperCase();
			if(c == 'N'){
				JOptionPane.showMessageDialog(null, "Your name is "+name+"."
					+"\nGame now starts!");
				Record.playername = name;
			}
			else //c == 'R'
				Record.mostRecentPlayerName = name;
		}
	}
	
	/* helper method used for getting rid of any white spaces
	 *  from input of a user's name */
	private String removeFrontWhiteSpace(String s){
		char[] tmp = s.toCharArray();
		short i = 0;
		for(; i<tmp.length; i++)
			if(tmp[i] != ' ')
				break;
		return s.substring(i);
	}
}
