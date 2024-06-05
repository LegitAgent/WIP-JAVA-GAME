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
import java.awt.geom.*;

import javax.swing.ImageIcon;
/**
* Creates the class for the bullet. It manipulates where the bullet is, where it is going, and what type it is.
**/
public class Bullets {
    private String bulletName;
    private double rotation;
    // reload speed in miliseconds
    private int damage, reloadSpeed, width, height, bulletType;
    private double xVelocity, yVelocity, x , y;
    private Float magnitude;
    private boolean inverseX, inverseY, infinite = false;
    private int upgrades;

    Image startBulletNormImg = new ImageIcon("BasicBullet.png").getImage();
    Image rifleBulletNormImg = new ImageIcon("RifleBullet.png").getImage();
    Image bowBulletNormImg = new ImageIcon("BowBullet.png").getImage();
    Image sniperBulletNormImg = new ImageIcon("SniperBullet.png").getImage();
    Image slingBulletNormImg = new ImageIcon("SlingshotBullet.png").getImage();

    Image startBulletUpgImg = new ImageIcon("BasicBullet2.png").getImage();
    Image rifleBulletUpgImg = new ImageIcon("RifleBullet2.png").getImage();
    Image bowBulletUpgImg = new ImageIcon("BowBullet2.png").getImage();
    Image sniperBulletUpgImg = new ImageIcon("SniperBullet2.png").getImage();
    Image slingBulletUpgImg = new ImageIcon("SlingshotBullet2.png").getImage();

/**
* @constructor
* @param (bulletType): depending on the gun, will shoot the specific bullet type
* @param (x): x-axis position of the bullet
* @param (y): y-axis position of the bullet
* @param (slope): used to calculate the magnitude, as the slope increases it should not make the bullet faster
* @param (inverseX): checks what direction the bullet is shot
* @param (inverseY): checks what direction the bullet is shot
* @param (upgrades): gets the upgrade level of the bullet
**/
    public Bullets(int bulletType, double x, double y, double slope, boolean inverseX, boolean inverseY, int upgrades) {
	//helps make sure the bullet moves at a constant speed
        magnitude = (float) Math.sqrt(1 + slope * slope);
        if(magnitude.isInfinite()) {
            infinite = true;
        }
        this.inverseY = inverseY;
        this.inverseX = inverseX;
        this.bulletType = bulletType;
        // EXPANDABLE BULLET SYSTEM
        switch(bulletType) {
            case 1:
                this.x = x;
                this.y = y;
                bulletName = "startBullet";
                reloadSpeed = 800;
                damage = 10;
                width = 10;
                height = 5;
                //base velocity = 7
                calculateSpeed(7, slope, infinite);
                break;
            case 2:
                this.x = x;
                this.y = y;
                bulletName = "rifleBullet";
                reloadSpeed = 300;
                damage = 15;
                width = 15;
                height = 5;
                //base velocity = 8
                calculateSpeed(8, slope, infinite);
                break;
            case 3:
                this.x = x;
                this.y = y;
                bulletName = "bowBullet";
                reloadSpeed = 600;
                damage = 40;
                width = 30;
                height = 20;
                //base velocity = 12
                calculateSpeed(12, slope, infinite);
                break;
            case 4:
                this.x = x;
                this.y = y;
                bulletName = "sniperBullet";
                reloadSpeed = 3000;
                damage = 150;
                width = 15;
                height = 10;
                //base velocity = 15
                calculateSpeed(15, slope, infinite);
                break;
            case 5:
                this.x = x;
                this.y = y;
                bulletName = "slingBullet";
                reloadSpeed = 1000;
                damage = 30;
                width = 30;
                height = 30;
                //base velocity = 2
                calculateSpeed(2, slope, infinite);
                break;
        }
        this.upgrades = upgrades;
        if(upgrades > 0) {
            upgrades++;
            reloadSpeed /= upgrades;
            damage *= upgrades;
        }
    }
/**
* @public gives the name of the bullet type
**/
    public String getBulletName() {
        return bulletName;
    }
/**
* @public gives the damage that the bullet can do
**/
    public int getBulletDamage() {
        return damage;
    }
/**
* @public gets the speed at which the gun could reload
**/
    public int getReloadSpeed() {
        return reloadSpeed;
    }
/**
* @public gives the upgrades the gun has
**/
    public int amountUpgrades() {
        return upgrades;
    }
/**
* @public calculates the speed at which the bullet moves
* @param (speed): the movement speed of the bullet
* @param (slope): the angle
* @param (infinity)
**/
    public void calculateSpeed(int speed, double slope, boolean infinity) {
        if(infinity) {
            if(inverseY) {
                yVelocity = speed;
                xVelocity = 0;
            }
            else {
                yVelocity = -speed;
                xVelocity = 0;
            }
        }
        else {
            yVelocity = (speed / magnitude) * slope;
            xVelocity = speed / magnitude;
        }

    }
/**
* @public makes the bullet move
**/
    public void move() {
        if(inverseX) {
            x += xVelocity * -1;
        }
        else {
            x += xVelocity;
        }
        y += yVelocity;
    }
/**
* @public gives the x value of the bullet
**/
    public double getX() {
        return x;
    }
/**
* @public gives the y value of the bullet
**/
    public double getY() {
        return y;
    }
/**
* @public gives the width of the bullet
**/
    public int getWidth() {
        return width;
    }
/**
* @public gives the height of the bullet
**/
    public int getHeight() {
        return height;
    }
/**
* @public changes the x value of the bullet
**/
    public void setX(double x) {
        this.x = x;
    }
/**
* @public changes the y value of the bullet
**/  
    public void setY(double y) {
        this.y = y;
    }
/**
* @public gets the type of the bullet
**/
    public int getBulletType() {
        return bulletType;
    }
/**
* @public checks which name the bullet should be depending on what number
**/
    public String determineName(int type) {
        switch(type) {
            case 1:
                return "startBullet";
            case 2:
                return "rifleBullet";
            case 3:
                return "bowBullet";
            case 4:
                return "sniperBullet";
            default:
                return "slingBullet";
        }
    }

/**
* @public draws the bullet onto the screen
**/
    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        if(!inverseY && !inverseX) rotation = -Math.acos(1/magnitude);
        else if(inverseX && !inverseY) rotation = Math.acos(1/magnitude) + Math.toRadians(180);
        else if(inverseX && inverseY) rotation = -Math.acos(1/magnitude) + Math.toRadians(180);
        else rotation = Math.acos(1/magnitude);
        
        g2d.rotate(rotation, x + width/2, y + height/2);
        if(upgrades == 0) {
            switch(bulletName) {
                case "startBullet":
                    g2d.drawImage(startBulletNormImg,(int) x, (int) y, width, height, null);
                    break;
                case "rifleBullet":
                    g2d.drawImage(rifleBulletNormImg,(int) x, (int) y, width, height, null);
                    break;
                case "bowBullet":
                    g2d.drawImage(bowBulletNormImg,(int) x, (int) y, width, height, null);
                    break;
                case "sniperBullet":
                    g2d.drawImage(sniperBulletNormImg,(int) x, (int) y, width, height, null);
                    break;
                case "slingBullet":
                    g2d.drawImage(slingBulletNormImg,(int) x, (int) y, width, height, null);
                    break;
            }
        }
        else {
            switch(bulletName) {
                case "startBullet":
                    g2d.drawImage(startBulletUpgImg,(int) x, (int) y, width + upgrades * 3, height + upgrades * 3, null);
                    break;
                case "rifleBullet":
                    g2d.drawImage(rifleBulletUpgImg,(int) x, (int) y, width + upgrades * 3, height + upgrades * 3, null);
                    break;
                case "bowBullet":
                    g2d.drawImage(bowBulletUpgImg,(int) x, (int) y, width + upgrades * 3, height + upgrades * 3, null);
                    break;
                case "sniperBullet":
                    g2d.drawImage(sniperBulletUpgImg,(int) x, (int) y, width + upgrades * 3, height + upgrades * 3, null);
                    break;
                case "slingBullet":
                    g2d.drawImage(slingBulletUpgImg,(int) x, (int) y, width + upgrades * 3, height + upgrades * 3, null);
                    break;
            }
        }

        g2d.setTransform(reset);
    }
}
