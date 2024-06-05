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

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
/**
* Creates the class for the player. It has all the attributes a player should hold.
**/
public class Player {

	private double x, y, moveSpeed, originalSpeed;
	private int width, height;
	private Image playerImage;
	private double degrees;
	private Bullets bulletCarry;
	private double health;
	private int coins, score;
	private boolean alive, hasHealthPerk, hasReloadPerk, hasSpeedPerk;
	private boolean hasRifle, hasBow, hasSniper, hasSling;
/**
* @constructor
* @param (x): x position of the player
* @param (y): y position of the player
* @param (width): width of the player
* @param (height): height of the player
* @param (moveSpeed): movement speed of the player
* @param (playerImage): image sprite of the player
* @param (bullet): type of bullet the player is using
**/
	public Player(double x, double y, int width, int height, double moveSpeed, ImageIcon playerImage, Bullets bullet) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.moveSpeed = moveSpeed;
		originalSpeed = moveSpeed;
		this.playerImage = playerImage.getImage();
		health = 200;
		bulletCarry = bullet;
		coins = 100;
		score = 0;
		alive = true;
	}
/**
* @public moves the player on the x-axis
* @param (neg): checks the direction of the player
**/
	public void moveX(boolean neg) {
		if (neg) {
			x -= moveSpeed;
		} else {
			x += moveSpeed;
		}
	}
/**
* @public moves the player on the y-axis
* @param (neg): checks the direction of the player
**/
	public void moveY(boolean neg) {
		if (neg) {
			y -= moveSpeed;
		} else {
			y += moveSpeed;
		}
	}
/**
* @public changes the health, reload speed, and movement speed of the player based on what upgrades they bought
* @param (perkName): name of the upgrade
**/
	public void updateStats(String perkName) {
		if(perkName.equals("Health_Perk") && !hasHealthPerk) {
			health += 200;
			hasHealthPerk = true;
		}
		if(perkName.equals("Reload_Perk") && !hasReloadPerk) {
			hasReloadPerk = true;
		}
		if(perkName.equals("Speed_Perk") && !hasSpeedPerk) {
			moveSpeed *= 2;
			hasSpeedPerk = true;
		}
	}
/**
* @public replenishes the health of the player
**/
	public void restorHealth() {
		if(hasHealthPerk) {
			health = 400;
		}
		else {
			health = 200;
		}
	}

/**
* @public changes the gun of the player
* @param (gunName): the name of the gun the player now owns
**/	
	public void updateGun(String gunName) {
		if(gunName.equals("Rifle")) {
			hasRifle = true;
			hasBow = false;
			hasSniper = false;
			hasSling = false;
		}
		if(gunName.equals("Sniper")) {
			hasRifle = false;
			hasBow = false;
			hasSniper = true;
			hasSling = false;
		}
		if(gunName.equals("Bow")) {
			hasRifle = false;
			hasBow = true;
			hasSniper = false;
			hasSling = false;
		}
		if(gunName.equals("Slingshot")) {
			hasRifle = false;
			hasBow = false;
			hasSniper = false;
			hasSling = true;
		}
	}
/**
* @public gets the score of the player
**/
	public int getScore() {
		return score;
	}
/**
* @public checks if the player has a rifle
**/
	public boolean hasRifle() {
		return hasRifle;
	}
/**
* @public checks if the player has a sniper
**/
	public boolean hasSniper() {
		return hasSniper;
	}
/**
* @public checks if the player has a bow
**/
	public boolean hasBow() {
		return hasBow;
	}
/**
* @public checks if the player has a slingshot
**/
	public boolean hasSling() {
		return hasSling;
	}
/**
* @public checks if the player has the health perk
**/
	public boolean hasHealthPerk() {
		return hasHealthPerk;
	}
/**
* @public checks if the player has the reload perk
**/
	public boolean hasReloadPerk() {
		return hasReloadPerk;
	}
/**
* @public checks if the player has the speed perk
**/
	public boolean hasSpeedPerk() {
		return hasSpeedPerk;
	}
/**
* @public changes the movement speed of the player
* @param (speed): the new movement speed of the player
**/
	public void setMoveSpeed(double speed) {
		moveSpeed = speed;
	}
/**
* @public gets the original speed of the player
**/
	public double getOriginalSpeed() {
		return originalSpeed;
	}
/**
* @public gets the current movement speed of the player
**/
	public double getMoveSpeed() {
		return moveSpeed;
	}
/**
* @public gets the x position of the player
**/
	public double getX() {
		return x;
	}
/**
* @public gets the y position of the player
**/
	public double getY() {
		return y;
	}
/**
* @public sets the x position of the player
* @param (x): uses this as the x position
**/
	public void setPlayerX(double x) {
		this.x = x;
	}
/**
* @public sets the y position of the player
* @param (y): uses this as the y position
**/
	public void setPlayerY(double y) {
		this.y = y;
	}
/**
* @public gets the health of the player
**/
	public double getHealth() {
		return health;
	}
/**
* @public makes the player take damage
**/
	public void takeDamage(double damage) {
		health -= damage;
	}
/**
* @public heals the player
**/
	public void addHP(int heal) {
		health += heal;
	}
/**
* @public gets the reload speed of the weapon the player owns
**/
	public int determineWeaponReload() {
		return bulletCarry.getReloadSpeed();
	}
/**
* @public gets what type of gun the player owns
**/
	public int getGunType() {
		return bulletCarry.getBulletType();
	}
/**
* @public changes the gun of the player
* @param (bulletType): what kind of gun the player got
* @param (upgrades): checks what upgrades the player got for the gun
**/
	public void replaceGun(int bulletType, int upgrades) {
		bulletCarry = new Bullets(bulletType, x + x/2, y + y/2, 0,false,false, upgrades);
	}
/**
* @public gets the type of bullet the gun uses
**/
	public Bullets getBullet() {
		return bulletCarry;
	}
/**
* @public gets coins of the player
**/
	//Coins Functions
	public int getCoins() {
		return coins;
	}
/**
* @public checks if the player is still alive
**/
	public boolean isAlive() {
		return alive;
	}
/**
* @public changes the player's alive status to dead
**/
	public void died() {
		alive = false;
	}
/**
* @public removes the total amount of coins the player has
* @param (deduction): how much coins needs to be removed
**/
	public void deductCoins(int deduction) {
		coins -= deduction;
	}
/**
* @public adds coins for the player
**/
	public void addCoins(int addition) {
		coins += addition;
		score += addition * 2;
	}
/**
* @public gets the width of the player
**/
	public int getWidth() {
		return width;
	}
/**
* @public gets the height of the player
**/
	public int getHeight() {
		return height;
	}
/**
* @public changes the angle of the player
* @param (deg): current angle
**/
	public void setRotation(double deg) {
		degrees = deg;
	}
/**
* @public changes the image sprite of the player
* @param (image): new image sprite
**/
	public void updateImage(ImageIcon image) {
		playerImage = image.getImage();
	}
/**
* @public draws the player onto the canvas
* @param (g2d): the drawing canvas
**/
	public void draw(Graphics2D g2d) {
		AffineTransform reset = new AffineTransform();
		g2d.rotate(Math.toRadians(degrees), x + 15, y + 15);
		g2d.drawImage(playerImage, (int) x, (int) y, 30, 30, null);
		g2d.setTransform(reset);
	}
}
