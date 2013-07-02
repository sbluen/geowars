package edu.ucsb.umail.sbluen.gameui;

import java.awt.Container;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** Creates a pop-up window displaying tips on playing games.
 * @author Yunsik Choi group G3
 * 
 */

@SuppressWarnings("serial")
public class Help extends JFrame{
	private Container c;
	private JLabel[] docLbl;
	
	/** Constructor for designing and determining contents of the help pop-up window.
	 */
	public Help(){
		setTitle("Help");
		setSize(690, 460);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BoxLayout(c, 1));
		c.setBackground(new Color(0, 100, 0));
		
		short size = 20; // total 20 lines in the window
		docLbl = new JLabel[size];
		for(short i=0; i<size; i++){
			docLbl[i] = new JLabel();
			docLbl[i].setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			docLbl[i].setForeground(Color.white);
		}
		
		docLbl[0].setText("            ********** Things to Know Before Playing Space Invaders **********");
		docLbl[1].setText("\n");
		docLbl[2].setText("@ to load a new game, click on \'Load New Game?\' and then click on \"Load\" button.");
		docLbl[3].setText("@ to resume the game, click on \'Resume Game?\' and then click on \"Load\" button.");
		docLbl[4].setText("@ to quit the game, click on \'Quit\' button. Close (X) is disabled.");
		docLbl[5].setText("@ to start a new game after having already loaded, click on \'New Game\' button.");
		docLbl[6].setText("@ to move on the next level, click on \'Next Level\' button.");
		docLbl[7].setText("@ to pause the game, press \'P\'.");
		docLbl[8].setText("@ to move the player, use the arrow keys or the WASD keys.");
		docLbl[10].setText("@ to fire missiles, press space bar or click on the left mouse.");
		docLbl[11].setText("@ to point the player toward a direction you want, point the mouse toward the direction.");
		docLbl[12].setText("@ to save the game, click on \'Save\' when you quit the game.");
		docLbl[13].setText("@ \'data.txt\' and \'highestScore.txt\' will be created in the current directory.");
		docLbl[14].setText("@ \'data.txt\' stores every play record (player's name, score, level, and remaining life).");
		docLbl[15].setText("@ \'data.txt\' will be created when you save the game.");
		docLbl[16].setText("@ \'highestScore.txt\' contains top 10 highest scores.");
		docLbl[17].setText("@ \'highestScore.txt\' will be created automatically.");
		docLbl[18].setText("@ sound is available. Enjoy the game!");
		docLbl[19].setText("@ created by Daryl Hennegan, Steven Bluen, and Yunsik Choi in 2009");
		
		for(short i=0; i<size; i++)
			c.add(docLbl[i]);
		
		setVisible(true);
	}
}
