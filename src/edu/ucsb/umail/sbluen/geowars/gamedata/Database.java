package edu.ucsb.umail.sbluen.geowars.gamedata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/** This handles saving and loading player's record
 * after finishing playing the game.
 * @author Yunsik Choi, group G3
 *  
 */
public class Database{
	/* output file */
	private static PrintWriter outfile = null;
	
	/* existing data file */
	private static File existingfile = null;
	
	/* store PlayerInfo into vector */
	private static Vector<PlayerInfo> highestScoreV = new Vector<PlayerInfo>();
	
	private static Vector<PlayerInfo> dataV = new Vector<PlayerInfo>();
	
	/** Creates data of record for player so that he/she can resume the game later
	 * @return boolean value. returns true if data created successfully, or else false.
	 */
	public static boolean createData(){
		try{
			//get a current directory
			File fCurrDir = new File(System.getProperty("user.dir"));
			existingfile = new File(fCurrDir + "/data.txt");
			
			PlayerInfo p =null;
			outfile = new PrintWriter(new FileOutputStream(existingfile));
			outfile.printf("%s,%s,%s,%s%n", "Name", "Score", "Level", "Life");
			int size = dataV.size();
			for(int i=0; i<size; i++){
				p = dataV.elementAt(i);
				outfile.printf("%s,%d,%d,%d%n", p.name(),
								p.score(), p.level(), p.life());
			}
		}
		catch(IOException e){
			outfile.close();
			return false;
		}
		catch(Exception e){
			outfile.close();
			return false;
		}
		outfile.close();
		return true;
	}
	
	/** Creates data of highest scores for player.
	 * @return boolean value. returns true if data created successfully, or else false.
	 */
	public static boolean createHighestScore(){
		/* sort vector based on score and name */
		int size = highestScoreV.size(), i;
		for(int j=0; j<size; j++){
			i = 0; 
			while(i<size-1){
				if(highestScoreV.elementAt(i).score() < highestScoreV.elementAt(i+1).score()){
					PlayerInfo tmp = highestScoreV.elementAt(i);
					highestScoreV.set(i, highestScoreV.elementAt(i+1));
					highestScoreV.set(i+1, tmp);
				}
				else if(highestScoreV.elementAt(i).score() == highestScoreV.elementAt(i+1).score() && 
						highestScoreV.elementAt(i).name().compareToIgnoreCase(highestScoreV.elementAt(i+1).name())>0){
					PlayerInfo tmp = highestScoreV.elementAt(i);
					highestScoreV.set(i, highestScoreV.elementAt(i+1));
					highestScoreV.set(i+1, tmp);
				}
				i++;
			}//end of while
		}//end of for
				
		try{
			//get a current directory
			File fCurrDir = new File(System.getProperty("user.dir"));
			existingfile = new File(fCurrDir + "/highestScore.txt");
						
			PlayerInfo p =null;
			outfile = new PrintWriter(new FileOutputStream(existingfile));
			outfile.printf("%s,%s,%s,%s%n", "Name", "Score", "Level", "Life");
			int cnt;
			for(i=0, cnt=0; i<size && cnt<10; i++, cnt++){ //save top 10 highest scores
				p = highestScoreV.elementAt(i);
				outfile.printf("%s,%d,%d,%d%n", p.name(),
								p.score(), p.level(), p.life());
			}
		}
		catch(IOException e){
			outfile.close();
			return false;
		}
		catch(Exception e){
			outfile.close();
			return false;
		}
		outfile.close();
		return true;
	}
	
	/** Creates the vector containing record of players.
	 * @return boolean value. returns true if data is read successfully, or else false.
	 */
	public static boolean readData(){
		Scanner infile = null;
		try{
			//get a current directory
			File fCurrDir = new File(System.getProperty("user.dir"));
			existingfile = new File(fCurrDir + "/data.txt");
			infile = new Scanner(new FileInputStream(existingfile));
			StringTokenizer token = null;
			infile.nextLine();
			String name = "";
			int score = 0, level = 0, life = 0;
			while(infile.hasNextLine()){
				token = new StringTokenizer(infile.nextLine(), ",");
				while(token.hasMoreTokens()){
					name = token.nextToken();
					score = Integer.parseInt(token.nextToken().trim());
					level = Integer.parseInt(token.nextToken().trim());
					life = Integer.parseInt(token.nextToken().trim());
					dataV.add(new PlayerInfo(name, score, level, life));
				}
			}//finish entire input file, vector created.
		}
		catch(FileNotFoundException e){
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			infile.close();
			return false;
		}
		infile.close();
		return true;
	}
	
	/** Gets player's previous record.
	 * @return boolean value. returns true if data is read and gets record successfully, or else false.
	 */
	public static boolean getMostRecentPlayerInfo(){
		int size = dataV.size();
		if(!Record.mostRecentPlayerName.equals("")){
			String name = Record.mostRecentPlayerName;
			for(int i=0; i<size; i++){
				if(name.equalsIgnoreCase(dataV.elementAt(i).name())){
					Record.score = dataV.elementAt(i).score();
					Record.level = dataV.elementAt(i).level();
					Record.playerLife = dataV.elementAt(i).life();
					return true;
				}
			}
			JOptionPane.showMessageDialog(null, 
				name+" was not saved. Load new game!");
			Record.mostRecentPlayerName = "";
			return false;
		}
		else
			return false;
	}
	
	/** Creates the vector containing highest scores of players.
	 * @return boolean value. returns true if data is read and gets record successfully, or else false.
	 */
	public static boolean readHighestScore(){
		Scanner infile = null;
		try{
			//get a current directory
			File fCurrDir = new File(System.getProperty("user.dir"));
			existingfile = new File(fCurrDir + "/highestScore.txt");
			infile = new Scanner(new FileInputStream(existingfile));
			StringTokenizer token = null;
			infile.nextLine(); //skip the first line
			String name = "";
			int score = 0, level = 0, life = 0;
			while(infile.hasNextLine()){
				token = new StringTokenizer(infile.nextLine(), ","); //separated by tab
				while(token.hasMoreTokens()){
					name = token.nextToken();
					score = Integer.parseInt(token.nextToken().trim());
					level = Integer.parseInt(token.nextToken().trim());
					life = Integer.parseInt(token.nextToken().trim());
					highestScoreV.add(new PlayerInfo(name, score, level, life));
				}
			}//finish entire input file, vector created.
			infile.close();
			return true;
		}
		catch(FileNotFoundException e){
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			infile.close();
			return false;
		}
	}
	
	/** Adds the current player's record to the vector, dataV
	 * @param p the PlayerInfo object that contains player's name, score, level, and life.
	 */
	public static void addPlayerInfoData(PlayerInfo p){
		dataV.insertElementAt(p, 0);
	}
	
	/** Adds the current player's highest score to the vector, highestScoreV if he/she sets a new record
	 * @param p the PlayerInfo object that contains player's name, score, level, and life.
	 */
	public static void addPlayerInfoHighestScore(PlayerInfo p){
		int size = highestScoreV.size();
		if(size == 0)
			highestScoreV.add(p);
		else{
			String name = Record.playername;
			int score = Record.score;
			boolean addNewPlayer = true;
			for(int i=0; i<size; i++){
				if(name.equalsIgnoreCase(highestScoreV.elementAt(i).name()) &&
						score > highestScoreV.elementAt(i).score()){ //update
					highestScoreV.elementAt(i).setScore(score);
					highestScoreV.elementAt(i).setLevel(Record.level);
					highestScoreV.elementAt(i).setLife(Record.playerLife);
					addNewPlayer = false;
				}
				else if(name.equalsIgnoreCase(highestScoreV.elementAt(i).name()))
					addNewPlayer = false;
			}
			if(addNewPlayer)
				highestScoreV.add(p);
		}//end else
	}
	
	/** Verifies if data.txt exists.
	 * @return value true if the file exists, false if not.
	 */
	public static boolean isDataFile(){
		File fCurrDir = new File(System.getProperty("user.dir"));
		File datafile = new File(fCurrDir + "/data.txt");
		return datafile.exists();
	}
	
	/** Verifies if highestScore.txt exists.
	 * @return value true if the file exists, false if not.
	 */
	public static boolean isHighestScoreFile(){
		File fCurrDir = new File(System.getProperty("user.dir"));
		File highestScorefile = new File(fCurrDir + "/highestScore.txt");
		return highestScorefile.exists();
	}
	
	/** Clear all the elements and sets size 0 of both vectors
	 */
	public static void emptyVectors(){
		dataV.clear();
		highestScoreV.clear();
		dataV.setSize(0);
		highestScoreV.setSize(0);
	}
}
