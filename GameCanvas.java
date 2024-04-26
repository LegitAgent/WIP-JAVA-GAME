
/**
	This is a template for a Java file.
	
	@author Jienzel Christenzen H. Chua (231567)
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

public class GameCanvas extends JComponent {

	public static final int CANVAS_X = 800;
	public static final int CANVAS_Y = 600;

	Player player1, player2;
	Bullets startBullet = new Bullets(1, 0, 0, 0, true, true);

	boolean singlePlayer = false;
	long tempTime1 = 0, tempTime2 = 0;
	// Line2D lineH, lineV;
	ArrayList<Bullets> bulletScreen = new ArrayList<>();
	ArrayList<Bullets> cloneBulletScreen = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	public static ArrayList<Enemy> enemies = new ArrayList<>();

	//Player 1 Animation Frames
	ImageIcon player1ImageIdle1 = new ImageIcon("Player1_Idle1.png");
	ImageIcon player1ImageIdle2 = new ImageIcon("Player1_Idle2.png");
	ImageIcon player1ImageWalk1 = new ImageIcon("Player1_Walk1.png");
	ImageIcon player1ImageWalk2 = new ImageIcon("Player1_Walk2.png");
	ImageIcon player1ImageWalk3 = new ImageIcon("Player1_Walk3.png");
	ImageIcon player1ImageWalk4 = new ImageIcon("Player1_Walk4.png");

	// Player 2 Animation Frames
	ImageIcon player2ImageIdle1 = new ImageIcon("Player2_Idle1.png");
	ImageIcon player2ImageIdle2 = new ImageIcon("Player2_Idle2.png");
	ImageIcon player2ImageWalk1 = new ImageIcon("Player2_Walk1.png");
	ImageIcon player2ImageWalk2 = new ImageIcon("Player2_Walk2.png");
	ImageIcon player2ImageWalk3 = new ImageIcon("Player2_Walk3.png");
	ImageIcon player2ImageWalk4 = new ImageIcon("Player2_Walk4.png");

	public GameCanvas(JFrame f) {
		f.setPreferredSize(new Dimension(815, 640));
		player1 = new Player(400, 300, 20, 20, 1, player1ImageIdle1, startBullet);
		player2 = new Player(500, 300, 20, 20, 1, player2ImageIdle1, startBullet);
	
		/**
		 * lineH = new Line2D.Double(0,300,800,300);
		 * lineV = new Line2D.Double(400,0,400,600);
		 */
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(singlePlayer) {
			player1.draw(g2d);
		}
		else {
			player1.draw(g2d);
			player2.draw(g2d);
		}

		g2d.setColor(Color.BLACK);
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
		/**
		 * g2d.draw(lineH);
		 * g2d.draw(lineV);
		 */

		for(Enemy enemy : enemies) {
			enemy.draw(g2d);
			enemy.move();
		}
	}

	// PLACEHOLDER
	public void updateSinglePlayerStatus(boolean status) {
		singlePlayer = status;
	}
	//

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
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
				/* gets tempTime as reference for the next call and subtracts the current time,
				 * of the current call, checks to see if the differences between calls is 400ms
				 * if so, switches to the next idle frame animation.
				 * Effectively: (FRAME 1: 400 ms, FRAME 2: 400 ms)
				 * (gives time for synchronization in multiplayer)
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

	public void createNewBullet(int bulletType, double x, double y, double slope, boolean inverseX, boolean inverseY) {
		Bullets bullets = new Bullets(bulletType, x + 10, y + 10, slope, inverseX, inverseY);
		cloneBulletScreen.add(bullets);
	}

	public void copyCloneToMain(ArrayList<Bullets> arr) {
		for (Bullets bullet : arr) {
			bulletScreen.add(bullet);
		}
		cloneBulletScreen.clear();
	}

	// Enemy methods
	public void newWave(int waveNo) {
		Random random = new Random();
		int numberOfEnemies = random.nextInt(11) + 5 * waveNo;
		double scaling = 1 + waveNo / 10;
		double x, y;
		for(int i = 0; i < numberOfEnemies; i++) {
			int leftOrRight = random.nextInt(2);
			y = -100 + random.nextInt(800);
			if(leftOrRight == 1) {
				x = -10 - random.nextInt(500);
			}
			else {
				x = 810 + random.nextInt(500);
			}
			enemies.add(new NormalZombie(scaling, x, y));
		}
	}

	public static int ENEMY_X_INTERVAL = 150;
	public static int ENEMY_Y_INTERVAL = 100;

	public void newMultiplayerWave(int waveNo) {
		int numberOfEnemies = 10 + 5 * waveNo;
		double scaling = 1 + waveNo / 10;
		double xRight = -30, xLeft = 830, y = -30;   
		for(int i = 0; i < numberOfEnemies; i++) {
			y += ENEMY_Y_INTERVAL;
			if(i % 2 == 0) {
				if(y >= 630) {
					xRight -= ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new NormalZombie(scaling, xRight, y));
			}
			else {
				if(y >= 630) {
					xLeft += ENEMY_X_INTERVAL;
					y = -30;
				}
				enemies.add(new NormalZombie(scaling, xLeft, y));
			}
		}
	}	

	public int checkAmountEnemies() {
		return enemies.size();
	}

	public void updateEnemySpeeds(boolean singlePlayer) {
		if(singlePlayer) {
			for(Enemy enemy : enemies) {
				enemy.getPlayerCoordinates(player1.getX(), player1.getY(), 10000, 10000);
			}
		}
		else {
			for(Enemy enemy : enemies) {
				enemy.getPlayerCoordinates(player1.getX(), player1.getY(), player2.getX(), player2.getY());
			}
		}
	}

	public ArrayList<Enemy> returnArrEnemy() {
		return enemies;
	}

	public void replaceEnemyArray(double scaling, double x , double y) {
		enemies.add(new NormalZombie(scaling, x, y));
	}

	public void clearEnemyArray() {
		enemies.clear();
	}
}
