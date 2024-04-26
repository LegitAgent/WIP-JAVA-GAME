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

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class GameFrame extends JFrame implements ActionListener {

	public static final int CANVAS_X = 800;
	public static final int CANVAS_Y = 600;

	private boolean singlePlayer;
	private JFrame frame;
	private GameCanvas canvas;
	private InputHandling ih;
	private Player playerMain, playerSide;
	private Socket socket;
	private int playerID;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;
	private long startTimeMovement, startTimeReloadGun;
	private int reloadSpeed;
	private static int waveNumber = 0;

	public GameFrame() {
		frame = new JFrame();
		canvas = new GameCanvas(frame);
		frame.add(canvas);
		
		createPlayers();
        ih = new InputHandling();
	
        frame.setFocusable(true);
        frame.requestFocus();
        frame.requestFocusInWindow();
        frame.addKeyListener(ih);
		frame.addMouseListener(ih);

		Timer timer = new Timer(10, this);
		timer.start();
	}

	public void singlePlayerSettings() { // for playtesting singleplayer PLACEHOLDER (tentative feature)
		playerID = 1;
		playerMain = canvas.getPlayer1();
		playerSide = null;
	}

    public void setUpGUI() {
        frame.pack();

		if(singlePlayer) {
			singlePlayerSettings();
		}
		else {
			createPlayers();
		}

        frame.setTitle("Finals Game " + playerID);
		frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

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
	boolean pressedW = false;
	boolean pressedA = false;
	boolean pressedS = false;
	boolean pressedD = false;

	// Player Variables
	double playerMainX = 0;
	double playerMainY = 0;
	int playerMainWidth = 0;
	int playerMainHeight = 0;
	int rotation = 0;
	int pixelsWalked = 0;
	int speed = 0;
	boolean idle = false;

	// Mouse Variables
	double mouseX = 0;
	double mouseY = 0;
	double slope = 0;
	Float magnitude = (float) 0;
	boolean inverseX, inverseY;
	boolean mouseClicks = false;
	int mouseClicking = 0;
	
	public class InputHandling implements KeyListener, MouseListener {

		// Keyboard Methods
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
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
					playerMain.setMoveSpeed(2);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
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
					playerMain.setMoveSpeed(1);
					break;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// get 2 (x,y) points in the frame
			reloadSpeed = playerMain.determineWeaponReload();
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
			if(mouseX < playerMainX) {
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
			if(System.currentTimeMillis() - startTimeReloadGun >= reloadSpeed) {
				startTimeReloadGun = System.currentTimeMillis();
				canvas.createNewBullet(1, playerMainX, playerMainY, slope, inverseX, inverseY);
				mouseClicks = true;
			}
			mouseClicking++;
			
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
		if(pixelsWalked > 40) { // resets pixels walked (1 cycle = 50)
			pixelsWalked = 0;
		}
		if(!pressedA && !pressedD && !pressedS && !pressedW) {
			idle = true;
		}
		else {
			idle = false;
		}
		
	
		// ENEMY METHODS

		if(singlePlayer && canvas.checkAmountEnemies() == 0) {
			waveNumber++;
			canvas.newWave(waveNumber);
		}

		if(!singlePlayer && canvas.checkAmountEnemies() == 0) {
			waveNumber++;
			canvas.newMultiplayerWave(waveNumber);
		}

		canvas.updateEnemySpeeds(singlePlayer);
		canvas.repaint();
	}

	//SERVER SIDE
	public void connectToServer() {
		try {
			socket = new Socket("localhost", 45371);
			
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
			System.out.println("Error: IOException GameFrame.connectToServer()");
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

					if(playerID == 1) {
						otherPlayerID = 2;
					}
					else {
						otherPlayerID = 1;
					}
					if(playerSide != null) {
						playerSide.setPlayerX(playerSideX);
						playerSide.setPlayerY(playerSideY);
						playerSide.setRotation(rotation);
						canvas.updatePlayerAnimation(otherPixelsWalked, otherPlayerID, otherIdle);
						if(mouseClicked) {
							canvas.createNewBullet(1, playerSideX, playerSideY, slope, inverseEkis, inverseYkis);
							mouseClicked = false;
						}
					}
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

						mouseClicks = false;
						dataOut.flush();
					}
					try {
						Thread.sleep(10);
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