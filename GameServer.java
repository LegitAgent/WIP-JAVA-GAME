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

import java.io.*;
import java.net.*;
public class GameServer {
	private ServerSocket ss;
	private int numPlayers;
	private int maxPlayers;

	private Socket p1Socket, p2Socket;
	private ReadFromClient p1ReadFromClient, p2ReadFromClient;
	private WriteToClient p1WriteToClient, p2WriteToClient;

	private double p1x, p1y, p2x, p2y, slopeP1, slopeP2;
	private boolean amountP1Fire, amountP2Fire;
	private boolean inverseX1, inverseX2, inverseY1, inverseY2;
	private int rotationP1, rotationP2;
	private int pixelsWalkedP1, pixelsWalkedP2;
	private boolean idleP1, idleP2;
	private boolean killedAnEnemyP1, killedAnEnemyP2;
	private int killedEnemyIndexP1, killedEnemyIndexP2;
	private double player1Died, player2Died;
	private int gunTypeP1, gunTypeP2;
	private int upgradesGunP1, upgradesGunP2;

	public GameServer() {
		System.out.println("GAME SERVER");
		numPlayers = 0;
		maxPlayers = 2;

		p1x = 400;
		p1y = 300;
		p2x = 700;
		p2y = 300;

		try {
			ss = new ServerSocket(45371);
		} catch(IOException ex) {
			System.out.println("Error: IOException GameServer");
		}
	}

	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections");

			while(numPlayers < maxPlayers) {
				Socket socket = ss.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());

				numPlayers++;
				out.writeInt(numPlayers);
				System.out.println("Player " + numPlayers + " has connected");
				System.out.println("Current Player count: " + numPlayers);

				ReadFromClient rfc = new ReadFromClient(numPlayers, in);
				WriteToClient wtc = new WriteToClient(numPlayers, out);

				if(numPlayers == 1) {
					p1Socket = socket;
					p1ReadFromClient = rfc;
					p1WriteToClient = wtc;
				}
				else {
					p2Socket = socket;
					p2ReadFromClient = rfc;
					p2WriteToClient = wtc;
					p1WriteToClient.sendMsg();
					p2WriteToClient.sendMsg();

					Thread readThreadp1 = new Thread(p1ReadFromClient);
					Thread readThreadp2 = new Thread(p2ReadFromClient);
					Thread writeThreadp1 = new Thread(p1WriteToClient);
					Thread writeThreadp2 = new Thread(p2WriteToClient);
					readThreadp1.start();
					readThreadp2.start();
					writeThreadp1.start();
					writeThreadp2.start();
				}
			}

			System.out.println("The game will start shortly.");
		} catch(IOException ex) {
			System.out.println("Error: IOException GameServer.acceptConnections()");
		}
	}

	private class ReadFromClient implements Runnable { 
		private int playerID;
		private DataInputStream dataIn;
		public ReadFromClient(int playerID, DataInputStream in) {
			this.playerID = playerID;
			dataIn = in;
			System.out.println("RFC" + playerID + " Runnable created");
		}
		@Override
		public void run() {
			try {
				while(true) {
					if(playerID == 1) {
						p1x = dataIn.readDouble();
						p1y = dataIn.readDouble();
						amountP1Fire = dataIn.readBoolean();
						slopeP1 = dataIn.readDouble();
						inverseX1 = dataIn.readBoolean();
						inverseY1 = dataIn.readBoolean();
						rotationP1 = dataIn.readInt();
						pixelsWalkedP1 = dataIn.readInt();
						idleP1 = dataIn.readBoolean();
						player1Died = dataIn.readDouble();
						gunTypeP1 = dataIn.readInt();
						upgradesGunP1 = dataIn.readInt();

						killedAnEnemyP1 = dataIn.readBoolean();
						killedEnemyIndexP1 = dataIn.readInt();
					}
					else {
						p2x = dataIn.readDouble();
						p2y = dataIn.readDouble();
						amountP2Fire = dataIn.readBoolean();
						slopeP2 = dataIn.readDouble();
						inverseX2 = dataIn.readBoolean();
						inverseY2 = dataIn.readBoolean();
						rotationP2 = dataIn.readInt();
						pixelsWalkedP2 = dataIn.readInt();
						idleP2 = dataIn.readBoolean();
						player2Died = dataIn.readDouble();
						gunTypeP2 = dataIn.readInt();
						upgradesGunP2 = dataIn.readInt();

						killedAnEnemyP2 = dataIn.readBoolean();
						killedEnemyIndexP2 = dataIn.readInt();
					}
				}
			} catch(IOException ex) {
				System.out.println("Error: IOException GameServer.ReadFromClient.run()");
			}
		}
	}

	private class WriteToClient implements Runnable{
		private int playerID;
		private DataOutputStream dataOut;
		public WriteToClient(int playerID, DataOutputStream out) {
			this.playerID = playerID;
			dataOut = out;
			System.out.println("WTC" + playerID + " Runnable created");
		}
		@Override
		public void run() {
			try {
				while(true) {
					if(playerID == 1) {
						dataOut.writeDouble(p2x);
						dataOut.writeDouble(p2y);
						dataOut.writeBoolean(amountP2Fire);
						dataOut.writeDouble(slopeP2);
						dataOut.writeBoolean(inverseX2);
						dataOut.writeBoolean(inverseY2);
						dataOut.writeInt(rotationP2);
						dataOut.writeInt(pixelsWalkedP2);
						dataOut.writeBoolean(idleP2);
						dataOut.writeDouble(player2Died);
						dataOut.writeInt(gunTypeP2);
						dataOut.writeInt(upgradesGunP2);

						dataOut.writeBoolean(killedAnEnemyP2);
						dataOut.writeInt(killedEnemyIndexP2);
					}
					else {
						dataOut.writeDouble(p1x);
						dataOut.writeDouble(p1y);
						dataOut.writeBoolean(amountP1Fire);
						dataOut.writeDouble(slopeP1);
						dataOut.writeBoolean(inverseX1);
						dataOut.writeBoolean(inverseY1);
						dataOut.writeInt(rotationP1);
						dataOut.writeInt(pixelsWalkedP1);
						dataOut.writeBoolean(idleP1);
						dataOut.writeDouble(player1Died);
						dataOut.writeInt(gunTypeP1);
						dataOut.writeInt(upgradesGunP1);
						
						dataOut.writeBoolean(killedAnEnemyP1);
						dataOut.writeInt(killedEnemyIndexP1);
					}

					dataOut.flush();
					try {
						Thread.sleep(20);
					} catch(InterruptedException ex) {
						System.out.println("Error: GameServer.WriteToClient.Thread.sleep()");
					}
					/**
					data.Out.writeInt(enemyPosX);
					data.Out.writeInt(enemyPosY);
					data.Out.writeInt(enemyHP);
					**/
				}
			} catch(IOException ex) {
				System.out.println("Error: GameServer.WriteToClient.run()");
			}
		}

		public void sendMsg() {
			try {
				dataOut.writeUTF("Sufficient Player Count");
			} catch(IOException ex) {
				System.out.println("Error: IOException GameServer.WriteToClient.sendMsg()");
			}
		}
	}

	public static void main(String[] args) {
		GameServer gameServer = new GameServer();
		gameServer.acceptConnections();
	}
}
