package main.gameUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import main.entities.ChaserEnemy;
import main.entities.Enemy;
import main.entities.Entity;
import main.entities.Missile;
import main.entities.PlaceholderEnemy;
import main.entities.Player;
import main.entities.SatelliteEnemy;
import main.gameData.AngleCalculator;
import main.gameData.Record;
import main.gameData.Sound;
import main.gameUI.SubMenu;
import main.entities.ShooterEnemy;
import main.entities.PowerUp;

/**This class is essentially the main class that is executed.
 *  Game contains the objects displayed 
 *  on the screen and displays/animates them.
 *  We made it also handle the user input.
 *
 *  @author Kevin Glass, at http://www.cokeandcode.com/node/6,
 *  and heavily modified by Daryl Hennegan, and Yunsik Choi, group G3
 */
@SuppressWarnings("serial")
public class Game extends Canvas implements KeyListener, MouseInputListener{ 
	
	/* Dimensions of game, sub-menu, and frame */
	private final int WIDTH = 800, HEIGHT = 600;
	private final int SUBMENU_W = 800, SUBMENU_H = 100;
	private final int FRAME_W = 800, FRAME_H = 800;
	/* Interval between frames in millisecond */
	private final int INTERVAL = 10;
	/* Used for buffering images and accelerating page flipping */
	private BufferStrategy strategy;
	/* Stores created entities */ 
	private Vector<Entity> entities = new Vector<Entity>();
	/* Stores entities to be removed from the game panel */
	private Vector<Entity> removedEntities = new Vector<Entity>();
	private Entity player = null;
	/* Represents if the arrow keys are pressed */
	private boolean leftPressed = false, rightPressed = false;
	private boolean upPressed = false, downPressed = false;
	/* Represents if space bar invoking firing missiles is pressed */
	private boolean shootingPressed = false;
	/* Speed of player, enemies, and missiles in millisecond */
	private double playerSpeed = 300, enemyMaxSpeed = 5.0, missileSpeed = 500;
	/* Time at which last missile was discharged */
	private long lastShooting = 0;
	/* Interval between shots in millisecond */
	private long shootingInterval = 200;
	/* Initial number of enemies at level 1 */
	private int numOfEnemies = 10;
	/* Keeps track of how many enemies are killed */
	private int enemyLife;
	/* Keeps track of how much a player's life remains */
	private double playerLifeFull = 100;
	
	/* x coordinate of the mouse */
	private int mouseX;
	/* y coordinate of the mouse */
	private int mouseY; //y coordinate of the mouse
	/* Sound variables */
	private Sound shootingSound, gameOverSound, scoreSound;
	
	SubMenu submenu;
	private JFrame frame;
	private JPanel gamePanel, submenuPanel;
	
	Game(){
		frame = new JFrame("Space Invaders");
		frame.setSize(FRAME_W, FRAME_H);
		/* Close, X is disabled */
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setLayout(new BorderLayout());

		/* Add Game panel */
		gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.black);
		setBounds(0, 0, WIDTH, HEIGHT);
		gamePanel.add(this);
				
		/* Add SubMenu panel */
		submenuPanel = new JPanel();
		submenuPanel.setPreferredSize(new Dimension(SUBMENU_W, SUBMENU_H));
		submenuPanel.setLayout(null);
		submenuPanel.setBackground(Color.yellow);
		submenu = new SubMenu();
		submenuPanel.add(submenu);
		
		/* Add the panels to the frame */
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(submenuPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		/* This is useful, for example, if running under full-screen mode  
		 * and better performance is desired, or if page-flipping is used 
		 * as the buffer strategy. */ 
		setIgnoreRepaint(true);
			
		addKeyListener(this);
	    addMouseListener(this);
	    addMouseMotionListener(this);
		requestFocus();
		
		/* Double buffering of images */
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		/* Creates sound objects */
		shootingSound = new Sound("shooting");
		gameOverSound = new Sound("gameOver");
		scoreSound = new Sound("score");
	}
	
	void newGame(){
		requestFocus();
		Record.playing = true;
		entities.removeAllElements();
		Graphics2D g2 = (Graphics2D)strategy.getDrawGraphics();
		g2.dispose();
		Record.level = 1;
		submenu.updateLevelLabel();
		Record.score = 0;
		submenu.updateScoreLabel();
		Record.playerLife = (int)playerLifeFull;
		submenu.updateNewPlayerLifeLabel();
		numOfEnemies = 10;
		if(Record.level == 1){
			player = new Player("resources/player.png", WIDTH/2, HEIGHT-80, this);
			entities.add(player);
		}
		initEntities();
		Record.newGame = false;
		submenu.setNewGameBtnEnabled(false);
		submenu.setNextLevelBtnEnabled(false);
	}

	/* The main game loop running during all game play */
	void gameLoop(){
		long lastLoopTime = System.currentTimeMillis();
		Graphics2D g2;
		Entity entity;
		long n;
		int size;
		
		if(Record.paused && Record.playing){ /* Pause gets disabled when game is over */
			submenu.setNewGameBtnEnabled(true);
			submenu.paused();
		}
		
		if(Record.newGame)
			newGame();
					
		while (Record.playing&&!Record.paused){
			/* Determines how long its been since the last update.
			 * Used to calculate how far the entities should move this loop */
			n = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			g2 = (Graphics2D)strategy.getDrawGraphics();
			g2.setColor(Color.black);
			g2.fillRect(0, 0, WIDTH, HEIGHT);
			
			if(Record.nextLevel)
				nextLevel();
				
			/* While playing, these buttons are inactive */
			submenu.setNewGameBtnEnabled(false);
						
			if(submenu.isNextLevelBtnEnabled())
				submenu.nextLevel(); /* Shows "Next Level? " on screen */
			else
				submenu.playing(); /* Shows "Let's Go! " on screen */
			
			size = entities.size();
			
			for(int i=0; i<size; i++){
				entity = entities.get(i);
				entity.draw(g2);
			}
			
			/* Calls the move method on every entity */
			for(int i=0; i<size; i++){
				entity = entities.get(i);
				entity.move(n);
				/* For enemies that shoot */
				if(entity instanceof ShooterEnemy){
					ShooterEnemy shooterentity = (ShooterEnemy) entity;
					if(shooterentity.getShot() == true)
						entities.add(shooterentity.Shoot());	
				}
				/* Remove missiles going off out of the boundary */
				if(entity instanceof Missile && 
						(entity.getY() < -100 || entity.getY() > 1000 || 
								entity.getX() < -100 || entity.getX() > 1000))
					removeEntity(entity);
			}
			
			/* Check collisions */
			Entity me, other;
			for(int i=0; i<size; i++){
				for(int j=i+1; j<size; j++){
					me = entities.get(i);
					other = entities.get(j);
					if(me.collide(other)){
						/* If a player runs into a powerup */
						if(other instanceof PowerUp && me instanceof Player){
							PowerUp powerup = (PowerUp) other;
							int amount = powerup.getAmount();
							String type = powerup.getType();
							if(type.equals("Score")){
								Record.score += amount;
								submenu.updateScoreLabel();
								scoreSound.play();
							}
							if(type.equals("Life")){
								if(Record.playerLife <= 80){
									Record.playerLife += amount;
									submenu.updatePlayerLifeLabel();
								}
								else
								if(Record.playerLife > 80){
									Record.playerLife = 100;
									submenu.updatePlayerLifeLabel();
								}
							}
							
							removeEntity(other);
						} //end of if(other instanceof PowerUp && me instanceof Player)
											
						/* EEnemies collide with Missile and not the other
						/* way around, for some reason */
						if((other instanceof Missile && me instanceof Enemy)){
							Missile missile = (Missile) other;
							Enemy enemy = (Enemy) me;
							if(missile.getSource() instanceof Player){
								notifyEnemyKilled(enemy.getValue(),enemy.getX(),enemy.getY());
								removeEntity(me);
								removeEntity(other);
								//--enemyLife;
							}
						}
						if(me instanceof Player && other instanceof Enemy)
							Record.playerLife-=0.5;
						
						notifyPlayerKilled();
						
						/* If an enemy missile hits the player */
						if((me instanceof Player && other instanceof Missile)){
							Missile missile = (Missile) other;
							if(missile.getSource() instanceof Enemy){
								removeEntity(other);
								notifyPlayerKilled();
							}
						}	
							
						//If an enemy missile hits the player
						if((other instanceof Missile && me instanceof Player)){
							Missile missile = (Missile) other;
							if((missile.getSource()) instanceof Enemy){
								removeEntity(other);
								Record.playerLife-=5;
								notifyPlayerKilled();
							}
						}							
					} //end of if(me.collide(other))
				} //end of for(int j=i+1; j<size; j++)
			} // end of for(int i=0; i<size; i++)
			
			/*Removes entities killed from the screen */
			entities.removeAll(removedEntities);
			removedEntities.clear();
	
			/* Disposes of this graphics context and releases any system resources 
			 * that it is using. A Graphics object cannot be used after dispose has been 
			 * called. When a Java program runs, a large number of Graphics objects 
			 * can be created within a short time frame. Although the finalization process 
			 * of the garbage collector also disposes of the same system resources, 
			 * it is preferable to manually free the associated resources by calling 
			 * this method rather than to rely on a finalization process 
			 * which may not run to completion for a long period of time. */
			g2.dispose();
	
			/* Makes the next available buffer visible by either copying the memory 
			 * (blitting) or changing the display pointer (flipping). */
			strategy.show();
	
			/* Mmoves over to a spot before enemies get created
			/* to allow the player's coordinate to be put inside there */
			//player = (entities.get(0)); 
			player.setHorizontalMove(0);
			player.setVerticalMove(0);
	
			if(leftPressed && !rightPressed){
				player.setHorizontalMove(-playerSpeed);
			}
			if(!leftPressed && rightPressed){
				player.setHorizontalMove(playerSpeed);
			}
			if(upPressed && !downPressed){
				player.setVerticalMove(-playerSpeed);
			}
			if(!upPressed && downPressed){
				player.setVerticalMove(playerSpeed);
			}
			
			/* Makes sure the player cant leave the screen */
			////Added by Daryl as of 5-2-09
			if(leftPressed && player.getX() <= 10){
				player.setHorizontalMove(0);
			}
			if(rightPressed && player.getX() >= (WIDTH - 30)){
				player.setHorizontalMove(0);
			}
			if(upPressed && player.getY() <= (10)){
				player.setVerticalMove(0);
			}
			if(downPressed && player.getY() >= (HEIGHT - 30)){
				player.setVerticalMove(0);
			}
				
			/* Quick additional check for use in the presentation */
			if (player.getY()>(HEIGHT-30)){
				player.setY(HEIGHT-30);
			}
				
			/* Invokes shooting event */			
			if(shootingPressed){
				shoot();
			}
	
			/* Sets the interval before redrawing the screen */
			try{
				Thread.sleep(INTERVAL);
			}
			catch(Exception e){
				System.exit(1);
			}
		} //end of while
	} //end of gameLoop()

	private void initEntities(){
		Entity enemy;
		
		//Limits number of enemies to 100
		if(numOfEnemies > 100)
		{
			numOfEnemies = 100;
		}
		
		/* Add enemies into the vector (entities) */
		double x, y, vx, vy;
		int choice = 0;  //choice of enemy type
		for(int i=0; i<numOfEnemies; i++){
			choice = (int)(Math.random()*5);
			x = Math.random()*(WIDTH-50);
			if(x < 50)
				x = 50;
			y = 50; /* coming from the top */
			vx = Math.random()*enemyMaxSpeed;
		    if(vx < 1)
		    	vx = 1;
		    vy = Math.random()*enemyMaxSpeed;		
		    if(vy < 1)
		    	vy = 1;	
		    if (choice==0){
		    //if (choice==0&&Record.level>=3){
		    	enemy=new ShooterEnemy(x, y, this,
		    			Math.random()*2-1, Math.random()*2-1, .5, WIDTH, HEIGHT, player);
		    }
		    else if (choice==1)
			    	enemy=new ChaserEnemy(x, y, this,
								vx, vy, enemyMaxSpeed, WIDTH, HEIGHT, player);
			    else if (choice==2){
				    enemy=new PlaceholderEnemy(x, y, this,
						vx, vy, enemyMaxSpeed, WIDTH, HEIGHT);
			    	entities.add(new SatelliteEnemy(Math.random()*2*Math.PI,
			    		Math.random()*0.02-0.01, 40, this, WIDTH, HEIGHT, enemy));
			    }
			    else
			    	enemy = new Enemy(x, y, this,
						vx, vy, enemyMaxSpeed, WIDTH, HEIGHT);
				entities.add(enemy);
			}
		
		enemyLife = numOfEnemies;
	}
	
	/* Initializes enemies when a user resumes the game */
	void initEntitiesResume(){
		numOfEnemies=10;
		for (int i=2; i<=Record.level+1; i++){
			numOfEnemies+=i;
		}
		player = new Player("resources/player.png", WIDTH/2, HEIGHT-80, this);
		entities.add(player);
				
		initEntities();
	}
		
	private void nextLevel()
	{
		requestFocus();
		Record.level++;
		submenu.updateLevelLabel();
		numOfEnemies += Record.level;
		player.setX(WIDTH/2);
		player.setY(HEIGHT - 50);
		
		/*wait 200 milliseconds before next level
		try{
			Thread.sleep(200);
		}
		catch(Exception e){
			System.exit(1);
		}
		*/
		
		Record.nextLevel = false;
		initEntities();
		submenu.setNextLevelBtnEnabled(false);
		submenu.setNewGameBtnEnabled(false);
	}
	
	private void removeEntity(Entity entity){
		removedEntities.add(entity);
	}
	
	private void notifyEnemyKilled(int score,double x, double y){
		--enemyLife;
		Record.score += score;
		submenu.updateScoreLabel();
		
		int chance = (int)((Math.random()) * 1000);
		if(chance < 100){
			PowerUp powerup = new PowerUp("Score",this,x,y);
			entities.add(powerup);	
		}
		if(chance > 900){
			PowerUp powerup = new PowerUp("Life",this,x,y);
			entities.add(powerup);	
		}
			
		scoreSound.play();
				
		if(enemyLife == 0){
			submenu.setNextLevelBtnEnabled(true);
			submenu.nextLevel();
		}
				
		for(int i=0; i<entities.size(); i++){
			Entity entity = entities.get(i);
			if(entity instanceof Enemy){
				/* Enemy's speed increases by 2% */
				entity.setHorizontalMove(entity.getHorizontalMove()*1.02);
				entity.setVerticalMove(entity.getVerticalMove()*1.02);
			}
		}
	}
	
	private void notifyPlayerKilled(){
		if(Record.playerLife < 0)
			Record.playerLife = 0;
		submenu.updatePlayerLifeLabel();
		if(Record.playerLife == 0){
			gameOverSound.play();
			Record.playing = false;
			submenu.setNewGameBtnEnabled(true);
			submenu.gameOver();
		}
	}
	
	private void shoot(){
		if((System.currentTimeMillis() - lastShooting) < shootingInterval)
			return;
		lastShooting = System.currentTimeMillis();
		Missile missile = new Missile(player, 
					player.getX(), player.getY(), this, 
					(missileSpeed*Math.sin(player.getTheta()))
					, missileSpeed*Math.cos(player.getTheta()), player.getTheta());
		
		entities.add(missile);
		shootingSound.play();
	}
	
	/** Handles keypresses by the player.
	 * This class sets the game variables to show the game that
	 * the key is pressed.
	 */
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
			upPressed = true;
			player.setTheta(AngleCalculator.calculateAngle(player,mouseX,mouseY));
		}	
		else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
			downPressed = true;
			player.setTheta(AngleCalculator.calculateAngle(player,mouseX,mouseY));
		}	
		else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = true;
			player.setTheta(AngleCalculator.calculateAngle(player,mouseX,mouseY));
		}	
		else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = true;
			player.setTheta(AngleCalculator.calculateAngle(player,mouseX,mouseY));
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			shootingPressed = true;
		else if(e.getKeyCode() == KeyEvent.VK_P) //pause
			Record.paused=!Record.paused;
		else /* Do nothing if pressed any other keys */
			return;
	}

	/**Handles key releases by the player.
	 * This class sets the game variables to show the game that
	 * the key is released.
	 */
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			upPressed = false;
		else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
			downPressed = false;
		else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = false;
		else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = false;
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			shootingPressed = false;
		else
			return;
	}

	/**Handles key types.
	 * Does nothing for now.
	 */
	public void keyTyped(KeyEvent e){}

	/**Does nothing for now.
	 * Handles mouse clicks.
	 */
	public void mouseClicked(MouseEvent e){}

	/**Does nothing for now.
	 * Handles the entering of the mouse.
	 */
	public void mouseEntered(MouseEvent e){}

	/**Does nothing for now.
	 * Handles the exiting of the mouse.
	 */
	public void mouseExited(MouseEvent e){}		

	/**Sets the current mouse coordinates to where the mouse is.
	 * Handles the dragging of the mouse.
	 */
	public void mouseDragged(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		player.setTheta(AngleCalculator.calculateAngle(player,e));
	}  

	/**Sets the current mouse coordinates to where the mouse is.
	 * Handles the movement of the mouse.
	 */
	public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		player.setTheta(AngleCalculator.calculateAngle(player,e));
	}

	/**Makes the player start shooting.
	 * Handles the pressing of the mouse.
	 */
	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1)
			shootingPressed = true;
	}

	/**Makes the player stop shooting.
	 * Handles the releasing of the mouse.
	 */
	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1)
			shootingPressed = false;
	}
	
	/**The main method.
	 * Put in this class because it makes things simpler
	 * @param args does nothing for now.
	 */
	public static void main(String[] args){
		Game game = new Game();
		
		/* Makes sure be ready to load game */
		while(!Record.loadGame);
						
		/* Loads a new game for a new user and a returning user */
		if(Record.loadNewGame)
			game.newGame();
				
		/* Reloads the game for a returning user */
		else if(Record.loadResume){
			game.submenu.updateLevelLabel();
			game.submenu.updateScoreLabel();
			game.initEntitiesResume();
		}
			
		game.submenu.setLoadBtnEnabled(false); /* Load button is no longer needed */
		game.submenu.setRadioEnabled(false); /*Only works with the load button */
		Record.beforeLoading = false;
		game.requestFocus();
		while(true)
			game.gameLoop();
	} //end of main()
}
