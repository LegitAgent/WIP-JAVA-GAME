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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
/**
* Creates the class for the fast zombie. It implements the enemy class.
**/
public class FastZombie implements Enemy {

    private int damage, width, height;
    private double speed, health, rotation;
    private double speedX, speedY;
    private String name;
    private double x, y;
    private boolean inverseX, inverseY, infinite;
    private Image zombieImage;
    private double originalSpeed;
/**
* @constructor
* @param (scaling): changes the hp of the enemy based on the wave number
* @param (x): initial position on the x-axis
* @param (y): initial position on the y-axis
**/
    public FastZombie(double scaling, double x, double y) {
        speed = 2;
        originalSpeed = speed;
        health = 5 * scaling;
        damage = 25;
        name = "Fast Zombie";
        width = 40;
        height = 40;
        this.x = x;
        this.y = y;
        zombieImage = new ImageIcon("FastZombieWalk1.png").getImage(); 
    }
/**
* @public gives the health of the enemy
**/
    @Override
    public double getHealth() {
        return health;
    }
/**
* @public gives the speed of the enemy
**/
    @Override
    public double getSpeed() {
        return speed;
    }
/**
* @public gives the damage of the enemy
**/
    @Override
    public int getDamage() {
        return damage;
    }
/**
* @public gives the width of the enemy
**/
    @Override
    public int getWidth() {
        return width;
    }
/**
* @public gives the height of the enemy
**/
    @Override
    public int getHeight() {
        return height;
    }
/**
* @public gives the x-axis starting position of the enemy
**/
    @Override
    public double getStartPosX() {
        return x;
    }
/**
* @public gives the y-axis starting position of the enemy
**/
    @Override
    public double getStartPosY() {
        return y;
    }
/**
* @public gives the name of the enemy
**/
    @Override
    public String getEnemyName() {
        return name;
    }
/**
* @public makes the enemy move
**/
    @Override
    public void move() {
        if(inverseX) {
            x += speedX * -1;
        }
        else {
            x += speedX;
        }
        y += speedY;
    }
/**
* @public lowers the health of the enemy
* @param (damage): the amount of damage the enemy takes
**/
    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
/**
* @public changes the movement speed of the enemy
* @param (changeSpeed): the new speed the enemy has
**/
    @Override
    public void setMoveSpeed(double changeSpeed) {
        speed = changeSpeed;
    }
/**
* @public gives the original speed of the enemy
**/
    @Override
    public double getOriginalSpeed() {
        return originalSpeed;
    }
/**
* @public the 'AI' that makes the zombie go after the player. it tracks the position of the player then moves towards the direction of the player
* @param (xPlayer1): gets the x-axis position of player 1
* @param (yPlayer1): gets the y-axis position of player 1
* @param (xPlayer2): gets the x-axis position of player 2
* @param (yPlayer2): gets the y-axis position of player 2
**/
    @Override
    public void getPlayerCoordinates(double xPlayer1, double yPlayer1, double xPlayer2, double yPlayer2) {
        // similar with bullets
        infinite = false;
        double slope;
        Float magnitude;
        // use distance formula, calculate which player is closer to the zombie.
        double player1Distance = Math.sqrt(Math.pow((xPlayer1 - x), 2) + Math.pow(yPlayer1 - y, 2));
        double player2Distance = Math.sqrt(Math.pow((xPlayer2 - x), 2) + Math.pow(yPlayer2 - y, 2));

        if(player1Distance < player2Distance)  {
            slope = (yPlayer1 - y) / (xPlayer1 - x);
            magnitude = (float) Math.sqrt(1 + slope * slope);
    
            if(xPlayer1 < x) {
                slope *= -1;
                inverseX = true;
            }
            else {
                inverseX = false;
            }
            if(yPlayer1 > y) {
                inverseY = true;
            }
            else {
                inverseY = false;
            }
        }
        else {
            slope = (yPlayer2 - y) / (xPlayer2 - x);
            magnitude = (float) Math.sqrt(1 + slope * slope);
    
            if(xPlayer2 < x) {
                slope *= -1;
                inverseX = true;
            }
            else {
                inverseX = false;
            }
            if(yPlayer2 > y) {
                inverseY = true;
            }
            else {
                inverseY = false;
            }
        }

        if(magnitude.isInfinite()) {
            infinite = true;
        }

        if(infinite) {
            if(inverseY) {
                speedY = speed;
                speedX = 0;
            }
            else {
                speedY = -speed;
                speedX = 0;
            }
        }
        else {
            speedY = (speed / magnitude) * slope;
            speedX = speed / magnitude;
        }

        // same rotation with bullet firing
        int degrees = (int) Math.toDegrees(Math.acos(1/magnitude));
        if(!inverseX && !inverseY) rotation = 90 - degrees; //Q1
        else if(inverseX && !inverseY) rotation = degrees - 90; // Q2
        else if(inverseX && inverseY) rotation = 270 - degrees; //Q3
        else if(!inverseX && inverseY) rotation = degrees - 270; //Q4
    }
/**
* @public changes the image file of the enemy
* @param (image): the image file
**/
    @Override
    public void updateImage(ImageIcon image) {
        zombieImage = image.getImage();
    }
/**
* @public draws the enemy on the canvas
* @param (g2d): the drawing canvas
**/
    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        g2d.rotate(Math.toRadians(rotation), x + 15, y + 15);
        g2d.drawImage(zombieImage, (int) x, (int) y, width, height, null);
        g2d.setTransform(reset);
    }
}
