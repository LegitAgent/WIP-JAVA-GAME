/**
	@author Jienzel Christenzen H. Chua (231567), Martin Darius Alba (230179)
	@version 02 September 2023
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import javax.swing.*;
import java.awt.*;
import java.util.*;

/*
 * GameCanvas is where all the shapes, images and animations are drawn.
 */

public class GameCanvas extends JComponent {
	
	public static final int CANVAS_X = 800;
	public static final int CANVAS_Y = 600;

	// Declares all variables to be used.
	private Player player1, player2;
	private Bullets startBullet = new Bullets(1, 0, 0, 0, true, true, 0);
	private DeathScreen deathScreen1, deathScreen2;

	private boolean singlePlayer = false;
	private long tempTime1 = 0, tempTime2 = 0;
	private int enemyHitIndex = 0;
	private int enemyAnimationIndex = 0;
	private int enemyIndex = 0;
	private boolean enemyHit = false;
	private JFrame frame;

	private ArrayList<Bullets> bulletScreen = new ArrayList<>();
	private ArrayList<Bullets> cloneBulletScreen = new ArrayList<>();
	private ArrayList<Bullets> sideCloneBulletScreen = new ArrayList<>();
	private ArrayList<Bullets> sideBulletScreen = new ArrayList<>();

	public static ArrayList<Enemy> enemies = new ArrayList<>();

	// BG & Miscellaneous
	Image bgImg = new ImageIcon("Background.png").getImage();
	Image hpImg = new ImageIcon("HP.png").getImage();

	// Player 1 Animation Frames
	ImageIcon player1ImageIdle1 = new ImageIcon("Player1_Idle1.png");
	ImageIcon player1ImageIdle2 = new ImageIcon("Player1_Idle2.png");
	ImageIcon player1ImageWalk1 = new ImageIcon("Player1_Walk1.png");
	ImageIcon player1ImageWalk2 = new ImageIcon("Player1_Walk2.png");
	ImageIcon player1ImageWalk3 = new ImageIcon("Player1_Walk3.png");
	ImageIcon player1ImageWalk4 = new ImageIcon("Player1_Walk4.png");
	ImageIcon player1ImageDead = new ImageIcon("Player1Dead.png");

	// Player 2 Animation Frames
	ImageIcon player2ImageIdle1 = new ImageIcon("Player2_Idle1.png");
	ImageIcon player2ImageIdle2 = new ImageIcon("Player2_Idle2.png");
	ImageIcon player2ImageWalk1 = new ImageIcon("Player2_Walk1.png");
	ImageIcon player2ImageWalk2 = new ImageIcon("Player2_Walk2.png");
	ImageIcon player2ImageWalk3 = new ImageIcon("Player2_Walk3.png");
	ImageIcon player2ImageWalk4 = new ImageIcon("Player2_Walk4.png");
	ImageIcon player2ImageDead = new ImageIcon("Player2Dead.png");

	// Normal Zombie Animation Frames
	ImageIcon zombieWalk1 = new ImageIcon("ZombieWalk1.png");
	ImageIcon zombieWalk2 = new ImageIcon("ZombieWalk2.png");
	ImageIcon zombieWalk3 = new ImageIcon("ZombieWalk3.png");
	ImageIcon zombieWalk4 = new ImageIcon("ZombieWalk4.png");
	ImageIcon zombieAttack1 = new ImageIcon("ZombieAttack1.png");

	// Big Zombie Animation Frames
	ImageIcon bigZombieWalk1 = new ImageIcon("BigZombieWalk1.png");
	ImageIcon bigZombieWalk2 = new ImageIcon("BigZombieWalk2.png");
	ImageIcon bigZombieWalk3 = new ImageIcon("BigZombieWalk3.png");
	ImageIcon bigZombieWalk4 = new ImageIcon("BigZombieWalk4.png");
	ImageIcon bigZombieAttack1 = new ImageIcon("BigZombieAttack2.png");

	// Fast Zombie Animation Frames
	ImageIcon fastZombieWalk1 = new ImageIcon("FastZombieWalk1.png");
	ImageIcon fastZombieWalk2 = new ImageIcon("FastZombieWalk2.png");
	ImageIcon fastZombieWalk3 = new ImageIcon("FastZombieWalk3.png");
	ImageIcon fastZombieAttack1 = new ImageIcon("FastZombieAttack.png");

	/**
	 * Constructor of GameCanvas
	 * @param f gets an input JFrame to use as a medium to where all of the things will be drawn.
	 */
	public GameCanvas(JFrame f) {
		frame = f;
		frame.setPreferredSize(new Dimension(815, 635));
		player1 = new Player(400, 300, 20, 20, 1, player1ImageIdle1, startBullet);
		player2 = new Player(500, 300, 20, 20, 1, player2ImageIdle1, startBullet);
		deathScreen1 = new DeathScreen(frame, player1);
		deathScreen2 = new DeathScreen(frame, player2);
		/**
		 * lineH = new Line2D.Double(0,300,800,300);
		 * lineV = new Line2D.Double(400,0,400,600);
		 */
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bgImg, 0, 0, 800, 600, null);
		g2d.drawImage(hpImg, 50, 560,30,30,null);

		if(singlePlayer) {
			player1.draw(g2d);
			if(!player1.isAlive()) {
				removeAllComponents();
				deathScreen1.showDeathScreen();
			}
		}
		else {
			player1.draw(g2d);
			player2.draw(g2d);
			if(!player1.isAlive()) {
				removeAllComponents();
				deathScreen1.showDeathScreen();
			}
			else if(!player2.isAlive()) {
				removeAllComponents();
				deathScreen2.showDeathScreen();
			}
		}
		
		// MAIN PLAYER BULLETS ================================================================
		// Makes sure that it does not edit the ArrayList as it is iterated.
		for (Bullets bullet : bulletScreen) {
			if (bullet.getX() > CANVAS_X + 10 || bullet.getX() < -10 || bullet.getY() > CANVAS_Y + 10 || bullet.getY() < -10) {
				bulletScreen.remove(bullet);
				break;
			}
		}
		
		for (Bullets bullet : bulletScreen) {
			bullet.draw(g2d);
			bullet.move();
		}
		copyCloneToMain(cloneBulletScreen);

		// SIDE PLAYER BULLETS ===============================================================
		for (Bullets bullet : sideBulletScreen) {
			if (bullet.getX() > CANVAS_X + 10 || bullet.getX() < -10 || bullet.getY() > CANVAS_Y + 10 || bullet.getY() < -10) {
				sideBulletScreen.remove(bullet);
				break;
			}
		}

		for (Bullets bullet : sideBulletScreen) {
			bullet.draw(g2d);
			bullet.move();
		}
		copyCloneToSide(sideCloneBulletScreen);

		/*
		 * Checks to see if each enemy has less than 0 health
		 * adds coins with different amounts, depending on the killed enemy.
		 */
		for(Enemy enemy : enemies) {
			if(enemy.getHealth() <= 0) {
				switch(enemy.getEnemyName()) {
					case "Normal Zombie":
						player1.addCoins(10);
						player2.addCoins(10);
						break;
					case "Big Zombie":
						player1.addCoins(50);
						player2.addCoins(50);
						break;
					case "Fast Zombie":
						player1.addCoins(25);
						player2.addCoins(25);
						break;
				}
				enemies.remove(enemy);
				break;
			}
		}
		
		/*
		 * Updates the animations of the zombies on screen.
		 */
		for(Enemy enemy : enemies) {
			updateEnemyAnimation(enemy, enemyAnimationIndex);
			enemyAnimationIndex++;
			enemy.draw(g2d);
			enemy.move();
		}
		enemyAnimationIndex = 0;
	}










	/**
	 * Updates the variable singlePlayer from GameCanvas.
	 * @param status true if there are no connections, false otherwise.
	 */
	public void updateSinglePlayerStatus(boolean status) {
		singlePlayer = status;
	}

	/**
	 * removes all the components of the JFrame.
	 */
    private void removeAllComponents() {

        // makes an array of type components and adds every single component by calling the getContentPane() function followed by getting every single component in the content pane.
        Component[] components = frame.getContentPane().getComponents();

        // iterates through a for loop function to remove these components one by one.
        for (Component component : components) {
            component.setVisible(false);
        }

        // we then repaint and revalidate the GUI to update it so that it displays nothing.
        frame.revalidate();
        frame.repaint();
    }

	/**
	 * returns player 1
	 * @return player 1
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * returns player 2
	 * @return player 2
	 */
	public Player getPlayer2() {
		return player2;
	}
	
	/**
	 * Updates player animations.
	 * @param pixelsWalked the amount of pixels walked by the player and updates the animation by some interval.
	 * @param playerID the players ID to check which animation to execute.
	 * @param idle if the player is Idle.
	 */
	public void updatePlayerAnimation(int pixelsWalked, int playerID, boolean idle) {
		if(playerID == 1) {
			if(!idle) {
				if(pixelsWalked >= 0 && pixelsWalked <= 10) {
					player1.updateImage(player1ImageWalk1);
				}
				else if(pixelsWalked > 10 && pixelsWalked <= 20) {
					player1.updateImage(player1ImageWalk2);
				}
				else if(pixelsWalked > 20 && pixelsWalked <= 30){
					player1.updateImage(player1ImageWalk3);
				}
				else if(pixelsWalked > 30 && pixelsWalked <= 40){
					player1.updateImage(player1ImageWalk4);
				}
			}
			else {
				/* gets tempTime as reference for the next call and subtracts the current time
				 * of the current call, checks to see if the differences between calls is 400ms
				 * switches to the next idle frame animation if true.
				 * Effectively: (FRAME 1: 400 ms, FRAME 2: 400 ms)
				*/
				long time1 = System.currentTimeMillis() - tempTime1;
				if(time1 >= 400 && time1 <= 1200) {
					player1.updateImage(player1ImageIdle1);
				}
				if(time1 >= 800) {
					player1.updateImage(player1ImageIdle2);
					tempTime1 = System.currentTimeMillis();
				}
			}
		}
		else {
			if(!idle) {
				if(pixelsWalked >= 0 && pixelsWalked <= 10) {
					player2.updateImage(player2ImageWalk1);
				}
				else if(pixelsWalked > 10 && pixelsWalked <= 20) {
					player2.updateImage(player2ImageWalk2);
				}
				else if(pixelsWalked > 20 && pixelsWalked <= 30){
					player2.updateImage(player2ImageWalk3);
				}
				else if(pixelsWalked > 30 && pixelsWalked <= 40){
					player2.updateImage(player2ImageWalk4);
				}
			}
			else {
				long time = System.currentTimeMillis() - tempTime2;
				if(time >= 400 && time <= 1200) {
					player2.updateImage(player2ImageIdle1);
				}
				if(time >= 800) {
					player2.updateImage(player2ImageIdle2);
					tempTime2 = System.currentTimeMillis();
				}
			}
		}
	}

	/*
	 * makes an arraylist of times when the player collided with an enemy
	 * sees whether or not the inervals between attacks is 2 seconds
	 * this prevents the player from dying instantly
	 */
	ArrayList<Long> timesEnemyPlayerCollision = new ArrayList<>();

	/**
	 * checks whether or not the specified player is colliding with any enemies.
	 * @param player player to check collision of.
	 */
	public void checkPlayerEnemyCollision(Player player) {
		int counter = 0;
		boolean collideWithID = false;
		// fills the array list of times. (prevents null pointers)
		if(timesEnemyPlayerCollision.size() <= checkAmountEnemies()) {
			timesEnemyPlayerCollision.add(System.currentTimeMillis());
		}
		for(Enemy enemy : enemies) {
			
			boolean horizontalCollision = player.getX() < enemy.getStartPosX() + enemy.getWidth() &&
										  player.getX() + player.getWidth() > enemy.getStartPosX();
			boolean verticalCollision = player.getY() < enemy.getStartPosY() + enemy.getHeight() &&
										player.getY() + player.getHeight() > enemy.getStartPosY();

			// if colliding, set enemy move speed to 0, and start a timer to prevent instantaneous damage.
			if(horizontalCollision && verticalCollision) {
				enemyAttackAnimation(enemy);
				enemy.setMoveSpeed(0);
				collideWithID = true;
				if(System.currentTimeMillis() - timesEnemyPlayerCollision.get(counter) > 2000) {
					timesEnemyPlayerCollision.set(counter, System.currentTimeMillis());
					player.takeDamage(enemy.getDamage());
				}
				
			}
			// checks to see if it is colliding with that specific player.
			else if(!horizontalCollision && !verticalCollision && collideWithID) {
				enemy.setMoveSpeed(enemy.getOriginalSpeed());
			}
			else if(!horizontalCollision && !verticalCollision && !collideWithID && singlePlayer) {
				enemy.setMoveSpeed(enemy.getOriginalSpeed());
			}
			counter++;
		}
	}

	/**
	 * prevents stagnant zombies, fixes zombie stuck when a player dies.
	 */
	public void resolveEnemySpeeds() {
		for(Enemy enemy : enemies) {
			enemy.setMoveSpeed(enemy.getOriginalSpeed());
		}
	}

	/**
	 * creates a new bullet to be added to the side-main array list of bullets.
	 * @param bulletType type of bullet.
	 * @param x x-coordinate of bullet
	 * @param y y-coordinate of bullet
	 * @param slope slope of the bullet.
	 * @param inverseX if the x is to the left.
	 * @param inverseY if the y is to the left.
	 * @param upgrades amount of upgrades the bullet has.
	 */
	public void createNewBullet(int bulletType, double x, double y, double slope, boolean inverseX, boolean inverseY, int upgrades) {
		Bullets bullets = new Bullets(bulletType, x + 10, y + 10, slope, inverseX, inverseY, upgrades);
		cloneBulletScreen.add(bullets);
	}

	/**
	 * creates a new bullet to be added to the side-side array list of bullets.
	 * @param bulletType type of bullet.
	 * @param x x-coordinate of bullet
	 * @param y y-coordinate of bullet
	 * @param slope slope of the bullet.
	 * @param inverseX if the x is to the left.
	 * @param inverseY if the y is to the left.
	 * @param upgrades amount of upgrades the bullet has.
	 */
	public void createSideBullet(int bulletType, double x, double y, double slope, boolean inverseX, boolean inverseY, int upgrades) {
		Bullets bullets = new Bullets(bulletType, x + 10, y + 10, slope, inverseX, inverseY, upgrades);
		sideCloneBulletScreen.add(bullets);
	}

	/**
	 * copies all bullets from the clone, to the main-main arraylist.
	 * @param arr array of bullets to be copied over to the bulletScreen array list.
	 */
	public void copyCloneToMain(ArrayList<Bullets> arr) {
		for (Bullets bullet : arr) {
			bulletScreen.add(bullet);
		}
		cloneBulletScreen.clear();
	}

	/**
	 * copies all bullets from the clone, to the side-main arraylist.
	 * @param arr array of bullets to be copied over to the bulletScreen array list.
	 */
	public void copyCloneToSide(ArrayList<Bullets> arr) {
		for(Bullets bullet : arr) {
			sideBulletScreen.add(bullet);
		}
		sideCloneBulletScreen.clear();
	} 

	/**
	 * updates the image of the player to its dead counterpart.
	 * @param playerID determines what image and which player to update.
	 */
	public void updateSideDead(int playerID) {
		if(playerID == 1) {
			player2.updateImage(player2ImageDead);
		}
		else {
			player1.updateImage(player1ImageDead);
		}
	}











	// Enemy methods
	/**
	 * generates new singleplayer waves to the game.
	 * @param waveNo the number of waves accomplished.
	 */
	public void newWave(double waveNo) {
		Random random = new Random();
		// determines amount of enemies to generate.
		int numberOfEnemiesBig = 0;
		int numberOfEnemiesFast = 0;
		int numberOfEnemiesNormal = random.nextInt(11) + (int) (5 * waveNo);
		if(waveNo >= 5) {
			numberOfEnemiesBig = (int) waveNo - 4;
		}
		if(waveNo >= 3) {
			numberOfEnemiesFast = (int) waveNo * 2 - 5;
		}
		
		// scaling affects the health of each zombie (+0.1x multiplier to HP per wave)
		double scaling =  1 + (waveNo / 10);
		double x, y;
		// Generates and adds the zombies to the array list of enemies, and randomzies their x, y spawn points.
		for(int i = 0; i < numberOfEnemiesNormal; i++) {
			int leftOrRight = random.nextInt(2);
			y = -100 + random.nextInt(800);
			if(leftOrRight == 1) {
				x = -10 - random.nextInt(500);
			}
			else {
				x = 810 + random.nextInt(500);
			}
			enemies.add(new NormalZombie(scaling, x, y)); //add enemyPosX and enemyPosY
		}
		for(int i = 0; i < numberOfEnemiesFast; i++) {
			int leftOrRight = random.nextInt(2);
			y = -100 + random.nextInt(800);
			if(leftOrRight == 1) {
				x = -10 - random.nextInt(800);
			}
			else {
				x = 810 + random.nextInt(800);
			}
			enemies.add(new FastZombie(scaling, x, y)); //add enemyPosX and enemyPosY
		}
		for(int i = 0; i < numberOfEnemiesBig; i++) {
			int leftOrRight = random.nextInt(2);
			y = -100 + random.nextInt(800);
			if(leftOrRight == 1) {
				x = -30 - random.nextInt(100);
			}
			else {
				x = 830 + random.nextInt(100);
			}
			enemies.add(new BigZombie(scaling, x, y)); //add enemyPosX and enemyPosY
		}
	}

	// Intervals of the zombie waves.
	public static final int ENEMY_X_INTERVAL = 300;
	public static final int ENEMY_Y_INTERVAL = 100;

	// occupies the canvas with rows of zombies
	/**
	 * generates new multiplayer waves to the game.
	 * @param waveNo the number of waves accomplished.
	 */
	public void newMultiplayerWave(int waveNo) {
		int numberOfEnemiesNormal = 10 + 5 * waveNo;
		int numberOfEnemiesBig = 0;
		int numberOfEnemiesFast = 0;
		if(waveNo >= 5) {
			numberOfEnemiesBig = (int) waveNo * 2 - 4;
		}
		if(waveNo >= 3) {
			numberOfEnemiesFast = (int) waveNo * 4 - 5;
		}
		double scaling = 1 + waveNo / 10;
		double xRight = -30, xLeft = 830, y = -30;   

		for(int i = 0; i < numberOfEnemiesBig; i++) {
			y += ENEMY_Y_INTERVAL;
			if(i % 2 == 0) {
				if(y >= 630) {
					xRight -= ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new BigZombie(scaling, xRight, y)); //same here but the xright stuff add to server I think
			}
			else {
				if(y >= 630) {
					xLeft += ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new BigZombie(scaling, xLeft, y)); // same
			}
		}

		for(int i = 0; i < numberOfEnemiesNormal; i++) {
			y += ENEMY_Y_INTERVAL;
			if(i % 2 == 0) {
				if(y >= 630) {
					xRight -= ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new NormalZombie(scaling, xRight, y)); //same here but the xright stuff add to server I think
			}
			else {
				if(y >= 630) {
					xLeft += ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new NormalZombie(scaling, xLeft, y)); // same
			}
		}

		for(int i = 0; i < numberOfEnemiesFast; i++) {
			y += ENEMY_Y_INTERVAL;
			if(i % 2 == 0) {
				if(y >= 630) {
					xRight -= ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new FastZombie(scaling, xRight, y)); //same here but the xright stuff add to server I think
			}
			else {
				if(y >= 630) {
					xLeft += ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new FastZombie(scaling, xLeft, y)); // same
			}
		}
	}	

	/**
	 * 
	 * @return amount of enemies in the array list of enemies.
	 */
	public int checkAmountEnemies() {
		return enemies.size();
	}

	/**
	 * Updates the trajectory of the enemies, to ensure that it follows the players around.
	 * @param singlePlayer if it is singleplayer gamemode
	 * @param sideDead if the side player is dead
	 * @param playerID the ID of the player, to know which of the players is the side player relative to it's ID.
	 */
	public void updateEnemySpeeds(boolean singlePlayer, boolean sideDead, int playerID) {
		if(singlePlayer) {
			for(Enemy enemy : enemies) {
				enemy.getPlayerCoordinates(player1.getX(), player1.getY(), 10000, 10000);
			}
		}
		else {
			if(playerID == 1 && sideDead) {
				for(Enemy enemy : enemies) {
					enemy.getPlayerCoordinates(player1.getX(), player1.getY(), 10000,10000);
				}
			}
			else if(playerID == 2 && sideDead) {
				for(Enemy enemy : enemies) {
					enemy.getPlayerCoordinates(10000,10000, player2.getX(),player2.getY());
				}
			}
			else {
				for(Enemy enemy : enemies) {
					enemy.getPlayerCoordinates(player1.getX(), player1.getY(), player2.getX(), player2.getY());
				}
			}
		}
	}

	/**
	 * checks the enemy that was damaged, and deducts the health of the enemies based on the damage of the bullet it got hit by.
	 */
	public void checkEnemyDamaged() {
		enemyIndex = 0;
		for(Enemy enemy : enemies) {
			
			double enemyX = enemy.getStartPosX();
			double enemyY = enemy.getStartPosY();
			int enemyWidth = enemy.getWidth();
			int enemyHeight = enemy.getHeight();

			for(Bullets bullet : bulletScreen) {
				double bulletX = bullet.getX();
				double bulletY = bullet.getY();
				int bulletWidth = bullet.getWidth();
				int bulletHeight = bullet.getHeight();
				boolean horizontalCollision = enemyX < bulletX + bulletWidth && enemyX + enemyWidth > bulletX;
				boolean verticalCollision = enemyY < bulletY + bulletHeight && enemyY + enemyHeight > bulletY;

				if(horizontalCollision && verticalCollision) {
					enemy.takeDamage(bullet.getBulletDamage());
					bulletScreen.remove(bullet);
					enemyHit = true;
					enemyHitIndex = enemyIndex;
					break;
				}
			}
			enemyIndex++;
		}
	}

	/**
	 * checks if the side bullet collided with an enemy, deletes the bullet if true.
	 */
	public void sideCheckEnemyDamaged() {
		for(Enemy enemy : enemies) {
			
			double enemyX = enemy.getStartPosX();
			double enemyY = enemy.getStartPosY();
			int enemyWidth = enemy.getWidth();
			int enemyHeight = enemy.getHeight();

			for(Bullets bullet : sideBulletScreen) {
				double bulletX = bullet.getX();
				double bulletY = bullet.getY();
				int bulletWidth = bullet.getWidth();
				int bulletHeight = bullet.getHeight();
				boolean horizontalCollision = enemyX < bulletX + bulletWidth && enemyX + enemyWidth > bulletX;
				boolean verticalCollision = enemyY < bulletY + bulletHeight && enemyY + enemyHeight > bulletY;

				if(horizontalCollision && verticalCollision) {
					sideBulletScreen.remove(bullet);
					break;
				}
			}
		}
	}

	/**
	 * subtracts the health of the enemy at index.
	 * @param index index of the enemy that got hit.
	 * @param bullet bullet that hit the enemy.
	 */
	public void subtractHealthFromIndex(int index, Bullets bullet) {
		enemies.get(index).takeDamage(bullet.getBulletDamage()); 
	}

	/**
	 * 
	 * @return the index of which enemy got hit
	 */
	public int getEnemyHitIndex() {
		return enemyHitIndex;
	}

	/**
	 * 
	 * @return if the player hit an enemy.
	 */
	public boolean hitAnEnemy() {
		return enemyHit;
	}

	/**
	 * sets the enemyHit variable of GameCanvas to false.
	 */
	public void setEnemyHitFalse() {
		enemyHit = false;
	}
	
	/* Makes an arraylist of times when it was last called with the unqiue index
	 * if the time surpasses a certain threshold, set that specific time index to a new current time.
	 */
	ArrayList<Long> timesAnimation = new ArrayList<>();

	/**
	 * updates the enemies animations.
	 * @param enemy the enemy to updates its animation.
	 * @param index the index of the enemy animation update.
	 */
	public void updateEnemyAnimation(Enemy enemy, int index) {
		if(timesAnimation.size() <= index) {
			timesAnimation.add(System.currentTimeMillis());
		}
		long time = System.currentTimeMillis() - timesAnimation.get(index);
		if(enemy.getEnemyName().equals("Normal Zombie")) {
			if(time >= 400 && time < 800) {
				enemy.updateImage(zombieWalk1);
			}
			else if(time >= 800 && time < 1200) {
				enemy.updateImage(zombieWalk2);
			}
			else if(time >= 1200 && time < 1600) {
				enemy.updateImage(zombieWalk3);
			}
			else if(time >= 1600){
				enemy.updateImage(zombieWalk4);
				timesAnimation.set(index, System.currentTimeMillis());
			}
		}
		else if(enemy.getEnemyName().equals("Big Zombie")) {
			if(time >= 400 && time < 800) {
				enemy.updateImage(bigZombieWalk1);
			}
			else if(time >= 800 && time < 1200) {
				enemy.updateImage(bigZombieWalk2);
			}
			else if(time >= 1200 && time < 1600) {
				enemy.updateImage(bigZombieWalk3);
			}
			else if(time >= 1600){
				enemy.updateImage(bigZombieWalk4);
				timesAnimation.set(index, System.currentTimeMillis());
			}
		}
		else if(enemy.getEnemyName().equals("Fast Zombie")) {
			if(time >= 200 && time < 400) {
				enemy.updateImage(fastZombieWalk1);
			}
			else if(time >= 400 && time < 600) {
				enemy.updateImage(fastZombieWalk2);
			}
			else if(time >= 600 && time < 800) {
				enemy.updateImage(fastZombieWalk1);
			}
			else if(time >= 800){
				enemy.updateImage(fastZombieWalk3);
				timesAnimation.set(index, System.currentTimeMillis());
			}
		}
	}

	/**
	 * updates the image of the zombie to an attacking image.
	 * @param enemy the enemy that attacked the player.
	 */
	public void enemyAttackAnimation(Enemy enemy) {
		if(enemy.getEnemyName().equals("Normal Zombie")) {
			enemy.updateImage(zombieAttack1);
		}
		else if(enemy.getEnemyName().equals("Big Zombie")) {
			enemy.updateImage(bigZombieAttack1);
		}
		else if(enemy.getEnemyName().equals("Fast Zombie")) {
			enemy.updateImage(fastZombieAttack1);
		}
	}
}
