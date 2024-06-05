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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

/**
 * The GameFrame is where the calculations for the server and input handling are handled.
 */
public class GameFrame extends JFrame implements ActionListener {
	public static final int CANVAS_X = 800;
	public static final int CANVAS_Y = 600;

	// declares variables to be used.
	private boolean singlePlayer;
	private JFrame frame;
	private MusicPlayer music;
	private GameCanvas canvas;
	private InputHandling ih;
	private Player playerMain, playerSide;
	private Socket socket;
	private int playerID;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;
	private long startTimeMovement, startTimeReloadGun;
	private double reloadSpeed;
	private JLabel waveLabel, coinsLabel, healthLabel, waveTimerLabel;
	private ShopMenu shop;
	private int waveNumber = 0;
	private long waveTimer;
	private boolean sideDead = false;
	private boolean startWaveTimer = true;
	private String stringWaveNumber = "", stringCoinsNumber = "", stringHealthNumber = "", stringWaveTimerNumber = "";

	/**
	 * Constructor for GameFrame, initializes variables and componnets to be used.
	 */
	public GameFrame() {
		frame = new JFrame();

		waveLabel = new JLabel();
		coinsLabel = new JLabel();
		healthLabel = new JLabel();
		waveTimerLabel = new JLabel();

		waveLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 25));
		coinsLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,15));
		healthLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		waveTimerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));

		stringWaveNumber = Integer.toString(waveNumber);
		stringCoinsNumber = Integer.toString(0);
		stringWaveTimerNumber = Integer.toString(0);

		waveLabel.setForeground(new Color(100, 13, 13));
		waveLabel.setBounds(0,0,200,25);
		coinsLabel.setForeground(new Color(238,181,1));
		coinsLabel.setBounds(650,0,150,50);
		healthLabel.setForeground(new Color(255,0,0));
		healthLabel.setBounds(0,550,100,50);
		waveTimerLabel.setForeground(new Color(255,255,255));
		waveTimerLabel.setBounds(10,20,100,30);

		frame.add(waveLabel);
		frame.add(coinsLabel);
		frame.add(healthLabel);
		frame.add(waveTimerLabel);

		canvas = new GameCanvas(frame);
		
		createPlayers();
        ih = new InputHandling();
		shop = new ShopMenu(frame, playerMain);

		frame.add(canvas);

		stringHealthNumber = Integer.toString((int)playerMain.getHealth());
	
        frame.setFocusable(true);
        frame.requestFocus();
        frame.requestFocusInWindow();
        frame.addKeyListener(ih);
		frame.addMouseListener(ih);
	}

	/**
	 * Sets the games settings(i.e: playerID and player sprite) to singleplayer.
	 */
	public void singlePlayerSettings() {
		playerID = 1;
		playerMain = canvas.getPlayer1();
		playerSide = null;
	}

	/**
	 * Sets up the GUI to be used by the GameFrame, this is where the players are set as well
	 * as where the timer starts.
	 */
    public void setUpGUI() {
        frame.pack();

		if(singlePlayer) {
			singlePlayerSettings();
			shop.changePlayer(playerMain);
		}
		else {
			createPlayers();
			shop.changePlayer(playerMain);
		}

		music = new MusicPlayer();
        // Silver Surfer - Level 1 - Nes Music (https://www.youtube.com/watch?v=-J0H5ah1G7A)
        music.playMusic("WaveMusic.wav", true, false);
		
		Timer timer = new Timer(10, this);
		timer.start();

        frame.setTitle("Finals Game " + playerID);
		frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

	/**
	 * Creates players based on the playerID of the GameFrame.
	 */
	public void createPlayers() {
		if(playerID == 1) {
			playerSide = canvas.getPlayer2();
			playerMain = canvas.getPlayer1();
		}
		else {
			playerSide = canvas.getPlayer1();
			playerMain = canvas.getPlayer2();
		}
	}
	










	// Key Variables
	private boolean pressedW = false;
	private boolean pressedA = false;
	private boolean pressedS = false;
	private boolean pressedD = false;
	private boolean pressedQ = false;

	// Player Variables
	private double playerMainX = 0;
	private double playerMainY = 0;
	private int playerMainWidth = 0;
	private int playerMainHeight = 0;
	private int rotation = 0;
	private int pixelsWalked = 0;
	private int speed = 0;
	private boolean idle = true;
	private boolean resolveEnemySpeeds = true;

	// Mouse Variables
	private double mouseX = 0;
	private double mouseY = 0;
	private double slope = 0;
	private Float magnitude = (float) 0;
	private boolean inverseX, inverseY;
	private boolean mouseClicks = false;
	private int mouseClicking = 0;
	
	/**
	 * Inner class where all inputs are handles, MouseListener and KeyListener.
	 */
	public class InputHandling implements KeyListener, MouseListener {

		// Keyboard Methods
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			/*
			 * Goes through a switch case statement, to determine what key was pressed.
			 */
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					pressedW = true;
					break;
				case KeyEvent.VK_S:
					pressedS = true;
					break;
				case KeyEvent.VK_A:
					pressedA = true;
					break;
				case KeyEvent.VK_D:
					pressedD = true;
					break;
				case KeyEvent.VK_SHIFT:
					if(playerMain.hasSpeedPerk()) {
						playerMain.setMoveSpeed(playerMain.getOriginalSpeed() * 4);
					}
					else {
						playerMain.setMoveSpeed(playerMain.getOriginalSpeed() * 2);
					}
					break;
				case KeyEvent.VK_Q:
					if(!pressedQ) pressedQ = true;
					else pressedQ = false;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			/*
			 * Goes through a switch case statement, to determine what key was released.
			 */
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					pressedW = false;
					break;
				case KeyEvent.VK_S:
					pressedS = false;
					break;
				case KeyEvent.VK_A:
					pressedA = false;
					break;
				case KeyEvent.VK_D:
					pressedD = false;
					break;
				case KeyEvent.VK_SHIFT:
					if(playerMain.hasSpeedPerk()) {
						playerMain.setMoveSpeed(playerMain.getOriginalSpeed() * 2);
					}
					else {
						playerMain.setMoveSpeed(playerMain.getOriginalSpeed());
					}
					break;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// get 2 (x,y) points in the frame
			if(playerMain.isAlive()) {
				if(playerMain.hasReloadPerk()) {
					reloadSpeed = playerMain.determineWeaponReload() - (playerMain.determineWeaponReload() * 0.5);
				}
				else {
					reloadSpeed = playerMain.determineWeaponReload();
				}
				playerMainX = playerMain.getX();
				playerMainY = playerMain.getY();
				
				mouseX = e.getX() - 20;
				mouseY = e.getY() - 20;
	
				// get its slope to get the ratio between x and y
				slope = (double) (mouseY - playerMainY) / (double) (mouseX - playerMainX);
				magnitude = (float) Math.sqrt(1 + slope * slope);
				/* makes sure that if the coordinates of the mouse is behind the player1, then we multiply the slope by -1 and turn x to its inverse.
				   since if we dont, it will have the same slope for some value that is in front of the player1 resulting in no bullets appearing behind, but rather in front.
				*/
				if(mouseX < playerMainX) { //QUADRANTS
					slope *= -1;
					inverseX = true;
				}
				else {
					inverseX = false;
				}
				if(mouseY > playerMainY) {
					inverseY = true;
				}
				else {
					inverseY = false;
				}
				/*
				 * if the current time when the function was last executed is greater than 
				 * the interval, in this case, reload speed, then fire another bullet
				 * this prevents spamming that can lead to an unfair advantage.
				 */
				if(System.currentTimeMillis() - startTimeReloadGun >= reloadSpeed) {
					startTimeReloadGun = System.currentTimeMillis();
					canvas.createNewBullet(playerMain.getGunType(), playerMainX, playerMainY, slope, inverseX, inverseY, playerMain.getBullet().amountUpgrades());
					mouseClicks = true;

					Random rand = new Random();
					String bulletName = playerMain.getBullet().getBulletName();
					// plays sounds based on what weapon the player is holding
					if(bulletName.equals("bowBullet")) {
						music.playMusic("Bow_Fire.wav", false, false);
					} else if(bulletName.equals("slingBullet")) {
						music.playMusic("Slingshot_Fire.wav", false, false);
					} else {
						int bulletSound = rand.nextInt(2);
						switch(bulletSound) {
							case 0:
								music.playMusic("Pistol_Shot.wav", false, true);
								break;
							case 1:
								music.playMusic("Pistol_Shot2.wav", false, true);
								break;
						}
					}
				}
				mouseClicking++;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(playerMain.isAlive()) {
			playerMainWidth = playerMain.getWidth();
			playerMainHeight = playerMain.getHeight();
			playerMainX = playerMain.getX();
			playerMainY = playerMain.getY();
			
			if(playerMainX + playerMainWidth >= CANVAS_X) {
				pressedD = false;
			}
			else if(playerMainX <= 0) {
				pressedA = false;
			}
			if(playerMainY + playerMainHeight >= CANVAS_Y) {
				pressedS = false;
			}
			else if(playerMainY <= 0) {
				pressedW = false;
			}

			speed = (int) playerMain.getMoveSpeed();
			
			if(pressedW) {
				playerMain.moveY(true);
				pixelsWalked += speed;
			}
			if(pressedS) {
				playerMain.moveY(false);
				pixelsWalked += speed;
			}
			if(pressedA) {
				playerMain.moveX(true);
				pixelsWalked += speed;
			}
			if(pressedD) {
				playerMain.moveX(false);
				pixelsWalked += speed;
			}

			/* Calculates if the elapsed time from when we started the timer is >= 800 miliseconds.
			(Gives us enough time for animations to not flicker when firing and moving)
			(FROM: https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java) */
			if(System.currentTimeMillis() - startTimeMovement >= 800) { 
				if(pressedW && pressedA) rotation = -45;
				else if(pressedW && pressedD) rotation = 45;
				else if(pressedS && pressedA) rotation = -135;
				else if(pressedS && pressedD) rotation = 135;
				else if(pressedW) rotation = 0;
				else if(pressedS) rotation = 180;
				else if(pressedA) rotation = -90;
				else if(pressedD) rotation = 90;
			}

			/*
			 * rotates the player based on where they click
			 */
			if(mouseClicking > 0) {
				startTimeMovement = System.currentTimeMillis(); // gets system time
				int degrees = (int) Math.toDegrees(Math.acos(1/magnitude));
				if(!inverseX && !inverseY) rotation = 90 - degrees; //Q1
				else if(inverseX && !inverseY) rotation = degrees - 90; // Q2
				else if(inverseX && inverseY) rotation = 270 - degrees; //Q3
				else if(!inverseX && inverseY) rotation = degrees - 270; //Q4
				mouseClicking--;
			}
			
			playerMain.setRotation(rotation);

			// walking and idle animations
			canvas.updatePlayerAnimation(pixelsWalked, playerID, idle);
			if(pixelsWalked > 40) { // resets pixels walked (1 cycle = 40)
				pixelsWalked = 0;
			}
			if(!pressedA && !pressedD && !pressedS && !pressedW) {
				idle = true;
			}
			else {
				idle = false;
			}
			
			if(singlePlayer) {
				canvas.checkPlayerEnemyCollision(playerMain);
			}
			else {
				if(!sideDead) {
					canvas.checkPlayerEnemyCollision(playerSide);
				}
				else if(sideDead && resolveEnemySpeeds) {
					canvas.resolveEnemySpeeds();
					resolveEnemySpeeds = false;
				}
				canvas.checkPlayerEnemyCollision(playerMain);
			}

			if(playerMain.getHealth() <= 0) {
				playerMain.died();
			}
			
			// ENEMY METHODS
			if(canvas.checkAmountEnemies() == 0) {
				if(startWaveTimer) {
					waveTimer = System.currentTimeMillis();
					startWaveTimer = false;
				}

				//only triggers when it is in the waiting phase
				if(pressedQ) {
					shop.showShopMenu();
				}
				else {
					shop.hideShopMenu();
				}

				stringWaveNumber = Integer.toString(waveNumber);
				waveLabel.setText("WAVE " + stringWaveNumber);

				long timer = System.currentTimeMillis() - waveTimer;
				long descendingWaveTimer = (10000  - timer)/ 1000; //change 10000 -> 10s
				stringWaveTimerNumber = Integer.toString((int) descendingWaveTimer);
				waveTimerLabel.setText("NEXT WAVE IN: " + stringWaveTimerNumber);

				if(timer > 10000) { // change 10000
					waveNumber++;
					stringWaveNumber = Integer.toString(waveNumber);
					waveLabel.setText("WAVE " + stringWaveNumber);
					if(singlePlayer) canvas.newWave(waveNumber);
					else canvas.newMultiplayerWave(waveNumber);
					startWaveTimer = true;
					waveTimerLabel.setText("WAVE START!");
					shop.hideShopMenu();
				}
			}
			
			stringCoinsNumber = Integer.toString(playerMain.getCoins());
			coinsLabel.setText("COINS: " + stringCoinsNumber);

			stringHealthNumber = Integer.toString((int)playerMain.getHealth());
			healthLabel.setText(stringHealthNumber);

			canvas.checkEnemyDamaged();
			canvas.sideCheckEnemyDamaged();
			canvas.updateEnemySpeeds(singlePlayer, sideDead, playerID);
		}
		canvas.repaint();
	}







	// SERVER SIDE
	private int firstIteration = 0;
	private Scanner scanner;
	
	public void connectToServer() {
		try {
			scanner = new Scanner(System.in);
			System.out.print("IP Address: ");
			String host = scanner.nextLine();
			System.out.print("Port #: ");
			int port = scanner.nextInt();

			socket = new Socket(host, port);
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			playerID = in.readInt();
			System.out.println("You are player " + playerID);

			if(playerID == 1) {
				System.out.println("Waiting for player 2 to connect...");
			}

			rfsRunnable = new ReadFromServer(in);
			wtsRunnable = new WriteToServer(out);
			rfsRunnable.waitPlayer();
		} catch (IOException ex) {
			System.out.println("Singleplayer Mode");
			singlePlayer = true;
			canvas.updateSinglePlayerStatus(true); // PLACEHOLDER
		}
	}
	
	private class ReadFromServer implements Runnable {

		private DataInputStream dataIn;
		private int otherPlayerID;
		public ReadFromServer(DataInputStream in) {
			dataIn = in;
			System.out.println("RFS Runnable created");
		}
		@Override
		public void run() {
			try {
				while(true) {
					double playerSideX = dataIn.readDouble();
					double playerSideY = dataIn.readDouble();
					boolean mouseClicked = dataIn.readBoolean();
					double slope = dataIn.readDouble();
					boolean inverseEkis = dataIn.readBoolean();
					boolean inverseYkis = dataIn.readBoolean();
					int rotation = dataIn.readInt();
					int otherPixelsWalked = dataIn.readInt();
					boolean otherIdle = dataIn.readBoolean();
					double sidePlayerHealth = dataIn.readDouble();
					int sidePlayerGunType = dataIn.readInt();
					int sideUpgradedGunAmount = dataIn.readInt();

					boolean otherHitAnEnemy = dataIn.readBoolean();
					int otherHitIndex = dataIn.readInt();

					// Checks to see the other players ID
					if(playerID == 1) {
						otherPlayerID = 2;
					}
					else {
						otherPlayerID = 1;
					}

					/* 
					* firstIteration was put here to ensure that when the server starts, it is consistent.
					* Not putting firstIteration would send 0 since other player has not been intialized.
					*/
					if(sidePlayerHealth <= 0 && firstIteration >= 300) {
						canvas.updateSideDead(playerID);
						sideDead = true;
					}
					
					// sends a boolean to let the other player know that it hit a zombie at n-index of the enemies array list.
					if(otherHitAnEnemy) {
						canvas.subtractHealthFromIndex(otherHitIndex, new Bullets(sidePlayerGunType, 0, 0, 0, false, false, sideUpgradedGunAmount));
					}

					// sets all the variables of sidePlayer needed to reflect the other player.
					if(playerSide != null) {
						if(!sideDead) {
							playerSide.setPlayerX(playerSideX);
							playerSide.setPlayerY(playerSideY);
							playerSide.setRotation(rotation);
							canvas.updatePlayerAnimation(otherPixelsWalked, otherPlayerID, otherIdle);
						}
						canvas.updateEnemySpeeds(singlePlayer, sideDead, playerID);

						if(mouseClicked) {
							canvas.createSideBullet(sidePlayerGunType, playerSideX, playerSideY, slope, inverseEkis, inverseYkis, sideUpgradedGunAmount);
							mouseClicked = false;

							Random rand = new Random();
							String bulletName = playerSide.getBullet().determineName(sidePlayerGunType);

							if(bulletName.equals("bowBullet")) {
								music.playMusic("Bow_Fire.wav", false, false);
							} else if(bulletName.equals("slingBullet")) {
								music.playMusic("Slingshot_Fire.wav", false, false);
							} else {
								int bulletSound = rand.nextInt(2);
								switch(bulletSound) {
									case 0:
										music.playMusic("Pistol_Shot.wav", false, true);
										break;
									case 1:
										music.playMusic("Pistol_Shot2.wav", false, true);
										break;
								}
							}
						}
					}
					firstIteration++;
				}
			} catch(IOException ex) {
				System.out.println("Error: IOException GameFrame.ReadFromServer.run()");
			}
		}

		public void waitPlayer() {
			try {
				String msg = dataIn.readUTF();
				System.out.println(msg);

				Thread readThread = new Thread(rfsRunnable);
				Thread writeThread = new Thread(wtsRunnable);

				readThread.start();
				writeThread.start();
			} catch(IOException ex) {
				System.out.println("Error: IOException GameFrame.ReadFromServer.waitPlayer()");
			}
		}
	}

	private class WriteToServer implements Runnable{

		private DataOutputStream dataOut;
		
		public WriteToServer(DataOutputStream out) {
			dataOut = out;
			System.out.println("WTS Runnable created");
		}

		@Override
		public void run() {
			try {
				while(true) {
					if(playerMain != null) {
						dataOut.writeDouble(playerMain.getX());
						dataOut.writeDouble(playerMain.getY());
						dataOut.writeBoolean(mouseClicks);
						dataOut.writeDouble(slope);
						dataOut.writeBoolean(inverseX);
						dataOut.writeBoolean(inverseY);
						dataOut.writeInt(rotation);
						dataOut.writeInt(pixelsWalked);
						dataOut.writeBoolean(idle);
						dataOut.writeDouble(playerMain.getHealth());
						dataOut.writeInt(playerMain.getGunType());
						dataOut.writeInt(playerMain.getBullet().amountUpgrades());

						dataOut.writeBoolean(canvas.hitAnEnemy());
						dataOut.writeInt(canvas.getEnemyHitIndex());
			
						mouseClicks = false;
						canvas.setEnemyHitFalse();
						dataOut.flush();
					}
					try {
						Thread.sleep(20);
					} catch(InterruptedException ex) {
						System.out.println("Error: InterruptedException GameFrame.WriteToServer.Thread.sleep()");
					}
				}
			} catch (IOException ex) {
				System.out.println("Error: IOException GameFrame.WriteToServer().run()");
			}		
		}
	}
}
