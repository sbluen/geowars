package edu.ucsb.umail.sbluen.gamedata;

/** Contains a current player's record.
 * They are player's name, score, level, and remaining life. 
 * @author Yunsik Choi, group G3
 * 
 */
public class PlayerInfo {
	private String name;
	private int score;
	private int level;
	private int life;
	
	/** Constructor for recording player's record
	 * 
	 * @param name player's name
	 * @param score player's score
	 * @param level player's level
	 * @param life player's remaining life
	 */
	public PlayerInfo(String name, int score, int level, int life){
		this.name = name;
		this.score = score;
		this.level = level;
		this.life = life;
	}
	
	/** Gets the player's name
	 * 
	 * @return the player's name 
	 */
	public String name(){
		return name;
	}
	
	/** Gets the player's score  
	 * 
	 * @return the player's score 
	 */
	public int score(){
		return score;
	}
	
	/** Gets the player's level 
	 * 
	 * @return the player's level 
	 */
	public int level(){
		return level;
	}
	
	/** Gets the player's remaining life  
	 * 
	 * @return the player's remaining life
	 */
	public int life(){
		return life;
	}
	
	/** Sets the player's name 
	 * 
	 * @param s the player's name 
	 */
	public void setName(String s){
		name = s;
	}
	
	/** Sets the player's score 
	 * 
	 * @param n the player's score 
	 */
	public void setScore(int n){
		score = n;
	}
	
	/** Sets the player's level 
	 * 
	 * @param n the player's level 
	 */
	public void setLevel(int n){
		level = n;
	}
	
	/** Sets the player's remaining life 
	 * 
	 * @param n the player's remaining life 
	 */
	public void setLife(int n){
		life = n;
	}
}
