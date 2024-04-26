
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
import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class Player {

	private double x, y, moveSpeed;
	private int width, height;
	private Image playerImage;
	private double degrees;
	private Bullets bulletCarry;

	public Player(double x, double y, int width, int height, double moveSpeed, ImageIcon playerImage, Bullets bullet) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.moveSpeed = moveSpeed;
		this.playerImage = playerImage.getImage();
		bulletCarry = bullet;
	}

	public void moveX(boolean neg) {
		if (neg) {
			x -= moveSpeed;
		} else {
			x += moveSpeed;
		}
	}

	public void moveY(boolean neg) {
		if (neg) {
			y -= moveSpeed;
		} else {
			y += moveSpeed;
		}
	}

	public void setMoveSpeed(double speed) {
		moveSpeed = speed;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setPlayerX(double x) {
		this.x = x;
	}

	public void setPlayerY(double y) {
		this.y = y;
	}

	public int determineWeaponReload() {
		return bulletCarry.getReloadSpeed();
	}

	// PLACEHOLDER, REMOVE WHEN NEEDED (HITBOX).
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	//
	public void setRotation(double deg) {
		degrees = deg;
	}

	public void updateImage(ImageIcon image) {
		playerImage = image.getImage();
	}

	public void draw(Graphics2D g2d) {
		AffineTransform reset = new AffineTransform();
		g2d.rotate(Math.toRadians(degrees), x + 15, y + 15);
		g2d.drawImage(playerImage, (int) x, (int) y, 30, 30, null);
		g2d.setTransform(reset);
	}
}
