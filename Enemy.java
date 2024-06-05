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
import javax.swing.ImageIcon;
/**
* @implements
**/
public interface Enemy {
    public double getHealth(); // returns health of enemy
    public double getSpeed(); // return speed of enemy
    public double getStartPosX(); // return starting x position
    public double getStartPosY(); // return starting y position
    public double getOriginalSpeed(); // returns original move speed
    public int getWidth(); // returns width
    public int getHeight(); // return height
    public int getDamage(); // return damage of enemy
    public String getEnemyName(); // returns enemy name
    public void move(); // moves enemy by its x and y coordinate
    public void takeDamage(int damage); // subtracts damage by the health
    public void setMoveSpeed(double moveSpeed); // sets enemy movespeed
    public void getPlayerCoordinates(double xP1, double yP1, double xP2, double yP2); //gets player coordinates
    public void updateImage(ImageIcon image);
    public void draw(Graphics2D g2d); //draws the enemy
}
